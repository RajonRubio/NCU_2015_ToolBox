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
    this.sock = null;
  }

  public static TCPClient getInstance() {
    if (client == null) {
      client = new TCPClient();
    }   
    return client;
  }

  public boolean connectServer(InetAddress serverIP) throws Exception{
    try {
      this.sock = new Socket(serverIP, this.TCPPORT);
      InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());  
      this.writer = new PrintStream(sock.getOutputStream());
    } catch(Exception e) {
			throw e;
    }
    return true;
  }

  public void inputMoves(int moveCode) {
    this.writer.print(moveCode);
    this.writer.flush();
  }

	public void clean() {
		try{
			this.sock.close();
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		this.writer = null;
		this.sock = null;
	}
}
