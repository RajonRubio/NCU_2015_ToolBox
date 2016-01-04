package TCPCM;

import java.awt.geom.Point2D;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Timer;

import DOM.Bullet;
import DOM.DOM;
import Protocols.BulletT;
import Protocols.Bullets;
import Protocols.Character;
import Protocols.ServerAction;
import Protocols.CharacterState;
import Protocols.ClientAction;
import Protocols.TeamState;
import RT.RT;
import SETTINGS.TCP;
import UIM.ChooseTeam;
import UIM.MAIN;
import UIM.Menu_login;

public class TCPCM {
	private MAIN main;
	private ObjectOutputStream writer = null;
	private ObjectInputStream reader = null;
	private DOM dom;
	public int XXXX;
	
	public TCPCM(MAIN main,DOM dom) {
		this.main = main;
		this.dom = dom;
	}

	public boolean connectServer(String serverIP, String nickname) {
		System.out.println("Connect " + serverIP + ", " + nickname);
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
	
	public void attack(Point2D.Double v) {
		try {
			writer.writeObject(ServerAction.ATTACK);
			writer.writeObject(v);
			writer.reset();
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
		try {
			writer.writeObject(ServerAction.READY);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		
		void update_bullet(ArrayList<BulletT> list) {
	        ArrayList<Bullet> bs = new ArrayList<Bullet>();
	        for (BulletT bullet : list) {
	            Bullet b = new Bullet(bullet.team, bullet.role, bullet.location);
	            bs.add(b);
	        }
	        dom.updateBullet(bs);
	    }

	    void update_character(Character character) {
	    	//System.out.println("clientno: " + character.clientno + ", LocationX: " + character.location.x + ", LocationY: " + character.location.y);
	        dom.updateVirtualCharacter(character.clientno,
	                                              character.status.ordinal(),
	                                              character.location,
	                                              character.HP,
	                                              character.time,
	                                              character.debuff,
	                                              character.kill,
	                                              character.dead);
	    }

		@Override
		public void run() {
			ClientAction action;
			boolean running = true;
			while(running) {
				try {
					action = (ClientAction)reader.readObject();
					switch(action) {
					case UPDATE_CHARACTER:
						ArrayList<Protocols.Character> characters = (ArrayList<Protocols.Character>)reader.readObject();
						for(Protocols.Character c:characters){
							update_character(c);
						}
						XXXX++;
						break;
					case UPDATE_BULLET:
						ArrayList<Protocols.BulletT> bulletTs = (ArrayList<Protocols.BulletT>)reader.readObject();
						update_bullet(bulletTs);
						break;
					case HEART_BEAT:
						this.timeout = 10;
						break;
					case NAME_OK:
						// ask first scene to go next
						int clientno = reader.readInt();
						dom.setClientno(clientno);
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
						running = false;
						break;
					case TEAM_STAT:
						TeamState teamState = (TeamState)reader.readObject();
						((ChooseTeam)this.main.getCurrentState()).UpdateTeamState(teamState);
						break;
					case GAME_START:
						// ask second scene to go next
						CharacterState characterState = (CharacterState)reader.readObject();
						System.out.println("gogogo");
						dom.gameStart(characterState);
						((ChooseTeam)this.main.getCurrentState()).GoNextState();
						break;
					case GAME_KILL:
						String killer = (String)reader.readObject();
						String victim = (String)reader.readObject();
						((RT)this.main.getCurrentState()).claimKill(killer, victim);
						break;
					case GAME_OVER:
						System.out.println("GameOver");
						((RT)this.main.getCurrentState()).goNext();
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
