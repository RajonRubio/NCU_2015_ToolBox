import java.io.*;
import java.net.*;
import java.util.*;

public class TCPClient {  
	private String serverAddress;       
	private int serverPort;
	private BufferedReader reader;           
	private PrintStream writer;
	private Socket sock;
	private int[] treasures;
	private static final String[] treasureName = {"A", "B", "C"};

	public static void main(String[] args){
		TCPClient client = new TCPClient();
	}

	public TCPClient (){ 
		this.treasures = new int[]{0, 0, 0};
		this.setServer();
		this.connect(serverAddress, serverPort);

		/** Create new thread to handle incoming message */
		Thread readerThread = new Thread(new IncomingReader());  
		readerThread.start();

		/** Create new thread to ask the server for treasures */
		Thread treasureThread = new Thread(new TreasureAsker());
		treasureThread.start();

		/** Create new thread to count down treasure's available time */
		Thread treasureTimerThread1 = new Thread(new TreasureTimer(0));
		Thread treasureTimerThread2 = new Thread(new TreasureTimer(1));
		Thread treasureTimerThread3 = new Thread(new TreasureTimer(2));
		
		treasureTimerThread1.start();
		treasureTimerThread2.start();
		treasureTimerThread3.start();
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
					String[] msgArray = message.split(" ");
					if(msgArray[0] == "YES"){
						switch(msgArray[1]){
							case "A":
								treasures[0] = 5;
								break;
							case "B":
								treasures[1] = 5;
								break;
							case "C":
								treasures[2] = 5;
								break;
						}
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	} 

	public class TreasureAsker implements Runnable{
		public void run() {
			int target = 0;
			try {
				while(true) {
					if(treasures[target] == 0) {
						writer.println("GET " + treasureName[target]);
						writer.flush();
						Thread.sleep(1000);
					}
					target = (target + 1) % 3;
				}
			}catch (Exception e) {
						e.printStackTrace();
			}
		}
	}

	public class TreasureTimer implements Runnable{
		private int target;
		public TreasureTimer(int target) {
			this.target = target;
		}
		public void run() {
			try {
				while(true) {
					if (treasures[target] != 0) {
						int pre = treasures[target];
						treasures[target] -= 1;
						if (treasures[target] < 0) {
							treasures[target] = 0;
							writer.println("RELEASE " + treasureName[target]);
							writer.flush();
						}
						Thread.sleep((pre - treasures[target]) * 1000);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
