package TCPCM;

import java.io.*;
import java.net.*;
import java.util.*; 

public class TCPCM {
	private final int TCPPORT = 8000;
	private Socket socket;
	
	public TCPCM() {
		this.socket = null;
	}
	
	public boolean connectServer(String serverIP) {
		try {
			this.socket = new Socket(serverIP, this.TCPPORT);
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void inputMoves() {
		
	}
	
	public void keyShootPressed() {
		
	}
	
	public void giveTeam() {
		
	}
	
	public void giveJob() {
		
	}
}
