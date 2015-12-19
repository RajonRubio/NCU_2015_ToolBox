package DOM;

import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Character extends DynamicObject {
	public int clientno;
	public String name;
	public int team = -1;
	public int job = -1; 
	public int status = 0;
	public Point2D.Double location;
	public int currentHP = 100;
	public int maxHP = 100;
	public int reviveTime = 0;
	public boolean[] debuff = {false, false};
	public int kill = 0;
	public int dead = 0;
	
	public BufferedImage spriteSheet;
	public int width;
	public int height;
	
	Character(int clientno, String name, int team, int job) {
		this.clientno = clientno;
		this.name = name;
		this.team = team;
		this.job = job;
	}

	@Override
	public void draw() {

	}
}
