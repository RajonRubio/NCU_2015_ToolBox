package SDM;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SDM {
	public BasicBlock[][] scene = new BasicBlock[40][100]; //scene is a BasicBlock type array
	public double mapdata[][] = new double[40][100];  
//	public ArrayList<WoodBox> woodbox = new ArrayList<WoodBox>(); //用一個ArrayList拿來存取木箱的資料 但是不用了

	public SDM() {
		BasicBlock[][] scene;
		loadMap();
		getMap();
	}
	/**********load mapfile's content******/
	public void loadMap() {
		try{
			FileReader fr = new FileReader("mapdata/map.txt"); 
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
			 /*conclutuin:
			  *after calling loadmap()
			  *would get double[][] content
			  *content[][0]:map type
			  *content[][1]:map's x location
			  *content[][2]:map's y locattion
			  */
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**********建構地圖 並存起來******/
	void constructMap(double content[][]){	
		for(int y=0;y<40;y++){
			for(int x=0;x<100;x++){
				scene[y][x] =new BasicBlock(); //全部宣告一次 給他記憶體空間
			}
		}
		try {
			for(int k=0;k<content.length;k++){
				if(content[k][0] == 1){
					scene[(int)content[k][2]][(int)content[k][1]].type= BasicBlock.woodbox;
					scene[(int)content[k][2]][(int)content[k][1]].touchable = false;
				}	
				if(content[k][0] == 2){
					scene[(int)content[k][2]][(int)content[k][1]].type= BasicBlock.rockbox;	
					scene[(int)content[k][2]][(int)content[k][1]].touchable = false;
				}
				//只要地圖格式錯了叫要throw exception
				if(content[k][0] >2 || content[k][0] <0){
					throw new Exception("map.txt has wrong format");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*getMap工作就是回傳mapdata*/
	public double[][] getMap() {
		/*測試有沒有真的讀到*/
		for(int y=0;y<40;y++){
			for(int x=0;x<100;x++)
			{
				mapdata[y][x]=scene[y][x].type;
//				System.out.print((int)mapdata[y][x] + " ");  //顯示方變 還是先轉型
//				if(x==99)
//					System.out.print('\n');
			}
		}
		return mapdata;
	}
	/*所以別人想要地圖資訊
	 *就是要先 new SDM()
	 *然後直接拿 mapdata[][]
	 */

	/* 從UDP那邊讀來 哪些木牆被撞到了 然後要更新他們的type*/
	public void updateBoxes(ArrayList update_boxes) {
		
	}
}
