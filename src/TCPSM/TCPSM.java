package TCPSM;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import Protocols.ClientAction;
import Protocols.ServerAction;
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
	
	public TCPSM(CDC cdc) {
		this.clientIPTable = new Vector<String>();
		this.clientConnections = new Vector<Socket>();
		this.clientThreads = new Vector<Thread>();
		this.connectNum = 0;
		this.cdc = cdc;
	}
	
	public void initServer() throws Exception {
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
	
	private class ConnectionHandler implements Runnable {
		
		@Override
		public void run() {
			try {
				while (true) {
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
			try {
				while (((ServerAction)reader.readObject()) != null){   
					//switchCode(something);
				}
			} catch (Exception e){
				e.printStackTrace(System.out);
			}
		}
		
		private void switchCode(int code) {
			if (code >= 1 && code <= 4) {
				//cdc.updateDirection(this.id, code);
			} else if(code == 5) {
				//cdc.getItem(this.id);
			}   
		}
	}
}
