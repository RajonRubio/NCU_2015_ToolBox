package Protocols;

import java.io.Serializable;
import java.util.ArrayList;

public class TeamState implements Serializable {
	public ArrayList<Member> red;
	public ArrayList<Member> blue;
	
	private class Member {
		public String name;
		public String address;
		
		public Member(String name, String address) {
			this.name = name;
			this.address = address;
		}
	}
}
