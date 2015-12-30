package CDC;

import java.awt.geom.Point2D;
import Protocols.*;

public class Bullet {
	private int clientnumber;
	private Team team;
	private Role role;
	private Point2D.Double location;
	private double bulletspeed;
	private Skill skill;
	private Point2D.Double angle;
	private int collisiontime;
	private int attack;
	
	public Bullet(int clientnumber, Team team, Role role, Point2D.Double location, double bulletspeed, Skill skill, Point2D.Double angle, int collisiontime, int attack) {
		this.clientnumber = clientnumber;
		this.team = team;
		this.role = role;
		this.location = location;
		this.bulletspeed = bulletspeed;
		this.skill = skill;
		this.angle = angle;
		this.collisiontime = collisiontime;
		this.attack = attack;
	}
	
	public void setLocation(Point2D.Double location) {
		this.location.setLocation(location);
	}
	
	public void setAngle(Point2D.Double angle) {
		this.angle = angle;
	}
	
	public void setCollisiontime(int collisiontime) {
		this.collisiontime = collisiontime;
	}
	
	public int getClientNumber() {
		return clientnumber;
	}
	
	public Role getRole() {
		return role;
	}
	
	public Point2D.Double getLocation() {
		return location;
	}
	
	public double getBulletspeed() {
		return bulletspeed;
	}
	
	public Skill getSkill() {
		return skill;
	}
	public Team getTeam() {
		return team;
	}
	
	public Point2D.Double getAngle() {
		return angle;
	}
	
	public int getCollisiontime() {
		return collisiontime;
	}
	
	public int getAttack() {
		return attack;
	}
}
