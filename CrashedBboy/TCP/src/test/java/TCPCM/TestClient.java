package TCPCM;

import TCPCM.TCPClient;
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;
import java.io.*;
import java.net.*;
import java.util.*;

public class TestClient {
	public static TCPClient client;
	private ServerSocket sc;
	private Thread st;
	private boolean serverUP;
	private static TestClient test;
	
	@BeforeClass
	public static void startServer() throws Exception{
		test = new TestClient();
		try {
			test.serverUP = true;
			test.sc = new ServerSocket(8000);
			test.st = new Thread(test.new ConnectionHandler(test.sc));
			test.st.start();
		} catch (Exception e) {
			e.printStackTrace(System.out);
			throw e;
		}
	}

	@AfterClass
	public static void stopServer() throws Exception{
		try {
			test.serverUP = false;
			test.st.interrupt();
			test.sc.close();
			test.st = null;
			test.sc = null;
		} catch (Exception e) {
			e.printStackTrace(System.out);
			throw e;
		}
	}

	@Before
	public void beforeEach() {
		client = TCPClient.getInstance();
	}

	@After
	public void resetClient() {
		client.clean();
	}

	@Test
	public void testConnect() throws Exception {
		boolean status = false;
		try {
			status = client.connectServer(InetAddress.getLocalHost());
			assertEquals(true, status);
		} catch (Exception e) {
			e.printStackTrace(System.out);
			throw e;
		}
	}

	public class ConnectionHandler implements Runnable{
		private ServerSocket server;
		public ConnectionHandler(ServerSocket s) {
			server = s;
		}
		public void run(){
			try {
				while (serverUP) {
					Socket s = server.accept();    
					Thread t = new Thread(new ClientHandler(s)); 
					t.start();
				}   
			} catch (Exception e){ 
				e.printStackTrace(System.out);
			}   
		}
	}

	public class ClientHandler implements Runnable {
		private Socket sock;
		private BufferedReader reader;

		public ClientHandler (Socket s){
			try{
				sock = s;
				InputStreamReader isReader = new InputStreamReader(sock.getInputStream()); 
				reader = new BufferedReader(isReader);
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}   
		}

		public void run(){
			String tmp;
			try {
				while ((tmp = reader.readLine()) != null){   
					System.out.println("Receive :" + tmp);
				}   
			} catch (Exception e){ 
				e.printStackTrace(System.out);
			}   
		}
	}
}

