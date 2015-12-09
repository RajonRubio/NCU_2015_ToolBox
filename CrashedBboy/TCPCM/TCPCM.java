import java.io.*;
import java.net.*;
import java.util.*;

public static final int TURNEAST = 1;
public static final int TURNSOUTH = 2;
public static final int TURNNORTH = 3;
public static final int TURNWEST = 4;
public static final int GET = 5;
public static final int TCPPORT = 8000;

public class TCPCM {  
	private PrintStream writer;
	private Socket sock;

	public TCPCM (){ 
		this.sock = new Socket();
	}

	protected boolean connectServer(InetAddress serverIP) {
		InetSocketAddress isa = new InetSocketAddress(serverIP.getHostAddress(), TCPPORT);
		try {
			this.sock.connect(isa, 10);
			InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());  
			this.writer = new PrintStream(sock.getOutputStream());
		} catch(IOException e) {
			System.out.println("I/O error occurs when creating the socket");
			e.printStackTrace(System.out);
			return false;
		} catch(SocketTimeoutException e){
			System.out.println("Connection timeout");
			e.printStackTrace(System.out);
			return false;
		}
		return true;
	}

	protected void inputMoves(int moveCode) {
		if(moveCode != null) {
			this.writer.print(moveCode);
			this.writer.flush();
		}
	}
}
