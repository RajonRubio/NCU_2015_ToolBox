package udp.server;

import java.util.Scanner;

import java.net.DatagramSocket;
import java.net.DatagramPacket;

import java.io.IOException;
import java.net.SocketException;

public class Server 
{
    int MTU;
    byte[] buf;
    DatagramSocket socket;

    public static void main(String[] args) {
        try {
            int port = 5566;
            Server server = new Server(port);
            System.out.println("This udp server is bind on port " + port + ".");
            while (true) {
                server.print_location();
            }
        } catch (SocketException exception) {
            System.out.println("Create UDP socket fail.");
            System.out.println("Try it later.");
        }
    }

    Server(int port) throws SocketException {
        this.MTU = 1500;
        this.socket = new DatagramSocket(port);
    }

    public void print_location() {
        Location location = get_location();
        System.out.println("Now his robot is at (" + location.get_X() + "," + location.get_Y() + ")");
    }

    Location get_location() {
        Location location = new Location();
        String data = receive();
        Scanner scanner = new Scanner(data);
        location.set_X(scanner.nextInt());
        location.set_Y(scanner.nextInt());
        return location;
    }

    String receive() {
        String data = null;
        this.buf = new byte[MTU];
        DatagramPacket packet = new DatagramPacket(buf, MTU);
        try {
            socket.receive(packet);
            data = new String(packet.getData());
            System.out.println("We receive a message from " + packet.getAddress().getHostAddress());
        } catch (IOException exception) {
            System.out.println("Receive data fail.");
        }
        return data;
    }

    class Location {
        int x;
        int y;

        Location() {
            this.x = 0;
            this.y = 0;
        }

        Location(int x, int y) {
            this.x = x;
            this.y = y;
        }

        void set_X(int x) {
            this.x = x;
        }

        void set_Y(int y) {
            this.y = y;
        }

        int get_X() {
            return x;
        }

        int get_Y() {
            return y;
        }
    }
}

