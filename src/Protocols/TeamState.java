package Protocols;

import java.io.Serializable;
import java.util.ArrayList;

public class TeamState implements Serializable {
	public ArrayList<Member> red;
	public ArrayList<Member> blue;
	
	public TeamState() {
		red = new ArrayList<Member>();
		blue = new ArrayList<Member>();
	}
	
	public class Member implements Serializable {
		public String name;
		public Role role;
		public boolean ready;
		

		public Member(String name, Role role ,boolean ready) {
			this.name = name;
			this.role = role;
			this.ready = ready;
		}
	}
}
