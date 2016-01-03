package RT;

import java.awt.Font;
import java.net.SocketException;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;

import DOM.DOM;
import SCENERE.SCENERE;
import SPRITERE.SPRITERE;
import TCPCM.TCPCM;
import UDPUS.UDPUS;
import UIM.GameOver;

public class RT extends BasicGameState{
	EventListener eventlistener;
	TCPCM tcpcm;
	DOM dom;
	SPRITERE spritere;
	SCENERE scenere;
	UDPUS udpus;
	GameOver gameover;
	boolean debuff[] = {true, true};
	int[] kill = new int[2];
	Image gameStatus;
	Font font;
	TrueTypeFont ttf;
	Font timeFont;
	TrueTypeFont timeTtf;
	double time = 120;
	boolean openTab = false;
	boolean isover = false;
	Image Mouse;
	int mouseX;
	int mouseY;

	
	public RT(TCPCM tcpcm, GameOver gameover, DOM dom) {
		this.tcpcm = tcpcm;
		this.gameover = gameover;
		this.dom = dom;
		spritere = new SPRITERE(dom);
		scenere = new SCENERE(dom);
		try {
			udpus = new UDPUS(5000, dom);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void init(GameContainer gc, StateBasedGame arg1) throws SlickException {
		Mouse = new Image("img/mouse.png");
		font = new Font("Time new Roman", Font.BOLD, 50);
		ttf = new TrueTypeFont(font, true);
		timeFont = new Font("Time new Roman", Font.BOLD, 40);
		timeTtf = new TrueTypeFont(timeFont, true);
		gameStatus = new Image("img/game/GameState.png");
		eventlistener = new EventListener(tcpcm);
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 8; j++) {
				String src = "img/char/" + (i+1) + "-" + (j+1) + ".png";
				dom.charSheet[i][j] = new SpriteSheet(src, 32, 32);
				dom.charAnimation[i][j] = new Animation(dom.charSheet[i][j], 200);
			}
		}
		for(int i = 0; i < 2; i++) {
			for(int j = 0; j < 4; j++) {
				String src = "img/bullet/" + (i+1) + "-" + (j+1) + ".png";
				dom.bulletSheet[i][j] = new SpriteSheet(src, 32, 32);
				dom.bulletAnimation[i][j] = new Animation(dom.bulletSheet[i][j], 200);
			}
		}
		dom.debuff[0] = new Image("img/game/Choas.png");
		dom.debuff[1] = new Image("img/game/OnFire.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics g) throws SlickException {
		scenere.renderScene(g);
		spritere.renderSprite(g);
		if(openTab == true) {
			g.drawImage(gameStatus, 204, 0);
			ttf.drawString(310, 50, ""+kill[0], Color.red);
			ttf.drawString(540, 50, ""+kill[1], Color.blue);
			if(time >= 100) {
				timeTtf.drawString(430, 55, ""+(int)time, Color.white);
			}
			else if(time >= 10) {
				timeTtf.drawString(444, 55, ""+(int)time, Color.white);
			}
			else {
				timeTtf.drawString(458, 55, ""+(int)time, Color.white);
			}	
		}
		g.drawImage(Mouse, mouseX-15, mouseY-15);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		mouseX = gc.getInput().getMouseX();
		mouseY = gc.getInput().getMouseY();
		eventlistener.listent(gc, delta);
		openTab = false;
		if(gc.getInput().isKeyDown(Input.KEY_SPACE)){
			openTab = true;
		}
		kill = dom.getKills();
		if(time >= 0) {
			time -= delta * 0.001;	
		}
		spritere.updateAnimation(delta);
		if(isover == true) {
			sbg.enterState(5, null, new HorizontalSplitTransition());
		}
	}

	@Override
	public int getID() {
		return 4;
	}
	
	public void goNext() {
		gameover.goNext(dom.getFinalResult());
		isover = true;
	}
	
	public void claimKill(String killer, String victim) {
		ttf.drawString(100, 200, killer + " kill " + victim + "!");
	}
}

