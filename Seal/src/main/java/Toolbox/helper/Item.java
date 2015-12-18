package Toolbox.helper;

public class Item {
    public String name;
    public int index;
    public boolean shared;
    public int owner;
    public int x;
    public int y;

    @Override
    public String toString() {
        String message = "";
        message += "Item{";
        message += "Name:" + name + ",";
        message += "Index:" + index + ",";
        message += "Shared:" + shared + ",";
        message += "Owner:" + owner + ",";
        message += "X:" + x + ",";
        message += "Y:" + y + "}";
        return message;
    }
}

