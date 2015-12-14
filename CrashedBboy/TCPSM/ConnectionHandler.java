package TCPSM;
import java.io.*;
import java.net.*;
import java.util.*;

public class ConnectionHandler implements Runnable{   

	private ServerSocket serverSock;
	private int connNum;
	private Vector<String> clientIPTable;

	public ConnectionHandler (ServerSocket sock, Vector<String> ipTable){
		this.serverSock = sock;
		this.connNum = 0;
		this.clientIPTable = ipTable;
	}

	public void run(){
		try {
			while (true) {
				Socket s = this.serverSock.accept();    
				connNum += 1;
				this.clientIPTable.add(s.getInetAddress().getHostAddress());
				Thread t = new Thread(new ClientHandler(s, connNum)); 
				t.start();    
			}
		} catch (Exception e){
			e.printStackTrace(System.out);
		}
	}
}
