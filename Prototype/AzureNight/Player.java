package Animation;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Player extends JFrame implements KeyListener {
	private Sprite sprite = new Sprite(32, 48, "Image");
	
	private BufferedImage[] walkingDown = new BufferedImage[4];
	private BufferedImage[] walkingLeft = new BufferedImage[4];
	private BufferedImage[] walkingRight = new BufferedImage[4];
	private BufferedImage[] walkingTop = new BufferedImage[4];
	private BufferedImage[] standing = new BufferedImage[1];
	
	private Animation walkDown;
	private Animation walkLeft;
	private Animation walkRight;
	private Animation walkTop;
	private Animation stand;
	
	private Animation animation;
	
	public static void main(String[] args) {
		new Player();
	}
	
	public Player() {
		for(int i = 0; i < 4; i++) {
			walkingDown[i] = sprite.getSprite(i, 0);
			walkingLeft[i] = sprite.getSprite(i, 1);
			walkingRight[i] = sprite.getSprite(i, 2);
			walkingTop[i] = sprite.getSprite(i, 3);
		}
		standing[0] = sprite.getSprite(0, 0);
		walkDown = new Animation(walkingDown, 100);
		walkLeft = new Animation(walkingLeft, 100);
		walkRight = new Animation(walkingRight, 100);
		walkTop = new Animation(walkingTop, 100);
		stand = new Animation(standing, 100);
		animation = stand;
		setSize(300, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(this);
		setVisible(true);
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		switch (keyCode) {
			case KeyEvent.VK_UP:
				if(animation != walkTop) {
					animation.reset();
					animation = walkTop;
					animation.start();
				}
				break;
			case KeyEvent.VK_LEFT:
				if(animation != walkLeft) {
					animation.reset();
					animation = walkLeft;
					animation.start();
				}
				break;
			case KeyEvent.VK_DOWN:
				if(animation != walkDown) {
					animation.reset();
					animation = walkDown;
					animation.start();
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(animation != walkRight) {
					animation.reset();
					animation = walkRight;
					animation.start();
				}
				break;
			case KeyEvent.VK_SPACE:
				animation.reset();
				animation = stand;
				break;
			default:
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
	
	Image dbImage;
	Graphics dbg;
	
	public void paint(Graphics g) {
		dbImage = createImage(getWidth(), getHeight());
		dbg = dbImage.getGraphics();
		paintComponent(dbg);
		g.drawImage(dbImage , 0, 0, null);
	}
	
	public void paintComponent(Graphics g) {
		if(animation != null) {
			animation.update();
			g.drawImage(animation.getSprite() , 125, 125, null);
		}
		repaint();
	}
}
