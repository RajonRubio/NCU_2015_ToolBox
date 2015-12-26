package CDC;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import Protocols.*;
import SETTINGS.*;
import TCPSM.TCPSM;

public class CDC {
	private TCPSM tcpsm;
	private int maximum = 4;
	private BasicBlock [][] map = new BasicBlock [40][100];
	private ArrayList<Character> characters = new ArrayList<Character>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
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
		assert clientnumber >= 0 && clientnumber <= 3: "clientnumber不再範圍裡";
		if(characters.size() == maximum)
		{
			System.out.println("玩家已滿");
			return;
		}
		characters.add(new Character(clientnumber, name));
		System.out.println("新增一位玩家" + name);
	}
	
	public void removeVirtualCharacter(int clientnumber) {
		assert clientnumber >= 0 && clientnumber <= 3: "clientnumber不再範圍裡";
		if(characters.size() == 0)
		{
			System.out.println("沒有玩家可以刪除");
			return;
		}
		else if(searchClientNumber(clientnumber) == -1)
		{
			System.out.println("找不到此玩家");
			return;
		}
		characters.remove(searchClientNumber(clientnumber));
		System.out.println("刪除一位玩家");
	}
	
	public void setTeam(int clientnumber, Team team) {
		assert clientnumber >= 0 && clientnumber <= 3: "clientnumber不再範圍裡";
		if(searchClientNumber(clientnumber) == -1)
		{
			System.out.println("找不到此玩家");
			return;
		}
		characters.get(searchClientNumber(clientnumber)).setTeam(team);
	}
	
	public void setRole(int clientnumber, Role role) {
		assert clientnumber >= 0 && clientnumber <= 3: "clientnumber不再範圍裡";
		if(searchClientNumber(clientnumber) == -1)
		{
			System.out.println("找不到此玩家");
			return;
		}
		characters.get(searchClientNumber(clientnumber)).setRole(role);
		characters.get(searchClientNumber(clientnumber)).setRoleConstant();
	}
	
	public void setReady(int clientnumber) {
		assert clientnumber >= 0 && clientnumber <= 3: "clientnumber不再範圍裡";
		if(searchClientNumber(clientnumber) == -1)
		{
			System.out.println("找不到此玩家");
			return;
		}
		characters.get(searchClientNumber(clientnumber)).setReady(true);
		if(checkReady())
		{
			GameStart();
			System.out.println("遊戲開始");
		}
	}
	
	public boolean checkReady() {
		if(characters.size() != maximum)
		{
			System.out.println("人數不夠");
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
		switch(action)
		{
			case UP_PRESS:
				location.setLocation(x-movespeed,y);
				characters.get(searchClientNumber(clientnumber)).setLocation(location);
				break;
			case DOWN_PRESS:
				location.setLocation(x+movespeed,y);
				characters.get(searchClientNumber(clientnumber)).setLocation(location);
				break;
			case RIGHT_PRESS:
				location.setLocation(x,y+movespeed);
				characters.get(searchClientNumber(clientnumber)).setLocation(location);
				break;
			case LEFT_PRESS:
				location.setLocation(x,y-movespeed);
				characters.get(searchClientNumber(clientnumber)).setLocation(location);
				break;
			default:
				break;
		}
	}
	
	public void BulletsMove() {
		Point2D location;
		double bulletspeed;
		Point2D angle;
		for(int i=0;i<bullets.size();i++)
		{
			location = bullets.get(i).getLocation();
			bulletspeed = bullets.get(i).getBulletspeed();
			angle = bullets.get(i).getAngle();
			location.setLocation(bulletspeed * angle.getX(), bulletspeed * angle.getY());
			bullets.get(i).setLocation(location);
		}
	}
	
	public void checkCollision() {
		
	}
	
	public void addBullet(int clientnumber, Point2D angle) {
		if(characters.get(searchClientNumber(clientnumber)).getCanAttack())
		{
			String name = characters.get(clientnumber).getName();
			Role role = characters.get(clientnumber).getRole();
			Team team = characters.get(clientnumber).getTeam();
			Point2D location = characters.get(clientnumber).getLocation();
			Skill skill = Skill.NULL;
			switch(role)
			{
				case Archer:
					bullets.add(new Bullet(name, team, location, 2, skill.DISPERSION, angle, 1, 15));
					break;
				case Marines:
					bullets.add(new Bullet(name, team, location, 1.5, skill, angle, 3, 20));
					break;
				case Cannon:
					bullets.add(new Bullet(name, team, location, 1, skill.CHAOS, angle, 3, 5));
					break;
				case Wizard:
					bullets.add(new Bullet(name, team, location, 2.5, skill, angle, 3, 10));
					break;
			}
			characters.get(searchClientNumber(clientnumber)).setCanAttack(false);
		}
	}
	
	public void Attack(int clientnumber1, int clientnumber2, int attack, Skill skill) {
		boolean debuff[] = {false,false};
		int HP = characters.get(searchClientNumber(clientnumber2)).getNowHP();
		characters.get(searchClientNumber(clientnumber2)).setNowHP(HP - attack);
		switch(skill)
		{
			case CHAOS:
				debuff[1] = true;
				characters.get(searchClientNumber(clientnumber2)).setDeBuff(debuff);
				break;
			case FIRE:
				debuff[2] = true;
				characters.get(searchClientNumber(clientnumber2)).setDeBuff(debuff);
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
	
	public double Random() {
		Random random = new Random();
		return random.nextDouble();
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
