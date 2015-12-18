package SEmodule;

import java.awt.Image;
import java.util.Observable;
import java.util.Observer;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
/**********定義地圖 匯入圖片******/
public class BasicBlock {
	public int type;
	JLabel block;
	public Boolean touchable;  //如果要避免用到public 請用getter ssetter
	static int grass=0,lake=1,rock=2,tree=3;
	
	
	public BasicBlock() {
		type = grass;
		touchable = true;  //目前用不到
	}
//	public static Image[] tileImages = new Image[4];
//	void Readimage(){
//		try {
//			tileImages[grass] = ImageIO.read(new File("grass.png"));
//			tileImages[lake] = ImageIO.read(new File("lake.png"));
//			tileImages[rock] = ImageIO.read(new File("rock.png"));
//			tileImages[tree] = ImageIO.read(new File("tree.png"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
}
