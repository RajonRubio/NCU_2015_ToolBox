package Test;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import TCPSM.TCPSM;
import DOM.DOM;

public class TestDOM {
	
	DOM dom;
	TCPSM tcpsm;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		dom = DOM.getInstance();
		tcpsm = TCPSM.getInstance();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddVirtualCharacter() throws Exception {
		dom.player.clear();
		try {
			dom.addVirtualCharacter(1, false);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
		assertEquals(dom.player.size(), 1);
	}
	
	@Test(expected = Exception.class)
	public void testAddExistingVirtualCharacter() throws Exception {
		dom.player.clear();
		try {
			dom.addVirtualCharacter(1, false);
			dom.addVirtualCharacter(1, false);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	public void testAddItem() throws Exception {
		dom.thing.clear();
		try {
			dom.addItem("gun", 10, 100, 100, true);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
		assertEquals(dom.thing.size(), 1);
	}
	
	@Test(expected = Exception.class)
	public void testAddExistingItem() throws Exception {
		dom.thing.clear();
		try {
			dom.addItem("gun", 10, 100, 100, true);
			dom.addItem("gun", 10, 100, 100, true);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	public void testUpdateVirtualCharacter() throws Exception {
		dom.player.clear();
		try {
			dom.addVirtualCharacter(1, false);
			dom.updateVirtualCharacter(1, 3, 5, 10, 10);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
		assertEquals(dom.player.get(0).dir, 3);
		assertEquals(dom.player.get(0).speed, 5);
		assertEquals(dom.player.get(0).x, 10);
		assertEquals(dom.player.get(0).y, 10);
	}
	
	@Test(expected = Exception.class)
	public void testUpdateNotExistingVirtualCharacter() throws Exception {
		dom.player.clear();
		try {
			dom.updateVirtualCharacter(1, 3, 5, 10, 10);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	public void testUpdateItem() throws Exception {
		dom.thing.clear();
		try {
			dom.addItem("gun", 10, 100, 100, true);
			dom.updateItem(10, true, 1, 150, 200);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
		assertEquals(dom.thing.get(0).owner, 1);
		assertEquals(dom.thing.get(0).x, 150);
		assertEquals(dom.thing.get(0).y, 200);
	}
	
	@Test(expected = Exception.class)
	public void testUpdateNotExistingItem() throws Exception {
		dom.thing.clear();
		try {
			dom.updateItem(10, true, 1, 150, 200);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	public void testGetAllDynamicObjects() throws Exception {
		dom.player.clear();
		dom.thing.clear();
		try {
			dom.addVirtualCharacter(1, true);
			dom.addVirtualCharacter(2, false);
			dom.addVirtualCharacter(3, false);
			dom.addVirtualCharacter(4, false);
			dom.addItem("gun", 1, 10, 10, true);
			dom.addItem("gun", 2, 20, 20, true);
			dom.addItem("gun", 3, 30, 30, true);
			dom.addItem("gun", 4, 40, 40, true);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
		assertEquals(dom.getAllDynamicObjects().size(), 8);
	}
	
	@Test
	public void testGetVirtualCharacterXY() throws Exception {
		dom.player.clear();
		try {
			dom.addVirtualCharacter(1, true);
			dom.updateVirtualCharacter(1, 1, 5, 50, 100);
			Point temp = dom.getVirtualCharacterXY();
			assertEquals(temp.x, 50);
			assertEquals(temp.y, 100);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	@Test(expected = Exception.class)
	public void testGetNotExistingVirtualCharacterXY() throws Exception {
		dom.player.clear();
		try {
			dom.addVirtualCharacter(1, false);
			dom.updateVirtualCharacter(1, 1, 5, 50, 100);
			dom.getVirtualCharacterXY();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
	
	@Test
	public void testKeyGETPressed() throws Exception {
		dom.player.clear();
		dom.thing.clear();
		try {
			dom.addVirtualCharacter(1, true);
			dom.updateVirtualCharacter(1, 1, 5, 50, 100);
			dom.addItem("gun", 10, 51, 100, true);
			dom.keyGETPressed();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
		assertEquals(tcpsm.checkInputMoves, "MoveCode is: 5");
	}
	
	@Test(expected = Exception.class)
	public void testKeyGETPressedWithWrongDirection() throws Exception {
		dom.player.clear();
		dom.thing.clear();
		try {
			dom.addVirtualCharacter(1, true);
			dom.updateVirtualCharacter(1, 10, 5, 50, 100);
			dom.addItem("gun", 10, 51, 100, true);
			dom.keyGETPressed();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			throw new Exception(e.getMessage());
		}
	}
}
