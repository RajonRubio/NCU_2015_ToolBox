package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import SEmodule.BasicBlock;

public class testBasicBlock {
	static BasicBlock[][] scene = new BasicBlock[20][50];
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		for(int y=0;y<20;y++){
			for(int x=0;x<50;x++){
				scene[y][x] =new BasicBlock(); //全部宣告一次 給他記憶體空間
			}
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before   //共同的前置作業
	public void setUp()  {
		scene[1][2].type =0;
		scene[1][3].type =1;
		scene[1][4].type =2;
		scene[1][5].type =3;
		scene[1][6].type =4;//錯誤的
	}

	@After
	public void tearDown()  {
	}

	@Test
	public void test() {
		assertEquals(0,scene[1][2].type);
		assertEquals(1,scene[1][3].type);
		assertEquals(2,scene[1][4].type);
		assertEquals(3,scene[1][5].type);
		assertEquals(true,scene[1][2].touchable);
//		assertNotEquals(2,scene[1][2].type);
	}

}
