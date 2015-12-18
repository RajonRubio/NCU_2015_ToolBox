package SEmodule;

import javax.swing.JFrame;

public class Frame extends JFrame{
	Frame(){
		//Map map = new Map();
		setTitle("MODULE 20151210");
		this.setLayout(null); 
		setSize(500,300);
		setLocationRelativeTo(null);//視窗彈出位置 預設在正中間
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new SceneRenderEngine());//要把paintComponent畫出來 就要用這個函式
        setVisible(true);
	}
}
