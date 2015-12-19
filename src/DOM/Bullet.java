package DOM;

import java.awt.Image;
import java.awt.geom.Point2D;

public class Bullet extends DynamicObject {
	public int team;
	public int job;
	public Point2D.Double location;
	
	public Image image;
	
	Bullet(int team, int job, Point2D.Double location) {
		this.team = team;
		this.job = job;
		this.location.x = location.x;
		this.location.y = location.y;
	}
	
	@Override
	public void draw() {
		
	}
}
