package RT;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class MAIN extends StateBasedGame{
	static AppGameContainer app ;
	
	public MAIN(String name) {
		super(name);
	}

	public static void main(String[] args) {
		try {
			app = new AppGameContainer(new MAIN("JCARD"));
			app.setDisplayMode(960 , 720 , false);
			app.setTargetFrameRate(60);
			app.setMouseGrabbed(false);
			app.setShowFPS(false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		this.addState(new RT());
	}

}
