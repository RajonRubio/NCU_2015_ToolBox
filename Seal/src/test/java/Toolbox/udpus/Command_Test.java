package Toolbox.udpus;

import Toolbox.helper.Item;
import Toolbox.helper.Character;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Command_Test {
    private Command command;

    @Before
    public void setup() {
        command = new Command();
    }

    @After
    public void reset() {
        command = null;
    }

    @Test
    public void test_constructor() {
    }

    @Test
    public void test_decode_add_item() {
        Item item = new Item();
        String message = "Add:" + item.toString();
        command.decode(message);

        assertTrue(command.code.equals("Add"));
        assertTrue(command.type.equals("Item"));
    }

    @Test
    public void test_decode_add_character() {
        Character character = new Character();
        String message = "Add:" + character.toString();
        command.decode(message);

        assertTrue(command.code.equals("Add"));
        assertTrue(command.type.equals("Character"));
    }

    @Test
    public void test_decode_update_item() {
        Item item = new Item();
        String message = "Update:" + item.toString();
        command.decode(message);

        assertTrue(command.code.equals("Update"));
        assertTrue(command.type.equals("Item"));
    }

    @Test
    public void test_decode_update_character() {
        Character character = new Character();
        String message = "Update:" + character.toString();
        command.decode(message);

        assertTrue(command.code.equals("Update"));
        assertTrue(command.type.equals("Character"));
    }

    @Test(expected = AssertionError.class)
    public void test_decode_item_missing_1() {
        String message = "XXX:Item{a:1,b:2,c:3,d:4,e:5,f:6}";
        command.decode(message);
    }

    @Test(expected = AssertionError.class)
    public void test_decode_item_missing_2() {
        String message = "XXX:Item{xxxxxxxxxxx}";
        command.decode(message);
    }

    @Test(expected = AssertionError.class)
    public void test_decode_character_missing_1() {
        String message = "XXX:Character{a:1,b:2,c:3,d:4,e:5}";
        command.decode(message);
    }

    @Test(expected = AssertionError.class)
    public void test_decode_character_missing_2() {
        String message = "XXX:Character{xxxxxxxxxx}";
        command.decode(message);
    }

    @Test
    public void test_decode_item() {
        Item item = new Item();
        item.name = "Hello World!";
        item.index = 123;
        item.shared = true;
        item.owner = 999;
        item.x = -1;
        item.y = 65535;
        String message = "XXX:" + item.toString();
        System.out.println(message);
        command.decode(message);

        assertTrue(command.item.name.equals(item.name));
        assertEquals(command.item.index, item.index);
        assertTrue(command.item.shared);
        assertEquals(command.item.owner, item.owner);
        assertEquals(command.item.x, item.x);
        assertEquals(command.item.y, item.y);
    }

    @Test
    public void test_decode_character() {
        Character character = new Character();
        character.no = 999;
        character.dir = 180;
        character.speed = 11111;
        character.x = 65535;
        character.y = -1;
        String message = "XXX:" + character.toString();
        command.decode(message);

        assertEquals(command.character.no, character.no);
        assertEquals(command.character.dir, character.dir);
        assertEquals(command.character.speed, character.speed);
        assertEquals(command.character.x, character.x);
        assertEquals(command.character.y, character.y);
    }

    @Test(expected = AssertionError.class)
    public void test_decode_weird_item_name_1() {
        Item item = new Item();
        item.name = "{";
        String message = "XXX:" + item.toString();
        System.out.println(message);
        command.decode(message);
    }

    @Test(expected = AssertionError.class)
    public void test_decode_weird_item_name_2() {
        Item item = new Item();
        item.name = "}";
        String message = "XXX:" + item.toString();
        System.out.println(message);
        command.decode(message);
    }

    @Test(expected = AssertionError.class)
    public void test_decode_weird_item_name_3() {
        Item item = new Item();
        item.name = ",";
        String message = "XXX:" + item.toString();
        System.out.println(message);
        command.decode(message);
    }

    @Test(expected = AssertionError.class)
    public void test_decode_weird_item_name_4() {
        Item item = new Item();
        item.name = ":";
        String message = "XXX:" + item.toString();
        System.out.println(message);
        command.decode(message);
    }
}
	

