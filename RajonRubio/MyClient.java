package trytry;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class MyClient extends JFrame implements ActionListener{
	 String    name,ip="";       
	 BufferedReader  reader;           
	 PrintStream  writer;
	 Socket    sock;
	 
	 JTextArea   incoming = new JTextArea(15,50); 
	 JTextField   outgoing = new JTextField(20);     
	 JLabel    jlmane   = new JLabel("�A���W�r�G");   
	 JLabel    jlip  = new JLabel("��Jip�G");   
	 JTextField   jfmane   = new JTextField("�L�W",10);
	 JTextField   jfip   = new JTextField("127.0.0.1",10);
	 JLabel    state  = new JLabel("�п�J�A���W�r�ΧA���W�r"); 
	 MenuBar mBar = new MenuBar();      
	 Menu mFile = new Menu("�ɮ�");
	 MenuItem mFileSave=new MenuItem("�x�s�ɮ�");     
	 
	 public static void main(String[] args){
		 MyClient client = new MyClient();       //
	 }
	 
	 MyClient (){ 
		 super("�h�J�s�uClient��");          
	     JPanel maneipPanel  = new JPanel();   
	     JButton setmaneip = new JButton("�s�u�]�w");
	     setmaneip.addActionListener(this);
	     maneipPanel.add(jlmane);
	     maneipPanel.add(jfmane);         
	     maneipPanel.add(jlip);
	     maneipPanel.add(jfip); 
	     maneipPanel.add(setmaneip); 
	     getContentPane().add(BorderLayout.NORTH,maneipPanel);  
	     
	     JButton sendButton = new JButton("�e�X");
	     sendButton.addActionListener(this);       
	     incoming.setLineWrap(true);         
	     incoming.setWrapStyleWord(true); 
	     incoming.setEditable(false); 
	     JScrollPane qScroller = new JScrollPane(incoming);
	     
	     //�����u��  
	     qScroller.setVerticalScrollBarPolicy(
	     ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); 
	     //�����u��
	     qScroller.setHorizontalScrollBarPolicy(
	     ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	     
	     JPanel mainPanel = new JPanel();       
	     mainPanel.add(qScroller);
	     mainPanel.add(outgoing);
	     mainPanel.add(sendButton);
	     getContentPane().add(BorderLayout.CENTER,mainPanel);  
	  
	     mFileSave.addActionListener(this); 
		 mFile.add(mFileSave);  
		 mBar.add(mFile);
		 setMenuBar(mBar);
		 getContentPane().add(BorderLayout.SOUTH,state);    
		 setSize(600,450);
		 setVisible(true);
		 addWindowListener(new WindowAdapter()      
		 {
			 public void windowClosing(WindowEvent e){
				 System.out.println("���}��ѫ�");
				 System.exit(0);
			 }
		 });
	 }
	 
	 //-�إ߳s�u
	 private void EstablishConnection(){
		 try{
			 sock = new Socket(ip,8888);      
			 InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());  
			 reader = new BufferedReader(streamReader);    
			 writer = new PrintStream(sock.getOutputStream());
			 state.setText("�����إ�-�s�u���\"); 
			 System.out.println("�����إ�-�s�u���\");    
	     }catch(IOException ex ){
	     System.out.println("�إ߳s�u����");
	 }
	 }
	 //-�������
	 public class IncomingReader implements Runnable{
		 public void run(){
	     String message;
	     try{
	    	 while ((message = reader.readLine()) != null){
	    		 incoming.append(message+'\n');
	    	 }
	     }catch(Exception ex ){ex.printStackTrace();}
		 }
	 } 
	//-5-���U���ʧ@
	 public void actionPerformed(ActionEvent e){
		 String str=e.getActionCommand();   
		 if(str.equals("�s�u�]�w")){
			 name = jfmane.getText();
			 ip  = jfip.getText(); 
			 state.setText("�]�w"+name+":"+ip); 
			 EstablishConnection();          
			 Thread readerThread = new Thread(new IncomingReader());  
			 readerThread.start();
		 }else if(str.equals("�e�X")){    
		 if((ip!=null)&&(outgoing.getText()!="")) {
			 try{     //�e�X���
				 writer.println((name+":"+outgoing.getText())); 
				 writer.flush();         
			 }catch(Exception ex ){
		     System.out.println("�e�X��ƥ���");
	    }
			 outgoing.setText("");        
	   }
	  }else if (str.equals("�x�s�ɮ�"))       
	  {               
	   try{             
		   FileWriter f = new FileWriter("log.txt");     
		   f.write(incoming.getText());      
		   f.close();           
		   state.setText("�x�s�ɮצ��\");
	   }catch (IOException e2){
		   state.setText("�x�s�ɮץ���");
	   }              
	  } 
	 }
	}