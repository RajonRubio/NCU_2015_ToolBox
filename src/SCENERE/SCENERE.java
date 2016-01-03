package SCENERE;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import DOM.DOM;
import Protocols.Role;
import Protocols.Team;
import SDM.SDM;

public class SCENERE {
	public SCENERE(DOM dom) {
		this.dom = dom;
		this.getBG();
	}
	DOM dom;
	public SDM sdm = new SDM();
	private double origin_x;  //viewport最左上角要畫的圖
	private double origin_y;
	double offset_x;  //偏移量 
	double offset_y;
	public Point2D.Double charactor_location ;//拿來接DOM.getvirtualcharactorXY
	double viewport[][] = new double[17][22]; //單一玩家的視窗畫面[y][x]
	public Image[] tileImages = new Image[5]; //宣告成slick2D的image格式
	public void renderScene(Graphics g) {
		charactor_location = dom.getVirtualCharacterXY(); //這樣就接到從DOM來的point了
		
		origin_x = (charactor_location.x-480)/50;   //BUG (479-480)/50 = 0
		origin_y = (charactor_location.y-360)/50;
		if(charactor_location.x<480 && charactor_location.x >80){
			origin_x = origin_x -1;
		}
		if(charactor_location.y<360 && charactor_location.y >60){
			origin_y = origin_y -1;
		}  
		
		int i=0,j=0;//畫圖的左上角
		for(int y=(int)origin_y;y<origin_y+17;y++,j++){     //這裏要來控制人物所在位置的
			i=0;
			for(int x=(int)origin_x;x<origin_x+22;x++,i++){
				if(origin_x+i<0||origin_y+j<0||origin_x+i>=100||origin_y+j>=40){
					g.drawImage(tileImages[2],(float)( (i-1)*50-( (charactor_location.x-80) %50) ),(float)( (j-1)*50-( (charactor_location.y-60) %50) ) );
				}
				else{
				g.drawImage(tileImages[ (int) sdm.mapdata[y][x] ],(float)( (i-1)*50-( (charactor_location.x-80) %50) ),(float)( (j-1)*50-( (charactor_location.y-60) %50) ) );
				/*(charactor-80)%50是偏移量 
				 * -80是因為調整 讓原點的480  
				 * sdm.mapdata會是一個數字 代表地圖型別
				 */
				}
			}
		}
	}
	/* 這裏只負責type要放哪一種圖
	 * 裡面的Image slick2Dimage*/
	public void getBG() {
		try {
			tileImages = new Image[5];//
			tileImages[0] = new Image("img/map/grass.png");
			tileImages[1] = new Image("img/map/woodbox.png");
			tileImages[2] = new Image("img/map/rockbox.png");
			tileImages[3] = new Image("img/map/woodbox_1.png");
			tileImages[4] = new Image("img/map/woodbox_2.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
