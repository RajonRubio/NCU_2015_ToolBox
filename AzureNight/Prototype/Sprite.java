package Animation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sprite {
	private BufferedImage spriteSheet;
	private int width;
	private int height;
	
	public Sprite(int width, int height, String file) {
		this.height = height;
		this.width = width;
		try {
			spriteSheet = ImageIO.read(new File("src/" + file + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getSprite(int xGrid, int yGrid) {
		return spriteSheet.getSubimage(xGrid * width, yGrid * height, width, height);
	}
}
