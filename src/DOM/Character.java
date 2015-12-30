package DOM;

import java.awt.geom.Point2D;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

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
	public Image burn;
	public Image choas;
	
	public Animation[] charAnimation = new Animation[8];
	
	Character(int clientno, String name, Team team, Role role, Point2D.Double location, Animation[] charAnimation) throws SlickException {
		this.clientno = clientno;
		this.name = name;
		this.team = team;
		this.role = role;
		this.location = location;
		this.charAnimation = charAnimation;
		burn = new Image("img/game/Choas.png");
		choas = new Image("img/game/OnFire.png");
	}
	
	public void update(int delta) {
		charAnimation[status].update(delta);
	}
	
	public void render(Graphics g, Point2D.Double clientLocation) {
		double frameX = location.x - clientLocation.x + 480 - 16;
		double frameY = location.y - clientLocation.y + 360 - 16;
		charAnimation[status].draw((int)frameX, (int)frameY);
		if(team == Team.RED) {
			g.setColor(Color.red);
		}
		else if(team == Team.BLUE) {
			g.setColor(Color.blue);
		}
		else {
			g.setColor(Color.white);
		}
		g.drawString(name, (int)frameX, (int)frameY-20);
		g.drawString(currentHP+"/"+maxHP, (int)frameX-15, (int)frameY+35);
		if(location.x == clientLocation.x && location.y == clientLocation.y) {
			g.setColor(Color.white);
			g.drawString("Kill: " + kill, 870, 10);
			g.drawString("Dead: " + dead, 870, 30);
			if(debuff[0] == true) {
				g.drawImage(burn, 10, 10);
			}
			if(debuff[1] == true) {
				g.drawImage(choas, 70, 10);
			}
		}
	}
}
