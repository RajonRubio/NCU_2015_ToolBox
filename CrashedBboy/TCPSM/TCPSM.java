package TCPSM;
import java.io.*;
import java.net.*;
import java.util.*;

public class TCPSM{
	private static final int LISTENPORT = 8000;
	private static ServerSocket serverSock;
	private Vector<String> clientIPTable;

	public TCPSM() {
		int connNum = 0;
		clientIPTable = new Vector<String>();
		serverSock = null;
	}

	protected void initTCPServer() {
		try {
			this.serverSock = new ServerSocket(this.LISTENPORT);  
			Thread ch = new Thread(new ConnectionHandler(this.serverSock, this.clientIPTable));
			ch.start();
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

	protected Vector getClientIPTable() {
		return this.clientIPTable;
	}
} 
