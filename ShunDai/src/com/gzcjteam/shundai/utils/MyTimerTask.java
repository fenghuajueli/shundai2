package com.gzcjteam.shundai.utils;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;
import android.os.Message;

public class MyTimerTask extends TimerTask {
      
	private static final int DAOJISAHI = 0;
	private static final int DAOJISAHI1 = 1;
	public static int  timeover;
	private Handler  handler;
	private Timer time;
	
	
	public MyTimerTask(int timeo,Handler  handler,Timer time) {
		this.timeover=timeo;
		this.handler=handler;
		this.time=time;
	}

	@Override
	public void run() {
		if (timeover == 0) {
			Message msg = new Message();
			msg.what = DAOJISAHI;
			handler.sendMessage(msg);
			time.cancel();
			cancel();
		} else {
			Message msg = new Message();
			msg.what = DAOJISAHI1;
			handler.sendMessage(msg);
		}
		timeover--;
	}
}
