package DOM;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import Protocols.CharacterState;
import Protocols.ResultInfo;
import Protocols.ResultInfo.Result;
import Protocols.Role;
import Protocols.Team;

public class DOM {
	public DOM() {
		
	}
	
	int clientno;
	Character me;
	ArrayList<Character> player = new ArrayList<Character>();
	public ArrayList<Bullet> bullet = new ArrayList<Bullet>();
	boolean checkRole[] = {false, false, false, false};
	
	public static SpriteSheet[][] charSheet = new SpriteSheet[4][8];
	public static Animation[][] charAnimation = new Animation[4][8];
	public static SpriteSheet[][] bulletSheet = new SpriteSheet[2][4];
	public static Animation[][] bulletAnimation = new Animation[2][4];
	public static Image[] debuff = new Image[2];
	
	public void setClientno(int clientno) {
		this.clientno = clientno;
	}
	
	public void gameStart(CharacterState state) {
		for(int i = 0; i < 4; i++) {
			addVirtualCharacter(state.player.get(i).clientno, state.player.get(i).name, state.player.get(i).team, state.player.get(i).role, state.player.get(i).location);
		}
	}
	
	public synchronized void addVirtualCharacter(int clientno, String name, Team team, Role role, Point2D.Double location) {
		try {
			Character temp = new Character(clientno, name, team, role, location, charAnimation[role.ordinal()], debuff);
			if(clientno == this.clientno) {
				me = temp;
			}
			player.add(temp);
		} catch(SlickException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void removeVirtualCharacter(int clientno) {
		int tempIndex = 0;
		for(int i = 0; i < player.size(); i++) {
			if(player.get(i).clientno == clientno) {
				tempIndex = i;
				break;
			}
		}
		player.remove(tempIndex);
	}
	
	public synchronized void updateVirtualCharacter(int clientno, int status, Point2D.Double location, int currentHP, int reviveTime, boolean[] debuff, int kill, int dead) {
		Character temp = null;
		int tempIndex = 0;
		for(int i = 0; i < player.size(); i++) {
			if(player.get(i).clientno == clientno) {
				temp = player.get(i);
				tempIndex = i;
				break;
			}
		}
		temp.status = status;
		temp.location = location;
		temp.currentHP = currentHP;
		temp.reviveTime = reviveTime;
		temp.debuff[0] = debuff[0];
		temp.debuff[1] = debuff[1];
		temp.kill = kill;
		temp.dead = dead;
		player.set(tempIndex, temp);
	}
	
	public synchronized void updateBullet(ArrayList<Bullet> temp) {
		int team, role;
		for(int i = 0; i < temp.size(); i++) {
			team = temp.get(i).team.ordinal();
			role = temp.get(i).role.ordinal();
			temp.get(i).setAnimation(bulletAnimation[team][role]);
		}
		this.bullet = temp;
	}
	
	public ArrayList<DynamicObject> getAllDynamicObjects() {
		ArrayList<DynamicObject> object = new ArrayList<DynamicObject>();
		for(Character c : player) {
			object.add(c);
		}
		for(Bullet b : bullet) {
			object.add(b);
		}
		return object;
	}
	
	public Point2D.Double getVirtualCharacterXY() {
		return me.location;
	}
	
	public int[] getKills() {
		int[] kill = {0, 0};
		for(int i = 0; i < player.size(); i++) {
			if(player.get(i).team == Team.RED) {
				kill[0] += player.get(i).kill;
			}
			if(player.get(i).team == Team.BLUE) {
				kill[1] += player.get(i).kill;
			}
		}
		return kill;
	}
	
	public ResultInfo getFinalResult() {
		ResultInfo resultinfo = new ResultInfo();
		for(int i = 0; i < player.size(); i++) {
			Character c = player.get(i);
			Result r = resultinfo.new Result(c.name, c.team, c.kill, c.dead);
			resultinfo.people.add(r);
		}
		return resultinfo;
	}
}
