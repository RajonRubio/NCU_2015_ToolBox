package Protocols;

import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;

public class Command {
	String type;
	
	public Command(String type) {
		this.type = type;
	}
	
	public String get_type() {
		return type;
	}
}
