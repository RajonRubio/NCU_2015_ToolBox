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

import Protocols.ClientAction;
import Protocols.Role;
import Protocols.ServerAction;
import Protocols.Team;
import SETTINGS.TCP;
import CDC.CDC;

public class TCPSM {
	private ServerSocket serverSock;
	private CDC cdc;
	private ArrayList<String> clientIPTable;
	private ArrayList<Socket> clientConnections;
	private ArrayList<Thread> clientThreads;
	private ArrayList<Boolean> clientEnable;
	private int connectNum;
	private Thread listenThread;
	private Timer pingTimer;
	private boolean serverStart;
	
	/** @Constructor */
	public TCPSM() {
		this.clientIPTable = new ArrayList<String>();
		this.clientConnections = new ArrayList<Socket>();
		this.clientThreads = new ArrayList<Thread>();
		this.connectNum = 0;
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
	public void initServer() throws Exception {
		this.serverStart = true;
		this.serverSock = new ServerSocket(TCP.PORT);
		this.listenThread = new Thread(new ConnectionHandler());
		this.listenThread.start();
		this.pingTimer.schedule(new PingTask(), 5000);
	}
	
	/** Stop listening and close all connection */
	public void stopServer() throws Exception {
		this.serverStart = false;
		for(Socket s : this.clientConnections) {
			s.close();
		}
		this.clientIPTable.clear();
		this.clientConnections.clear();
		this.clientThreads.clear();
		this.clientEnable.clear();
		this.pingTimer.cancel();
	}
	
	/*
	 * Remove client from ArrayList if heart beat failed.
	 * @Param id disconnected client's connection id
	 */
	private void removeClient(int id){
		try {
			clientConnections.get(id-1).close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		clientEnable.set(id, false);
		clientThreads.get(id).interrupt();
		clientIPTable.remove(id);
		clientConnections.remove(id);
		clientThreads.remove(id);
		clientEnable.remove(id);
		connectNum -= 1;
	}
	
	/*
	 * Called by UDPBC
	 * Get all client's address who is connecting to the server
	 * @return 	the set of addresses 
	 */
	public ArrayList getClientIPTable() {
		return this.clientIPTable;
	}
	
	/*
	 * Called by CDC
	 * Send code "GAME_OVER" to TCPCM for notifying the game has been end
	 */
	public void gameOver() throws Exception {
		for(Socket s : this.clientConnections) {
			ObjectOutputStream writer = new ObjectOutputStream(s.getOutputStream());
			writer.writeObject(ClientAction.GAME_OVER);
			writer.flush();
		}
	}
	
	/*
	 * Called by CDC
	 * Send code "GAME_START" to TCPCM for notifying the game has been start
	 */
	public void gameStart() throws Exception {
		for(Socket s : this.clientConnections) {
			ObjectOutputStream writer = new ObjectOutputStream(s.getOutputStream());
			writer.writeObject(ClientAction.GAME_START);
			writer.flush();
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
					if(connectNum < 4) {
						Socket s = serverSock.accept();    
						addConnection(s);
					}
				}   
			} catch (Exception e){ 
				e.printStackTrace(System.out);
			}
		}
		
		/** Create a thread to handler per connection to client */
		private void addConnection(Socket s) {
			connectNum += 1;
			clientIPTable.add(s.getInetAddress().getHostAddress());
			clientConnections.add(s);
			Thread t = new Thread(new ClientHandler(connectNum, s));
			clientThreads.add(t);
			t.start();
		}
	}
	
	/*
	 * Class implements Runnable
	 * Handle one client's connection and parse the imcoming message.
	 */
	private class ClientHandler implements Runnable {
		private Socket sock;
		private ObjectInputStream reader;
		private ObjectOutputStream writer;
		private int id;
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
				while (serverStart){
					if ((code = (ServerAction)reader.readObject()) != null){
						switchCode(code);
					}
				}
			} catch (Exception e){
				e.printStackTrace(System.out);
			}
		}
		
		/*
		 * Parse action code sended from client and call correspond function in CDC
		 * @Param code action code
		 */
		private void switchCode(ServerAction code) throws Exception{
			switch(code) {
				case UP_PRESS:
				case DOWN_PRESS:
				case RIGHT_PRESS:
				case LEFT_PRESS:
					cdc.CharacterMove(this.id, code);
					break;
				case ATTACK:
					Point2D angle;
					angle = (Point2D)this.reader.readObject();
					cdc.addBullet(id, angle);
					break;
				case CH_NAME:
					String name;
					name = (String)this.reader.readObject();
					if(cdc.checkName(name)) {
						this.writer.writeObject(ClientAction.NAME_OK);
						cdc.addVirtualCharacter(id, name);
					} else {
						this.writer.writeObject(ClientAction.NAME_FAIL);
						// close socket
					}
					break;
				case CH_TEAM:
					Team team;
					team = (Team)this.reader.readObject();
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
			for(Socket s : clientConnections) {
				try {
					ObjectOutputStream writer = new ObjectOutputStream(s.getOutputStream());
					writer.writeObject(ClientAction.GAME_OVER);
					writer.flush();
					counter += 1;
				} catch (IOException e) {
					removeClient(counter);
				}
			}
		}
		
	}
}
