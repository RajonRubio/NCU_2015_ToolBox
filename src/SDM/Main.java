package SDM;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;
import com.sun.corba.se.spi.orbutil.fsm.Input;

import DOM.DOM;
import SCENERE.SCENERE;

public class Main extends BasicGame{
	static AppGameContainer app ;
	public Main(String title) {
		super(title);
	}
	public static void main(String[] args) {
		try {
			app = new AppGameContainer(new Main("JCARD"));
			app.setDisplayMode(960 , 720 , false);
			app.setTargetFrameRate(60);
			app.setMouseGrabbed(false);
			app.setShowFPS(false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	SDM sdm = new SDM();
	DOM dom = new DOM();
//	SCENERE sre = new SCENERE();
	SCENERE sre = new SCENERE(dom);
	public void render(GameContainer arg0, Graphics arg1) throws SlickException {
		// TODO Auto-generated method stub
		sre.renderScene(arg1);
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub
		sre.getBG();
	}

	@Override
	public void update(GameContainer gc, int arg1) throws SlickException {
		// TODO Auto-generated method stub
		if(gc.getInput().isKeyDown(org.newdawn.slick.Input.KEY_S)){
			sre.charactor_location.y += 10;
			
		}
		if(gc.getInput().isKeyDown(org.newdawn.slick.Input.KEY_W)){
			sre.charactor_location.y -= 10;
		}
		if(gc.getInput().isKeyDown(org.newdawn.slick.Input.KEY_A)){
			sre.charactor_location.x -= 10;
			System.out.print((int)sre.charactor_location.x+" ");
//			System.out.print((int)(sre.charactor_location.x-80) %50+" ");
			
		}
		if(gc.getInput().isKeyDown(org.newdawn.slick.Input.KEY_D)){
			sre.charactor_location.x += 10;
			System.out.print((int)sre.charactor_location.x+" ");
//			System.out.print((int)(sre.charactor_location.x-80) %50+" ");
		}
	}
	
}
