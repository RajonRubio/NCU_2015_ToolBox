package TCPSM;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import CDC.CDC;

public class TCPSM {
	private ServerSocket serverSock;
	private CDC cdc;
	private Vector<String> clientIPTable;
	private Vector<Socket> clientConnections;
	private Vector<Thread> clientThreads;
	private int connectNum;
	private Thread listenThread;
	
	public TCPSM() {
		clientIPTable = new Vector<String>();
		clientIPTable.clear();
		clientConnections = new Vector<Socket>();
		clientConnections.clear();
		clientThreads = new Vector<Thread>();
		clientThreads.clear();
		connectNum = 0;
	}
	
	public void initServer() {
		
	}
	
	public void getClientIPTable() {
		
	}
	
	public void gameOver() {
		
	}
	
	public void gameStart() {
		
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
		private BufferedReader reader;
		private PrintStream writer;
		private int id;
		
		public ClientHandler(int id, Socket s) {
			try {
				this.sock = s;
				this.id = id;
				this.reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				this.writer = new PrintStream(sock.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null){   
					switchCode(Integer.valueOf(message));
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
