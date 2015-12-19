package DOM;

import java.awt.geom.Point2D;

public class Bullet extends DynamicObject {
	public int team;
	public int role;
	public Point2D.Double location;
	
	Bullet(int team, int role, Point2D.Double location) {
		this.team = team;
		this.role = role;
		this.location.x = location.x;
		this.location.y = location.y;
	}
	
	public void update(int delta) {
		
	}
	
	public void render(Point2D.Double clientLocation) {
		
	}
}
