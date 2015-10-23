package ChatroomClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread {
	ClientUI ui;
	Socket client;
	BufferedReader reader;
	PrintWriter writer;

	public ClientThread(ClientUI ui) {
		this.ui = ui;
		try {
			client = new Socket("127.0.0.1", 1228); // �o�̳]�m�s���A�Ⱦ��ݪ�IP���ݤf
			println("�s���A�Ⱦ����\�G�ݤf1228");
			reader = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			writer = new PrintWriter(client.getOutputStream(), true);
			// �p�G��true�A�hprintln�Bprintf��format��k�N��s��X�w�İ�
		} catch (IOException e) {
			println("�s���A�Ⱦ����ѡG�ݤf1228");
			println(e.toString());
			e.printStackTrace();
		}
		this.start();
	}

	public void run() {
		String msg = "";
		while (true) {
			try {
				msg = reader.readLine();
			} catch (IOException e) {
				println("�A�Ⱦ��_�}�s��");
				break;
			}
			if (msg != null && msg.trim() != "") {
				println(">>" + msg);
			}
		}
	}

	public void sendMsg(String msg) {
		try {
			writer.println(msg);
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