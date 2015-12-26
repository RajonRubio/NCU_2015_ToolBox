package Protocols;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class WoodBox {
	public ArrayList<WoodBoxState> boxes = new ArrayList<WoodBoxState>();
	
	public class WoodBoxState{
		public int type;
		public int x;
		public int y;
		
		public WoodBoxState(int type, int x,int y){
			this.type = type;
			this.x = x;
			this.y = y;
		}
	}
}


/*public ArrayList<Person> player = new ArrayList<Person>();

public class Person {
	public int clientno;
	public String name;
	public Team team;
	public Role role;
	public Point2D.Double location;
	
	public Person(int clientno, String name, Team team, Role role, Point2D.Double location) {
		this.clientno = clientno;
		this.name = name;
		this.team = team;
		this.role = role;
		this.location = location;
	}
}*/