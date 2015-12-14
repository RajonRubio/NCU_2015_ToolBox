import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServer{
	private static final int LISTENPORT = 8000;
	private static ServerSocket serverSock;
	private Vector<String> clientIPTable;

	public TCPServer() {
		int connNum = 0;
		clientIPTable = new Vector<String>();
		serverSock = null;
	}

	protected void initTCPServer() {
		try {
			this.serverSock = new ServerSocket(this.listenPort);  
			Thread al = new Thread(new AcceptLoop(this.serverSock, this.clientIPTable));
			al.start();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

	protected Vector getClientIPTable() {
		return this.clientIPTable;
	}
} 
