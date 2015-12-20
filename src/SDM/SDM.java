package SDM;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;


public class SDM {
	public BasicBlock[][] scene = new BasicBlock[40][100]; //scene is a BasicBlock type array
	public double mapdata[][] = new double[40][100];  
	public ArrayList<WoodBox> woodbox = new ArrayList<WoodBox>(); //用一個ArrayList拿來存取木箱的資料
	public Image[] tileImages = new Image[5];
	public SDM() {
		BasicBlock[][] scene;
	}
	/**********load mapfile's content******/
	public void loadMap() {
//		String mapfile;
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
		
		for(int y=0;y<40;y++){
			for(int x=0;x<100;x++){
				scene[y][x] =new BasicBlock(); //全部宣告一次 給他記憶體空間
			}
		}
		
		for(int k=0;k<content.length;k++){
			if(istype==true){
				if(content[k][0] == 1){
					scene[(int)content[k][2]][(int)content[k][1]].type= BasicBlock.woodbox;
					scene[(int)content[k][2]][(int)content[k][1]].touchable = false;
					
					woodbox.add(new WoodBox((int)content[k][1], (int)content[k][2]) );
				}	
				if(content[k][0] == 2){
					scene[(int)content[k][2]][(int)content[k][1]].type= BasicBlock.rockbox;	
					scene[(int)content[k][2]][(int)content[k][1]].touchable = false;
					
				}
			}
		}
	}
	/*getMap工作就是回傳mapdata*/
	public double[][] getMap() {
		/*測試有沒有真的讀到*/
		for(int y=0;y<40;y++){
			for(int x=0;x<100;x++)
			{
				mapdata[y][x]=scene[y][x].type;
				System.out.print((int)mapdata[y][x] + " ");  //顯示方變 還是先轉型
				if(x==99)
					System.out.print('\n');
			}
		}
		for(int i=0;i<woodbox.size();i++){
				System.out.print((int)woodbox.get(i).location_x+" "+(int)woodbox.get(i).location_y + '\n');  //顯示方變 還是先轉型
		}
		return mapdata;
	}
	/* 這裏只負責type要放哪一種圖*/
	public void getBG() {
		try {
			tileImages[0] = ImageIO.read(new File("grass.png"));
			tileImages[1] = ImageIO.read(new File("woodbox.png"));
			tileImages[2] = ImageIO.read(new File("rockbox.png"));
			tileImages[3] = ImageIO.read(new File("woodbox_1.png"));
			tileImages[4] = ImageIO.read(new File("woodbox_2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/* 從UDP那邊讀來 哪些木牆被撞到了 然後要更新他們的type*/
	public void updateBoxes() {
		
	}
}
