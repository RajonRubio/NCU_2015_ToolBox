package CDC;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		CDC cdc = new CDC();
		cdc.addVirtualCharacter(0, "StPig");
		System.out.println(cdc.searchClientNumber(0));
		cdc.removeVirtualCharacter(0);
	}
}
