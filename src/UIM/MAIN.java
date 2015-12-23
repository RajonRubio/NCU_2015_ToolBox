package UIM;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import TCPCM.TCPCM;


public class MAIN extends StateBasedGame{
	static AppGameContainer app ;
	private TCPCM tcpcm;
	
	public MAIN(String name) {
		super(name);
		tcpcm = new TCPCM(this);
	}

	public static void main(String[] args) {
		try {
			
			app = new AppGameContainer(new MAIN("JCARD"));
			app.setMouseGrabbed(false);
			app.setDisplayMode(960 , 720 , false);
			app.setTargetFrameRate(60);
			app.setMouseGrabbed(true);
			app.setShowFPS(false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		
		GameOver gameOver = new GameOver(tcpcm);
		RT r = new RT(tcpcm , gameOver);
		//this.addState(new Menu_login(app, tcpcm));
		//this.addState(r);
		//this.addState(gameOver);
		//this.addState(new ChooseTeam(tcpcm));	
		//this.addState(new AuthorList());
		this.addState(new Guide());
		
	}

}
