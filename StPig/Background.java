package prototypeE;

import java.awt.*;
import javax.swing.*;

public class Background extends JPanel{
	private int x,y;
	BasicBlock[][] background = new BasicBlock[20][50];
	public Background()
	{
		setBounds(0,0,500,300);
		x = 2500;
		y = 1000;
		init();
	}
	
	private void init()
	{
		for(int i=0;i<20;i++)
		{
			for(int j=0;j<50;j++)
			{
				background[i][j] = new BasicBlock();
			}
		}
	}
	
	public void paintComponent(Graphics g)
	{
		int Xout=0,Yout=0,Xclip=0,Yclip=0,imore=0,jmore=0;
		super.paintComponent(g);
		switch(x)
		{
			case 0:
				Xout = 250;
				break;
			case 5000:
				Xout = -250;
				Xclip = -200;
				jmore = -7;
				break;
			default:
				Xout = 0;
				if(x < 100)
				{
					switch(x%100)
					{	
						case 0:
							Xclip = 150;
							break;
						case 25:
							Xclip = 225;
							break;
						case 50:
							Xclip = 200;
							break;
						case 75:
							Xclip = 175;
							break;
					}
				}
				else if(x >= 100 && x < 200)
				{
					jmore = -1;
					switch(x%100)
					{	
						case 0:
							Xclip = 150;
							break;
						case 25:
							Xclip = 125;
							break;
						case 50:
							Xclip = 100;
							break;
						case 75:
							Xclip = 75;
							break;
					}
				}
				else if(x >= 200 && x < 300)
				{
					jmore = -2;
					switch(x%100)
					{	
						case 0:
							Xclip = 50;
							break;
						case 25:
							Xclip = 25;
							break;
						case 50:
							Xclip = 0;
							break;
						case 75:
							Xclip = -25;
							break;
					}
				}
				else if(x >= 300 && x < 4700)
				{
					jmore = -3;
					switch(x%100)
					{	
						case 0:
							Xclip = -50;
							break;
						case 25:
							Xclip = -75;
							break;
						case 50:
							Xclip = -100;
							break;
						case 75:
							Xclip = -125;
							break;
					}
				}
				else if(x >= 4700 && x < 4800)
				{
					jmore = -4;
					switch(x%100)
					{	
						case 0:
							Xclip = -150;
							break;
						case 25:
							Xclip = -175;
							break;
						case 50:
							Xclip = -200;
							break;
						case 75:
							Xclip = -225;
							break;
					}
				}
				else if(x >= 4800 && x < 4900)
				{
					jmore = -5;
					switch(x%100)
					{	
						case 0:
							Xclip = -250;
							break;
						case 25:
							Xclip = -275;
							break;
						case 50:
							Xclip = -300;
							break;
						case 75:
							Xclip = -325;
							break;
					}
				}
				else
				{
					jmore = -6;
					switch(x%100)
					{	
						case 0:
							Xclip = -350;
							break;
						case 25:
							Xclip = -375;
							break;
						case 50:
							Xclip = -400;
							break;
						case 75:
							Xclip = -425;
							break;
					}
				}
				break;
		}
		switch(y)
		{
			case 0:
				Yout = 150;
				break;
			case 2000:
				Yout = -150;
				Yclip = -200;
				imore = -5;
				break;
			default:
				Yout = 0;
				if(y <= 100)
				{
					switch(y%100)
					{
						case 0:
							imore = -1;
							Yclip = 50;
							break;
						case 25:
							Yclip = 125;
							break;
						case 50:
							Yclip = 100;
							break;
						case 75:
							Yclip = 75;
							break;
					}
				}
				else if(y > 100 && y <200)
				{
					imore = -1;
					switch(y%100)
					{
						case 0:
							Yclip = 50;
							break;
						case 25:
							Yclip = 25;
							break;
						case 50:
							Yclip = 0;
							break;
						case 75:
							Yclip = -25;
							break;
					}
				}
				else if(y >= 200 && y < 1800)
				{
					imore = -2;
					switch(y%100)
					{
						case 0:
							Yclip = -50;
							break;
						case 25:
							Yclip = -75;
							break;
						case 50:
							Yclip = -100;
							break;
						case 75:
							Yclip = -125;
							break;
					}
				}
				else if(y >= 1800 && y < 1900)
				{
					imore = -3;
					switch(y%100)
					{
						case 0:
							Yclip = -150;
							break;
						case 25:
							Yclip = -175;
							break;
						case 50:
							Yclip = -200;
							break;
						case 75:
							Yclip = -225;
							break;
					}
				}
				else
				{
					imore = -4;
					switch(y%100)
					{
						case 0:
							Yclip = -250;
							break;
						case 25:
							Yclip = -275;
							break;
						case 50:
							Yclip = -300;
							break;
						case 75:
							Yclip = -325;
							break;
					}
				}
				break;
		}
		for(int i=0;i<5;i++)
		{
			for(int j=0;j<7;j++)
			{
				g.drawImage(background[(y/100+i+imore)][(x/100+j+jmore)].getbackground(),j*100 + Xclip + Xout,i*100 + Yclip + Yout,100,100,null);
				g.fillOval(247,147,6,6);
			}
		}
	}
	
	public void setX(int moveX)
	{
		if(moveX > 0)
		{
			if(x < 5000)
				x = x + moveX;
		}
		else
		{
			if(x > 0)
				x = x + moveX;
		}
	}
	
	public void setY(int moveY)
	{
		if(moveY > 0)
		{
			if(y < 2000)
				y = y + moveY;
		}
		else
		{
			if(y > 0)
				y = y + moveY;
		}
	}
	
	public void update()
	{
		repaint();
	}
}
