package UDPBC;

import TCPSM.TCPSM;
import CDC.CDC;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import java.net.InetAddress;
import java.net.DatagramSocket;
import java.net.DatagramPacket;

import java.io.ObjectOutputStream;
import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPBC {
    TCPSM tcp_server;
    CDC data_center;

    DatagramSocket socket;
    DatagramPacket packet;

    int port;
    ArrayList<InetAddress> servers;

    Timer timer;
    TimerTask do_update;
    final int UPDATE_DELAY = 200;

    public UDPBC(int port, TCPSM tcp_server, CDC data_center) throws SocketException {
        this.port = port;
        this.tcp_server = tcp_server;
        this.data_center = data_center;
        this.timer = new Timer();
        this.do_update = new Do_Update();
        this.socket = new DatagramSocket();
    }

    public void start() {
        get_servers();

        timer.schedule(do_update, UPDATE_DELAY, UPDATE_DELAY);
    }

    public void stop() {
        this.tcp_server = null;
        this.data_center = null;
        this.timer.cancel();
        this.timer = null;
        this.do_update = null;
        this.socket.close();
        this.socket = null;
    }

    void get_servers() {
        ArrayList<String> ip_tables = tcp_server.getClientIPTable();
        servers = new ArrayList<InetAddress>();

        for (String ip : ip_tables) {
            try {
                InetAddress address = InetAddress.getByName(ip);
                servers.add(address);
            } catch (UnknownHostException exception) {
                assert false :"Not all IP in TCPSM is a IP address";
            }
        }
    }

    void update_all_info() {
        ArrayList info = get_all_info();

        for (Object obj : info) {
            send_all_server(obj);
        }
    }

    ArrayList get_all_info() {
        ArrayList info = new ArrayList();
        info.addAll(data_center.getCharacter());
        info.addAll(data_center.getBullets());
        info.addAll(data_center.getWoodBox());

        return info;
    }

    void send_all_server(Object object) {
        for (InetAddress address : servers) {
            do_send(address, object);
        }
    }

    void do_send(InetAddress address, Object object) {
        byte[] buf = new byte[1500];

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.flush();

            buf= baos.toByteArray();
        } catch (IOException exception) {
        }

        packet = new DatagramPacket(buf, buf.length, address, port);

        try {
            socket.send(packet);
            Thread.sleep(10);
        } catch (IOException exception) {
            assert false : "Message cannot be sent to server" + address.toString() + ".";
        } catch (InterruptedException exception) {
            assert false : "Sleep fail. I cloud send datagram packet too fast";
        }
    }

    class Do_Update extends TimerTask {

        public Do_Update() {
        }

        @Override
        public void run() {
            update_all_info();
        }
    }
}

