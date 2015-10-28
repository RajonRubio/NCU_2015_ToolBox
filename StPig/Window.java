package prototypeE;

import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.*;

public class Window extends JFrame implements KeyListener{
	Background background;
	private int keycode;
	public Window()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(516,339);
		setTitle("prototypeE");
		addKeyListener(this);
		background = new Background();
		add(background);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		keycode = e.getKeyCode();
		switch(keycode)
		{
		case 37:
			background.setX(-25);
			background.update();
			break;
		case 38:
			background.setY(-25);
			background.update();
			break;
		case 39:
			background.setX(25);
			background.update();
			break;
		case 40:
			background.setY(25);
			background.update();
			break;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
