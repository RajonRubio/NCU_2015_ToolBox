package Toolbox.udpus;

import Toolbox.udpus.UDP_Server;
import Toolbox.dom.Dynamic_Object;
import Toolbox.helper.Item;
import Toolbox.helper.Character;

import java.util.Timer;
import java.util.TimerTask;

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

class Mock_DOM implements Dynamic_Object {
    boolean flag_add_item;
    boolean flag_add_character;
    boolean flag_update_item;
    boolean flag_update_character;

    public void addVirtualCharacter(int clientno) {
        flag_add_character = true;
    }

    public void addItem(String name, int index, boolean shared) {
        flag_add_item = true;
    }

    public void updateVirtualCharacter(int clientno, int dir, int speed, int x, int y) {
        flag_update_character = true;
    }

    public void updateItem(int index, boolean shared, int owner, int x, int y) {
        flag_update_item = true;
    }

    public void reset_flag() {
        flag_add_item = false;
        flag_add_character = false;
        flag_update_item = false;
        flag_update_character = false;
    }
}

class Mock_Send extends TimerTask {
    DatagramPacket packet;
    DatagramSocket socket;
    InetAddress address;
    int port;

    public Mock_Send() throws UnknownHostException {
        this.address = InetAddress.getLocalHost();
        this.port = 5566;
    }

    public void set_message(String message) {
        packet = new DatagramPacket(message.getBytes(), message.length(), address ,port);
    }

    @Override
    public void run() {
        try {
            socket = new DatagramSocket();
            socket.send(packet);
            socket.close();
        } catch (SocketException exception) {
        } catch (IOException exception) {
        }
    }
}

public class UDP_Server_Test {
    private UDP_Server server;
    private Mock_DOM dom;
    private Timer timer;
    private Mock_Send mock_send;

    @Before
    public void setup() throws SocketException, UnknownHostException {
        dom = new Mock_DOM();
        server = new UDP_Server(5566, dom);
        timer = new Timer();
        mock_send = new Mock_Send();
    }

    @After
    public void reset() {
        dom = null;
        server.socket.close();
        server = null;
        timer = null;
    }

    @Test
    public void test_constructor() {
        assertEquals(5566, server.port);
        assertEquals(dom, server.dynamic_object);
    }

    @Test
    public void test_get_message() {
        String message = "Hello World!";
        mock_send.set_message(message);

        timer.schedule(mock_send, 200);

        String reply = server.get_message();

        assertTrue(reply.equals(message));
    }

    @Test
    public void test_receive_add_item() {
        Item item = new Item();
        String message = "Add:" + item.toString();
        mock_send.set_message(message);

        timer.schedule(mock_send, 200);
        server.do_update();

        assertTrue(dom.flag_add_item);
        assertFalse(dom.flag_add_character);
        assertFalse(dom.flag_update_item);
        assertFalse(dom.flag_update_character);
    }

    @Test
    public void test_receive_add_character() {
        Character character = new Character();
        String message = "Add:" + character.toString();
        mock_send.set_message(message);

        timer.schedule(mock_send, 200);
        server.do_update();

        assertFalse(dom.flag_add_item);
        assertTrue(dom.flag_add_character);
        assertFalse(dom.flag_update_item);
        assertFalse(dom.flag_update_character);
    }

    @Test
    public void test_receive_update_item() {
        Item item = new Item();
        String message = "Update:" + item.toString();
        mock_send.set_message(message);

        timer.schedule(mock_send, 200);
        server.do_update();

        assertFalse(dom.flag_add_item);
        assertFalse(dom.flag_add_character);
        assertTrue(dom.flag_update_item);
        assertFalse(dom.flag_update_character);
    }

    @Test
    public void test_receive_update_character() {
        Character character = new Character();
        String message = "Update:" + character.toString();
        mock_send.set_message(message);

        timer.schedule(mock_send, 200);
        server.do_update();

        assertFalse(dom.flag_add_item);
        assertFalse(dom.flag_add_character);
        assertFalse(dom.flag_update_item);
        assertTrue(dom.flag_update_character);
    }

    @Test
    public void test_receive_add_unknown() {
        String message = "Add:XXX{X,X}";
        mock_send.set_message(message);

        timer.schedule(mock_send, 200);
        server.do_update();

        assertFalse(dom.flag_add_item);
        assertFalse(dom.flag_add_character);
        assertFalse(dom.flag_update_item);
        assertFalse(dom.flag_update_character);
    }

    @Test
    public void test_receive_update_unknown() {
        String message = "Update:XXX{X,X}";
        mock_send.set_message(message);

        timer.schedule(mock_send, 200);
        server.do_update();

        assertFalse(dom.flag_add_item);
        assertFalse(dom.flag_add_character);
        assertFalse(dom.flag_update_item);
        assertFalse(dom.flag_update_character);
    }

    @Test
    public void test_receive_unknown_command() {
        String message = "XXX:XXX{X,X}";
        mock_send.set_message(message);

        timer.schedule(mock_send, 200);
        server.do_update();

        assertFalse(dom.flag_add_item);
        assertFalse(dom.flag_add_character);
        assertFalse(dom.flag_update_item);
        assertFalse(dom.flag_update_character);
    }
}

