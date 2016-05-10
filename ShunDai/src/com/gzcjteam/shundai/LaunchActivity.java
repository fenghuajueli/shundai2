package com.gzcjteam.shundai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;

public class LaunchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);
		
		new Thread() {
			public void run() {
				SystemClock.sleep(2000);
				Intent intent = new Intent();
				intent.setClass(LaunchActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();
			};
		}.start();
		
	}

}
