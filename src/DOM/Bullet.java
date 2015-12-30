package DOM;

import java.awt.geom.Point2D;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;

import Protocols.Role;
import Protocols.Team;

public class Bullet extends DynamicObject {
	public Team team;
	public Role role;
	public Point2D.Double location;
	
	public Animation bulletAnimation;
	
	public Bullet(Team team, Role role, Point2D.Double location) {
		this.team = team;
		this.role = role;
		this.location = location;
	}
	
	public void setAnimation(Animation bulletAnimation) {
		this.bulletAnimation = bulletAnimation;
	}
	
	public void update(int delta) {
		bulletAnimation.update(delta);
	}
	
	public void render(Graphics g, Point2D.Double clientLocation) {
		double frameX = this.location.x - clientLocation.x + 480 - 16;
		double frameY = this.location.y - clientLocation.y + 360 - 16;
		bulletAnimation.draw((int)frameX, (int)frameY);
	}
}
