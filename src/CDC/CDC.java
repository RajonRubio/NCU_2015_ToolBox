package CDC;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;

import Protocols.*;
import SETTINGS.*;
import TCPSM.TCPSM;

public class CDC {
	private TCPSM tcpsm;
	private int maximum = 4;
	private BasicBlock [][] map = new BasicBlock [40][100];
	private ArrayList<Character> characters = new ArrayList<Character>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<Integer> changebullet = new ArrayList<Integer>();
	private ArrayList<WoodBox> changebox = new ArrayList<WoodBox>();
	
	public CDC() {
		tcpsm = new TCPSM(this);
		loadmap();
	}
	
	public void loadmap() {
		try {
			FileReader fr = new FileReader("map.txt"); 
			BufferedReader br = new BufferedReader(fr);
			String str, tempstr;
			String[] tempArray= new String[3];
			ArrayList myList = new ArrayList();
			int i = 0;
			while((str = br.readLine())!= null)
			{
				tempstr = str; 
				tempArray = tempstr.split("\\s");
			    for(i=0;i<tempArray.length;i++)
			    {          
			    	myList.add(tempArray[i]);
			    }
			 }		   
			 int k = myList.size()/3;
			 int count = 0;
			 double[][] content = new double[k][3];   
			 for(int x=0;x<myList.size()/3;x++)
			 {
				 for(int y=0;y<3;y++)
			     {
					 content[x][y] = Double.parseDouble((String)myList.get(count));
			         count++;
			     }
			 }
			 br.close();
			 constructMap(content);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void constructMap(double content[][]) {
		boolean istype=true;
		for(int y=0;y<40;y++)
		{
			for(int x=0;x<100;x++)
			{
				map[y][x] = new BasicBlock();
			}
		}
		for(int k=0;k<content.length;k++)
		{
			if(istype == true)
			{
				if(content[k][0] == 1)
				{
					map[(int)content[k][2]][(int)content[k][1]].setType(BasicBlock.woodbox);
					map[(int)content[k][2]][(int)content[k][1]].setTouchable(false);
				}	
				else if(content[k][0] == 2)
				{
					map[(int)content[k][2]][(int)content[k][1]].setType(BasicBlock.rockbox);	
					map[(int)content[k][2]][(int)content[k][1]].setTouchable(false);
				}
			}
		}
	}
	
	public int searchClientNumber(int clientnumber) {
		for(int i=0;i<characters.size();i++)
		{
			if(characters.get(i).getClientNumber() == clientnumber)
			{
				return characters.get(i).getClientNumber();
			}
		}
		return -1;
	}
	
	public boolean checkName(String name) {
		if(characters.size() == 0)
		{
			return true;
		}
		for(int i=0;i<characters.size();i++)
		{
			if(characters.get(i).getName() == name)
			{
				return false;
			}
		}
		return true;
	}
	
	public void addVirtualCharacter(int clientnumber, String name) {
		assert clientnumber >= 0 && clientnumber <= 3: "clientnumber is not in range";
		if(characters.size() == maximum)
		{
			System.out.println("characters are full");
			return;
		}
		characters.add(new Character(clientnumber, name));
		System.out.println("add" + name);
	}
	
	public void removeVirtualCharacter(int clientnumber) {
		assert clientnumber >= 0 && clientnumber <= 3: "clientnumber is not in range";
		if(characters.size() == 0)
		{
			System.out.println("no player can be deleted");
			return;
		}
		else if(searchClientNumber(clientnumber) == -1)
		{
			System.out.println("do not have this clientnumber");
			return;
		}
		characters.remove(searchClientNumber(clientnumber));
		System.out.println("remove");
	}
	
	public void setTeam(int clientnumber, Team team) {
		assert clientnumber >= 0 && clientnumber <= 3: "clientnumber is not in range";
		if(searchClientNumber(clientnumber) == -1)
		{
			System.out.println("do not have this clientnumber");
			return;
		}
		characters.get(searchClientNumber(clientnumber)).setTeam(team);
	}
	
	public void setRole(int clientnumber, Role role) {
		assert clientnumber >= 0 && clientnumber <= 3: "clientnumber is not in range";
		if(searchClientNumber(clientnumber) == -1)
		{
			System.out.println("do not have this clientnumber");
			return;
		}
		characters.get(searchClientNumber(clientnumber)).setRole(role);
		characters.get(searchClientNumber(clientnumber)).setRoleConstant();
	}
	
	public void setReady(int clientnumber) {
		assert clientnumber >= 0 && clientnumber <= 3: "clientnumber is not in range";
		if(searchClientNumber(clientnumber) == -1)
		{
			System.out.println("do not have this clientnumber");
			return;
		}
		characters.get(searchClientNumber(clientnumber)).setReady(true);
		if(checkReady())
		{
			GameStart();
			System.out.println("game start");
		}
	}
	
	public boolean checkReady() {
		if(characters.size() != maximum)
		{
			System.out.println("have characters are not ready");
			return false;
		}
		else
		{
			for(int i=0;i<maximum;i++)
			{
				if(characters.get(i).getReady() == false)
				{
					return false;
				}
			}
		}
		return true;
	}
	
	public void GameStart() {
		for(int i=0;i<maximum;i++)
		{
			RandomLocation(i);
		}
		Timer timer = new Timer();
		timer.schedule(new GameOver(), 2*60*1000);
		timer.schedule(new BulletsMove(), new Date(), 1000/60);
		try {
			tcpsm.gameStart();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void RandomLocation(int number) {
		Point2D location = null;
		boolean check = true;
		while(check)
		{
			location.setLocation(Random()*5000, Random()*2000);
			for(int i=0;i<characters.size();i++)
			{
				if(location != characters.get(i).getLocation()) {
					check = false;
				}	
			}
			if(map[(int)location.getY()/50][(int)location.getX()/50].getType() == 0) {
				check = false;
			}
		}
		characters.get(searchClientNumber(number)).setLocation(location);
	}
	
	public void CharacterMove(int clientnumber, ServerAction action) {
		Point2D location = characters.get(searchClientNumber(clientnumber)).getLocation();
		double x = location.getX(), y = location.getX();
		double movespeed = characters.get(searchClientNumber(clientnumber)).getMoveSpeed();
		boolean [] debuff = {true,false};
		if(characters.get(searchClientNumber(clientnumber)).getDeBuff() == debuff)
		{
			switch(action)
			{
				case UP_PRESS:
					location.setLocation(x+movespeed,y);
					break;
				case DOWN_PRESS:
					location.setLocation(x-movespeed,y);
					break;
				case RIGHT_PRESS:
					location.setLocation(x,y-movespeed);
					break;
				case LEFT_PRESS:
					location.setLocation(x,y+movespeed);
					break;
				default:
					break;
			}
		}
		else
		{
			switch(action)
			{
				case UP_PRESS:
					location.setLocation(x-movespeed,y);
					break;
				case DOWN_PRESS:
					location.setLocation(x+movespeed,y);
					break;
				case RIGHT_PRESS:
					location.setLocation(x,y+movespeed);
					break;
				case LEFT_PRESS:
					location.setLocation(x,y-movespeed);
					break;
				default:
					break;
			}
		}
		if(map[(int)location.getY()/50][(int)location.getX()/50].getType() == 0)
		{
			characters.get(searchClientNumber(clientnumber)).setLocation(location);
		}
	}
	
	public void checkCollision() {
		Point2D bullet_location;
		Point2D character_location;
		for(int i=0;i<bullets.size();i++)
		{
			bullet_location = bullets.get(i).getLocation();
			for(int j=0;j<maximum;j++)
			{
				character_location = characters.get(j).getLocation();
				if((bullet_location.getX()+30 == character_location.getX() || bullet_location.getX()-30 == character_location.getX()) && (bullet_location.getY()+30 == character_location.getY() || bullet_location.getY()-30 == character_location.getY()))
				{
					Attack(bullets.get(i).getClientNumber(), characters.get(j).getClientNumber(), bullets.get(i).getAttack(), bullets.get(i).getSkill());
					if(bullets.get(i).getSkill() != Skill.DISPERSION)
					{
						changebullet.add(i);
					}
				}
				else if(map[(int)bullet_location.getY()][(int)bullet_location.getX()/50].getType() == 0)
				{
					ChangeAngle(i);
				}
				else if(map[(int)bullet_location.getY()][(int)bullet_location.getX()/50].getType() == 1)
				{
					map[(int)bullet_location.getY()][(int)bullet_location.getX()/50].setType(2);
					changebox.add(new WoodBox(2, (int)bullet_location.getX()/50, (int)bullet_location.getY()));
					ChangeAngle(i);
					ChangeCollisiontime(i);
				}
				else if(map[(int)bullet_location.getY()][(int)bullet_location.getX()/50].getType() == 2)
				{
					map[(int)bullet_location.getY()][(int)bullet_location.getX()/50].setType(3);
					changebox.add(new WoodBox(2, (int)bullet_location.getX()/50, (int)bullet_location.getY()));
					ChangeAngle(i);
					ChangeCollisiontime(i);
				}
				else if(map[(int)bullet_location.getY()][(int)bullet_location.getX()/50].getType() == 3)
				{
					map[(int)bullet_location.getY()][(int)bullet_location.getX()/50].setType(0);
					changebox.add(new WoodBox(2, (int)bullet_location.getX()/50, (int)bullet_location.getY()));
					ChangeAngle(i);
					ChangeCollisiontime(i);
				}
			}
		}
		for(int i=0;i<changebullet.size();i++)
		{
			bullets.remove(changebullet.get(i));
		}
	}
	
	public void ChangeAngle(int i) {
		Point2D location = bullets.get(i).getLocation();
		Point2D angle = bullets.get(i).getAngle();
		double bulletspeed = bullets.get(i).getBulletspeed();
		double pre_X = location.getX() - bulletspeed * angle.getX();
		double pre_Y = location.getY() - bulletspeed * angle.getY();
		if(location.getX() > pre_X)
		{
			
		}
	}
	
	public void ChangeCollisiontime(int i) {
		int c = bullets.get(i).getCollisiontime();
		if(c == 0)
		{
			changebullet.add(i);
		}
		else if(c <= 4 && c >= 1)
		{
			bullets.get(i).setCollisiontime(c-1);
		}
	}
	
	public void addBullet(int clientnumber, Point2D angle) {
		if(characters.get(searchClientNumber(clientnumber)).getCanAttack())
		{
			Role role = characters.get(clientnumber).getRole();
			Team team = characters.get(clientnumber).getTeam();
			Point2D location = characters.get(clientnumber).getLocation();
			Skill skill = Skill.NULL;
			switch(role)
			{
				case Archer:
					bullets.add(new Bullet(clientnumber, team, location, 2, skill.DISPERSION, angle, 1, 15));
					break;
				case Marines:
					bullets.add(new Bullet(clientnumber, team, location, 1.5, skill, angle, 3, 20));
					break;
				case Cannon:
					bullets.add(new Bullet(clientnumber, team, location, 1, skill.CHAOS, angle, 3, 5));
					break;
				case Wizard:
					bullets.add(new Bullet(clientnumber, team, location, 2.5, skill, angle, 3, 10));
					break;
			}
			characters.get(searchClientNumber(clientnumber)).setCanAttack(false);
		}
	}
	
	public void Attack(int clientnumber1, int clientnumber2, int attack, Skill skill) {
		boolean debuff[] = {false,false};
		int HP = characters.get(searchClientNumber(clientnumber2)).getNowHP();
		characters.get(searchClientNumber(clientnumber2)).setNowHP(HP - attack);
		Timer timer = new Timer();
		switch(skill)
		{
			case CHAOS:
				debuff[1] = true;
				characters.get(searchClientNumber(clientnumber2)).setDeBuff(debuff);
				timer.schedule(new DeleteDeBuff(clientnumber2, 1), 60*1000);
				break;
			case FIRE:
				debuff[2] = true;
				characters.get(searchClientNumber(clientnumber2)).setDeBuff(debuff);
				timer.schedule(new DeleteDeBuff(clientnumber2, 2), 60*1000);
				break;
			default:
				break;
		}
		if(HP == 0)
		{
			KillAndDead(clientnumber1, clientnumber2);
		}
	}
	
	public void KillAndDead(int clientnumber1, int clientnumber2) {
		characters.get(searchClientNumber(clientnumber1)).addKill();
		characters.get(searchClientNumber(clientnumber2)).addDead();
		Timer timer = new Timer();
		timer.schedule(new ResurrectionRandomLoction(clientnumber2), 60000);
	}
	
	public double Random() {
		Random random = new Random();
		return random.nextDouble();
	}
	
	public void ClearChangeBox() {
		changebox.clear();
	}
	
	public class BulletsMove extends TimerTask{
		Point2D location;
		double bulletspeed;
		Point2D angle;
		public void run() {
			if(bullets.size() == 0)
			{
				return;
			}
			for(int i=0;i<bullets.size();i++)
			{
				location = bullets.get(i).getLocation();
				bulletspeed = bullets.get(i).getBulletspeed();
				angle = bullets.get(i).getAngle();
				location.setLocation(location.getX() + bulletspeed * angle.getX(), location.getY() + bulletspeed * angle.getY());
				bullets.get(i).setLocation(location);
			}
			checkCollision();		
		}
	}
	
	public class DeleteDeBuff extends TimerTask {
		int clientnumber, i;
		boolean [] debuff;
		public DeleteDeBuff(int clientnumber, int i) {
			this.clientnumber = clientnumber;
			this.i = i;
		}
		
		public void run() {
			debuff = characters.get(searchClientNumber(clientnumber)).getDeBuff();
			debuff[i] = false;
			characters.get(searchClientNumber(clientnumber)).setDeBuff(debuff);
		}
	}
	
	public class ResurrectionRandomLoction extends TimerTask {
		int clientnumber;
		public ResurrectionRandomLoction(int i) {
			clientnumber = i;
		}
		
		public void run()  {
			RandomLocation(clientnumber);
		}
	}
	
	public class GameOver extends TimerTask {
		public void run() {
			try {
				tcpsm.gameOver();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<Character> getCharacter() {
		return characters;
	}
	
	public ArrayList<Bullet> getBullets() {
		return bullets;
	}
	
	public ArrayList<WoodBox> getWoodBox() {
		return changebox;
	}
}