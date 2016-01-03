package CDC;

import java.awt.geom.Point2D;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.sun.swing.internal.plaf.synth.resources.synth_zh_TW;

import Protocols.*;
import SETTINGS.*;

public class Character {
	private int clientnumber;
	private String name;
	private Team team = Team.NULL;
	private Role role = Role.NULL;
	private Point2D.Double location;
	private int maxHP = 100;
	private int nowHP = maxHP;
	private Status status = Status.UP_STOP;
	private double movespeed;
	private double attackspeed;
	private boolean canattack = true;
	private boolean [] debuff = {false, false};
	private int kill = 0;
	private int dead = 0;
	private int time = 0;
	private boolean ready = false;
	Timer timer = new Timer();
	
	public Character(int clientnumber, String name) {
		this.clientnumber = clientnumber;
		this.name = name;
	}
	
	public void setTeam(Team team) {
		this.team = team;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public void setLocation(Point2D.Double location) {
		this.location = location;
	}
	
	public void setNowHP(int nowHP) {
		this.nowHP = nowHP;
	}
	
	public void setCanAttack(boolean canattack) {
		this.canattack = canattack;
		Timer timer = new Timer();
		timer.schedule(new ReadyAttack(), (long)attackspeed * 1000);
	}
	
	public void setDeBuff(boolean [] debuff) {
		this.debuff = debuff;
	}
	
	public void setTime(int time) {
		this.time = time;
	}
	
	public void addKill() {
		kill++;
	}
	
	public void addDead() {
		dead++;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public void setReady(boolean ready) {
		this.ready = ready;
	}
	
	public void setRoleConstant() {
		switch(role)
		{
			case Archer:
				movespeed = 1;
				attackspeed = 2.5;
				break;
			case Marines:
				movespeed = 1.5;
				attackspeed = 2;
				break;
			case Cannon:
				movespeed = 2;
				attackspeed = 1.5;
				break;
			case Wizard:
				movespeed = 2.5;
				attackspeed = 1;
				break;
			default:
				break;
		}
	}
	
	public int getClientNumber() {
		return clientnumber;
	}
	
	public Status getState() {
		return status;
	}
	
	public String getName() {
		return name;
	}
	
	public Team getTeam() {
		return team;
	}
	
	public Role getRole() {
		return role;
	}
	
	public Point2D.Double getLocation() {
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
	
	public int getTime() {
		return time;
	}
	
	public boolean getReady() {
		return ready;
		
	}
	
	public class ReadyAttack extends TimerTask {
		public void run() {
			canattack = true;
		}
	}
	
	public void TimerResume() {
		timer.schedule(new Move(), new Date(), 1000);
	}

	
	public void TimerPause() {
		timer.cancel();
	}
	
	public class Move extends TimerTask {
		public void run() {
			if(debuff[1] == true)
			{
				switch(status)
				{
					case UP:
						location.setLocation(location.getX()+movespeed,location.getY());
						break;
					case DOWN:
						location.setLocation(location.getX()-movespeed,location.getY());
						break;
					case RIGHT:
						location.setLocation(location.getX(),location.getY()-movespeed);
						break;
					case LEFT:
						location.setLocation(location.getX(),location.getY()+movespeed);
						break;
					default:
						break;
				}
			}
			else
			{
				switch(status)
				{
					case UP:
						location.setLocation(location.getX()-movespeed,location.getY());
						break;
					case DOWN:
						location.setLocation(location.getX()+movespeed,location.getY());
						break;
					case RIGHT:
						location.setLocation(location.getX(),location.getY()+movespeed);
						break;
					case LEFT:
						location.setLocation(location.getX(),location.getY()-movespeed);
						break;
					default:
						break;
				}
			}
			System.out.println(status + "x:" + location.getX() + "y: " + location.getY());
		}
	}
}
