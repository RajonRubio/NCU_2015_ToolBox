package SPRITERE;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

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
	
	public void renderSprite(Graphics g) {
		ArrayList<DynamicObject> object = new ArrayList<DynamicObject>();
		object = dom.getAllDynamicObjects();
		for(DynamicObject o: object) {
			o.render(g, dom.getVirtualCharacterXY());
		}
	}
}
