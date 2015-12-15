package TCPCM;
import java.io.*;
import java.net.*;
import java.util.*;

public class TCPClient {  
	private PrintStream writer;
	private Socket sock;
	private static final int TCPPORT = 8000;
	private static TCPClient client = null;

	private TCPClient (){ 
		this.sock = new Socket();
	}

	public static TCPClient getClient() {
		if (client == null) {
			client = new TCPClient();
		}
		return client;
	}

	public boolean connectServer(InetAddress serverIP) {
		InetSocketAddress isa = new InetSocketAddress(serverIP.getHostAddress(), this.TCPPORT);
		try {
			this.sock.connect(isa, 10);
			InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());  
			this.writer = new PrintStream(sock.getOutputStream());
		} catch(Exception e) {
			return false;
		}
		return true;
	}

	public void inputMoves(int moveCode) {
		this.writer.print(moveCode);
		this.writer.flush();
	}
}
