package TCPCM;

import java.io.*;
import java.net.*;
import java.util.Timer;

import Protocols.ServerAction;
import Protocols.CharacterState;
import Protocols.ClientAction;
import Protocols.TeamState;

import SETTINGS.TCP;
import UIM.ChooseTeam;
import UIM.MAIN;
import UIM.Menu_login;

public class TCPCM {
	private MAIN main;
	private ObjectOutputStream writer = null;
	private ObjectInputStream reader = null;
	
	public TCPCM(MAIN main) {
		this.main = main;
	}

	public boolean connectServer(String serverIP, String nickname) {
		try {
			Socket socket = new Socket(serverIP, TCP.PORT);
			writer = new ObjectOutputStream(socket.getOutputStream());
			reader = new ObjectInputStream(socket.getInputStream());
			writer.writeObject(Protocols.ServerAction.CH_NAME);
			writer.writeObject(nickname);
			writer.flush();
			new Handler(socket, this.main).start();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/*
	 * move attack
	 */
	public void keyChange(ServerAction action) {
		try {
			writer.writeObject(action);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void attack(int x, int y) {
		try {
			writer.writeObject(ServerAction.ATTACK);
			writer.writeInt(x);
			writer.writeInt(y);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void chooseTeam(Protocols.Team team) {
		try {
			writer.writeObject(Protocols.ServerAction.CH_TEAM);
			writer.writeObject(team);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void IAmReady(){
		
		
	}
	
	
	public void chooseRole(Protocols.Role role) {
		try {
			writer.writeObject(ServerAction.CH_ROLE);
			writer.writeObject(role);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public class Handler extends Thread {
		private MAIN main;
		private Socket socket = null;
		private Timer timer;
		private int timeout;

		Handler(Socket socket, MAIN main) {
			this.main = main;
			this.socket = socket;
			this.timeout = 10;
			this.timer = new Timer();
			this.timer.schedule(new HeartBeatTask(this), 0, 1000);
		}

		public int getTimeout() {
			return this.timeout;
		}

		public void countDown() {
			this.timeout -= 1;
		}

		public void disConnected() {
			this.timer.cancel();
			// disconnected
		}

		@Override
		public void run() {
			ClientAction action;
			boolean running = true;
			while(running) {
				try {
					action = (ClientAction)reader.readObject();
					switch(action) {
					case HEART_BEAT:
						this.timeout = 10;
						break;
					case NAME_OK:
						// ask first scene to go next
						int clientno = reader.readInt();
						((Menu_login)this.main.getCurrentState()).GoNextState();
						break;
					case NAME_FAIL:
						// ask first scene to pick another name
						((Menu_login)this.main.getCurrentState()).DupName();
						running = false;
						break;
					case SERVER_FULL:
						// ask first scene to say that server is full
						((Menu_login)this.main.getCurrentState()).ServerFull();
						break;
					case TEAM_STAT:
						TeamState teamState = (TeamState)reader.readObject();
						// give second scene
						((ChooseTeam)this.main.getCurrentState()).UpdateTeamState(teamState);
						break;
					case GAME_START:
						// ask second scene to go next
<<<<<<< HEAD
						CharacterState characterState = (CharacterState)reader.readObject();
=======
						// make dom know everything
						
>>>>>>> 05275067865b9aac2e04e1448465bf3ffc3f96a2
						break;
					case GAME_OVER:
						// ask game scene to over
						break;
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} // while
			// disconnect
			try {
				this.socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.socket = null;
		} // void run()
	}
}
