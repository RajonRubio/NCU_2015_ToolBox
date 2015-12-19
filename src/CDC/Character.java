package CDC;

import java.awt.geom.Point2D;

public class Character {
	private int clientnumber;
	private String name;
	private int team = -1;
	private int job = -1;
	private Point2D location;
	private int maxHP = 100;
	private int nowHP = maxHP;
	private double movespeed;
	private double attackspeed;
	private boolean canattack = true;
	private boolean [] debuff = {false, false};
	private int kill = 0;
	private int dead = 0;
	private boolean ready = false;
	
	public Character(int clientnumber, String name) {
		this.clientnumber = clientnumber;
		this.name = name;
	}
	
	public void setTeam(int team) {
		this.team = team;
	}
	
	public void setJob(int job) {
		this.job = job;
	}
	
	public void setLocation(Point2D location) {
		this.location = location;
	}
	
	public void setNowHP(int nowHP) {
		this.nowHP = nowHP;
	}
	
	public void setCanAttack(boolean canattack) {
		this.canattack = canattack;
	}
	
	public void setDeBuff(boolean [] debuff) {
		this.debuff = debuff;
	}
	
	public void setKill(int kill) {
		this.kill = kill;
	}
	
	public void setDead(int dead) {
		this.dead = dead;
	}
	
	public void setReady(boolean ready) {
		this.ready = ready;
	}
	
	public void setJobConstant() {
		switch(job)
		{
			case 0:
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
		}
	}
	
	public int getClientNumber() {
		return clientnumber;
	}
	
	public String getName() {
		return name;
	}
	
	public int getTeam() {
		return team;
	}
	
	public int getJob() {
		return job;
	}
	
	public Point2D getLocation() {
		return location;
	}
	
	public int getNowHP() {
		return nowHP;
	}
	
	public double getMoveSpeed() {
		return movespeed;
	}
	
	public double getAttackSpeed() {
		return attackspeed;
	}
	
	public boolean getCanAttack() {
		return canattack;
	}
	
	public boolean [] getDeBuff() {
		return debuff;
	}
	
	public int getKill() {
		return kill;
	}
	
	public int getDead() {
		return dead;
	}
	
	public boolean getReady() {
		return ready;
		
	}
}
