package SEmodule;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.text.AbstractDocument.Content;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import sun.net.www.content.audio.x_aiff;

/*******This module is responsible for keep data of the background (scene) and map.*****/
//called by main program of client computer.
//this function load a map from a file name mapfile
//please design your own map file format.    
//in this module programming project we do not enforce any standard 
//on the map file format.
//When reading the map, all the necessary background image files
//should be loaded as well.
//When the map file is loaded, a map data structure is constructed.

public class SceneDataModule {
	//****宣告成public 是為了test方便  保險的方式是用getter setter
	public BasicBlock[][] scene = new BasicBlock[20][50]; //scene is a BasicBlock type array
	public int mapdata[][] = new int[20][50];  
	public SceneDataModule() {
		BasicBlock[][] scene;
	}
	public void loadMap(String mapfile){
		//**********load mapfile's content******/
		try{
			FileReader fr = new FileReader("map.txt"); 
			BufferedReader br = new BufferedReader(fr);
			String str,tempstr;
			String[] tempArray= new String[3];
			ArrayList myList = new ArrayList();
			
			int i=0;
			while((str = br.readLine())!=null)
			{
				//br.readstr()是指讀取txt檔的每一行資料,把讀到的資料存到str
				//再將str丟給tempstr去儲存
			    tempstr = str; 
			    //因為我這個test檔的資料格式是-->一行有3個字串，用兩個空白鍵隔開，
			    //tempstr.split("\\s") 會依照空白鍵來切割,剛好切三個,所以這邊我的tempArray的大小才會宣告3
			    tempArray = tempstr.split("\\s");
			              
			    //這邊就是按照順序,一行一行的儲存到動態陣列裡面
			    for(i=0;i< tempArray.length;i++)
			    {          
			    	myList.add(tempArray[i]);
			    }
			 }		   
			     //這邊的除3,和矩陣的需告大小,其實就是上面講的 "tempArray.length"這個值來做決定的
			     //y小於3,也是從"tempArray.length"的概念來的
			 int k = myList.size()/3;
			 int count=0;
			 double[][] content = new double[k][3];
			    
			 for(int x=0;x<myList.size()/3;x++)
			 {
				 for(int y=0;y<3;y++)
			     {
					 content[x][y]=Double.parseDouble((String) myList.get(count));
			         count++; //一個index來決定myList讀取值的位置
			         //System.out.println("x:"+ x + " y:"+y+" "+content[x][y]);
			         //所以要處理的就是content
			     }
			 }
			 constructMap(content);
			 br.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**********建構地圖 並存起來******/
	void constructMap(double content[][]){
		boolean istype=true;
		
		for(int y=0;y<20;y++){
			for(int x=0;x<50;x++){
				scene[y][x] =new BasicBlock(); //全部宣告一次 給他記憶體空間
			}
		}
		
		for(int k=0;k<content.length;k++){
			if(istype==true){
				if(content[k][0] == 1)
					scene[(int)content[k][2]][(int)content[k][1]].type= BasicBlock.lake;
				if(content[k][0] == 2)
					scene[(int)content[k][2]][(int)content[k][1]].type= BasicBlock.rock;
				if(content[k][0] == 3)
					scene[(int)content[k][2]][(int)content[k][1]].type= BasicBlock.tree;
				if(content[k][0] > 3 || content[k][0] <=0)//如果超過範圍要怎麼辦？？exception
					istype=false;			
			}
		}

		for(int y=0;y<20;y++){
			for(int x=0;x<50;x++)
			{
				mapdata[y][x]=scene[y][x].type;
				System.out.print(mapdata[y][x] + " ");
				if(x==49)
					System.out.print('\n');
			}
		}
	}
	public Image[] tileImages = new Image[4];
	public void Readimage(){
		try {
			tileImages[0] = ImageIO.read(new File(filemanager(0) ));
			tileImages[1] = ImageIO.read(new File(filemanager(1) ));
			tileImages[2] = ImageIO.read(new File(filemanager(2) ));
			tileImages[3] = ImageIO.read(new File(filemanager(3) ));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String filemanager(int i){
		String filename = null;
		if(i==0)
			filename = "grass.png" ;
		if(i==1)
			filename = "lake.png";
		if(i==2)
			filename = "rock.png";
		if(i==3)
			filename = "tree.png";
			return filename; 
	}
}

//filemanager class
