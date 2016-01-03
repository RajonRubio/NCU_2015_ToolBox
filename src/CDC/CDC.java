package CDC;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import Protocols.*;
import Protocols.CharacterState.Person;
import Protocols.TeamState.Member;
import SETTINGS.*;
import TCPSM.TCPSM;
import UDPBC.UDPBC;

public class CDC {
	private TCPSM tcpsm;
	private UDPBC udpbc;
	private int maximum = 4;
	private int gameTime = 120;
	private BasicBlock [][] map = new BasicBlock [40][100];
	public  TeamState teamstate;
	private ArrayList<Character> characters = new ArrayList<Character>();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<Integer> changebullet = new ArrayList<Integer>();
	private ArrayList<WoodBox> changebox = new ArrayList<WoodBox>();
	private boolean test;
	public CDC(TCPSM tcpsm) {
		this.tcpsm = tcpsm;
		this.tcpsm.initServer();
		teamstate = new TeamState();
		loadmap();
	}
	
	public void setUDPBC(UDPBC udpbc) {
		this.udpbc = udpbc;
	}
	
	public void loadmap() {
		try {
			FileReader fr = new FileReader("mapdata/map.txt"); 
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
				return i;
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
			if(characters.get(i).getName().equals(name))
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
		switch(team)
		{
			case BLUE:
				if(teamstate.blue.size() == maximum/2)
				{
					return;
				}
				else
				{
					characters.get(searchClientNumber(clientnumber)).setTeam(team);
					teamstate.blue.add(teamstate.new Member(characters.get(searchClientNumber(clientnumber)).getName(), characters.get(searchClientNumber(clientnumber)).getRole(), characters.get(searchClientNumber(clientnumber)).getReady()));
					for(int i = 0; i < teamstate.red.size(); i++)
					{
						Member mem = teamstate.red.get(i);
						if(mem.name == characters.get(searchClientNumber(clientnumber)).getName())
						{
							teamstate.red.remove(i);
							break;
						}
					}
				}
				break;
			case RED:
				if(teamstate.red.size() == maximum/2)
				{
					return;
				}
				else
				{
					characters.get(searchClientNumber(clientnumber)).setTeam(team);
					teamstate.red.add(teamstate.new Member(characters.get(searchClientNumber(clientnumber)).getName(), characters.get(searchClientNumber(clientnumber)).getRole(), characters.get(searchClientNumber(clientnumber)).getReady()));
					for(int i = 0; i < teamstate.blue.size(); i++)
					{
						Member mem = teamstate.blue.get(i);
						if(mem.name == characters.get(searchClientNumber(clientnumber)).getName())
						{
							teamstate.blue.remove(i);
							break;
						}
					}
				}
				break;
		}
		tcpsm.teamStateBroadcast(teamstate);
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
		for(int i=0;i<teamstate.blue.size();i++)
		{
			if(characters.get(searchClientNumber(clientnumber)).getName() == teamstate.blue.get(i).name)
			{
				teamstate.blue.get(i).role = role;
			}
		}
		for(int i=0;i<teamstate.red.size();i++)
		{
			if(characters.get(searchClientNumber(clientnumber)).getName() == teamstate.red.get(i).name)
			{
				teamstate.red.get(i).role = role;
			}
		}
		tcpsm.teamStateBroadcast(teamstate);
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
		CharacterState cs = new CharacterState();
		for(int i=0;i<characters.size();i++)
		{
			Person p = cs.new Person(characters.get(i).getClientNumber(), characters.get(i).getName(), characters.get(i).getTeam(), characters.get(i).getRole(), characters.get(i).getLocation());
			cs.player.add(p);
		} 
		Timer timer = new Timer();
		timer.schedule(new GameOver(), new Date(), 1000);
		timer.schedule(new BulletsMove(), new Date(), 16);
		try {
			tcpsm.gameStart(cs);
			udpbc.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void RandomLocation(int number) {
		Point2D.Double location = new Point2D.Double();
		boolean check = true;
		while(check)
		{
			location.setLocation(Random()*3850+600, Random()*950+500);
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
		characters.get(number).setLocation(location);
	}
	
	public void CharacterMove(int clientnumber, ServerAction action) {
		System.out.println("CDC:" + action);
		switch(action)
		{
			case UP_PRESS:
				characters.get(searchClientNumber(clientnumber)).setStatus(Status.UP);
				characters.get(searchClientNumber(clientnumber)).TimerResume(map);
				break;
			case DOWN_PRESS:
				characters.get(searchClientNumber(clientnumber)).setStatus(Status.DOWN);
				characters.get(searchClientNumber(clientnumber)).TimerResume(map);
				break;
			case RIGHT_PRESS:
				characters.get(searchClientNumber(clientnumber)).setStatus(Status.RIGHT);
				characters.get(searchClientNumber(clientnumber)).TimerResume(map);
				break;
			case LEFT_PRESS:
				characters.get(searchClientNumber(clientnumber)).setStatus(Status.LEFT);
				characters.get(searchClientNumber(clientnumber)).TimerResume(map);
				break;
			case STANDING:
				Status status = characters.get(searchClientNumber(clientnumber)).getState();
				switch(status)
				{
					case UP:
						characters.get(searchClientNumber(clientnumber)).setStatus(Status.UP_STOP);
						break;
					case DOWN:
						characters.get(searchClientNumber(clientnumber)).setStatus(Status.DOWN_STOP);
						break;
					case RIGHT:
						characters.get(searchClientNumber(clientnumber)).setStatus(Status.RIGHT_STOP);
						break;
					case LEFT:
						characters.get(searchClientNumber(clientnumber)).setStatus(Status.LEFT_STOP);
						break;
				}
				characters.get(searchClientNumber(clientnumber)).TimerPause();
				break;
		}
	}
	
	public int checkCollision(int i) {
		double b_x = bullets.get(i).getLocation().getX();
		double b_y = bullets.get(i).getLocation().getY();
		double a_x = bullets.get(i).getAngle().getX();
		double a_y = bullets.get(i).getAngle().getY();
		double s = bullets.get(i).getBulletspeed();
		for(int j=0;j<maximum;j++)
		{
			double c_x = characters.get(j).getLocation().getX();
			double c_y = characters.get(j).getLocation().getY();
			if((b_x+s*a_x+30 >= c_x && b_x+s*a_x+30 <= c_x+25) || (b_y+s*a_y+30 >= c_y && b_y+s*a_y+30 <= c_y+25))
			{
				if(characters.get(j).getTeam() == bullets.get(i).getTeam())
				{
					Attack(bullets.get(i).getClientNumber(), characters.get(i).getClientNumber(), bullets.get(i).getAttack(), bullets.get(i).getSkill());
					if(bullets.get(i).getSkill() == Skill.PENETRATE)
					{
						return -1;
					}
					if(bullets.get(i).getSkill() == Skill.DISPERSION)
					{
						return 1;
					}
					return 0;
				}
			}
		}
		System.out.println((int)((b_x+s*a_x+30)-480)/50 +"  "+(int)((b_y)-360)/50);
		int type = map[(int)((b_y)-360)/50][(int)((b_x+s*a_x+30)-480)/50].getType();
		if(type == 1 || type == 2 || type ==3)
		{
			ChangeCollisiontime(i);
			return 2;
		}
		type = map[(int)((b_y+s*a_y+30)-360)/50][(int)((b_x)-480)/50].getType();
		if(type == 1 || type == 2 || type ==3)
		{
			ChangeCollisiontime(i);
			return 3;
		}
		return -1;
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
	
	public void addBullet(int clientnumber, Point2D.Double angle) {
		System.out.println("NO."+clientnumber+"add a bullet");
		if(characters.get(searchClientNumber(clientnumber)).getCanAttack())
		{
			Role role = characters.get(searchClientNumber(clientnumber)).getRole();
			Team team = characters.get(searchClientNumber(clientnumber)).getTeam();
			double temp = Math.sqrt(angle.getX() * angle.getX() + angle.getY() * angle.getY());
			angle.setLocation(angle.getX()/temp, angle.getY()/temp);
			Point2D.Double location = characters.get(searchClientNumber(clientnumber)).getLocation();
			System.out.println("X:"+location.getX()+"Y:"+location.getY());
			Skill skill = Skill.NULL;
			switch(role)
			{
				case Archer:
					bullets.add(new Bullet(clientnumber, team, role, location, 0.01, skill.DISPERSION, angle, 1, 15));
					break;
				case Marines:
					bullets.add(new Bullet(clientnumber, team, role, location, 1.5, skill, angle, 3, 20));
					break;
				case Cannon:
					bullets.add(new Bullet(clientnumber, team, role, location, 1, skill.CHAOS, angle, 3, 5));
					break;
				case Wizard:
					bullets.add(new Bullet(clientnumber, team, role, location, 2.5, skill, angle, 3, 10));
					break;
			}
			characters.get(searchClientNumber(clientnumber)).setCanAttack(false);
		}
		System.out.println(bullets.size());
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
		characters.get(searchClientNumber(clientnumber2)).setTime(60);
		try {
			tcpsm.killBroadcast(characters.get(searchClientNumber(clientnumber1)).getName(), characters.get(searchClientNumber(clientnumber2)).getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Timer timer = new Timer();
		timer.schedule(new Resurrection(clientnumber2), new Date(), 1000);
	}
	
	public double Random() {
		Random random = new Random();
		return random.nextDouble();
	}
	
	public void ClearChangeBox() {
		changebox.clear();
	}
	
	public class BulletsMove extends TimerTask{
		Point2D.Double location;
		double bulletspeed;
		Point2D.Double angle;
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
//				int type = checkCollision(i);
//				switch(type)
//				{
//					case -1:
//						break;
//					case 0:
//						changebullet.add(i);
//						break;
//					case 1:
//						changebullet.add(i);
//						for(int j=0;j<=4;j++)
//						{
//							double rx = (angle.getX() * Math.cos(45)) - (angle.getY() * Math.sin(45));
//						    double ry = (angle.getX() * Math.sin(45)) + (angle.getY() * Math.cos(45));
//						    angle.setLocation(rx, ry);
//							bullets.add(new Bullet(bullets.get(i).getClientNumber(), bullets.get(i).getTeam(), bullets.get(i).getRole(), location, bulletspeed, Skill.NULL, angle, 1, bullets.get(i).getAttack()));
//						}			
//						break;
//					case 2:
//						angle.setLocation(-angle.getX(), angle.getY());
//						break;
//					case 3:
//						angle.setLocation(angle.getX(), -angle.getY());
//						break;
//				}
				location.setLocation(location.getX()+bulletspeed*angle.getX(), location.getY()+bulletspeed*angle.getY());
				//System.out.println("X:"+location.getX()+"Y:"+location.getY());
				bullets.get(i).setLocation(location);
			}
//			for(int i=0;i<changebullet.size();i++)
//			{
//				bullets.remove(changebullet.get(i));
//			}
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
	
	public class Resurrection extends TimerTask {
		int clientnumber;
		public Resurrection(int i) {
			clientnumber = i;
		}
		
		public void run()  {
			int time = characters.get(searchClientNumber(clientnumber)).getTime();
			if(time != 0)
			{
				characters.get(searchClientNumber(clientnumber)).setTime(time-1);
			}
			else
			{
				RandomLocation(clientnumber);
			}
		}
	}
	
	public class GameOver extends TimerTask {
		public void run() {
			if(gameTime != 0)
			{
				gameTime -= 1;
				System.out.println(gameTime);
			}
			else
			{
				this.cancel();
				try {
					System.out.println("GG");
					tcpsm.gameOver();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public ArrayList<Protocols.Character> getCharacter() {
		ArrayList<Protocols.Character> changecharacters = new ArrayList<Protocols.Character>();
		for(int i=0;i<characters.size();i++)
		{
			int clientno = characters.get(i).getClientNumber();
			Status status = characters.get(i).getState();
			int HP = characters.get(i).getNowHP();
			Point2D.Double location = characters.get(i).getLocation();
			int time = characters.get(i).getTime();
			boolean [] debuff = characters.get(i).getDeBuff();
			int kill = characters.get(i).getKill();
			int dead = characters.get(i).getDead();
			changecharacters.add(new Protocols.Character(clientno, status, HP, location, debuff, kill, dead));
		}
		return changecharacters;
	}
	
	public ArrayList<Protocols.BulletT> getBullets() {
		ArrayList<Protocols.BulletT> changebullets = new ArrayList<Protocols.BulletT>();
		for(int i=0;i<bullets.size();i++)
		{
			Team team = bullets.get(i).getTeam();
			Role role = bullets.get(i).getRole();
			Point2D.Double location = bullets.get(i).getLocation();
			changebullets.add(new Protocols.BulletT(team, role, location));
		}
		return changebullets;
	}
	
	public ArrayList<WoodBox> getWoodBox() {
		return changebox;
	}
}