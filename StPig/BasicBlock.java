package prototypeE;

import java.awt.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;

public class BasicBlock {
	private int type;
	private Image background;
	public BasicBlock()
	{
		Random rnd = new Random();
		type = rnd.nextInt(5);
		newbackground();
	}
	
	public Image getbackground()
	{
		return background;
	}
	
	public void newbackground()
	{
		switch(type)
		{
		case 0:
			try
			{
				background = ImageIO.read(new File("green.png"));
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			break;
		case 1:
			try
			{
				background = ImageIO.read(new File("red.png"));
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			break;
		case 2:
			try
			{
				background = ImageIO.read(new File("blue.png"));
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			break;
		case 3:
			try
			{
				background = ImageIO.read(new File("yellow.png"));
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			break;
		case 4:
			try
			{
				background = ImageIO.read(new File("black.png"));
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			break;
		}
	}
}
