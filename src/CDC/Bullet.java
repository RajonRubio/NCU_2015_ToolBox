package CDC;

import java.awt.geom.Point2D;
import Protocols.*;

public class Bullet {
	private Team team;
	private Point2D location;
	private double bulletspeed;
	private Skill skill;
	private Point2D angle;
	private int collisiontime;
	private int attack;
	
	public Bullet(Team team, Point2D location, double bulletspeed, Skill skill, Point2D angle, int collisiontime, int attack) {
		this.team = team;
		this.location = location;
		this.bulletspeed = bulletspeed;
		this.skill = skill;
		this.angle = angle;
		this.collisiontime = collisiontime;
		this.attack = attack;
	}
}
