import java.io.*;
import java.net.*;
import java.util.*;

public class AccpetLoop implements Runnable{   

	private Socket serverSock;
	private int connNum;
	private Vector<String> clientIPTable;

	public AcceptLoop (Socket sock, Vector ipTable){
		this.serverSock = sock;
		this.connNum = 0;
		this.clientIPTable = ipTable;
	}

	public void run(){
		try {
			while (true) {
				Socket s = this.serverSock.accept();    
				connNum += 1;
				this.clientIPTable.add(s.getInetAddress());
				Thread t = new Thread(new ConnectionHandler(s, connNum)); 
				t.start();    
			}
		} catch (Exception e){
			e.printStackTrace(System.out);
		}
	}
}
