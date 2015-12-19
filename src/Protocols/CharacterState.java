package Protocols;

import java.util.ArrayList;

public class CharacterState {
	public ArrayList<person> player = new ArrayList<person>();
	
	public class person {
		public int clientno;
		public String name;
		public Team team;
		public Role role;
		
		public person(int clientno, String name, Team team, Role role) {
			this.clientno = clientno;
			this.name = name;
			this.team = team;
			this.role = role;
		}
	}
}
