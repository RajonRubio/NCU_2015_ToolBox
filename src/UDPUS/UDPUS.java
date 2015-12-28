package UDPUS;

import DOM.DOM;

import Protocols.Command;
import Protocols.Character;

import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;

import java.net.DatagramSocket;
import java.net.DatagramPacket;

import java.io.IOException;
import java.net.SocketException;

public class UDPUS {
    DOM dynamic_object;

    int port;
    int MTU;
    byte[] buf;
    DatagramSocket socket;

    public UDPUS(int port, DOM dynamic_object) throws SocketException {
        this.port = port;
        this.dynamic_object = dynamic_object;
        this.MTU = 1500;
        this.socket = new DatagramSocket(port);
    }

    public void init() {
        while (true) {
            do_update();
        }
    }

    void do_update() {
        Command command = recieve_object();
        String type = command.get_type();

        if (type.equals("Bullet")) {
            //update_Bullet(command);
        } else if (type.equals("Character")) {
            update_character((Character)command);
        } else if (type.equals("Box")) {
            //update_character(command);
        }
    }

    //void update_bullet(ArrayList<Bullet> bullets) {
    //    dynamic_object.updateBullet(bullets);
    //}

    void update_character(Character character) {
        int no = character.get_client_no();
        int status = character.get_status();
        int hp = character.get_current_hp();
        dynamic_object.updateVirtualCharacter(no, status, hp);
    }

    //void update_box(Box box) {
    //
    //}

    Command recieve_object() {
        this.buf = new byte[MTU];
        Command command = new Command("Null");

        DatagramPacket packet = new DatagramPacket(buf, MTU);

        try {
            System.out.println("update");
            socket.receive(packet);
            System.out.println("We receive a message from " + packet.getAddress().getHostAddress());
        } catch (IOException exception) {
            assert false;
            System.out.println("Receive data fail.");
        }

        try {
            ByteArrayInputStream baos = new ByteArrayInputStream(this.buf);
            ObjectInputStream oos = new ObjectInputStream(baos);
            command = (Command)oos.readObject();
            System.out.println(command.getClass());
        } catch (IOException exception) {
            assert false;
        } catch (ClassNotFoundException exception) {
            assert false;
        }

        return command;
    }
}

