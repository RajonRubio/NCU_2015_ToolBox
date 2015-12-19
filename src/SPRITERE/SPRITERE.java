package SPRITERE;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import DOM.DOM;
import DOM.DynamicObject;

public class SPRITERE {

	DOM dom;
	
	public SPRITERE(DOM dom) {
		this.dom = dom;
	}
	
	public void updateAnimation(int delta) {
		ArrayList<DynamicObject> object = new ArrayList<DynamicObject>();
		object = dom.getAllDynamicObjects();
		for(DynamicObject o: object) {
			o.update(delta);
		}
	}
	
	public void renderSprite(Point2D clientLocation) {
		ArrayList<DynamicObject> object = new ArrayList<DynamicObject>();
		object = dom.getAllDynamicObjects();
		for(DynamicObject o: object) {
			o.render(dom.getVirtualCharacterXY());
		}
	}
}
