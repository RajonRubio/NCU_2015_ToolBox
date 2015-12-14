package TCPSM;
import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable{   

	private Socket sock;
	private BufferedReader reader;
	private int connectionID;

	public ClientHandler (Socket s, int id){
		try{
			this.sock = s;
			this.connectionID = id;
			InputStreamReader isReader = new InputStreamReader(this.sock.getInputStream()); 
			this.reader = new BufferedReader(isReader);
		} catch (Exception e) {
			e.printStackTrace(System.out);
		} 
	}

	public void run(){
		String message;
		try {
			while ((message=reader.readLine()) != null){   
				System.out.print(message);
			}
		} catch (Exception e){
			e.printStackTrace(System.out);
		}
	}
}
