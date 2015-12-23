package UIM;

import DOM.DOM;
import SCENERE.SCENERE;
import SDM.SDM;

public class UIM {
	public UIM() {
		
	}
	
	public void gameOver() {
		
	}
	
	public static void main(String[] args) {
		System.out.println("Hello CDC");
		SDM sdm = new SDM();
		for(int y=0;y<40;y++){
			for(int x=0;x<100;x++)
			{
				System.out.print((int)sdm.mapdata[y][x] + " ");  //顯示方變 還是先轉型
				if(x==99)
					System.out.print('\n');
			}
		}
	}
}
