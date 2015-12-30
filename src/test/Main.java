import Protocols.Character;
import Protocols.Command;

import UDPUS.UDPUS;
import UDPBC.UDPBC;

import DOM.DOM;
import CDC.CDC;
import TCPSM.TCPSM;

import java.util.ArrayList;

import java.net.SocketException;

public class Main {
    public static void main(String args[]) throws SocketException {
        TCPSM tcpsm = new Mock_TCPSM();
        CDC cdc = new Mock_CDC();
        UDPBC client = new UDPBC(5566, tcpsm, cdc);
        client.start();

        DOM dom = new Mock_DOM();
        UDPUS server = new UDPUS(5566, dom);
        server.init();
    }


}
class Mock_TCPSM implements TCPSM {
    public ArrayList<String> getClientIPTable() {
        ArrayList<String> table = new ArrayList<String>(1);
        table.add("127.0.0.1");
        return table;
    }
}

class Mock_CDC implements CDC {
    public ArrayList<Character> getUpdateInfo() {
        ArrayList<Character> info = new ArrayList<Character>(1);
        Character character = new Character();
        info.add(character);
        return info;
    }
}

class Mock_DOM implements DOM {
    public void updateVirtualCharacter(int no, int status, int currentHP) {
        System.out.println("No: " + no);
        System.out.println("Status: " + status);
        System.out.println("HP: " + currentHP);
    }
}
