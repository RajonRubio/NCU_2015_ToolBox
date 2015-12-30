package Protocols;

import java.awt.geom.Point2D;

public class Bullet {
	public Team team;
	public Role role;
	public Point2D.Double location;
		
	public Bullet(Team team, Role role, Point2D.Double location) {
		this.team = team;
		this.role = role;
		this.location = location;
	}
}
