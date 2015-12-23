package CDC;

import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import CDC.Character.Resurrection;
import Protocols.*;
import SETTINGS.*;
import TCPSM.TCPSM;

public class CDC {
	private Character [] character = new Character [4];
	private int characternumber = 0;
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private TCPSM tcpsm;
	private BasicBlock [][] map; 
	
	public CDC() {
		tcpsm = new TCPSM();
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
	
	void constructMap(double content[][]) {
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
	
	public boolean checkName(String name) {
		for(int i=0;i<=characternumber;i++)
		{
			if(character[i].getName() == name)
			{
				return false;
			}
		}
		return true;
	}
	
	public void addVirtualCharacter(int clientnumber, String name) {
		assert clientnumber >= 0 && clientnumber <= 3: "clientnumber不再範圍裡";
		character[clientnumber] = new Character(clientnumber, name);
		characternumber++;
	}
	
	public void removeVirtualCharacter(int clientnumber) {
		assert clientnumber >= 0 && clientnumber <= 3: "clientnumber不再範圍裡";
		character[clientnumber] = null;
		characternumber--;
	}
	
	public void setTeam(int clientnumber, Team team) {
		assert clientnumber >= 0 && clientnumber <= 3: "clientnumber不再範圍裡";
		character[clientnumber].setTeam(team);
	}
	
	public void setRole(int clientnumber, Role role) {
		assert clientnumber >= 0 && clientnumber <= 3: "clientnumber不再範圍裡";
		character[clientnumber].setRole(role);
		character[clientnumber].setRoleConstant();
	}
	
	public void setReady(int clientnumber, Boolean ready) {
		assert clientnumber >= 0 && clientnumber <= 3: "clientnumber不再範圍裡";
		character[clientnumber].setReady(ready);
		if(checkReady())
		{
			GameStart();
		}
	}
	
	public boolean checkReady() {
		for(int i=0;i<=characternumber;i++)
		{
			if(character[i].getReady() == false)
			{
				return false;
			}
		}
		return true;
	}
	
	public void GameStart() {
<<<<<<< HEAD
		for(int i=0;i<=characternumber;i++)
		{
			RandomLocation(i);
		}
		tcpsm.gameStart();
		Timer timer = new Timer();
		timer.schedule(new GameOver(), 2*60*1000);
=======
		//tcpsm.gamestart();
>>>>>>> 719c64a004ede139fb1b42756603afb965415ac3
	}
	
	public void RandomLocation(int number) {
		Point2D location = null;
		location.setLocation(Random()*5000, Random()*2000);
		boolean check = true;
		while(check)
		{
			for(int i=0;i<=characternumber;i++)
			{
				if(location != character[i].getLocation()) {
					check = false;
				}	
			}
			if(map[(int)location.getY()][(int)location.getX()].getType() == 0) {
				check = false;
			}
		}
		character[number].setLocation(location);
	}
	
	public void Move(int clientnumber) {
		
	}
	
	public void addBullet(int clientnumber, Point2D angle) {
		if(character[characternumber].getCanAttack())
		{
			Role role = character[characternumber].getRole();
			Team team = character[characternumber].getTeam();
			Point2D location = character[characternumber].getLocation();
			Skill skill = Skill.NULL;
			switch(role)
			{
				case A:
					bullets.add(new Bullet(team, location, 2, skill.DISPERSION, angle, 1, 15));
					break;
				case B:
					bullets.add(new Bullet(team, location, 1.5, skill.NULL, angle, 3, 20));
					break;
				case C:
					bullets.add(new Bullet(team, location, 1, skill.CHAOS, angle, 3, 5));
					break;
				case D:
					bullets.add(new Bullet(team, location, 2.5, skill.FIRE, angle, 3, 10));
					break;
			}
			character[characternumber].setCanAttack(false);
		}
	}
	public void checkCollision() {
		
	}
	
	public void getUpdateInfo() { //傳所有要更新的物件資訊給UDP
		
	}
	
	public void KillAndDead(int characternumber1, int characternumber2) {
		character[characternumber1].addKill();
		character[characternumber2].addDead();
		Timer timer = new Timer();
		timer.schedule(new ResurrectionRandomLoction(characternumber2), 60000);
	}
	
	public class ResurrectionRandomLoction extends TimerTask {
		int characternumber;
		public ResurrectionRandomLoction(int i) {
			characternumber = i;
		}
		
		public void run()  {
			RandomLocation(characternumber);
		}
	}
	
	public class GameOver extends TimerTask {
		public void run() {
			tcpsm.gameOver();
		}
	}
	
	public double Random() {
		Random random = new Random();
		return random.nextDouble();
	}
}
