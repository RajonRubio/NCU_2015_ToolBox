package UIM;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.BlobbyTransition;

import Protocols.ResultInfo;
import Protocols.ResultInfo.Result;
import Protocols.Team;
import TCPCM.TCPCM;

public class GameOver extends BasicGameState{
	Image[] background;
	Image playagain;
	Image Onplayagain;
	Image quit;
	Image Onquit;
	Image Mouse;
	int RedKill;
	int BlueKill;
	java.awt.Font f0;
	java.awt.Font f1;
	java.awt.Font f2;
	Font font0;
	Font font1;
	Font font2;
	ResultInfo resultInfo;
	int redno;
	int blueno;
	
	RT r;
	boolean MouseOnPlayAgain;
	boolean MouseOnQuit;
	int x,y;
	
	TCPCM tcpcm;
	
	public GameOver(TCPCM tcpcm) {
		this.tcpcm = tcpcm;
	}
	
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		background = new Image[3];
		background[0] = new Image("GameOver/RedWin.png");
		background[1] = new Image("GameOver/BlueWin.png");
		background[2] = new Image("GameOver/Draw.png");
		playagain = new Image("GameOver/PlayAgain.png");
		Onplayagain = new Image("GameOver/OnPlayAgain.png");
		quit = new Image("GameOver/Quit.png");
		Onquit = new Image("GameOver/OnQuit.png");
		Mouse = new Image("mouse.png");
		
		f0 = new java.awt.Font("Script MT Bold", java.awt.Font.BOLD ,  60);
		f1 = new java.awt.Font("Jokerman", java.awt.Font.BOLD ,  35);
		f2 = new java.awt.Font("Shruti", java.awt.Font.PLAIN , 30);
		
		font0 = new TrueTypeFont(f0, true);
		font1 = new TrueTypeFont(f1, true);
		font2 = new TrueTypeFont(f2, true);
		
		resultInfo = new ResultInfo();
		resultInfo.people.add(resultInfo.new Result("RajonRubio", Team.RED, 20, 10));
		resultInfo.people.add(resultInfo.new Result("JxCode", Team.RED, 2, 4));
		
		resultInfo.people.add(resultInfo.new Result("AzureNight", Team.BLUE, 13, 6));
		resultInfo.people.add(resultInfo.new Result("STPig", Team.BLUE, 9, 1));
		
		UpdateKillResult();
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		
		
		if(RedKill>BlueKill){
			g.drawImage(background[0], 0, 0);
		}else if(BlueKill>RedKill){
			g.drawImage(background[1], 0, 0);
		}else {
			g.drawImage(background[2], 0, 0);
		}
		
		
		font0.drawString(380, 200, ""+RedKill ,Color.red);
		font0.drawString(500, 200, ""+BlueKill ,Color.cyan);
		
		redno = 0;
		blueno = 0;
		for(Result r:resultInfo.people){
			if(r.team==Team.RED){
				font1.drawString(50, 300+redno*100, r.name ,Color.red);
				font2.drawString(350, 310+redno*100, "" + r.kill +" / " + r.dead ,Color.red);
				redno++;
			}else {
				font1.drawString(500, 300+blueno*100, r.name ,Color.cyan);
				font2.drawString(800, 310+blueno*100, "" + r.kill +" / " + r.dead ,Color.cyan);
				blueno++;
			}
		}
		
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
		g.drawImage(Mouse, x-15, y-15);
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
	public void goNext(ResultInfo resultInfo){
		this.resultInfo = resultInfo;
	}
	
	public void UpdateKillResult() {
		for(Result r:resultInfo.people){
			if(r.team==Team.RED){
				RedKill += r.kill;
			}else {
				BlueKill += r.kill;
			}
		}
	}

	@Override
	public int getID() {
		return 5;
	}

}
