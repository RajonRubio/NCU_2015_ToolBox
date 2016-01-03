package TCPSM;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import Protocols.CharacterState;
import Protocols.ClientAction;
import Protocols.Role;
import Protocols.ServerAction;
import Protocols.Team;
import Protocols.TeamState;
import SETTINGS.TCP;
import CDC.CDC;

public class TCPSM {
	private ServerSocket serverSock;
	private CDC cdc;
	private ArrayList<Thread> clientThreads;
	private ArrayList<ClientHandler> clients;
	private int connectCount;
	private Thread listenThread;
	private Timer pingTimer;
	private boolean serverStart;
	
	/** @Constructor */
	public TCPSM() {
		this.clientThreads = new ArrayList<Thread>();
		this.clients = new ArrayList<ClientHandler>();
		this.connectCount = 0;
		this.serverStart = false;
		this.pingTimer = new Timer();
	}
	
	/*
	 * Set cdc property to call while getting action code from clinet
	 * @Param cdc CDC instance
	 */
	public void setDataCenter(CDC cdc) {
		this.cdc = cdc;
	}
	
	/** Start to listen and accept new connection */
	public void initServer() {
		try {
			this.serverStart = true;
			this.serverSock = new ServerSocket(TCP.PORT);
			this.listenThread = new Thread(new ConnectionHandler());
			this.listenThread.start();
			this.pingTimer.schedule(new PingTask(), 5000);
		} catch (IOException e) {
			System.out.println("server start error");
			e.printStackTrace();
		}
		
	}
	
	/** Stop listening and close all connection */
	public void stopServer() throws Exception {
		this.serverStart = false;
		for(ClientHandler c : this.clients) {
			c.sock.close();
			c.clientStart = false;
		}
		this.clients.clear();
		this.clientThreads.clear();
		this.pingTimer.cancel();
	}
	
	/*
	 * Remove client from ArrayList if heart beat failed.
	 * @Param id disconnected client's connection id
	 */
	private void removeClient(int id){
		try {
			clients.get(id).sock.close();
			clients.get(id).clientStart = false;
			cdc.removeVirtualCharacter(clients.get(id).id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		clients.remove(id);
		clientThreads.get(id).interrupt();
		clientThreads.remove(id);
	}
	
	/*
	 * Called by UDPBC
	 * Get all client's address who is connecting to the server
	 * @return 	the set of addresses 
	 */
	public ArrayList getClientIPTable() {
		ArrayList<String> list = new ArrayList<String>();
		for(ClientHandler c : this.clients) {
			list.add(c.sock.getInetAddress().getHostAddress());
		}
		return list;
	}
	
	/*
	 * Called by CDC
	 * broadcast Teamstate to all client
	 */
	public void teamStateBroadcast(TeamState t){
		for(int i = 0; i < t.red.size(); i++) {
			System.out.println(t.red.get(i).name);
		}
		System.out.println();
		for(int i = 0; i < t.blue.size(); i++) {
			System.out.println(t.blue.get(i).name);
		}
		for(ClientHandler c : this.clients) {
			try {
				c.writer.writeObject(ClientAction.TEAM_STAT);
				c.writer.writeObject(t);
//				c.writer.flush();
				c.writer.reset();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void killBroadcast(String killer, String victim) throws IOException {
		for(ClientHandler c : this.clients) {
			c.writer.writeObject(ClientAction.GAME_KILL);
			c.writer.writeChars(killer);
			c.writer.writeChars(victim);
			c.writer.flush();
		}
	}
	
	/*
	 * Called by CDC
	 * Send code "GAME_OVER" to TCPCM for notifying the game has been end
	 */
	public void gameOver() throws Exception {
		for(ClientHandler c : this.clients) {
			c.writer.writeObject(ClientAction.GAME_OVER);
			c.writer.flush();
		}
	}
	
	/*
	 * Called by CDC
	 * Send code "GAME_START" to TCPCM for notifying the game has been start
	 */
	public void gameStart(CharacterState cs) throws Exception {
		for(ClientHandler c : this.clients) {
			c.writer.writeObject(ClientAction.GAME_START);
			c.writer.writeObject(cs);
			c.writer.flush();
		}
	}
	
	
	/*
	 * Class implement Runnable
	 * In order to accept new connection
	 */
	private class ConnectionHandler implements Runnable {
		@Override
		public void run() {
			try {
				while (serverStart) {
					if(clients.size() < 5) {
						Socket s = serverSock.accept();
						System.out.println("Someone Connect: " + s.getInetAddress().getHostAddress());
						addConnection(s);
					}
				}   
			} catch (Exception e){ 
				e.printStackTrace(System.out);
			}
		}
		
		/** Create a thread to handler per connection to client */
		private void addConnection(Socket s) {
			connectCount += 1;
			ClientHandler ch = new ClientHandler(connectCount, s);
			clients.add(ch);
			Thread t = new Thread(ch);
			clientThreads.add(t);
			t.start();
		}
	}
	
	/*
	 * Class implements Runnable
	 * Handle one client's connection and parse the imcoming message.
	 */
	private class ClientHandler implements Runnable {
		public Socket sock;
		public ObjectInputStream reader;
		public ObjectOutputStream writer;
		public int id;
		public boolean clientStart;
		
		/*
		 * @Constructor
		 * @Param id		the id of this connection
		 * @Param s 		socket connection
		 */
		public ClientHandler (int id, Socket s) {
			try {
				this.sock = s;
				this.id = id;
				this.reader = new ObjectInputStream(sock.getInputStream());
				this.writer = new ObjectOutputStream(sock.getOutputStream());
				this.clientStart = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			ServerAction code;
			try {
				while (serverStart && this.clientStart){
					if ((code = (ServerAction)reader.readObject()) != null){
						switchCode(code);
					}
				}
			} catch (Exception e){
				e.printStackTrace(System.out);
			}
		}
		
		/*
		 * Parse action code send from client and call correspond function in CDC
		 * @Param code action code
		 */
		private void switchCode(ServerAction code) throws Exception{
			System.out.println("TCP" + code);
			switch(code) {
				case UP_PRESS:
				case DOWN_PRESS:
				case RIGHT_PRESS:
				case LEFT_PRESS:
				case STANDING:
					cdc.CharacterMove(this.id, code);
					break;
				case ATTACK:
					Point2D.Double angle;
					angle = (Point2D.Double)this.reader.readObject();
					cdc.addBullet(id, angle);
					break;
				case CH_NAME:
					String name;
					name = (String)this.reader.readObject();
					System.out.println(name);
					if(cdc.checkName(name)) {
						this.writer.writeObject(ClientAction.NAME_OK);
						this.writer.writeInt(this.id);
						cdc.addVirtualCharacter(id, name);
					} else {
						this.writer.writeObject(ClientAction.NAME_FAIL);
						removeClient(clients.indexOf(this));
					}
					this.writer.flush();
					break;
				case CH_TEAM:
					Team team;
					team = (Team)this.reader.readObject();
					System.out.println("CH_TEAM to " + team.toString());
					cdc.setTeam(id, team);
					break;
				case CH_ROLE:
					Role role;
					role = (Role)this.reader.readObject();
					cdc.setRole(id, role);
					break;
				case READY:
					cdc.setReady(id);
					break;
			}
		}
	}
	
	private class PingTask extends TimerTask{

		@Override
		public void run() {
			int counter = 0;
			for(ClientHandler c : clients) {
				try {
					c.writer.writeObject(ClientAction.GAME_OVER);
					c.writer.flush();
					counter += 1;
				} catch (IOException e) {
					removeClient(counter);
				}
			}
		}
		
	}
}
