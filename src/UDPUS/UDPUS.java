package UDPUS;

import DOM.Bullet;
import DOM.DOM;
import SDM.SDM;

import Protocols.BulletT;
import Protocols.Character;
import Protocols.Command;
import Protocols.WoodBox;

import java.util.ArrayList;

import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;

import java.net.DatagramSocket;
import java.net.DatagramPacket;

import java.io.IOException;
import java.net.SocketException;

public class UDPUS extends Thread {
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

	public void run() {
		init();
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
            update_bullet((BulletT)command);
        } else if (type.equals("Character")) {
            update_character((Character)command);
        } else if (type.equals("WoodBox")) {
            //update_woodbox((WoodBox)object);
        }
    }

    void update_bullet(Protocols.BulletT bullet) {
        ArrayList<Bullet> bullets = new ArrayList<Bullet>();
        Bullet b = new Bullet(bullet.team, bullet.role, bullet.location);
        bullets.add(b);
        dynamic_object.updateBullet(bullets);
    }

    void update_character(Character character) {
        dynamic_object.updateVirtualCharacter(character.clientno,
                                              character.status.ordinal(),
                                              character.location,
                                              character.HP,
                                              character.time,
                                              character.debuff,
                                              character.kill,
                                              character.dead);
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
        } catch (IOException exception) {
            assert false;
        } catch (ClassNotFoundException exception) {
            assert false;
        }

        return command;
    }
}

