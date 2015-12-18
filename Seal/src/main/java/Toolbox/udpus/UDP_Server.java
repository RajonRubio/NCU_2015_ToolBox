package Toolbox.udpus;

import Toolbox.dom.Dynamic_Object;
import Toolbox.helper.Item;
import Toolbox.helper.Character;

import java.util.Scanner;
import java.util.regex.MatchResult;

import java.net.DatagramSocket;
import java.net.DatagramPacket;

import java.io.IOException;
import java.net.SocketException;

public class UDP_Server {
    Dynamic_Object dynamic_object;

    int port;
    int MTU;
    byte[] buf;
    DatagramSocket socket;

    public UDP_Server(int port, Dynamic_Object dynamic_object) throws SocketException {
        this.port = port;
        this.dynamic_object = dynamic_object;
        this.MTU = 1500;
        this.socket = new DatagramSocket(port);
    }

    void init() {
        while (true) {
            do_update();
        }
    }

    void do_update() {
        String message = get_message();
        Command command = new Command();
        command.decode(message);
        if (command.code.equals("Add")) {
            if (command.type.equals("Item")) {
                add_item(command.item);
            } else if (command.type.equals("Character")) {
                add_character(command.character);
            }
        } else if (command.code.equals("Update")) {
            if (command.type.equals("Item")) {
                update_item(command.item);
            } else if (command.type.equals("Character")) {
                update_character(command.character);
            }
        }
    }

    void add_item(Item item) {
        assert item.name != null;
        dynamic_object.addItem(item.name, item.index, item.shared);
    }

    void add_character(Character character) {
        dynamic_object.addVirtualCharacter(character.no);
    }

    void update_item(Item item) {
        dynamic_object.updateItem(item.index, item.shared, item.owner, item.x, item.y);
    }

    void update_character(Character character) {
        dynamic_object.updateVirtualCharacter(character.no, character.dir, character.speed, character.x, character.y);
    }

    String get_message() {
        this.buf = new byte[MTU];
        DatagramPacket packet = new DatagramPacket(buf, MTU);
        String data = null;
        try {
            socket.receive(packet);
            data = new String(packet.getData(), 0, packet.getLength());
            System.out.println("We receive a message from " + packet.getAddress().getHostAddress());
        } catch (IOException exception) {
            System.out.println("Receive data fail.");
        }
        return data;
    }
}

