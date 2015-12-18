package SEmodule;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.PaintContext;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/*****
  Scene Render Engine simply accesses the map data structure and paint the image blocks which can be seen by the view port. 
  The virtual character (controlled by this client computer) should always appear in the center of the view port. 
  In this module programming exercise. You should prepare at least 4 kinds of image background blocks. 
  plain grass 綠草
  tree  樹木
  water 湖泊
  rock 岩石
*****/
public class SceneRenderEngine extends JPanel {
	// called by Render Thread
	// This method calls getVirtualCharacterXY() of DOM to get the current X,Y 
	// of virtual character (controlled by this client computer). Use the X,Y to decide 
	// which part of the map should be appeared in the viewport and 
	// paint the image blocks accordingly
	public SceneRenderEngine(DOM dom) {
		this.dom = dom;//
	}
	public DOM dom;//
	public SceneDataModule sdm = new SceneDataModule();
	public Image[] tileImages = new Image[4];
	public int charactor_x;
	public int charactor_y;
	public int origin_x;
	public int origin_y;
	public int offset_x;
	public int offset_y;
	public int map[][] = new int[5][7];
	public void renderScene(){
		sdm.loadMap("map.txt");
		dom.getVirtualCharactor();
		charactor_x=dom.virtualCharactor.x;
		charactor_y=dom.virtualCharactor.y;//人物的位置 應為圖的正中間  我先寫死 之後要call getVirtualCharactor
		origin_x = (charactor_x-350)/100;
		origin_y = (charactor_y-250)/100;
		offset_x = (charactor_x)%100;
		offset_y = (charactor_y)%100;
	
		int i=0,j=0;//畫圖的左上角   //test方便用的
		for(int y=origin_y;y<origin_y+5;y++,j++){     //這裏要來控制人物所在位置的
			i=0;
			for(int x=origin_x;x<origin_x+7;x++,i++){
				map[j][i]=sdm.mapdata[y][x];
				System.out.print(map[j][i]);  //test用的  不然finalproject用不到這個
				if(i==6){
					System.out.print('\n');
				}
			}		
		}
		repaint();
	}
//	@Override
//	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		sdm.loadMap("map.txt");
//		
//		try {
//			tileImages[0] = ImageIO.read(new File("grass.png"));
//			tileImages[1] = ImageIO.read(new File("lake.png"));
//			tileImages[2] = ImageIO.read(new File("rock.png"));
//			tileImages[3] = ImageIO.read(new File("tree.png"));
//		}catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		};
//		fake.getVirtualCharactor();
//		int charactor_x=fake.virtualCharactor.x;
//		int charactor_y=fake.virtualCharactor.y;//人物的位置 應為圖的正中間  我先寫死 之後要call getVirtualCharactor
//		origin_x = (charactor_x-350)/100;
//		origin_y = (charactor_y-250)/100;  
//		int i=0,j=0;//畫圖的左上角
//		for(int y=origin_y;y<origin_y+5;y++,j++){     //這裏要來控制人物所在位置的
//			i=0;
//			for(int x=origin_x;x<origin_x+7;x++,i++){
//				g.drawImage(tileImages[ sdm.mapdata[y][x] ], (i-1)*100- (charactor_x%100) , (j-1)*100-(charactor_y%100) , null);//100是關鍵 而且位置可以用-的
//				map[j][i]=sdm.mapdata[y][x];
//				System.out.print(map[j][i]);  //test用的  不然finalproject用不到這個
//				if(i==6){
//					System.out.print('\n');
//				}
//			}
//		}
//	}
//	FinalProject的時候 可以直接把下面這些拿來用唷～～～
//  繪圖部分 還有一些問題(偏移量的調整)
}
