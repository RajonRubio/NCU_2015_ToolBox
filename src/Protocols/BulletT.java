package Protocols;

import java.awt.geom.Point2D;

public class BulletT extends Command{
	public Team team;
	public Role role;
	public Point2D.Double location;
		
	public BulletT(Team team, Role role, Point2D.Double location) {
		super("Bullet");
		this.team = team;
		this.role = role;
		this.location = location;
	}
}
