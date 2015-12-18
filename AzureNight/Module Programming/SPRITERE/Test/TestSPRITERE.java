package Test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import DOM.DOM;
import SPRITERE.SPRITERE;

public class TestSPRITERE {
	
	SPRITERE spritere;
	DOM dom;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		spritere = new SPRITERE(DOM.getInstance());
		dom = DOM.getInstance();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRenderSprites() throws Exception {
		dom.checkDraw[0] = false;
		dom.checkDraw[1] = false;
		spritere.renderSprites();
		assertTrue(dom.checkDraw[0]);
		assertTrue(dom.checkDraw[1]);
	}

}
