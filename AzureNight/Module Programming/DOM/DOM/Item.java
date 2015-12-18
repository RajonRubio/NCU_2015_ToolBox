package DOM;

import java.awt.image.BufferedImage;

public class Item extends DynamicObject {
	public String name;
	public int index;
	public int x;
	public int y;
	public boolean shared;
	public int owner = -1;
	
	public BufferedImage spriteSheet;
	public int width;
	public int height;
	
	Item(String name, int index, int x, int y, boolean shared) {
		this.name = name;
		this.index = index;
		this.x = x;
		this.y = y;
		this.shared = shared;
		//再根據不同的name，使用ImageIO.read(new File(/path/of/picture))來載入對應的物件圖片即可
	}
	
	@Override
	public void draw() {
		System.out.println("Item index: " + index);
	}
	
}
