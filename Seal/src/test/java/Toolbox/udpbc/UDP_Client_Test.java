package Toolbox.udpbc;

import Toolbox.udpbc.UDP_Client;
import Toolbox.cdc.Data_Center;
import Toolbox.tcpsm.TCP_Server;

import Toolbox.helper.Item;
import Toolbox.helper.Character;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import java.net.InetAddress;
import java.net.DatagramSocket;
import java.net.DatagramPacket;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

class Mock_CDC implements Data_Center {    
    private Vector<Object> info;

    public Mock_CDC() {
    }

    public Vector<Object> getUpdateInfo() {
        return info;
    }

    public void set_info(Vector<Object> info) {
        this.info = info;
    }
}

class Mock_TCPSM implements TCP_Server {
    private Vector<String> ip_tables;

    public Mock_TCPSM() {
    }

    public Vector<String> getClientIPTables() {
        return ip_tables;
    }

    public void set_ip_tables(Vector<String> ip_tables) {
        this.ip_tables = ip_tables;
    }
}

class Mock_Do_Send extends TimerTask {
    private UDP_Client client;
    private InetAddress address;
    private String message;

    public Mock_Do_Send(UDP_Client client) throws UnknownHostException {
        this.client = client;
        this.address = InetAddress.getLocalHost();
    }

    public void set_message(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        client.do_send(address, message);
    }
}

class Mock_Send_All extends TimerTask {
    private UDP_Client client;
    private String message;

    public Mock_Send_All(UDP_Client client) {
        this.client = client;
    }

    public void set_message(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        client.get_servers();
        client.send_all_server(message);
    }
}

class Mock_Start extends TimerTask {
    private UDP_Client client;

    public Mock_Start(UDP_Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        client.start();
    }
}

class Mock_Update extends TimerTask {
    private UDP_Client client;
    private String message;

    public Mock_Update(UDP_Client client) {
        this.client = client;
    }

    public void set_message(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        client.get_servers();
        client.update_all_info(message);
    }
}

public class UDP_Client_Test {
    private UDP_Client client;
    private Mock_TCPSM tcpsm;
    private Mock_CDC cdc;
    private Timer timer;

    @Before
    public void setup() throws SocketException, UnknownHostException {
        tcpsm = new Mock_TCPSM();
        cdc = new Mock_CDC();
        client = new UDP_Client(5566, tcpsm, cdc);
        timer = new Timer();
    }

    @After
    public void reset() {
        tcpsm = null;
        cdc = null;
        client.socket.close();
        client = null;
        timer = null;
    }

    @Test
    public void test_constructor() {
        assertEquals(5566, client.port);
        assertEquals(tcpsm, client.tcp_server);
        assertEquals(cdc, client.data_center);
    }

    @Test
    public void test_get_servers_size() {
        Vector<String> ip_tables = new Vector<String>();
        ip_tables.add("0.0.0.0");
        ip_tables.add("1.2.3.4");
        ip_tables.add("99.99.99.99");
        ip_tables.add("123.45.67.89");
        ip_tables.add("127.0.0.1");
        ip_tables.add("10.0.0.1");
        ip_tables.add("172.17.0.1");
        ip_tables.add("192.168.0.1");
        ip_tables.add("140.115.50.1");
        ip_tables.add("140.115.50.48");
        ip_tables.add("140.115.50.56");
        ip_tables.add("255.255.255.255");
        tcpsm.set_ip_tables(ip_tables);

        client.get_servers();
        Vector<InetAddress> servers = client.servers;

        assertEquals(servers.size(), ip_tables.size());
        for (int i = 0; i < ip_tables.size() ; i++) {
            assertEquals(servers.get(i).getHostAddress(), ip_tables.get(i));
        }
    }

    @Test
    public void test_get_servers_context() {
        Vector<String> ip_tables = new Vector<String>();
        ip_tables.add("0.0.0.0");
        ip_tables.add("1.2.3.4");
        ip_tables.add("99.99.99.99");
        ip_tables.add("123.45.67.89");
        ip_tables.add("127.0.0.1");
        ip_tables.add("10.0.0.1");
        ip_tables.add("172.17.0.1");
        ip_tables.add("192.168.0.1");
        ip_tables.add("140.115.50.1");
        ip_tables.add("140.115.50.48");
        ip_tables.add("140.115.50.56");
        ip_tables.add("255.255.255.255");
        tcpsm.set_ip_tables(ip_tables);

        client.get_servers();
        Vector<InetAddress> servers = client.servers;

        for (int i = 0; i < ip_tables.size() ; i++) {
            assertEquals(servers.get(i).getHostAddress(), ip_tables.get(i));
        }
    }

    @Test(expected = AssertionError.class)
        public void test_get_servers_wrong() {
            Vector<String> ip_tables = new Vector<String>();
            ip_tables.add("a.a.a.a");
            tcpsm.set_ip_tables(ip_tables);

            client.get_servers();
        }

    @Test
    public void test_get_servers_many_wrong() {
        Vector<String> test_cases = new Vector<String>();
        test_cases.add("0.0.0.0.0");
        test_cases.add("-1.-1.-1.-1");
        test_cases.add("999.999.999.999");
        test_cases.add("a.a.a.a");

        boolean flag = true;

        for (String test_case : test_cases) {
            Vector<String> ip_tables = new Vector<String>();
            ip_tables.add(test_case);
            tcpsm.set_ip_tables(ip_tables);

            try {
                client.get_servers();
                flag = false;
                System.out.println(test_case);
            } catch (AssertionError ae) {
            }
        }
        assertTrue(flag);
    }

    @Test
    public void test_get_servers_i_dont_know_why() {
        Vector<String> ip_tables = new Vector<String>();
        ip_tables.add("0");
        ip_tables.add("0.0");
        ip_tables.add("0.0.0");
        tcpsm.set_ip_tables(ip_tables);

        client.get_servers();
        Vector<InetAddress> servers = client.servers;

        for (int i = 0; i < ip_tables.size() ; i++) {
            assertNotEquals(servers.get(i).getHostAddress(), ip_tables.get(i));
        }
    }

    @Test
    public void test_start() {
        Item item = new Item();
        Character character = new Character();

        Mock_Start mock_start = new Mock_Start(client);

        Vector<Object> info = new Vector<Object>();
        info.add(item);
        info.add(character);
        cdc.set_info(info);

        Vector<String> ip_tables = new Vector<String>();
        ip_tables.add("127.0.0.1");
        tcpsm.set_ip_tables(ip_tables);

        timer.schedule(mock_start, 200); 

        int count_item = -1;
        int count_character = -1;
        String add_item = "Add:" + item.toString();
        String add_character = "Add:" + character.toString();
        String update_item = "Update:" + item.toString();
        String update_character = "Update:" + character.toString();


        for(int i = 0; i < 100 ; i++) {
            String data = get_message();
            if (data.equals(add_item)) {
                assertEquals(count_item, -1);
                count_item++;
            } else if (data.equals(add_character)) {
                assertEquals(count_character, -1);
                count_character++;
            } else if (data.equals(update_item)) {
                count_item++;
            } else if (data.equals(update_character)) {
                count_character++;
            }
        }
        timer.purge();

        assertTrue(count_item >= 10);
        assertTrue(count_character >= 10);
    }

    @Test
    public void test_update_all_info() {
        String hello = "Hello ";
        String world = "World!";

        Mock_Update mock_update = new Mock_Update(client);
        mock_update.set_message(hello);

        Vector<Object> info = new Vector<Object>();
        info.add(world);
        cdc.set_info(info);

        Vector<String> ip_tables = new Vector<String>();
        ip_tables.add("127.0.0.1");
        tcpsm.set_ip_tables(ip_tables);

        timer.schedule(mock_update, 200); 

        String data = get_message();

		assertTrue(data.equals(hello + world));
    }

    @Test
    public void test_send_all_server() {
        String message = "Hello World!";

        Mock_Send_All mock_send_all = new Mock_Send_All(client);
        mock_send_all.set_message(message);

        Vector<String> ip_tables = new Vector<String>();
        ip_tables.add("127.0.0.1");
        tcpsm.set_ip_tables(ip_tables);

        timer.schedule(mock_send_all, 200); 

        String data = get_message();

		assertTrue(data.equals(message));
    }

    @Test
    public void test_do_send() throws UnknownHostException {
        Mock_Do_Send mock_do_send;
        mock_do_send = new Mock_Do_Send(client);

        String message = "Hello World!";
        mock_do_send.set_message(message);

        timer.schedule(mock_do_send, 200); 
        String data = get_message();
        
		assertTrue(data.equals(message));
    }

    String get_message() {
		int MTU = 1500;
        byte[] buf = new byte[MTU];
        DatagramPacket packet = new DatagramPacket(buf, MTU);
        String data = null;
        try {
			DatagramSocket socket = new DatagramSocket(5566);
            socket.receive(packet);
            data = new String(packet.getData(), 0, packet.getLength());
			socket.close();
        } catch (IOException exception) {
        }
        return data;
    }

}
