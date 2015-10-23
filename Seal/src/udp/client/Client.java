package udp.client;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;

import java.net.InetAddress;
import java.net.DatagramSocket;
import java.net.DatagramPacket;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {

    final int MOVE_DELAY = 2000;
    final int SEND_DELAY = 200;

    int port;
    Location location;
    ArrayList<InetAddress> server;

	public static void main(String[] args) {
        Client client = new Client(2, 5566);
        client.run();
	}

    public Client(int server_number, int port) {
        this.port = port;
        this.location = new Location(0, 0);
        this.server = new ArrayList<InetAddress>(server_number);
        for (int i = 0; i < server_number; i++) {
            try {
                System.out.print("Input Your server[" + i + "]: ");
                InetAddress address = get_server_address();
                System.out.println("The IP addreee of Server[" + i + "] is " + address.getHostAddress());
                server.add(address);
            } catch (UnknownHostException exception) {
                System.out.println("Your Input seems not a IP Address");
            }
        }
    }

    public void run() {
        Timer timer = new Timer();
        TimerTask move = new Move();
        timer.schedule(move, MOVE_DELAY, MOVE_DELAY);
        try {
            TimerTask send = new Send();
            timer.schedule(send, SEND_DELAY, SEND_DELAY);
        } catch (SocketException exception) {
            System.out.println("Cannot create a link, Please check your network is working.");
        }
    }

    InetAddress get_server_address() throws UnknownHostException {
        Scanner scanner = new Scanner(System.in);
        String hostname = scanner.nextLine();
        InetAddress address = InetAddress.getByName(hostname);
        return address;
    }

    class Move extends TimerTask {
        
        @Override
        public void run() {
            int x = location.get_X();
            int y = location.get_Y();
            location.set_X(x+1);
            location.set_Y(y+1);
            System.out.println("Now robot is at (" + location.get_X() + ", " + location.get_Y() + ")");
        }
    }

    class Send extends TimerTask {
        DatagramSocket socket;
        
        public Send() throws SocketException {
            this.socket = new DatagramSocket();
            System.out.println("This udp client is bind on port " + socket.getLocalPort() + ".");
        }

        @Override
        public void run() {
            //"X Y" cannot parse in server, there is "X Y "
            String message = location.get_X() + " " + location.get_Y() + " ";
            for (InetAddress address: server) {
                DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(),address ,port);
                try {
                    socket.send(packet);
                } catch (IOException exception) {
                    System.out.println("Message cannot be sent to server.");
                    System.out.println("Try it later");
                }
            }
        }
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
            if (x >=100) {
                x = 0;
            }
            this.x = x;
        }

        void set_Y(int y) {
            if (y >=100) {
                y = 0;
            }
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
