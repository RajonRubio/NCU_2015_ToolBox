import java.awt.*;
import java.awt.event.*;

import javax.swing.JComponent;
  
public class MouseEventDemo extends Panel implements MouseListener ,MouseMotionListener,KeyListener {
    Frame frame;
    Panel panel;
    Label text;
    TextArea textx;
    public MouseEventDemo(Frame frame) {
        //frame = new Frame("XDDDD");
        frame.addWindowListener(new AdapterDemo());
        frame.setSize(900, 800);

        //panel = new Panel();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addKeyListener(this);
        this.setBackground(Color.GREEN);
        text = new Label("something happened..");
        textx = new TextArea();
        textx.addKeyListener(this); 
        
        //MouseEventDemo canvas = new MouseEventDemo();

        
        this.add(text);
        frame.add(textx);
        //frame.add(this, BorderLayout.CENTER);
        //frame.add(panel);
        frame.add(this);
        //frame.add(canvas, BorderLayout.CENTER);
       // panel.setVisible(true);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
    	Frame f = new Frame("El Psy Congroo");
        new MouseEventDemo(f);
        
    }
    
    public int Region(int x,int y){
    	if(x>=10&&x<870 && y>10&&y<585){
    		return 1;
    	}
    	else if(x>=10&&x<300 && y>595&&y<755){
    		return 9;
    	}
    	else if(x>=320&&x<620 && y>595&&y<755){
    		return 8;
    	}
    	else if(x>=630&&x<710 && y>595&&y<675){
    		return 2;
    	}
    	else if(x>=710&&x<790 && y>595&&y<675){
    		return 3;
    	}
    	else if(x>=790&&x<870 && y>595&&y<675){
    		return 4;
    	}
    	else if(x>=630&&x<710 && y>675&&y<755){
    		return 5;
    	}
    	else if(x>=710&&x<790 && y>675&&y<755){
    		return 6;
    	}
    	else if(x>=790&&x<870 && y>675&&y<755){
    		return 7;
    	}
    	return 0;
    	
    }
    
	@Override
	public void mouseClicked(MouseEvent arg0) {
		text.setText("x: " + arg0.getX() + ", y: " + arg0.getY());
		System.out.println("screen " +Region(arg0.getX(),arg0.getY())+": Mouse Click"  );
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		text.setText("x: " + e.getX() + ", y: " + e.getY());
		System.out.println("screen " +Region(e.getX(),e.getY())+": Mouse Press"  );
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		text.setText("x: " + e.getX() + ", y: " + e.getY());	
		System.out.println("screen " +Region(e.getX(),e.getY())+": Mouse Releas"  );
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		text.setText("x: " + e.getX() + ", y: " + e.getY());
		System.out.println("screen " +Region(e.getX(),e.getY())+": Mouse Drag"  );
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==e.VK_UP){ //向上鍵
			System.out.println("上");
        }
		else if (e.getKeyCode()==e.VK_DOWN) //向下鍵
        {
			System.out.println("下");
        }
        else if (e.getKeyCode()==e.VK_LEFT) //向左鍵
        {
        	System.out.println("左");
        }
        else if (e.getKeyCode()==e.VK_RIGHT) //向右鍵
        {
        	System.out.println("右");
        }
        
		
		
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		//System.out.println("keyReleased");
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		//System.out.println("keyTyped");
		
	}
	
	
	Image dbImage;
	Graphics dbg;
	
	public void paint(Graphics g) {
		
		g.setColor(Color.BLACK);

		
		g.setColor(new Color(0,97,0));
		for(int j=0 ; j<20 ; j++){
			for(int i=0 ; i<50 ; i++){
				
				g.drawLine(i+100*j-800, 0, i+100*j,800);
			}
		}
		
		
		g.setColor(Color.RED);
		g.fillRect(10, 10, 860, 575);
		g.setColor(Color.CYAN);
		g.fillRect(10, 595,300, 160);
		g.setColor(Color.MAGENTA);
		g.fillRect(320,595,300, 160);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(630,595,80, 80);
		g.fillRect(630,675,80, 80);
		g.fillRect(710,595,80, 80);
		g.fillRect(710,675,80, 80);
		g.fillRect(790,595,80, 80);
		g.fillRect(790,675,80, 80);
		
		g.setColor(Color.BLACK);
		g.drawRect(10, 10, 860, 575);
		g.drawRect(10, 595,300, 160);
		g.drawRect(320,595,300, 160);
		
		g.drawRect(630,595,80, 80);
		g.drawRect(710,595,80, 80);
		g.drawRect(790,595,80, 80);
		
		g.drawRect(630,675,80, 80);
		g.drawRect(710,675,80, 80);
		g.drawRect(790,675,80, 80);
		
		
		//this.repaint();
	}
	
	/*public void paintComponent(Graphics g) {
		g.drawLine(10, 10, 220, 220);
		//repaint();
	}*/
    
}
  
class AdapterDemo extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }
}