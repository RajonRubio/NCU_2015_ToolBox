package UIM;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;

import com.sun.org.apache.regexp.internal.recompile;

import Protocols.TeamState;
import Protocols.TeamState.Member;
import TCPCM.TCPCM;

public class ChooseTeam extends BasicGameState{
	int x,y;
	TeamState teamState;
	
	
	Image Background;
	Image Leftteam;
	Image Leftteamlight;
	Image Rightteam;
	Image Rightteamlight;
	
	Image Archer;
	Image Archerlight;
	Image Wizard;
	Image Wizardlight;
	Image Marines;
	Image Marineslight;
	Image Cannon;
	Image Cannonlight;
	
	Image Choose;
	Image Go;
	Image OnGo;
	
	boolean MouseOnLeft;
	boolean MouseOnRight;
	
	boolean MouseOnArcher;
	boolean MouseOnWizard;
	boolean MouseOnMarines;
	boolean MouseOnCannon;
	boolean MouseOnGo;
	
	boolean LockLeft;
	boolean LockRight;
	
	boolean LockArcher;
	boolean LockWizard;
	boolean LockMarines;
	boolean LockCannon;
	
	boolean LockTeam;
	boolean LockHero;
	
	boolean playagain;	
	boolean IsReady;
	
	int TeamImageY = 0;
	int HeroImageY = 450;
	
	int PlayerYstart = 368;
	
	TCPCM tcpcm;
	StateBasedGame sbg;

	public ChooseTeam(TCPCM tcpcm) {
		this.tcpcm = tcpcm;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		Background = new Image("ChooseTeam/Choose.png");
		Leftteam = new Image("ChooseTeam/Leftteam.png");
		Leftteamlight = new Image("ChooseTeam/Leftteamlight.png");
		Rightteam = new Image("ChooseTeam/Rightteam.png");
		Rightteamlight = new Image("ChooseTeam/Rightteamlight.png");
		
		Archer = new Image("Hero/Archer.png");       
		Archerlight = new Image("Hero/OnArcher.png");  
		Wizard = new Image("Hero/Wizard.png");      
		Wizardlight = new Image("Hero/OnWizard.png");  
		Marines = new Image("Hero/Marines.png");      
		Marineslight = new Image("Hero/OnMarines.png"); 
		Cannon = new Image("Hero/Cannon.png");       
		Cannonlight = new Image("Hero/OnCannon.png"); 
		
		Choose = new Image("ChooseTeam/Title.png");
		Go = new Image("ChooseTeam/GO.png");
		OnGo = new Image("ChooseTeam/OnGO.png");
		
		LockLeft = false;
		LockRight = false;
		
		LockArcher = false; 
		LockWizard = false;
		LockMarines = false;  
		LockCannon = false;  
		
		LockTeam = false;
		LockHero = false;
		
		playagain = false;
		
		IsReady = false;
		this.sbg = arg1;

		teamState = new TeamState();
		
		//teamState.blue.add(teamState.new Member("jxcode", "192.168.0.0", "Archer"));
		teamState.blue.add(teamState.new Member("S.T.Pig", "192.168.0.0", "Wizard" , true));
		
		//teamState.red.add(teamState.new Member("AzureNight", "192.168.0.0", "Marines"));
		teamState.red.add(teamState.new Member("RajonRubio", "192.168.0.0", "Cannon",true));
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		g.drawImage( Background , 0 , 0 ); //µe­I´º
		
		if(LockLeft){
			g.drawImage(Leftteamlight , 0 , TeamImageY ); 
			g.drawImage(Rightteam , 480 , TeamImageY ); 
		}else if(LockRight){
			g.drawImage(Rightteamlight , 480 , TeamImageY ); 
			g.drawImage(Leftteam , 0 , TeamImageY ); 
		}else{
			if(!MouseOnLeft){
				g.drawImage(Leftteam , 0 , TeamImageY ); 
			}else {
				g.drawImage(Leftteamlight , 0 , TeamImageY ); 
			}
			if(!MouseOnRight){
				g.drawImage(Rightteam , 480 , TeamImageY ); 
			}else {
				g.drawImage(Rightteamlight , 480 , TeamImageY ); 
			}
		}
	
		
		if(LockArcher){
			g.drawImage(Archerlight , 0 , HeroImageY ); 
			g.drawImage(Wizard , 240 , HeroImageY ); 
			g.drawImage(Marines , 480 , HeroImageY );
			g.drawImage(Cannon , 720 , HeroImageY );  
		}else if(LockWizard){
			g.drawImage(Wizardlight , 240 , HeroImageY ); 
			g.drawImage(Archer , 0 , HeroImageY ); 
			g.drawImage(Marines , 480 , HeroImageY );
			g.drawImage(Cannon , 720 ,HeroImageY );
		}else if (LockMarines) {
			g.drawImage(Marineslight , 480 , HeroImageY ); 
			g.drawImage(Archer , 0 , HeroImageY ); 
			g.drawImage(Wizard , 240 , HeroImageY ); 
			g.drawImage(Cannon , 720 , HeroImageY );
		}else if (LockCannon) {
			g.drawImage(Cannonlight , 720 , HeroImageY ); 
			g.drawImage(Archer , 0 , HeroImageY ); 
			g.drawImage(Wizard , 240 , HeroImageY ); 
			g.drawImage(Marines , 480 , HeroImageY );
		}else {
			if(!MouseOnArcher){
				g.drawImage(Archer , 0 , HeroImageY ); 
			}else {
				g.drawImage(Archerlight , 0 ,HeroImageY ); 
			}
			
			if(!MouseOnWizard){
				g.drawImage(Wizard , 240 , HeroImageY ); 
			}else {
				g.drawImage(Wizardlight , 240 , HeroImageY); 
			}
			
			if(!MouseOnMarines){
				g.drawImage(Marines , 480 ,HeroImageY ); 
			}else {
				g.drawImage(Marineslight , 480 ,HeroImageY ); 
			}
			
			if(!MouseOnCannon){
				g.drawImage(Cannon , 720 , HeroImageY ); 
			}else {
				g.drawImage(Cannonlight , 720 , HeroImageY); 
			}
		}
		
		if(LockHero&&LockTeam){
			if(MouseOnGo){
				g.drawImage(OnGo, 400, 350);
			} else{
				g.drawImage(Go, 400, 350);
			}
		} else {
			g.drawImage(Choose, 290, 350);
		}
		
		for(int i=0;i<teamState.red.size();i++){
			g.setColor(Color.white);
			g.drawString(teamState.red.get(i).name, 100, PlayerYstart + i*40);
			g.setColor(Color.red);
			g.drawString("["+teamState.red.get(i).job+"]", 10, PlayerYstart + i*40);
		}
		
		for(int i=0;i<teamState.blue.size();i++){
			g.setColor(Color.white);
			g.drawString(teamState.blue.get(i).name, 784, PlayerYstart + i*40);
			g.setColor(Color.cyan);
			g.drawString("["+teamState.blue.get(i).job+"]", 694, PlayerYstart + i*40);
		}
		
		g.drawString("( "+ x +" , "+ y +")", 0, 0);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int arg2) throws SlickException {

		x = gc.getInput().getMouseX();
		y = gc.getInput().getMouseY();
		
		MouseOnLeft = false;
		MouseOnRight = false;
		if(x>0&&x<480 && y>TeamImageY&&y<TeamImageY+335){
			MouseOnLeft = true;
		}
		if(x>480&&x<960 && y>TeamImageY&&y<TeamImageY+335){
			MouseOnRight = true;
		}
		
		
		MouseOnArcher = false;    
		MouseOnWizard = false;   
		MouseOnMarines = false;  
		MouseOnCannon = false;
		
		if(x>56&&x<202 && y>HeroImageY&&y<HeroImageY+260){
			MouseOnArcher = true;
		}
		if(x>287&&x<429 && y>HeroImageY&&y<HeroImageY+260){
			MouseOnWizard = true;
		}
		if(x>520&&x<674 && y>HeroImageY&&y<HeroImageY+260){
			MouseOnMarines = true;
		}
		if(x>762&&x<917 && y>HeroImageY&&y<HeroImageY+260){
			MouseOnCannon = true;
		}
		
		MouseOnGo = false;
		if(x>410&&x<543 && y>364&&y<428){
			MouseOnGo = true;
		}
		
		if (gc.getInput().isKeyDown(Input.KEY_W)){
			if(teamState.blue.size()>0){
				Member m = teamState.blue.get(0);
				teamState.red.add(m);
				teamState.blue.remove(m);
			}
		}
		
		if (gc.getInput().isKeyDown(Input.KEY_S)){
			if(teamState.red.size()>1){
				Member m = teamState.red.get(1);
				teamState.blue.add(m);
				teamState.red.remove(m);
			}
		}
		
		if(gc.getInput().isMouseButtonDown((Input.MOUSE_LEFT_BUTTON))){
			//team
			if(x>0&&x<480 && y>65&&y<400){
				LockTeam = true;
				LockRight = false;
				LockLeft = true;
			}
			if(x>480&&x<960 && y>65&&y<400){
				LockTeam = true;
				LockLeft = false;
				LockRight = true;
			}
			//hero
			if(x>56&&x<202 && y>HeroImageY&&y<HeroImageY+260){
				LockHero = true;
				LockArcher = true; 
				LockWizard = false;
				LockMarines = false;  
				LockCannon = false; 
			}
			if(x>287&&x<429 && y>HeroImageY&&y<HeroImageY+260){
				LockHero = true;
				LockArcher = false; 
				LockWizard = true;
				LockMarines = false;  
				LockCannon = false; 
			}
			if(x>520&&x<674 && y>HeroImageY&&y<HeroImageY+260){
				LockHero = true;
				LockArcher = false; 
				LockWizard = false;
				LockMarines = true;  
				LockCannon = false; 
			}
			if(x>762&&x<917 && y>HeroImageY&&y<HeroImageY+260){
				LockHero = true;
				LockArcher = false; 
				LockWizard = false;
				LockMarines = false;  
				LockCannon = true; 
			}
			if(LockHero&&LockTeam){
				if(x>410&&x<543 && y>364&&y<428){
					//TCP ³s½u
					tcpcm.IAmReady();
						
				}
			} 
		}
	}
	
	public void UpdateTeamState(Protocols.TeamState teamstate){
		this.teamState = teamstate;
	}
	
	public void GoNextState(){
		this.sbg.enterState(4,null, new HorizontalSplitTransition());
	}

	@Override
	public int getID() {
		return 3;
	}

}
