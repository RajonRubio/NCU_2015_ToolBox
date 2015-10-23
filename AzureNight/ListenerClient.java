package ChatroomServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/* �o�����O�A�Ⱦ��ݪ����ݫȤ�ݵo�e�H��*/
public class ListenerClient extends Thread {
	BufferedReader reader;
	PrintWriter writer;
	ServerUI ui;
	Socket client;

	public ListenerClient(ServerUI ui, Socket client) {
		this.ui = ui;
		this.client = client;
		this.start();
	}

	// ���C�@�ӫȤ�ݳЫؽu�{���ݱ����H���A�M���H���s���X�h
	public void run() {
		String msg = "";
		while (true) {
			try {
				reader = new BufferedReader(new InputStreamReader(
						client.getInputStream()));
				writer = new PrintWriter(client.getOutputStream(), true);
				msg = reader.readLine();
				sendMsg(msg);

			} catch (IOException e) {
				println(e.toString());
				// e.printStackTrace();
				break;
			}
			if (msg != null && msg.trim() != "") {
				println(">>" + msg);
			}
		}
	}

	// ��H���s����Ҧ��Τ�
	public synchronized void sendMsg(String msg) {
		try {
			for (int i = 0; i < ui.clients.size(); i++) {
				Socket client = ui.clients.get(i);
				writer = new PrintWriter(client.getOutputStream(), true);
				writer.println(msg);
			}

		} catch (Exception e) {
			println(e.toString());
		}
	}

	public void println(String s) {
		if (s != null) {
			this.ui.taShow.setText(this.ui.taShow.getText() + s + "\n");
			System.out.println(s + "\n");
		}
	}
}