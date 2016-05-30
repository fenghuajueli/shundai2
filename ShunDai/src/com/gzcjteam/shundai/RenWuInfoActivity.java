package com.gzcjteam.shundai;

import org.json.JSONException;
import org.json.JSONObject;

import com.gzcjteam.shundai.bean.NetCallBack;
import com.gzcjteam.shundai.fargment.ForgivePwdFrament;
import com.gzcjteam.shundai.fargment.LoginFrament;
import com.gzcjteam.shundai.fargment.NextFrament;
import com.gzcjteam.shundai.fargment.PwdNextFrament;
import com.gzcjteam.shundai.fargment.SignUpFrament;
import com.gzcjteam.shundai.utils.RequestUtils;
import com.gzcjteam.shundai.utils.ToastUtil;
import com.gzcjteam.shundai.utils.getUserInfo;
import com.gzcjteam.shundai.weight.NormalTopBar;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RenWuInfoActivity extends Activity implements OnClickListener {

	private NormalTopBar mTopBar;
	private Button  btn_shensu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_renwuinfo);
		btn_shensu=(Button) findViewById(R.id.btn_shensu);
		mTopBar = (NormalTopBar) findViewById(R.id.activity_login_top_bar);
		btn_shensu.setOnClickListener(this);
		mTopBar.setOnBackListener(RenWuInfoActivity.this);
		mTopBar.setOnActionListener(RenWuInfoActivity.this);		
		mTopBar.setBackVisibility(true);
		mTopBar.setTitle("任务详情");
	}

	@Override
	public void onClick(View v) {
		if (v == mTopBar.getBackView()) {
			//clickBack();
			finish();
		} else if(v==btn_shensu){
//			Intent  intent=new Intent();
//			intent.setClass(RenWuInfoActivity.this, ShenSuActivity.class);
//			startActivity(intent);
		}
	}

	

}
