package UDPUS;

import DOM.DOM;
import SDM.SDM;

import Protocols.Character;
import Protocols.Bullet;
import Protocols.WoodBox;

import java.util.ArrayList;

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
        Object object = recieve_object();

        if (object instanceof Bullet) {
            update_bullet((Bullet)object);
        } else if (object instanceof Character) {
            update_character((Character)object);
        } else if (object instanceof WoodBox) {
            update_woodbox((WoodBox)object);
        }
    }

    void update_bullet(Bullet bullet) {
        ArrayList<Bullet> bullets = new ArrayList<Bullet>();
        bullets.add(bullet);
        dynamic_object.updateBullet(bullets);
    }

    void update_character(Character character) {
        dynamic_object.updateVirtualCharacter(character.clientno,
                                              character.status,
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

    Object recieve_object() {
        this.buf = new byte[MTU];
        Object object;

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
            object = oos.readObject();
        } catch (IOException exception) {
            assert false;
        } catch (ClassNotFoundException exception) {
            assert false;
        }

        return object;
    }
}

