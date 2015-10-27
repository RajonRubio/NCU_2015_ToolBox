import java.io.*;
import java.net.*;
import java.util.*;

public class MyClient {  
	private String serverAddress;       
	private int serverPort;
	private BufferedReader reader;           
	private PrintStream writer;
	private Socket sock;
	private int[] treasures;

	public static void main(String[] args){
		MyClient client = new MyClient();
	}

	public MyClient (){ 
		this.treasures = new int[]{0, 0, 0};
		this.setServer();
		this.connect(serverAddress, serverPort);

		/** Create new thread to handle incoming message */
		Thread readerThread = new Thread(new IncomingReader());  
		readerThread.start();

		/** Create new thread to ask the server for treasures */
		Thread treasureThread = new Thread(new TreasureAsker());
		treasureThread.start();

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
			sock = new Socket(host, port);      
			InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());  
			reader = new BufferedReader(streamReader);    
			writer = new PrintStream(sock.getOutputStream());
		}catch(IOException e){
			e.printStackTrace(System.out);
		}
	}

	public class IncomingReader implements Runnable{
		public void run(){
			String message;
			try{
				while ((message = reader.readLine()) != null){
					System.out.println("Someone:"+message);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	} 

	public class TreasureAsker implements Runnable{
		public void run() {
			int target = 0;
			String[] name = {"A", "B", "C"};
			try {
				while(true) {
					if(treasures[target] == 0) {
						writer.println("GET " + name[target]);
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
}
