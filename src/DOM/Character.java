package DOM;

import java.awt.geom.Point2D;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import Protocols.Role;
import Protocols.Team;

public class Character extends DynamicObject {
	public int clientno;
	public String name;
	public Team team = Team.NULL;
	public Role role = Role.NULL; 
	public int status = 0;
	public Point2D.Double location;
	public int currentHP = 100;
	public int maxHP = 100;
	public int reviveTime = 0;
	public boolean[] debuff = {false, false};
	public int kill = 0;
	public int dead = 0;
	
	public SpriteSheet[] charSheet = new SpriteSheet[8];
	public Animation[] charAnimation = new Animation[8];
	
	Character(int clientno, String name, Team team, Role role, SpriteSheet[] charSheet, Animation[] charAnimation) throws SlickException {
		this.clientno = clientno;
		this.name = name;
		this.team = team;
		this.role = role;
		this.charSheet = charSheet;
		this.charAnimation = charAnimation;
	}
	
	public void update(int delta) {
		charAnimation[status].update(delta);
	}
	
	public void render(Graphics g, Point2D.Double clientLocation) {
		double frameX = this.location.x - clientLocation.x + 480;
		double frameY = this.location.y - clientLocation.y + 360;
		charAnimation[status].draw((int)frameX, (int)frameY);
	}
}
