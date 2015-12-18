package TCPSM;

import CDC.CentralDataCenter;
import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServer{
	private static final int LISTENPORT = 8000;
	private static ServerSocket serverSock;
	private Vector<String> clientIPTable;
	private static TCPServer server = null;
	public CentralDataCenter cdc = null;
	private int connNum;

	private TCPServer() {
		connNum = 0;
		clientIPTable = new Vector<String>();
		serverSock = null;
	}

	public static TCPServer getInstance() {
		if (server == null) {
			server = new TCPServer();
			server.cdc = CentralDataCenter.getInstance();
		}
		return server;
	}

	public void initTCPServer() throws Exception {
		try {
			this.serverSock = new ServerSocket(this.LISTENPORT);  
			Thread ch = new Thread(new ConnectionHandler());
			ch.start();
		} catch (Exception e) {
			throw e;
		}
	}

	public Vector getClientIPTable() {
		return this.clientIPTable;
	}

	public void connectionComplete() {
		cdc.startUpdatingThread();
	}

	public void stop() throws Exception {
		try {
			this.serverSock.close();
			this.clientIPTable.clear();
			this.serverSock = null;
			this.connNum = 0;
		} catch (Exception e) {
			throw e;
		}
	}

	public void addConnection(String s) {
		connNum += 1;
		clientIPTable.add(s);
	}

	public class ConnectionHandler implements Runnable{   
		public void run(){
			try {
				while (true) {
					Socket s = serverSock.accept();    
					addConnection(s.getInetAddress().getHostAddress());
					Thread t = new Thread(new ClientHandler(s, connNum)); 
					t.start();    
				}   
			} catch (Exception e){ 
				e.printStackTrace(System.out);
			}   
		}
	}

	public class ClientHandler implements Runnable{   

		private Socket sock;
		private BufferedReader reader;
		private PrintStream writer;
		private int connectionID;

		public ClientHandler (Socket s, int id){
			try{
				sock = s;
				connectionID = id; 
				InputStreamReader isReader = new InputStreamReader(sock.getInputStream()); 
				reader = new BufferedReader(isReader);
				writer = new PrintStream(sock.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}   
		}

		public void run(){
			String message;
			try {
				while ((message=reader.readLine()) != null){   
					int code = Integer.valueOf(message);
					switchCode(code);
				}
			} catch (Exception e){
				e.printStackTrace(System.out);
			}
		}

		public void switchCode(int code) {
			if (code >= 1 && code <= 4) {
				cdc.updateDirection(connectionID, code);
			} else if(code == 5) {
				cdc.getItem(connectionID);
			}   
		}
	}

} 
