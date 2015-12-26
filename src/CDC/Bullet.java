package CDC;

import java.awt.geom.Point2D;
import Protocols.*;

public class Bullet {
	private String name;
	private Team team;
	private Point2D location;
	private double bulletspeed;
	private Skill skill;
	private Point2D angle;
	private int collisiontime;
	private int attack;
	
	public Bullet(String name, Team team, Point2D location, double bulletspeed, Skill skill, Point2D angle, int collisiontime, int attack) {
		this.name = name;
		this.team = team;
		this.location = location;
		this.bulletspeed = bulletspeed;
		this.skill = skill;
		this.angle = angle;
		this.collisiontime = collisiontime;
		this.attack = attack;
	}
	
	public void setLocation(Point2D location) {
		this.location.setLocation(location);
	}
	
	public void setAngle(Point2D angle) {
		this.angle = angle;
	}
	
	public String getName() {
		return name;
	}
	
	public Point2D getLocation() {
		return location;
	}
	
	public double getBulletspeed() {
		return bulletspeed;
	}
	
	public Point2D getAngle() {
		return angle;
	}
}
