package RT;

import java.awt.Font;
import java.awt.geom.Point2D;

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

import DOM.Bullet;
import DOM.DOM;
import Protocols.ResultInfo;
import Protocols.Role;
import Protocols.Team;
import SPRITERE.SPRITERE;
import TCPCM.TCPCM;
import UDPSM.UDPSM;

public class RT extends BasicGameState{
	EventListener eventlistener;
	TCPCM tcpcm;
	DOM dom;
	SPRITERE spritere;
	UDPSM udpsm;
	double x = 700;
	double y = 700;
	boolean debuff[] = {true, true};
	int[] kill = new int[2];
	Image gameStatus;
	Font font;
	TrueTypeFont ttf;
	Font timeFont;
	TrueTypeFont timeTtf;
	double time = 120;
	boolean openTab = false;

	
	public RT() {
		//this.tcpcm = tcpcm;
		dom = new DOM();
		spritere = new SPRITERE(dom);
		//udpsm = new UDPSM(dom);

	}
	
	public RT(TCPCM tcpcm) {
		this.tcpcm = tcpcm;
		dom = new DOM();
		spritere = new SPRITERE(dom);
		//udpsm = new UDPSM(dom);
	}
	
	
	@Override
	public void init(GameContainer gc, StateBasedGame arg1) throws SlickException {
		//tcpcm.gameStart(dom);
		font = new Font("Time new Roman", Font.BOLD, 50);
		ttf = new TrueTypeFont(font, true);
		timeFont = new Font("Time new Roman", Font.BOLD, 40);
		timeTtf = new TrueTypeFont(timeFont, true);
		gameStatus = new Image("img/game/GameState.png");
		eventlistener = new EventListener();
		dom.setClientno(10);
		try {
			String src = "img/char/" + 1 + "-" + 1 + ".png";
			dom.charSheet[0][0] = new SpriteSheet(src, 32, 32);
			dom.charAnimation[0][0] = new Animation(dom.charSheet[0][0], 200);
			dom.addVirtualCharacter(10, "123", Team.BLUE, Role.Archer, new Point2D.Double(100, 100));
			dom.updateVirtualCharacter(10, 0, new Point2D.Double(500.2, 500.2), 100, 0, debuff, 50, 10);
			src = "img/char/" + 2 + "-" + 2 + ".png";
			dom.charSheet[1][1] = new SpriteSheet(src, 32, 32);
			dom.charAnimation[1][1] = new Animation(dom.charSheet[1][1], 200);
			dom.addVirtualCharacter(3, "234", Team.RED, Role.Wizard, new Point2D.Double(0, 0));
			dom.updateVirtualCharacter(3, 1, new Point2D.Double(x, 700), 100, 0, debuff, 100, 100);
			src = "img/bullet/" + 2 + "-" + 1 + ".png";
			dom.bulletSheet[1][0] = new SpriteSheet(src, 32, 32);
			dom.bulletAnimation[1][0] = new Animation(dom.bulletSheet[1][0], 200);
			dom.bullet.add(new Bullet(Team.RED, Role.Archer, new Point2D.Double(400, 700), dom.bulletAnimation[1][0]));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
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
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
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
		x -= 0.2 * delta;
		y -= 0.2 * delta;
		try {
			dom.updateVirtualCharacter(3, 1, new Point2D.Double(x, 700), 50, 0, debuff, 100, 100);
			dom.bullet.clear();
			dom.bullet.add(new Bullet(Team.RED, Role.Archer, new Point2D.Double(400, y), dom.bulletAnimation[1][0]));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int getID() {
		return 2;
	}
	
	public ResultInfo getFinalResult() {
		return dom.getFinalResult();
	}
}

