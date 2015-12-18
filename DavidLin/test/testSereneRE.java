package test;

import static org.junit.Assert.*;

import java.awt.Image;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import SEmodule.SceneRenderEngine;
import SEmodule.DOM;

public class testSereneRE {
	DOM fake = new DOM();
	SceneRenderEngine sre = new SceneRenderEngine(fake);
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		sre.dom.getVirtualCharactor();
		sre.renderScene();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		assertEquals(325,sre.dom.virtualCharactor.x);
		assertEquals(700,sre.dom.virtualCharactor.y);
		assertEquals(0, sre.map[1][1]);
		assertEquals(3, sre.map[1][3]);
		assertEquals(2, sre.map[1][5]);
		assertEquals(0, sre.map[3][1]);
		assertEquals(2, sre.map[3][5]);

	}

}
