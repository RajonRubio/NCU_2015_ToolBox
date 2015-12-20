package DOM;

import java.awt.geom.Point2D;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SpriteSheet;

import Protocols.Role;
import Protocols.Team;

public class Bullet extends DynamicObject {
	public Team team;
	public Role role;
	public Point2D.Double location;
	
	public SpriteSheet bulletSheet;
	public Animation bulletAnimation;
	
	Bullet(Team team, Role role, Point2D.Double location, SpriteSheet bulletSheet, Animation bulletAnimation) {
		this.team = team;
		this.role = role;
		this.location.x = location.x;
		this.location.y = location.y;
		this.bulletSheet = bulletSheet;
		this.bulletAnimation = bulletAnimation;
	}
	
	public void update(int delta) {
		bulletAnimation.update(delta);
	}

	
	public void render(Point2D.Double clientLocation) {
		double frameX = this.location.x - clientLocation.x + 480;
		double frameY = this.location.y - clientLocation.y + 360;
		bulletSheet.draw((int)frameX, (int)frameY);
	}
}
