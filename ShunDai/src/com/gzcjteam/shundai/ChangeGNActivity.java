package com.gzcjteam.shundai;

import com.gzcjteam.shundai.utils.ToastUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChangeGNActivity extends Activity implements OnClickListener {

	private Button btn_kuaidi;
	private Button btn_xiaoye;
	private Button btn_waimai;
	private Button btn_lietou;
	private Button btn_shenhuo;
	private Button btn_dayin;
	private static boolean isExit = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_frament);
		btn_kuaidi = (Button) findViewById(R.id.btn_kuaidi);
		btn_xiaoye = (Button) findViewById(R.id.btn_zhaoxiaoye);
		btn_waimai = (Button) findViewById(R.id.btn_waimian);
		btn_lietou = (Button) findViewById(R.id.btn_lietou);
		btn_shenhuo = (Button) findViewById(R.id.btn_shenghuo);
		btn_dayin = (Button) findViewById(R.id.btn_daidayin);
		btn_kuaidi.setOnClickListener(this);
		btn_xiaoye.setOnClickListener(this);
		btn_waimai.setOnClickListener(this);
		btn_lietou.setOnClickListener(this);
		btn_shenhuo.setOnClickListener(this);
		btn_dayin.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_kuaidi:
			startActivity(new Intent().setClass(ChangeGNActivity.this,
					MainActivity.class));
			break;
		case R.id.btn_zhaoxiaoye:
			ToastUtil.show(this, "正在开发中。。");
			break;
		case R.id.btn_waimian:
			ToastUtil.show(this, "正在开发中。。");
			break;
		case R.id.btn_lietou:
			ToastUtil.show(this, "正在开发中。。");
			break;
		case R.id.btn_shenghuo:
			ToastUtil.show(this, "正在开发中。。");
			break;
		case R.id.btn_daidayin:
			ToastUtil.show(this, "正在开发中。。");
			break;

		default:
			break;
		}

	}
	
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit = false;
		}
	};
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void exit() {
		if (!isExit) {
			isExit = true;
			ToastUtil.show(ChangeGNActivity.this, "再按一次退出程序");
			// 利用handler延迟发送更改状态信息
			mHandler.sendEmptyMessageDelayed(0, 2000);
		} else {
			finish();
			System.exit(0);
		}
	}

}
