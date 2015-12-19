package TCPCM;

import java.io.*;
import java.net.*;

import SETTINGS.TCP; 

public class TCPCM {
	
	private ObjectOutputStream writer = null;
	private ObjectInputStream reader = null;
	
	public TCPCM() {
		
	}
	
	public boolean connectServer(String serverIP, String nickname) {
		try {
			Socket socket = new Socket(serverIP, TCP.PORT);
			writer = new ObjectOutputStream(socket.getOutputStream());
			reader = new ObjectInputStream(socket.getInputStream());
			new Handler(socket).start();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void inputMoves() {
		try {
			writer.writeInt(1);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void keyShootPressed() {
		
	}
	
	public void giveTeam() {
		
	}
	
	public void giveJob() {
		
	}
	
	private class Handler extends Thread {
		private Socket socket = null;
		
		Handler(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
		}
	}
}
