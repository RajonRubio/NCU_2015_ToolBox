package UIM;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;

import TCPCM.TCPCM;

public class RT extends BasicGameState{
	EventListener eventlistener;
	Image background;
	TCPCM tcpcm;
	
	public RT(TCPCM tcpcm) {
		this.tcpcm = tcpcm;
	}
	
	
	@Override
	public void init(GameContainer gc, StateBasedGame arg1) throws SlickException {
		background = new Image("BLACK.png");
		eventlistener = new EventListener();
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		g.drawImage(background, 0, 0);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (gc.getInput().isKeyDown(Input.KEY_P)){
			sbg.enterState(5,null,new HorizontalSplitTransition());
		}
		
		eventlistener.listent(gc,delta);
	}

	@Override
	public int getID() {
		return 4;
	}

}

