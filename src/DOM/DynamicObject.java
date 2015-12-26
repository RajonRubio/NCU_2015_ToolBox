package DOM;

import java.awt.geom.Point2D;

import org.newdawn.slick.Graphics;

public abstract class DynamicObject {
	public int team;
	public int role;
	Point2D.Double location;
	
	public void update(int delta) {
		
	}
	
	public void render(Graphics g, Point2D.Double clientLocation) {
		
	}
}
