package Protocols;

public class Bullets extends Command{
    ArrayList<BulletT> list;

    public Bullets() {
        super("Bullets");
        list = new ArrayList<BulletT>();
    }

    public ArrayList<BulletT> get_list() {
        return this.list;
    }

    public void add(BulletT bullet) {
        list.add(bullet);
    }

    public void addAll(ArrayList<BulletT> bullets) {
        list.addAll(bullets);
    }
}
