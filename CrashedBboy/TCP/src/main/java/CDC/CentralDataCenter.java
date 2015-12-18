package CDC;
import java.io.*;
import java.net.*;
import java.util.*;

public class CentralDataCenter {

  private static CentralDataCenter cdc = null;
	public int clientNo;
	public int moveCode;
	public boolean get;
	public boolean start;

  private CentralDataCenter (){ 
		clientNo = -1;
		moveCode = -1;
		start = false;
		get = false;
  }

  public static CentralDataCenter getInstance() {
		if (cdc == null) {
			cdc = new CentralDataCenter();
		}
		return cdc;
  }

	public void updateDirection(int clientno, int movecode) {
		this.clientNo = clientno;
		this.moveCode = movecode;
	}

	public void getItem(int clientno) {
		this.get = true;
	}

	public void startUpdatingThread() {
		this.start = true;
	}

	public void reset() {
		clientNo = -1;
		moveCode = -1;
		start = false;
	}
}
