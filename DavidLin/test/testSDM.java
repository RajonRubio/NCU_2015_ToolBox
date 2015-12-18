package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import SEmodule.BasicBlock;
import SEmodule.SceneDataModule;

public class testSDM {
	SceneDataModule sdm = new SceneDataModule();
	BasicBlock[][] scene = new BasicBlock[20][50];
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		sdm.loadMap("map.txt");
		sdm.Readimage();
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void test() {
		assertEquals(0,sdm.mapdata[1][2]);
		assertEquals(1,sdm.mapdata[2][40]);
		assertEquals(2,sdm.mapdata[1][0]);
		assertEquals(3,sdm.mapdata[3][3]);
		assertEquals("grass.png" ,sdm.filemanager(0) );
		assertEquals("lake.png" ,sdm.filemanager(1) );
		assertEquals("rock.png" ,sdm.filemanager(2) );
		assertEquals("tree.png" ,sdm.filemanager(3) );
	}

}
