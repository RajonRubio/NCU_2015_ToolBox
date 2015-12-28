package CDC;

public class BasicBlock {
	private int type;
	private Boolean touchable;
	static int grass=0,woodbox=1,rockbox=2,woodbox_1=3,woodbox_2=4;
	
	public BasicBlock() {
		type = grass;
		touchable = true;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public void setTouchable(boolean touchable) {
		this.touchable = touchable;
	}
	
	public int getType() {
		return type;
	}
}
