package Toolbox.udpbc;

import Toolbox.tcpsm.TCP_Server;
import Toolbox.cdc.Data_Center;

import java.util.Vector;
import java.util.Timer;
import java.util.TimerTask;

import java.net.InetAddress;
import java.net.DatagramSocket;
import java.net.DatagramPacket;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDP_Client {
    TCP_Server tcp_server;
    Data_Center data_center;

    DatagramSocket socket;
    DatagramPacket packet;

    int port;
    Vector<InetAddress> servers;

    Timer timer;
    TimerTask do_update;
    final int UPDATE_DELAY = 200;

    public UDP_Client(int port, TCP_Server tcp_server, Data_Center data_center) throws SocketException {
        this.port = port;
        this.tcp_server = tcp_server;
        this.data_center = data_center;
        this.timer = new Timer();
        this.do_update = new Do_Update();
        this.socket = new DatagramSocket();
    }

    public void start() {
        get_servers();

        update_all_info("Add:");

        timer.schedule(do_update, UPDATE_DELAY, UPDATE_DELAY);
    }

    void get_servers() {
        Vector<String> ip_tables = tcp_server.getClientIPTables();
        servers = new Vector<InetAddress>();

        for (String ip : ip_tables) {
            try {
                InetAddress address = InetAddress.getByName(ip);
                servers.add(address);
            } catch (UnknownHostException exception) {
                assert false :"Not all IP in TCPSM is a IP address";
            }
        }
    }

    void update_all_info(String Command) {
        Vector info = data_center.getUpdateInfo();

        for (Object obj : info) {
            String message = Command + obj.toString();
            send_all_server(message);
        }
    }

    void send_all_server(String message) {
        for (InetAddress address : servers) {
            do_send(address, message);
        }
    }

    void do_send(InetAddress address, String message) {
        packet = new DatagramPacket(message.getBytes(), message.length(), address ,port);
        try {
            socket.send(packet);
            Thread.sleep(10);
        } catch (IOException exception) {
        } catch (InterruptedException exception) {
            assert false : "Message cannot be sent to server" + address.toString() + ".";
        }
    }

    class Do_Update extends TimerTask {

        public Do_Update() {
        }

        @Override
        public void run() {
            update_all_info("Update:");
        }
    }
}

