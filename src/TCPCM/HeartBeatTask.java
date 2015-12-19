package TCPCM;

import java.util.TimerTask;

public class HeartBeatTask extends TimerTask {
	private TCPCM.Handler handler;
	
	public HeartBeatTask(TCPCM.Handler handler) {
		this.handler = handler;
	}
	
	@Override
	public void run() {
		if (this.handler.getTimeout() <= 0) {
			this.handler.disConnected();
		} else {
			this.handler.countDown();
		}
	}
}
