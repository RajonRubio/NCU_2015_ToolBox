package Protocols;

import java.awt.geom.Point2D;

public class BulletT {
	public Team team;
	public Role role;
	public Point2D.Double location;
		
	public BulletT(Team team, Role role, Point2D.Double location) {
		this.team = team;
		this.role = role;
		this.location = location;
	}
}
