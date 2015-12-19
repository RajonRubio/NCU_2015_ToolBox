package DOM;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class DOM {
	public DOM() {
		
	}
	
	int clientno;
	Character me;
	ArrayList<Character> player = new ArrayList<Character>();
	ArrayList<Bullet> bullet = new ArrayList<Bullet>();
	
	public SpriteSheet[][] charSheet = new SpriteSheet[4][8];
	public Animation[][] charAnimation = new Animation[4][8];
	
	public void setClientno(int clientno) {
		this.clientno = clientno;
	}
	
	public void gameStart() {
		
	}
	
	public synchronized void addVirtualCharacter(int clientno, String name, int team, int role) throws SlickException {
		Character temp = new Character(clientno, name, team, role, charSheet[role], charAnimation[role]);
		if(clientno == this.clientno) {
			me = temp;
		}
		player.add(temp);
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
	
	public synchronized void updateVirtualCharacter(int clientno, int status, Point2D.Double location, int currentHP, int reviveTime, boolean debuff[], int kill, int dead) throws Exception {
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
		temp.location.x = location.x;
		temp.location.y = location.y;
		temp.currentHP = currentHP;
		temp.reviveTime = reviveTime;
		temp.debuff[0] = debuff[0];
		temp.debuff[1] = debuff[1];
		temp.kill = kill;
		temp.dead = dead;
		player.set(tempIndex, temp);
	}
	
	public synchronized void updateBullet(ArrayList<Bullet> bullet) {
		this.bullet = bullet;
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
	
	public Point2D.Double getVirtualCharacterXY() throws Exception {
		return me.location;
	}
}
