package UIM;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.BlobbyTransition;

public class Guide extends BasicGameState{
	int x,y;
	Image Mouse;
	Image Background;
	Image BackOn;
	Image Back;
	boolean MouseOnBack;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		Background = new Image("img/UIM/Guide/GuideList.png");
		Back = new Image("img/UIM/AuthorList/BACK.png");
		BackOn = new Image("img/UIM/AuthorList/OnBACK.png");
		Mouse = new Image("img/mouse.png");
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		g.drawImage( Background , 0 , 0 ); 
		if(!MouseOnBack){
			g.drawImage(Back , 340 , 560 ); 
		}else {
			g.drawImage(BackOn , 340 , 560 ); 
		}
		
		g.drawImage(Mouse, x-15, y-15);
		g.drawString("( "+ x +" , "+ y +")", 0, 0);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {

		x = gc.getInput().getMouseX();
		y = gc.getInput().getMouseY();
		
		MouseOnBack = false;
		
		if(x>360&&x<588 && y>577&&y<638){
			MouseOnBack = true;
		}
		
		if(gc.getInput().isMousePressed((Input.MOUSE_LEFT_BUTTON))){
			System.out.println("YYYYYYYY");
			if(x>360&&x<588 && y>577&&y<638){
				sbg.enterState(0, null, new BlobbyTransition());
			}
		}
		
	}

	@Override
	public int getID() {
		return 2;
	}

}
