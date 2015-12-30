package Protocols;

import java.awt.geom.Point2D;

public class Character {
	public int clientno;
	public Status status;
	public int HP;
	public Point2D.Double location;
	public int time;
	public boolean [] debuff = {false,false};
	public int kill;
	public int dead;
		
	public Character(int clientno, Status status, int HP, Point2D.Double location, boolean [] debuff, int kill, int dead) {
		this.clientno = clientno;
		this.status = status;
		this.HP = HP;
		this.location = location;
		this.debuff = debuff;
		this.kill = kill;
		this.dead = dead;
	}
}