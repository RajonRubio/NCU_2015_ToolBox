package TCPSM;
import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable{   

	private Socket sock;
	private BufferedReader reader;
	private PrintStream writer;
	private int connectionID;

	public ClientHandler (Socket s, int id){
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
		try {
			while ((message=reader.readLine()) != null){   
				System.out.print(message);
				this.writer.println(message);
				this.writer.flush();
			}
		} catch (Exception e){
			e.printStackTrace(System.out);
		}
	}
}
