package RT;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class EventListener {
	int x,y;
	int lastx,lasty;
	int time;
	int UpdatePerTime = 100;
	boolean IsUpdate;
	
	boolean IsAnyKeyPressed;
	GameContainer gc;
	
	boolean IsWPressed ;
	boolean IsSPressed ;
	boolean IsAPressed ;
	boolean IsDPressed ;
	boolean IsSHOOTPressed ;
	
	boolean WPressed;
	boolean SPressed;
	boolean APressed;
	boolean DPressed;
	boolean SHOOTPressed;
	
	

	public EventListener() {
		IsWPressed = false;
		IsSPressed = false;
		IsAPressed = false;
		IsDPressed = false;
		IsSHOOTPressed = false;
		
		WPressed = false;
		SPressed = false;
		APressed = false;
		DPressed = false;
		SHOOTPressed = false;
		
		time = 0;
		IsUpdate = false;
	}
	
	public void listent(GameContainer gc,int delta){
		time += delta;
		
		if(time%UpdatePerTime<25 && !IsUpdate){
			WPressed = IsWPressed;
			SPressed = IsSPressed;
			APressed = IsAPressed;
			DPressed = IsDPressed;
			
			IsWPressed = false;
			IsSPressed = false;
			IsAPressed = false;
			IsDPressed = false;

			
			if (gc.getInput().isKeyDown(Input.KEY_W)){
				IsWPressed = true ;
			}
			if (gc.getInput().isKeyDown(Input.KEY_S)){
				IsSPressed = true ;
			}
			if (gc.getInput().isKeyDown(Input.KEY_A)){
				IsAPressed = true ;
			}
			if (gc.getInput().isKeyDown(Input.KEY_D)){
				IsDPressed = true ;
			}
			
			if(IsWPressed!=WPressed){
				if(IsWPressed){
					System.out.println("W pressed");
				}else {
					System.out.println("W release");
				}
			}
			if(IsSPressed!=SPressed){
				if(IsSPressed){
					System.out.println("S pressed");
				}else {
					System.out.println("S release");
				}
			}
			if(IsAPressed!=APressed){
				if(IsAPressed){
					System.out.println("A pressed");
				}else {
					System.out.println("A release");
				}
			}
			if(IsDPressed!=DPressed){
				if(IsDPressed){
					System.out.println("D pressed");
				}else {
					System.out.println("D release");
				}
			}
			
			
			lastx = x;
			lasty = y;
			
			x = gc.getInput().getMouseX();
			y = gc.getInput().getMouseY();
			
			SHOOTPressed = IsSHOOTPressed ;
			IsSHOOTPressed = false ;
			
			if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
				IsSHOOTPressed = true;
			}
			
			
			if(IsSHOOTPressed!=SHOOTPressed||x!=lastx||y!=lasty){
				if(IsSHOOTPressed){
					System.out.println("SHOOT: (" + (x-480) + "," + (y-360) +")");
				}else if(!IsSHOOTPressed && IsSHOOTPressed!=SHOOTPressed){
					System.out.println("SHOOT release");
				}
			}

			IsUpdate = true;
		}
		
		if(time%UpdatePerTime>25 && IsUpdate){
			IsUpdate = false;
		}
		
		
	}
}
