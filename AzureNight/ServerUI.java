package ChatroomServer;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/* �o�����O�A�Ⱦ��ݪ�UI */
public class ServerUI extends JFrame {
	public static void main(String[] args) {
		ServerUI serverUI = new ServerUI();
	}

	public JButton btStart; // �ҰʪA�Ⱦ�
	public JButton btSend; // �o�e�H�����s
	public JTextField tfSend; // �ݭn�o�e���奻�H��
	public JTextArea taShow; // �H���i��
	public Server server; // �ΨӺ�ť�Ȥ�ݳs��
	static List<Socket> clients; // �O�s�s����A�Ⱦ����Ȥ��

	public ServerUI() {
		super("�A�Ⱦ���");
		btStart = new JButton("�ҰʪA��");
		btSend = new JButton("�o�e�H��");
		tfSend = new JTextField(10);
		taShow = new JTextArea();

		btStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				server = new Server(ServerUI.this);
			}
		});
		btSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = tfSend.getText();
				server.sendMsg(s);
				server.ui.taShow.setText(server.ui.taShow.getText() + ">>"
						+ tfSend.getText() + "\n");
				System.out.println(">>" + s + "\n");
				tfSend.setText("");
			}
		});
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int a = JOptionPane.showConfirmDialog(null, "�T�w�����ܡH", "���ɴ���",
						JOptionPane.YES_NO_OPTION);
				if (a == 1) {
					server.closeServer();
					System.exit(0); // ����
				}
			}
		});
		JPanel top = new JPanel(new FlowLayout());
		top.add(tfSend);
		top.add(btSend);
		top.add(btStart);
		this.add(top, BorderLayout.SOUTH);
		final JScrollPane sp = new JScrollPane();
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setViewportView(this.taShow);
		this.taShow.setEditable(false);
		this.add(sp, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 300);
		this.setLocation(100, 200);
		this.setVisible(true);
	}
}
