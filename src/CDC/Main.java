package CDC;

import java.util.ArrayList;

import TCPSM.TCPSM;

public class Main {

	public static void main(String[] args) {
		TCPSM tcpsm = new TCPSM();
		CDC cdc = new CDC(tcpsm);
	}
}
