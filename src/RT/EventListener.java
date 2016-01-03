package RT;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import Protocols.ServerAction;
import TCPCM.TCPCM;

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
	boolean Ischange;
	ArrayList<ServerAction> move;
	TCPCM tcpcm;
	Point2D.Double pDouble;

	public EventListener(TCPCM tcpcm) {
		this.tcpcm = tcpcm;
		move = new ArrayList<ServerAction>();
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
		pDouble = new Point2D.Double();
	}
	
	public void listent(GameContainer gc,int delta){
		time += delta;
		Ischange = false;
		
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
				Ischange = true;
				if(IsWPressed&&!move.contains(ServerAction.UP_PRESS)){
					move.add(ServerAction.UP_PRESS);
					System.out.println("W pressed");
				}else {
					move.remove(ServerAction.UP_PRESS);
					System.out.println("W release");
				}
			}
			if(IsSPressed!=SPressed){
				Ischange = true;
				if(IsSPressed&&!move.contains(ServerAction.DOWN_PRESS)){
					move.add(ServerAction.DOWN_PRESS);
					System.out.println("S pressed");
				}else {
					move.remove(ServerAction.DOWN_PRESS);
					System.out.println("S release");
				}
			}
			if(IsAPressed!=APressed){
				Ischange = true;
				if(IsAPressed&&!move.contains(ServerAction.LEFT_PRESS)){
					move.add(ServerAction.LEFT_PRESS);
					System.out.println("A pressed");
				}else {
					move.remove(ServerAction.LEFT_PRESS);
					System.out.println("A release");
				}
			}
			if(IsDPressed!=DPressed){
				Ischange = true;
				if(IsDPressed&&!move.contains(ServerAction.RIGHT_PRESS)){
					move.add(ServerAction.RIGHT_PRESS);
					System.out.println("D pressed");
				}else {
					move.remove(ServerAction.RIGHT_PRESS);
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
					pDouble.x = (x-480);
					pDouble.y = (y-360);
					tcpcm.attack(pDouble);
				}else if(!IsSHOOTPressed && IsSHOOTPressed!=SHOOTPressed){
					System.out.println("SHOOT release");
				}
			}

			IsUpdate = true;
		}
		
		if(time%UpdatePerTime>25 && IsUpdate){
			IsUpdate = false;
		}
		
		if(Ischange){
			if(move.isEmpty()){
				System.out.println("STANDING");
				tcpcm.keyChange(ServerAction.STANDING);
			}else {
				System.out.println(""+move.get(0));
				tcpcm.keyChange(move.get(0));
			}
		}
	}
}
