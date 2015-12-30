package UIM;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;

import DOM.DOM;
import TCPCM.TCPCM;

public class RT extends BasicGameState{
	EventListener eventlistener;
	Image background;
	TCPCM tcpcm;
	DOM dom;
	GameOver gameOver;
	Image Mouse;
	int x,y;
	
	public RT(TCPCM tcpcm,GameOver gameOver,DOM dom) {
		this.tcpcm = tcpcm;
		this.gameOver = gameOver;
		this.dom = dom;
	}
	
	
	@Override
	public void init(GameContainer gc, StateBasedGame arg1) throws SlickException {
		background = new Image("img/BLACK.png");
		eventlistener = new EventListener();
		Mouse = new Image("img/mouse.png");
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		g.drawImage(background, 0, 0);
		g.drawImage(Mouse, x-15, y-15);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		x = gc.getInput().getMouseX();
		y = gc.getInput().getMouseY();
		
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

