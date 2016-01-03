package Protocols;

import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;
import java.io.Serializable;

public class Command implements Serializable {
	String type;
	
	public Command(String type) {
		this.type = type;
	}
	
	public String get_type() {
		return type;
	}
}
