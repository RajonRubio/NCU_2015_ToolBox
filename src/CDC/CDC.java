package CDC;

import java.util.ArrayList;

import TCPSM.TCPSM;

public class CDC {
	private Character [] character = new Character [4];
	private int characternumber = 0;
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private boolean full = false;
	private TCPSM tcpsm;
	
	public CDC(TCPSM tcpsm) {
		this.tcpsm = tcpsm;
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
		assert clientnumber >= 0 && clientnumber <= 3: "clientnumber���A�d���";
		character[characternumber] = new Character(clientnumber, name);
	}
	
	public void removeVirtualCharacter(int clientnumber) {
		assert clientnumber >= 0 && clientnumber <= 3: "clientnumber���A�d���";
		character[characternumber] = null;
	}
	
	public void setTeam(int clientnumber, int team) {
		assert clientnumber >= 0 && clientnumber <= 3: "clientnumber���A�d���";
		character[characternumber].setTeam(team);
	}
	
	public void setJob(int clientnumber, int job) {
		assert clientnumber >= 0 && clientnumber <= 3: "clientnumber���A�d���";
		character[characternumber].setJob(job);
		character[characternumber].setJobConstant();
	}
	
	public void setReady(int clientnumber, Boolean ready) {
		assert clientnumber >= 0 && clientnumber <= 3: "clientnumber���A�d���";
		character[characternumber].setReady(ready);
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
		//tcpsm.gamestart();
	}
	
	public void addBullet() {
		
	}
	
	public void updateSpeed() {
		
	}
	
	public void updateDirection() {
		
	}
	
	public void loadmap() {
		
	}
	
	public void startUpdatingThread() {
		
	}
	
	public void setTimeout() {
		
	}
	
	public void checkCollision() {
		
	}
	
	public void getUpdateInfo() {
		
	}
}
