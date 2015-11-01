import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServer{
	private int listenPort;
	private static ServerSocket serverSock;
	private int[] treasure;
	private final static String[] treasureName = {"A", "B", "C"};

	public static void main (String args[]){
		TCPServer server = new TCPServer();
		server.startListen();
	}

	public TCPServer() {
		this.treasure = new int[]{0, 0, 0};
		this.startListen();
		int connNum = 0;
		Thread logThread = new Thread(new LogHandler());
		logThread.start();
		while (connNum < 2) {
			try {
					Socket s = serverSock.accept();    
					System.out.println("Get one connection from " + s.getInetAddress());
					connNum += 1;
					Thread t = new Thread(new ConnectionHandler(s, connNum)); 
					t.start();           
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}
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

		private Socket sock;
		private BufferedReader reader;
		private PrintStream writer;
		private int connectionID;

		public ConnectionHandler (Socket s, int id){
			try{
				this.sock = s;
				this.connectionID = id;
				InputStreamReader isReader = new InputStreamReader(this.sock.getInputStream()); 
				this.reader = new BufferedReader(isReader);
				this.writer = new PrintStream(this.sock.getOutputStream());  
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
					if (msgArray[0].equals("GET")) {
						this.get(msgArray[1]);
					} else if (msgArray[0].equals("RELEASE")) {
						this.release(msgArray[1]);
					}
				}
			} catch (Exception e){
				e.printStackTrace(System.out);
			}
		}

		private void get(String name) {
			try {
				Random ran = new Random();
				int treasureID = Arrays.asList(treasureName).indexOf(name);
				if (treasureID != -1 && treasure[treasureID] == 0/* && ran.nextInt(2) == 1*/) {
						treasure[treasureID] = this.connectionID;
						this.writer.println("YES " + treasureName[treasureID]);
				} else {
						this.writer.println("NO " + treasureName[treasureID]);
				}
				writer.flush();
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}

		private void release(String name) {
			int treasureID = Arrays.asList(treasureName).indexOf(name);
			treasure[treasureID] = 0;
		}
	}

	public class LogHandler	implements Runnable {
		public void run() {
			try {
				while (true) {
					System.out.println("A " + (treasure[0]==0?"NO ":"YES ") + treasure[0]);
					System.out.println("B " + (treasure[1]==0?"NO ":"YES ") + treasure[1]);
					System.out.println("C " + (treasure[2]==0?"NO ":"YES ") + treasure[2]);
					Thread.sleep(3000);
				}
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}
		}
	}
} 
