package TCPSM;

public class TCPSM {
	private static TCPSM instance = null;
	
	public String checkInputMoves = "";
	
	public static TCPSM getInstance() {
		if(instance == null) {
			instance = new TCPSM();
		}
		return instance;
	}
	
	public void inputMoves(int MoveCode) {
		checkInputMoves = "MoveCode is: " + MoveCode;
	}
}
