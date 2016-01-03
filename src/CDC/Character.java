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
	Timer timer;
	
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
				movespeed = 0.5;
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
	
	public void TimerStart(BasicBlock map[][]) {
		timer = new Timer();
		timer.schedule(new Move(map), new Date(), 16);
	}
	
	public class Move extends TimerTask {
		BasicBlock [][] map = new BasicBlock [40][100];
		public Move(BasicBlock [][] map) {
			this.map = map;
		}
		
		public void run() {
			if(debuff[1] == true)
			{
				switch(status)
				{
					case UP:
						if(location.getY()+movespeed > 400 && location.getY()+movespeed < 1400)
						{
							location.setLocation(location.getX(),location.getY()+movespeed);
						}
						break;
					case DOWN:
						if(location.getY()-movespeed > 400 && location.getY()-movespeed < 1400)
						{
							location.setLocation(location.getX(),location.getY()-movespeed);
						}
						break;
					case RIGHT:
						if(location.getX()-movespeed > 350 && location.getX()-movespeed < 4450)
						{
							location.setLocation(location.getX()-movespeed,location.getY());
						}
						break;
					case LEFT:
						if(location.getX()+movespeed > 350 && location.getX()+movespeed < 4450)
						{
							location.setLocation(location.getX()+movespeed,location.getY());
						}
						break;
					default:
						System.out.println(status);
						break;
				}
			}
			else
			{
				switch(status)
				{
					case UP:
						if(location.getY()-movespeed > 350 && location.getY()-movespeed < 1450 && map[(int)(location.getY()-movespeed+50)/50][(int)(location.getX()+50)/50].getType() == 0)
						{
							location.setLocation(location.getX(),location.getY()-movespeed);
						}
						break;
					case DOWN:
						if(location.getY()+movespeed > 350 && location.getY()+movespeed < 1450 && map[(int)(location.getY()+movespeed+50)/50][(int)(location.getX()+50)/50].getType() == 0)
						{
							location.setLocation(location.getX(),location.getY()+movespeed);
						}
						break;
					case RIGHT:
						if(location.getX()+movespeed > 500 && location.getX()+movespeed < 4450 && map[(int)(location.getY()+50)/50][(int)(location.getX()+movespeed+50)/50].getType() == 0)
						{
							location.setLocation(location.getX()+movespeed,location.getY());
						}
						break;
					case LEFT:
						if(location.getX()-movespeed > 500 && location.getX()-movespeed < 4450 && map[(int)(location.getY()+50)/50][(int)(location.getX()-movespeed+50)/50].getType() == 0)
						{
							location.setLocation(location.getX()-movespeed,location.getY());
						}
						break;
					default:
						break;
				}
			}
		}
	}
}
