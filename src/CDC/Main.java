package CDC;

import java.net.SocketException;
import java.util.ArrayList;

import TCPSM.TCPSM;
import UDPBC.UDPBC;

public class Main {

	public static void main(String[] args) {
		TCPSM tcpsm = new TCPSM();
		CDC cdc = new CDC(tcpsm);
		tcpsm.setDataCenter(cdc);
		try {
			UDPBC udpbc = new UDPBC(5000, tcpsm, cdc);
			cdc.setUDPBC(udpbc);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
}
