import java.io.*;
import java.net.*;
import java.util.*;

public static final int TURNEAST = 1;
public static final int TURNSOUTH = 2;
public static final int TURNNORTH = 3;
public static final int TURNWEST = 4;
public static final int GET = 5;

public class TCPCM {  
	private String serverAddress;       
	private int serverPort;
	private BufferedReader reader;           
	private PrintStream writer;
	private Socket sock;

	public static void main(String[] args){
		TCPCM client = new TCPCM();
	}

	public TCPCM (){ 
		this.setServer();
		this.connect(serverAddress, serverPort);

		/** Create new thread to handle incoming message */
		Thread readerThread = new Thread(new IncomingReader());  
		readerThread.start();
	}

	protected boolean connectServer(InetAddress serverIP) {
		/**
			return true:connection success,false: connection failed
		*/
	}

	protected void inputMoves(int moveCode) {
		/**
			wrap the moveCode passed in and transmit to TCP server
		*/
	}

	/** Set up host information */
	private void setServer() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Server IP Address: ");
		this.serverAddress = sc.nextLine();
		System.out.print("\nServer Port: ");
		while(!sc.hasNextInt())
			sc.next();
		this.serverPort = sc.nextInt();
	}

	/** Create connection */
	private void connect(String host, int port) {
		try{
			this.sock = new Socket(host, port);      
			InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());  
			this.reader = new BufferedReader(streamReader);    
			this.writer = new PrintStream(sock.getOutputStream());
		}catch(IOException e){
			e.printStackTrace(System.out);
		}
	}

	public class IncomingReader implements Runnable{
		public void run(){
			String message;
			try{
				while ((message = reader.readLine()) != null){
					/* parse */
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	} 
}
