import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServer{
	private static final int LISTENPORT = 8000;
	private static ServerSocket serverSock;
	private vector<String> clientIPTable;

	public TCPServer() {
		int connNum = 0;
		clientIPTable = new vector<String>();
		serverSock = null;
	}

	protected void initTCPServer() {
		try {
			this.serverSock = new ServerSocket(this.listenPort);  
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		while (true) {
			try {
					Socket s = serverSock.accept();    
					connNum += 1;
					clientIPTable.add(s.getInetAddress());
					Thread t = new Thread(new ConnectionHandler(s, connNum)); 
					t.start();           
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}
	}

	public class ConnectionHandler implements Runnable{   

		private Socket sock;
		private BufferedReader reader;
		private int connectionID;

		public ConnectionHandler (Socket s, int id){
			try{
				this.sock = s;
				this.connectionID = id;
				InputStreamReader isReader = new InputStreamReader(this.sock.getInputStream()); 
				this.reader = new BufferedReader(isReader);
			} catch (Exception e) {
				e.printStackTrace(System.out);
			} 
		}

		public void run(){
			String message;
			try {
				while ((message=reader.readLine()) != null){   
					/* parse and call CDC */
				}
			} catch (Exception e){
				e.printStackTrace(System.out);
			}
		}
	}
} 
