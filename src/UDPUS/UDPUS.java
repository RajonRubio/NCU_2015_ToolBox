package UDPUS;

import DOM.Bullet;
import DOM.DOM;
import SDM.SDM;

import Protocols.Command;
import Protocols.Bullets;
import Protocols.BulletT;
import Protocols.Character;
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

        if (type.equals("Bullets")) {
	        //System.out.println("receive some bullets");
            update_bullet((Bullets)command);
        } else if (type.equals("Character")) {
            //System.out.println("receive a character");
            update_character((Character)command);
        } else if (type.equals("WoodBox")) {
            //update_woodbox((WoodBox)object);
        }
    }

    void update_bullet(Bullets bullets) {
        ArrayList<BulletT> list = bullets.get_list();
        ArrayList<Bullet> bs = new ArrayList<Bullet>();
        for (BulletT bullet : bullets.get_list()) {
            Bullet b = new Bullet(bullet.team, bullet.role, bullet.location);
            bs.add(b);
        }
        dynamic_object.updateBullet(bs);
    }

    void update_character(Character character) {
    	//System.out.println("clientno: " + character.clientno + ", LocationX: " + character.location.x + ", LocationY: " + character.location.y);
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
        byte[] buf = new byte[MTU];
        Command command = new Command("Null");

        DatagramPacket packet = new DatagramPacket(buf, MTU);

        try {
            //System.out.println("update");
            socket.receive(packet);
            //System.out.println("We receive a message from " + packet.getAddress().getHostAddress());
        } catch (IOException exception) {
            assert false;
            System.out.println("Receive data fail.");
        }

        try {
        	//System.out.println(buf);
            ByteArrayInputStream baos = new ByteArrayInputStream(buf);
            ObjectInputStream oos = new ObjectInputStream(baos);
            command = (Command)oos.readObject();
        } catch (IOException exception) {
        	//System.out.println(exception);
            assert false;
        } catch (ClassNotFoundException exception) {
            assert false;
        }

        return command;
    }
}

