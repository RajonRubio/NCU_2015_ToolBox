package Protocols;

import java.io.Serializable;

public class Command implements Serializable {
    private static final long serialVersionUID = -4507489610617393544L;
    String type;

    public Command(String type) {
        this.type = type;
    }

    public String get_type() {
        return this.type;
    }
}
