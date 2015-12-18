package Toolbox.dom;

public interface Dynamic_Object {
    public void addVirtualCharacter(int clientno);
    public void addItem(String name, int index, boolean shared);
    public void updateVirtualCharacter(int clientno, int dir, int speed, int x, int y);
    public void updateItem(int index, boolean shared, int owner, int x, int y);
}

