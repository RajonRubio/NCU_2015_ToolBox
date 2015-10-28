import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServer{
	private static Vector output;
	private int listenPort;
	private static ServerSocket serverSock;
	private int[] treasure;

	public static void main (String args[]){
		TCPServer server = new TCPServer();
		server.startListen();
		while (true) {
			try {
					Socket s = serverSock.accept();    
					PrintStream writer = new PrintStream(s.getOutputStream());  
					System.out.println("Get one connection from " + s.getInetAddress()); 
					output.add(writer);         
					Thread t = new Thread(new TCPServer().new ConnectionHandler(s)); 
					t.start();           
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}
	}

	public TCPServer() {
		this.output = new Vector();          
		treasure = new int[]{0, 0, 0};
	}

	private void startListen() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Listen Port: ");
		this.listenPort = sc.nextInt();
		try {
			this.serverSock = new ServerSocket(this.listenPort);  
			System.out.println("Listen on port " + this.listenPort + " ...");
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

	public class ConnectionHandler implements Runnable{   

		/** Reads text from a character-input stream */
		BufferedReader reader;  
		Socket sock;            

		public ConnectionHandler (Socket s){
			try{
				sock = s;
				InputStreamReader isReader = new InputStreamReader(sock.getInputStream()); 
				reader = new BufferedReader(isReader);
			} catch (Exception e) {
				e.printStackTrace(System.out);
			} 
		}

		public void run(){
			String message;
			Random ran = new Random();
			try {
				while ((message=reader.readLine()) != null){   
					String[] msgArray = message.split(" ");
					if (msgArray[0] == "GET") {
						/** Check and response */
					} else if (msgArray[0] == "RELEASE") {
						/** Reset treasure */
					}
				}
			} catch (Exception e){
				e.printStackTrace(System.out);
			}
		}
	}
} 
