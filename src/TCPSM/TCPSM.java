package TCPSM;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.management.relation.Role;

import Protocols.ClientAction;
import Protocols.ServerAction;
import Protocols.Team;
import SETTINGS.TCP;

import CDC.CDC;

public class TCPSM {
	private ServerSocket serverSock;
	private CDC cdc;
	private Vector<String> clientIPTable;
	private Vector<Socket> clientConnections;
	private Vector<Thread> clientThreads;
	private int connectNum;
	private Thread listenThread;
	private boolean serverStart;
	
	public TCPSM(CDC cdc) {
		this.clientIPTable = new Vector<String>();
		this.clientConnections = new Vector<Socket>();
		this.clientThreads = new Vector<Thread>();
		this.connectNum = 0;
		this.cdc = cdc;
		this.serverStart = false;
	}
	
	public void initServer() throws Exception {
		this.serverStart = true;
		this.serverSock = new ServerSocket(TCP.PORT);
		this.listenThread = new Thread(new ConnectionHandler());
		this.listenThread.start();
	}
	
	public Vector getClientIPTable() {
		return this.clientIPTable;
	}
	
	public void gameOver() throws Exception {
		for(Socket s : this.clientConnections) {
			ObjectOutputStream writer = new ObjectOutputStream(s.getOutputStream());
			writer.writeObject(ClientAction.GAME_OVER);
			writer.flush();
		}
	}
	
	public void gameStart() throws Exception {
		for(Socket s : this.clientConnections) {
			ObjectOutputStream writer = new ObjectOutputStream(s.getOutputStream());
			writer.writeObject(ClientAction.GAME_START);
			writer.flush();
		}
	}
	
	public void stopServer() throws Exception {
		this.serverStart = false;
		for(Socket s : this.clientConnections) {
			s.close();
		}
		clientIPTable.clear();
		clientConnections.clear();
		clientThreads.clear();
	}
	
	private class ConnectionHandler implements Runnable {
		@Override
		public void run() {
			try {
				while (serverStart) {
					Socket s = serverSock.accept();    
					addConnection(s);
				}   
			} catch (Exception e){ 
				e.printStackTrace(System.out);
			}
		}
		
		private void addConnection(Socket s) {
			connectNum += 1;
			clientIPTable.add(s.getInetAddress().getHostAddress());
			clientConnections.add(s);
			Thread t = new Thread(new ClientHandler(connectNum, s));
			clientThreads.add(t);
			t.start();
		}
	}
	
	private class ClientHandler implements Runnable {
		private Socket sock;
		private ObjectInputStream reader;
		private ObjectOutputStream writer;
		private int id;
		
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
					if ((code = (ServerAction)reader.readObject()) != null)
					switchCode(code);
				}
			} catch (Exception e){
				e.printStackTrace(System.out);
			}
		}
		
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
