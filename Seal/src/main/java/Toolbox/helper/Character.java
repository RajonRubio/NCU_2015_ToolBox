package Toolbox.helper;

public class Character {
    public int no;
    public int dir;
    public int speed;
    public int x;
    public int y;

    @Override
    public String toString() {
        String message = "";
        message += "Character{";
        message += "No:" + no + ",";
        message += "Dir:" + dir + ",";
        message += "Speed:" + speed + ",";
        message += "X:" + x + ",";
        message += "Y:" + y + "}";
        return message;
    }
}

