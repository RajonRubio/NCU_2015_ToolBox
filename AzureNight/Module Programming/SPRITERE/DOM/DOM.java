package DOM;

import java.util.ArrayList;


public class DOM {
	private static DOM instance = null;
	
	private DOM() { }
	
	public static DOM getInstance() {
		if(instance == null) {
			instance = new DOM();
		}
		return instance;
	}
	
	public boolean[] checkDraw = new boolean[2];

	public synchronized ArrayList<DynamicObject> getAllDynamicObjects() {
		ArrayList<DynamicObject> dynamicObjects = new ArrayList<DynamicObject>();
		dynamicObjects.add(new Character(1, false));
		dynamicObjects.add(new Item("gun", 10, 100, 100, false));
		return dynamicObjects;
	}
		
}
