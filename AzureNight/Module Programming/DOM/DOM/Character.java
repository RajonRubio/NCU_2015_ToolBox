package DOM;

import java.awt.image.BufferedImage;

public class Character extends DynamicObject {
	public int clientno;
	public String ID;
	public boolean isLocalClient;
	
	public int x;
	public int y;
	public int dir;
	public int speed;
	
	public BufferedImage spriteSheet;
	public int width;
	public int height;
	
	Character(int clientno, boolean isLocalClient) {
		this.clientno = clientno;
		this.isLocalClient = isLocalClient;
	}
	
	@Override
	public void draw() {
		System.out.println("Character clientno: " + clientno);
	}
}
