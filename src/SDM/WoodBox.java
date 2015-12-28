package SDM;

import java.awt.geom.Point2D;

public class WoodBox {
	public int life=2;
	public Point2D.Double location;
	public WoodBox(Point2D.Double location) {
		this.location.x = location.x;
		this.location.y = location.y;
	}
}
