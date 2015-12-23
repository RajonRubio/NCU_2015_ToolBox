package UIM;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;

import Protocols.TeamState;
import Protocols.TeamState.Member;
import TCPCM.TCPCM;

public class ChooseTeam extends BasicGameState{
	int x,y;
	TeamState teamState;
	Image Mouse;
	
	Image Background;
	Image Leftteamlight;
	Image Rightteamlight;
	Image Archerlight;
	Image Wizardlight;
	Image Marineslight;
	Image Cannonlight;
	
	Image Choose;
	Image Go;
	Image OnGo;
	
	Image[] waiting;
	int time;
	
	int MouseOnLeftTime;
	int MouseOnRightTime;
	int MouseOnArcherTime;
	int MouseOnWizardTime;
	int MouseOnMarinesTime;
	int MouseOnCannonTime;
	
	
	boolean MouseOnLeft;
	boolean MouseOnRight;
	boolean MouseOnLeftLong;
	boolean MouseOnRightLong;
	
	int whichready;
	
	boolean MouseOnArcher;
	boolean MouseOnWizard;
	boolean MouseOnMarines;
	boolean MouseOnCannon;
	boolean MouseOnArcherLong;
	boolean MouseOnWizardLong;
	boolean MouseOnMarinesLong;
	boolean MouseOnCannonLong;
	
	
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
	
	boolean IsLockTeam;
	boolean IsLockHero;
	
	int TeamImageY = 0;
	int HeroImageY = 450;
	
	int PlayerYstart = 368;
	
	
	TCPCM tcpcm;
	StateBasedGame sbg;
	
	Color BBlack;
	Color LeftTeam;
	Color RightTeam;
	Color ArcherColor;
	Color WizardColor;
	Color MarinesColor;
	Color CannonColor;
	
	
	
	

	public ChooseTeam(TCPCM tcpcm) {
		this.tcpcm = tcpcm;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		Background = new Image("ChooseTeam/Choose.png");
		Leftteamlight = new Image("ChooseTeam/Leftteamlight.png");
		Rightteamlight = new Image("ChooseTeam/Rightteamlight.png");
		
		Mouse = new Image("mouse.png");
		   
		Archerlight = new Image("Hero/OnArcher.png");  
		Wizardlight = new Image("Hero/OnWizard.png");  
		Marineslight = new Image("Hero/OnMarines.png");    
		Cannonlight = new Image("Hero/OnCannon.png"); 
		
		Choose = new Image("ChooseTeam/Title.png");
		Go = new Image("ChooseTeam/GO.png");
		OnGo = new Image("ChooseTeam/OnGO.png");
		
		waiting = new Image[4];
		waiting[0] = new Image("ChooseTeam/waiting0.png");
		waiting[1] = new Image("ChooseTeam/waiting1.png");
		waiting[2] = new Image("ChooseTeam/waiting2.png");
		waiting[3] = new Image("ChooseTeam/waiting3.png");
		
		MouseOnLeftLong = false;
		MouseOnRightLong = false;
		
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

		RightTeam = new Color(0,0,0,0.7f);
		LeftTeam = new Color(0,0,0,0.7f);
		ArcherColor = new Color(0,0,0,0.7f);
		WizardColor = new Color(0,0,0,0.7f);
		MarinesColor = new Color(0,0,0,0.7f);
		CannonColor = new Color(0,0,0,0.7f);
		
		BBlack = new Color(0,0,0,0.7f);
		
		IsLockTeam = false;
		IsLockHero = false;
		
		MouseOnLeftTime = 0;
		MouseOnRightTime = 0;
		teamState = new TeamState();
		
		//teamState.blue.add(teamState.new Member("jxcode", "192.168.0.0", "Archer"));
		teamState.blue.add(teamState.new Member("S.T.Pig", "192.168.0.0", "Wizard" , true));
		
		//teamState.red.add(teamState.new Member("AzureNight", "192.168.0.0", "Marines"));
		teamState.red.add(teamState.new Member("RajonRubio", "192.168.0.0", "Cannon",true));
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		g.drawImage( Background , 0 , 0 ); //�e�I��
		
		g.drawImage(Leftteamlight , 0 , TeamImageY ); 
		g.drawImage(Rightteamlight , 480 , TeamImageY ); 
		
		if(!LockLeft&&!LockRight){
			g.setColor(LeftTeam);
			g.fillRect(0  , 0 , 480, 400);
			g.setColor(RightTeam);
			g.fillRect(480  , 0 , 480, 400);
		}
		if(LockLeft){
			g.setColor(new Color(0,0,0,0.7f));
			g.fillRect(480  , 0 , 480, 400);
		}
		if(LockRight){
			g.setColor(new Color(0,0,0,0.7f));
			g.fillRect(0  , 0 , 480, 400);
		}

		if(LockHero&&LockTeam){
			if(IsReady){
				g.drawImage(waiting[whichready], 298, 350);
			}
			else {
				if(MouseOnGo){
					g.drawImage(OnGo, 400, 350);
				} else{
					g.drawImage(Go, 400, 350);
				}
			}
			
		} else {
			g.drawImage(Choose, 290, 350);
		}

		g.drawImage(Archerlight , 0 , HeroImageY ); 
		g.drawImage(Wizardlight , 240 , HeroImageY ); 
		g.drawImage(Marineslight , 480 , HeroImageY );
		g.drawImage(Cannonlight , 720 , HeroImageY ); 
		
		if(!LockArcher&&!LockWizard&&!LockMarines&&!LockCannon){
			g.setColor(ArcherColor);
			g.fillRect(0   , HeroImageY , 240, 300);
			g.setColor(WizardColor);
			g.fillRect(240 , HeroImageY , 240, 300);
			g.setColor(MarinesColor);
			g.fillRect(480 , HeroImageY , 240, 300);
			g.setColor(CannonColor);
			g.fillRect(720 , HeroImageY , 240, 300);
		}
		if(LockArcher){
			g.setColor(new Color(0,0,0,0.7f));
			g.fillRect(240 , HeroImageY , 240, 300);
			g.fillRect(480 , HeroImageY , 240, 300);
			g.fillRect(720 , HeroImageY , 240, 300);
		}
		if(LockWizard){
			g.setColor(new Color(0,0,0,0.7f));
			g.fillRect(0   , HeroImageY , 240, 300);
			g.fillRect(480 , HeroImageY , 240, 300);
			g.fillRect(720 , HeroImageY , 240, 300);
		}
		if(LockMarines){
			g.setColor(new Color(0,0,0,0.7f));
			g.fillRect(0   , HeroImageY , 240, 300);
			g.fillRect(240 , HeroImageY , 240, 300);
			g.fillRect(720 , HeroImageY , 240, 300);
		}
		if(LockCannon){
			g.setColor(new Color(0,0,0,0.7f));
			g.fillRect(0   , HeroImageY , 240, 300);
			g.fillRect(240 , HeroImageY , 240, 300);
			g.fillRect(480 , HeroImageY , 240, 300);
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
		
		g.setColor(Color.white);
		g.drawString("( "+ x +" , "+ y +")", 0, 0);
		g.drawImage(Mouse, x-15, y-15); 
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		x = gc.getInput().getMouseX();
		y = gc.getInput().getMouseY();
		time += delta;
		
		if(time%1000<250){
			whichready = 0;
		}else if (time%1000<500&&time%1000>=250) {
			whichready = 1;
		}else if (time%1000<750&&time%1000>=500) {
			whichready = 2;
		}else {
			whichready = 3;
		}

		IsLockTeam = LockHero;
		IsLockHero = LockHero;
			
		
	
		MouseOnRight = false;
		if(x>480&&x<960 && y>TeamImageY&&y<TeamImageY+335){
			MouseOnRight = true;
		}
		if(MouseOnRight&&!MouseOnRightLong){
			MouseOnRightTime += delta;
			RightTeam = new Color(0,0,0,0.7f-(float)MouseOnRightTime%1050/1500);
			if(MouseOnRightTime>=1050){
				MouseOnRightLong = true;
			}
		}
		if(MouseOnRight&&MouseOnRightLong){
			RightTeam = new Color(0,0,0,0.0f);
			if(MouseOnRightTime>=1050){
				MouseOnRightLong = true;
			}
		}
		if(!MouseOnRight){
			MouseOnRightLong = false;
			if(MouseOnRightTime>=10){
				MouseOnRightTime -= delta;
				RightTeam = new Color(0,0,0,0.7f-(float)MouseOnRightTime%1050/1500);
			}
		}
		
		
		
		MouseOnLeft = false;
		if(x>0&&x<480 && y>TeamImageY&&y<TeamImageY+335){
			MouseOnLeft = true;
		}
		if(MouseOnLeft&&!MouseOnLeftLong){
			MouseOnLeftTime += delta;
			LeftTeam = new Color(0,0,0,0.7f-(float)MouseOnLeftTime%1050/1500);
			if(MouseOnLeftTime>=1050){
				MouseOnLeftLong = true;
			}
		}
		if(MouseOnLeft&&MouseOnLeftLong){
			LeftTeam = new Color(0,0,0,0.0f);
			if(MouseOnLeftTime>=1050){
				MouseOnLeftLong = true;
			}
		}
		if(!MouseOnLeft){
			MouseOnLeftLong = false;
			if(MouseOnLeftTime>=10){
				MouseOnLeftTime -= delta;
				LeftTeam = new Color(0,0,0,0.7f-(float)MouseOnLeftTime%1050/1500);
			}
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
		
		if(MouseOnArcher&&!MouseOnArcherLong){
			MouseOnArcherTime += delta;
			ArcherColor = new Color(0,0,0,0.7f-(float)MouseOnArcherTime%1050/1500);
			if(MouseOnArcherTime>=1050){
				MouseOnArcherLong = true;
			}
		}
		if(MouseOnArcher&&MouseOnArcherLong){
			ArcherColor = new Color(0,0,0,0.0f);
			if(MouseOnArcherTime>=1050){
				MouseOnArcherLong = true;
			}
		}
		if(!MouseOnArcher){
			MouseOnArcherLong = false;
			if(MouseOnArcherTime>=10){
				MouseOnArcherTime -= delta;
				ArcherColor = new Color(0,0,0,0.7f-(float)MouseOnArcherTime%1050/1500);
			}
		}
		
		
		
		
		if(MouseOnWizard&&!MouseOnWizardLong){
			MouseOnWizardTime += delta;
			WizardColor = new Color(0,0,0,0.7f-(float)MouseOnWizardTime%1050/1500);
			if(MouseOnWizardTime>=1050){
				MouseOnWizardLong = true;
			}
		}
		if(MouseOnWizard&&MouseOnWizardLong){
			WizardColor = new Color(0,0,0,0.0f);
			if(MouseOnWizardTime>=1050){
				MouseOnWizardLong = true;
			}
		}
		if(!MouseOnWizard){
			MouseOnWizardLong = false;
			if(MouseOnWizardTime>=10){
				MouseOnWizardTime -= delta;
				WizardColor = new Color(0,0,0,0.7f-(float)MouseOnWizardTime%1050/1500);
			}
		}
		
		
		
		
		if(MouseOnMarines&&!MouseOnMarinesLong){
			MouseOnMarinesTime += delta;
			MarinesColor = new Color(0,0,0,0.7f-(float)MouseOnMarinesTime%1050/1500);
			if(MouseOnMarinesTime>=1050){
				MouseOnMarinesLong = true;
			}
		}
		if(MouseOnMarines&&MouseOnMarinesLong){
			MarinesColor = new Color(0,0,0,0.0f);
			if(MouseOnMarinesTime>=1050){
				MouseOnMarinesLong = true;
			}
		}
		if(!MouseOnMarines){
			MouseOnMarinesLong = false;
			if(MouseOnMarinesTime>=10){
				MouseOnMarinesTime -= delta;
				MarinesColor = new Color(0,0,0,0.7f-(float)MouseOnMarinesTime%1050/1500);
			}
		}
		
		
		
		
		
		
		if(MouseOnCannon&&!MouseOnCannonLong){
			MouseOnCannonTime += delta;
			CannonColor = new Color(0,0,0,0.7f-(float)MouseOnCannonTime%1050/1500);
			if(MouseOnCannonTime>=1050){
				MouseOnCannonLong = true;
			}
		}
		if(MouseOnCannon&&MouseOnCannonLong){
			CannonColor = new Color(0,0,0,0.0f);
			if(MouseOnCannonTime>=1050){
				MouseOnCannonLong = true;
			}
		}
		if(!MouseOnCannon){
			MouseOnCannonLong = false;
			if(MouseOnCannonTime>=10){
				MouseOnCannonTime -= delta;
				CannonColor = new Color(0,0,0,0.7f-(float)MouseOnCannonTime%1050/1500);
			}
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
		
		
		
		//Lock 
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
					//TCP �s�u
					IsReady = true ;
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