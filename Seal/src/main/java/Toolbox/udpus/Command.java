package Toolbox.udpus;

import Toolbox.helper.Item;
import Toolbox.helper.Character;

public class Command {
    String code;
    String type;
    Item item;
    Character character;

    public Command() {
    }
    
    public void decode(String message) {
        String[] inside = message.split("[{}:,;]");
        assert inside.length%2 == 0 : "You seem missing something";
        code = inside[0];
        type = inside[1];
        if (type.equals("Item")) {
            find_item_inside(inside);
        } else if (type.equals("Character")) {
            find_character_inside(inside);
        }
    }

    void find_item_inside(String[] inside) {
        assert inside.length == 2+6*2 : "Miss Something";
        item = new Item();
        for (int i = 2 ; i+1 < inside.length ; i+=2) {
            String key = inside[i];
            String value = inside[i+1];
            if (key.equals("Name")) {
                item.name = value;
            } else if (key.equals("Index")) {
                item.index = Integer.parseInt(value);
            } else if (key.equals("Shared")) {
                item.shared = Boolean.parseBoolean(value);
            } else if (key.equals("Owner")) {
                item.owner = Integer.parseInt(value);
            } else if (key.equals("X")) {
                item.x = Integer.parseInt(value);
            } else if (key.equals("Y")) {
                item.y = Integer.parseInt(value);
            } else {
                assert false : "Miss Something";
            }
        }
    }

    void find_character_inside(String[] inside) {
        assert inside.length == 2+5*2 : "Miss Something";
        character = new Character();
        for (int i = 2 ; i+1 < inside.length ; i+=2) {
            String key = inside[i];
            String value = inside[i+1];
            if (key.equals("No")) {
                character.no = Integer.parseInt(value);
            } else if (key.equals("Dir")) {
                character.dir = Integer.parseInt(value);
            } else if (key.equals("Speed")) {
                character.speed = Integer.parseInt(value);
            } else if (key.equals("X")) {
                character.x = Integer.parseInt(value);
            } else if (key.equals("Y")) {
                character.y = Integer.parseInt(value);
            } else {
                assert false : "Miss Something";
            }
        }
    }
}
