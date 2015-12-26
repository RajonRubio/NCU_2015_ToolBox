package UIM;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;

import TCPCM.TCPCM;


public class Menu_login extends BasicGameState{
	Image Background ;
	Image Mouse;
	
	Image Author;
	Image OnAuthor;
	Image Guide;
	Image OnGuide;
	Image Gamestart;
	Image OnGamestart;
	Image IPWrong;
	Image IDWrong;
	Image unconnect;
	Image DupName;
	Image serverfull;
	
	java.awt.Font f;
	
	int time;
	int x;
	int y;
	int whichbackground;
	boolean MouseOnAuthor;
	boolean MouseOnGamestart;
	boolean MouseOnGuide;
	boolean InputIP;
	boolean InputID;
	String message;
	Font font;
	TextField IPInput ;
	TextField IDInput ;
	AppGameContainer app;
	StateBasedGame sbg;
	String IP;
	String ID;
	TCPCM tcpcm;
	
	boolean IPFormatOK;
	boolean IDFormatOK;
	boolean TryStart;
	boolean CantConnet;
	boolean NameisDup;
	boolean NoSeat;
	
	public Menu_login(AppGameContainer app,TCPCM tcpcm) {
		this.app = app;
		this.tcpcm = tcpcm;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		this.sbg = arg1;
		Background = new Image("img/UIM/Menu/noinput.png");
		Mouse = new Image("img/mouse.png");
		Author = new Image("img/UIM/Menu/author.png");
		OnAuthor = new Image("img/UIM/Menu/authorpressed.png");
		Guide = new Image("img/UIM/Menu/guide.png");
		OnGuide = new Image("img/UIM/Menu/guidepressed.png");
		Gamestart = new Image("img/UIM/Menu/gamestart.png");
		OnGamestart = new Image("img/UIM/Menu/gamestartpressed.png");
		IPWrong = new Image("img/UIM/Menu/IPWrong.png");
		IDWrong = new Image("img/UIM/Menu/IDWrong.png");
		unconnect = new Image("img/UIM/Menu/CantConnect.png");
		DupName = new Image("img/UIM/Menu/DupID.png");
		serverfull = new Image("img/UIM/Menu/peoplefull.png");

		f = new java.awt.Font("Sans", java.awt.Font.PLAIN,  20);
		font = new TrueTypeFont(f, false); 
		
		IPInput = new TextField(app, font, 377, 288, 275, 35, null);
		
		IDInput = new TextField(app, font, 377, 340, 275, 35, null);
		
		IPFormatOK = false;
		IDFormatOK = false;
		NameisDup = false;
		CantConnet = false;
		TryStart = false;
		NoSeat = false;
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		g.drawImage( Background , 0 , 0 ); //畫背景
		g.drawString("( "+ x +" , "+ y +")", 0, 0);
		g.setColor(Color.black);
		g.drawRect(377, 288, 275, 35); //IP框框
		g.drawRect(377, 340, 275, 35); //ID框框
		g.setColor(Color.white);
		IPInput.render(app, g);
		IDInput.render(app, g);

		if(!MouseOnGamestart){
			g.drawImage(Gamestart , 340 , 400 ); 
		}else{
			g.drawImage(OnGamestart , 340 , 400 );
		}
		if(!MouseOnGuide){
			g.drawImage(Guide , 340 , 480 ); 
		}else {
			g.drawImage(OnGuide , 340 , 480 );
		}
		if(!MouseOnAuthor){
			g.drawImage(Author , 340 , 560 ); 
		}else {
			g.drawImage(OnAuthor , 340 , 560 ); 
		}
		
		if(TryStart&&!IPFormatOK){
			g.drawImage(IPWrong, 655,280);
		}
		if(TryStart&&!IDFormatOK){
			g.drawImage(IDWrong, 655,330);
		}
		if(CantConnet){
			g.drawImage(unconnect, 720,0 );
		}
		if(NameisDup){
			g.drawImage(DupName, 720,0 );
		}
		if(NoSeat){
			g.drawImage(serverfull, 720,0 );
		}
		
		g.drawImage(Mouse, x-15, y-15);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {
		
		
		x = gc.getInput().getMouseX();
		y = gc.getInput().getMouseY();
		
		MouseOnAuthor = false;
		MouseOnGamestart = false;
		MouseOnGuide = false;
		
		if(x>364&&x<594 && y>430&&y<484){
			MouseOnGamestart = true;
		}
		if(x>364&&x<594 && y>508&&y<564){
			MouseOnGuide = true;
		}
		if(x>364&&x<594 && y>590&&y<646){
			MouseOnAuthor = true;
		}
		
		if(gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			if(x>=377&&x<=652&&y>=288&&y<=323){
				InputID = false;
				InputIP = true;
			}
			if(x>=377&&x<=652&&y>=340&&y<=375){
				InputIP = false;
				InputID = true;
			}
		}
		
		if(gc.getInput().isKeyDown(Input.KEY_Q)){
			System.out.println(IPInput.getText());
		}
		
		if(gc.getInput().isMouseButtonDown((Input.MOUSE_LEFT_BUTTON))){
			if(x>364&&x<594 && y>430&&y<484){
				TryStart = true;
				if(IPInput.getText().matches("^(?:[0-9]{1,3}.){3}[0-9]{1,3}$")) {
					IPFormatOK = true;
					System.out.println("格式正確"); 
				}    
		        else {
		        	IPFormatOK = false;
		        	System.out.println("格式錯誤");
		        }
				if(IDInput.getText().length()==0){
					IDFormatOK = false;
				}else {
					IDFormatOK = true;
				}
				if(IDFormatOK&&IPFormatOK){
					login(IPInput.getText(), IDInput.getText());
				}
				
			}
			if(x>364&&x<594 && y>508&&y<564){
				sbg.enterState(2,null, new HorizontalSplitTransition());
			}
			if(x>364&&x<594 && y>590&&y<646){
				sbg.enterState(1,null, new HorizontalSplitTransition());
			}
		}
	}
	
	private void login(String serverIP, String nickname) {
		boolean connected = this.tcpcm.connectServer(serverIP, nickname);
		if (!connected) {
			CantConnet = true;
			System.out.println("XXXX");
		}
	}
	
	public void GoNextState() { 
		this.IPFormatOK = false;
		this.IDFormatOK = false;
		this.NameisDup = false;
		this.CantConnet = false;
		this.TryStart = false;
		this.NoSeat = false ;
		this.sbg.enterState(3,null, new HorizontalSplitTransition());
	}
	
	public void DupName(){
		this.NameisDup = true;
	}
	
	public void ServerFull() {
		this.NoSeat = true ;
	}
	

	@Override
	public int getID() {
		return 0;
	}

}
