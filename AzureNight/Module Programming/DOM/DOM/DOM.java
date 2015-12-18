package DOM;

import java.awt.Point;
import java.util.ArrayList;

import TCPSM.TCPSM;

public class DOM {
	private static DOM instance = null;
	
	private DOM() { }
	
	public static DOM getInstance() {
		if(instance == null) {
			instance = new DOM();
		}
		return instance;
	}
	
	public ArrayList<Character> player = new ArrayList<Character>();
	public ArrayList<Item> thing = new ArrayList<Item>();
	
	public synchronized void addVirtualCharacter(int clientno, boolean isLocalClient) throws Exception {
		Character temp = null;
		for(int i = 0; i < player.size(); i++) {
			if(player.get(i).clientno == clientno) {
				temp = player.get(i);
				break;
			}
		}
		if(temp == null) {
			player.add(new Character(clientno, isLocalClient));			
		}
		else {
			throw new Exception("Character clientno: " + clientno + " is already existed.");
		}
	}
	
	public synchronized void addItem(String name, int index, int x, int y, boolean shared) throws Exception {
		Item temp = null;
		for(int i = 0; i < thing.size(); i++) {
			if(thing.get(i).index == index) {
				temp = thing.get(i);
				break;
			}
		}
		if(temp == null) {
			thing.add(new Item(name, index, x, y, shared));
		}
		else {
			throw new Exception("Item index: " + index + " is already existed.");
		}
		
	}
	
	public synchronized void updateVirtualCharacter(int clientno, int dir, int speed, int x, int y) throws Exception {
		Character temp = null;
		int temp_index = 0;
		for(int i = 0; i < player.size(); i++) {
			if(player.get(i).clientno == clientno) {
				temp = player.get(i);
				temp_index = i;
				break;
			}
		}
		if(temp == null) {
			throw new Exception("Character clientno: " + clientno + " is not existed.");
		}
		else {
			temp.dir = dir;
			temp.speed = speed;
			temp.x = x;
			temp.y = y;
			player.set(temp_index, temp);
		}
	}
	
	public synchronized void updateItem(int index, boolean shared, int owner, int x, int y) throws Exception {
		Item temp = null;
		int temp_index = 0;
		for(int i = 0; i < thing.size(); i++) {
			if(thing.get(i).index == index) {
				temp = thing.get(i);
				temp_index = i;
				break;
			}
		}
		if(temp == null) {
			throw new Exception("Item index: " + index + " is not existed.");
		}
		else {
			temp.shared = shared;
			temp.owner = owner;
			temp.x = x;
			temp.y = y;
			thing.set(temp_index, temp);
		}
	}
	
	public synchronized ArrayList<DynamicObject> getAllDynamicObjects() {
		ArrayList<DynamicObject> dynamicObjects = new ArrayList<DynamicObject>();
		for(int i = 0; i < player.size(); i++) {
			dynamicObjects.add(player.get(i));
		}
		for(int i = 0; i < thing.size(); i++) {
			dynamicObjects.add(thing.get(i));
		}
		return dynamicObjects;
	}
	
	public synchronized Point getVirtualCharacterXY() throws Exception {
		Character me;
		for(int i = 0; i < player.size(); i++) {
			if(player.get(i).isLocalClient == true) {
				me = player.get(i);
				return new Point(me.x, me.y);
			}
		}
		throw new Exception("Local client is not existed.");
	}
	
	public synchronized void keyGETPressed() throws Exception {
		TCPSM tcpsm = TCPSM.getInstance();
		Character me = null;
		for(int i = 0; i < player.size(); i++) {
			if(player.get(i).isLocalClient == true) {
				me = player.get(i);
				break;
			}
		}
		if(me == null) {
			throw new Exception("Local client is not existed.");
		}
		switch(me.dir) {
			//EAST = 1
			case 1:
				for(int i = 0; i < thing.size(); i++) {
					Item temp = thing.get(i);
					if(temp.x - me.x == 1 && temp.y - me.y == 0) {
						if(temp.shared == true) {
							//GET = 5
							tcpsm.inputMoves(5);
						}
						else {
							if(temp.owner == -1) {
								tcpsm.inputMoves(5);
							}
						}
					}
				}
				break;
			//SOUTH = 2
			case 2:
				for(int i = 0; i < thing.size(); i++) {
					Item temp = thing.get(i);
					if(temp.y - me.y == 1 && temp.x - me.x == 0) {
						if(temp.shared == true) {
							//GET = 5
							tcpsm.inputMoves(5);
						}
						else {
							if(temp.owner == -1) {
								tcpsm.inputMoves(5);
							}
						}
					}
				}
				break;
			//NORTH = 3
			case 3:
				for(int i = 0; i < thing.size(); i++) {
					Item temp = thing.get(i);
					if(temp.y - me.y == -1 && temp.x - me.x == 0) {
						if(temp.shared == true) {
							//GET = 5
							tcpsm.inputMoves(5);
						}
						else {
							if(temp.owner == -1) {
								tcpsm.inputMoves(5);
							}
						}
					}
				}
				break;
			//WEST = 4
			case 4:
				for(int i = 0; i < thing.size(); i++) {
					Item temp = thing.get(i);
					if(temp.x - me.x == -1 && temp.y - me.y == 0) {
						if(temp.shared == true) {
							//GET = 5
							tcpsm.inputMoves(5);
						}
						else {
							if(temp.owner == -1) {
								tcpsm.inputMoves(5);
							}
						}
					}
				}
				break;
			default:
				throw new Exception("Invalid director of Character.");
		}
	}
}
