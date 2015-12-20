package UIM;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.BlobbyTransition;

import TCPCM.TCPCM;

public class GameOver extends BasicGameState{
	Image[] background;
	Image playagain;
	Image Onplayagain;
	Image quit;
	Image Onquit;
	boolean MouseOnPlayAgain;
	boolean MouseOnQuit;
	int x,y;
	
	TCPCM tcpcm;
	
	public GameOver(TCPCM tcpcm) {
		this.tcpcm = tcpcm;
	}
	
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		background = new Image[2];
		background[0] = new Image("GameOver/BlueWin.png");
		background[1] = new Image("GameOver/RedWin.png");
		playagain = new Image("GameOver/PlayAgain.png");
		Onplayagain = new Image("GameOver/OnPlayAgain.png");
		quit = new Image("GameOver/Quit.png");
		Onquit = new Image("GameOver/OnQuit.png");

	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		g.drawImage(background[0], 0, 0);
		if(!MouseOnPlayAgain){
			g.drawImage(playagain, 337, 550);
		}else {
			g.drawImage(Onplayagain, 337, 550);
		}
		if(!MouseOnQuit){
			g.drawImage(quit, 408, 630);
		}else {
			g.drawImage(Onquit, 408, 630);
		}
		
		g.drawString("( "+ x +" , "+ y +")", 0, 0);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		x = gc.getInput().getMouseX();
		y = gc.getInput().getMouseY();
		
		MouseOnPlayAgain = false;
		MouseOnQuit = false;
		if(x>352&&x<587 && y>556&&y<590){
			MouseOnPlayAgain = true;
		}
		if(x>424&&x<528 && y>639&&y<672){
			MouseOnQuit = true;
		}
		
		if(gc.getInput().isMousePressed((Input.MOUSE_LEFT_BUTTON))){
			if(x>352&&x<587 && y>556&&y<590){
				sbg.enterState(0, null, new BlobbyTransition());
			}
			if(x>424&&x<528 && y>639&&y<672){
				System.exit(0);
			}
		}
		
		
	}

	@Override
	public int getID() {
		return 5;
	}

}
