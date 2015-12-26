package Protocols;

import java.util.ArrayList;

public class ResultInfo {
	public ArrayList<Result> people = new ArrayList<Result>();
	
	public class Result {
		public String name;
		public Team team;
		public int kill;
		public int dead;
		
		public Result(String name, Team team, int kill, int dead) {
			this.name = name;
			this.team = team;
			this.kill = kill;
			this.dead = dead;
		}
	}
}
