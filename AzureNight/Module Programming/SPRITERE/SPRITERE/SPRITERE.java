package SPRITERE;

import java.util.ArrayList;

import DOM.DOM;
import DOM.DynamicObject;

public class SPRITERE {

	DOM dom;
	
	public SPRITERE(DOM dom) { 
		this.dom = dom;
	}
	
	public void renderSprites() {
		ArrayList<DynamicObject> dynamicObjects = new ArrayList<DynamicObject>();
		dynamicObjects = dom.getAllDynamicObjects();
		for(int i = 0; i < dynamicObjects.size(); i++) {
			dynamicObjects.get(i).draw();
		}
	}
}
