package TCPSM;

import TCPSM.TCPServer;
import CDC.CentralDataCenter;
import static org.junit.Assert.*;
import org.junit.*;
import org.junit.Test;
import java.io.*;
import java.net.*;
import java.util.*;

public class TestServer {

	private static TCPServer server;
	private Socket client;
	private PrintStream writer;
	private ServerSocket sc;

	@Before
	public void init() throws Exception {
		server = TCPServer.getInstance();
	}

	@Test
	public void testInitServer() throws Exception {
		server.initTCPServer();
		clientConnect();
		Thread.sleep(100);
		assertEquals(true, client.isConnected());
		cleanClient();
		cleanServer();
	}

	@Test
	public void testInitServerFailed() throws Exception {
		try {
			sc = new ServerSocket(8000);
			server.initTCPServer();
		} catch (Exception e) {
			assertNotNull(e);
			sc.close();
		}
	}

	@Test
	public void testStartUpdate() throws Exception {
		server.connectionComplete();
		assertEquals(true, server.cdc.start);
	}

	@Test
	public void testIPTable() throws Exception{
		server.initTCPServer();
		Vector<String> v1 = server.getClientIPTable();
		assertEquals(0, v1.size());
		clientConnect();
		Thread.sleep(100);
		Vector<String> v2 = server.getClientIPTable();
		assertEquals(1, v2.size());
		cleanClient();
		cleanServer();
	} 

	@Test
	public void testMove() throws Exception {
		server.initTCPServer();
		clientConnect();
		getClientWriter();
		clientWrite(1);
		Thread.sleep(100);
		assertEquals(1, server.cdc.moveCode);
		cleanClient();
		cleanServer();
	}

	@Test
	public void testGetItem() throws Exception {
		server.initTCPServer();
		clientConnect();
		getClientWriter();
		clientWrite(5);
		Thread.sleep(100);
		assertEquals(true, server.cdc.get);
		cleanClient();
		cleanServer();
	}

	@After
	public void clean() throws Exception {
		server.cdc.reset();
	}

	public void clientConnect() throws Exception {
		try {
			client = new Socket("127.0.0.1", 8000);
		} catch (Exception e) {
			throw e;
		}
	}

	public void getClientWriter() throws Exception {
		try {
			writer = new PrintStream(client.getOutputStream());
		} catch (Exception e) {
			throw e;
		}
	}

	public void clientWrite(int n) throws Exception {
		writer.println(n);
		writer.flush();
	}

	public void cleanClient() throws Exception {
		try {
			client.close();
		} catch (Exception e) {
			throw e;
		}
	}

	public void cleanServer() throws Exception {
		try {
			server.stop();
			Thread.sleep(100);
		} catch (Exception e) {
			throw e;
		}
	}
}
