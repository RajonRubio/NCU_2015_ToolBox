package TCPSM;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
	private int connectNum;
	private Thread listenThread;
	private Thread pingThread;
	private boolean serverStart;
	
	/** @Constructor */
	public TCPSM() {
		this.clientIPTable = new ArrayList<String>();
		this.clientConnections = new ArrayList<Socket>();
		this.clientThreads = new ArrayList<Thread>();
		this.connectNum = 0;
		this.serverStart = false;
	}
	
	/*
	 * Set cdc property to call while getting action code from clinet
	 * @Param cdc 	CDC instance
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
	}
	
	/*
	 * Remove client from ArrayList if heartbit failed.
	 * @Param id disconnected client's connection id
	 */
	private void removeClient(int id) throws Exception {
		
	}
	
	/*
	 * Called by UDPBC
	 * Get all client's address who is connecting to the server
	 * @return 		the set of addresses 
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
	
	/** Stop listening and close all connection */
	public void stopServer() throws Exception {
		this.serverStart = false;
		for(Socket s : this.clientConnections) {
			s.close();
		}
		clientIPTable.clear();
		clientConnections.clear();
		clientThreads.clear();
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
					// call cdc move
					break;
				case UP_RELEASE:
				case DOWN_RELEASE:
				case RIGHT_RELEASE:
				case LEFT_RELEASE:
					// call cdc stop move
					break;
				case ATTACK:
					// call cdc attack
					break;
				case CH_NAME:
					String name;
					name = (String)this.reader.readObject();
					// call cdc set name
					break;
				case CH_TEAM:
					Team team;
					team = (Team)this.reader.readObject();
					// call cdc select team
					break;
				case CH_ROLE:
					Role role;
					role = (Role)this.reader.readObject();
					// call cdc select role
					break;
			}
		}
	}
}
