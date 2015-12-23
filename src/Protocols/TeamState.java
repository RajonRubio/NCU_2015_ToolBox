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
	
	public class Member {
		public String name;
		public String address;
		public String job;
		public boolean ready;
		
		public Member(String name, String address , String job , boolean ready) {
			this.name = name;
			this.address = address;
			this.job = job;
			this.ready = ready;
		}
	}
}
