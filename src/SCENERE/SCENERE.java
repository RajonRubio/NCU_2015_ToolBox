package SCENERE;

import java.awt.Image;

import org.newdawn.slick.Graphics;

import DOM.DOM;
import SDM.SDM;

public class SCENERE {
//	public SCENERE(SDM sdm) {
//		Map = sdm.getmap
//	}
	SCENERE(DOM dom) {
		this.dom = dom;
	}
	DOM dom;
	SDM sdm = new SDM();
	int charactor_x;
	int charactor_y;
	int origin_x;
	int origin_y;
	int offset_x;
	int offset_y;
	double map[][] = new double[5][7];
	public void renderScene(Graphics g) {
		sdm.loadMap();
		sdm.getBG();
		dom.getVirtualCharacterXY();
		charactor_x = dom.virtualCharactor.x;
		charactor_y = dom.virtualCharactor.y;
		origin_x = (charactor_x-350)/100;
		origin_y = (charactor_y-250)/100;
		offset_x = (charactor_x)%100;
		offset_y = (charactor_y)%100;
		int i=0,j=0;//畫圖的左上角
		for(int y=origin_y;y<origin_y+5;y++,j++){     //這裏要來控制人物所在位置的
			i=0;
			for(int x=origin_x;x<origin_x+7;x++,i++){
				g.drawImage(sdm.tileImages[ (int) sdm.mapdata[y][x] ], (i-1)*100-( (charactor_x-50) %100), (j-1)*100-( (charactor_y-50) %100));
//				g.drawImage(sdm.tileImages[ (int) sdm.mapdata[y][x] ], (i-1)*100-( (charactor_x-50) %100) , (j-1)*100-( (charactor_y-50) %100) , null);
				/*(charactor-50)%100是偏移量 
				 * -50是因為調整 讓原點的350,2500  
				 * sdm.mapdata會是一個數字 代表地圖型別
				 */
				map[j][i]= sdm.mapdata[y][x];
				System.out.print(map[j][i]);  //test用的  不然finalproject用不到這個
				if(i==6){
					System.out.print('\n');
				}
			}
		}
	}
   
}
