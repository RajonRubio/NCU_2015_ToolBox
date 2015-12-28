package Protocols;

public class Character extends Command {
    int clientno;
    int status;
    int currentHP;

    public Character() {
        super("Character");
        this.clientno = 123;
        this.status = 456;
        this.currentHP = 789;
    }

    public int get_client_no() {
        return this.clientno;
    }

    public int get_status() {
        return this.status;
    }

    public int get_current_hp() {
        return this.currentHP;
    }
}
