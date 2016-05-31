package com.gzcjteam.shundai;

import org.json.JSONArray;
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
import com.gzcjteam.shundai.weight.PullUpDialog;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Message;
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
import android.widget.TextView;

public class RenWuInfoActivity extends Activity implements OnClickListener {

	private NormalTopBar mTopBar;
	private Button btn_shensu;
	private String task_id = "";
	private TextView tv_launch_name;
	private TextView tv_renwucode;
	private TextView tv_myphone;
	private TextView tv_kuaidigonsi;
	private TextView tv_xiaoqu;
	private TextView tv_myaddress;
	private TextView tv_expressinfo;
	private TextView tv_lingqu_name;
	private TextView lingqu_phone;
	PullUpDialog dia;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_renwuinfo);
		tv_launch_name = (TextView) findViewById(R.id.launch_name);
		tv_renwucode = (TextView) findViewById(R.id.renwucode);
		tv_myphone = (TextView) findViewById(R.id.myphone);
		tv_kuaidigonsi = (TextView) findViewById(R.id.kuaidigonsi);
		tv_xiaoqu = (TextView) findViewById(R.id.xiaoqu);
		tv_myaddress = (TextView) findViewById(R.id.myaddress);
		tv_expressinfo = (TextView) findViewById(R.id.expressinfo);
		tv_lingqu_name = (TextView) findViewById(R.id.lingqu_name);
		lingqu_phone = (TextView) findViewById(R.id.lingqu_phone);
		btn_shensu = (Button) findViewById(R.id.btn_shensu);
		mTopBar = (NormalTopBar) findViewById(R.id.activity_login_top_bar);
		btn_shensu.setOnClickListener(this);
		mTopBar.setOnBackListener(RenWuInfoActivity.this);
		mTopBar.setOnActionListener(RenWuInfoActivity.this);
		mTopBar.setBackVisibility(true);
		mTopBar.setTitle("任务详情");
		Intent intent = getIntent();
		task_id = intent.getStringExtra("task_id");
		System.out.println(task_id);
		task_Info(task_id, getUserInfo.getInstance().getId());
	}

	public void task_Info(String ta_id, String user_id) {
		dia = new PullUpDialog(this, R.style.adialog, "信息加载中。。");
		dia.show();
		String url = "http://119.29.140.85/index.php/task/task_detail";
		RequestParams params = new RequestParams();
		params.put("user_id", user_id);
		System.out.println(user_id);
		params.put("task_id", ta_id);
		System.out.println(ta_id);
		RequestUtils.ClientPost(url, params, new NetCallBack() {
			@Override
			public void onMySuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					Boolean status = json.getBoolean("status");
					String info = json.getString("info");
					JSONObject jsonarray = new JSONObject(json
							.getString("data"));
					if (status) {
						dia.dismiss();
						if (jsonarray != null) {
							JSONObject jsono = jsonarray
									.getJSONObject("task_detail");
							tv_launch_name.setText(jsono
									.getString("launch_user_name"));
							tv_renwucode.setText("任务编号："+jsono.getString("task_number"));
							tv_myphone.setText("手机："+jsono.getString("launch_phone"));
							tv_kuaidigonsi.setText("快递公司："+jsono
									.getString("express_name"));
							tv_xiaoqu.setText("校区："+jsono.getString("school_name"));
							tv_myaddress.setText("收货地址："+jsono
									.getString("receive_address"));
							tv_expressinfo.setText(jsono
									.getString("express_info"));
							tv_lingqu_name.setText("领取人："+jsono
									.getString("receive_express_name"));
							lingqu_phone.setText("手机："+jsono
									.getString("receive_express_phone"));
						} else {
							ToastUtil.show(RenWuInfoActivity.this,
									"未查找到该任务的详细信息！");
						}
					} else {
						dia.dismiss();
						ToastUtil.show(RenWuInfoActivity.this, "未查找到该任务的详细信息！");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onMyFailure(Throwable arg0) {
				dia.dismiss();
				finish();
				ToastUtil.show(RenWuInfoActivity.this, "查询失败！");
			}
		});

	}

	@Override
	public void onClick(View v) {
		if (v == mTopBar.getBackView()) {
			// clickBack();
			finish();
		} else if (v == btn_shensu) {
			Intent intent = new Intent();
			intent.setClass(RenWuInfoActivity.this,
					PersonalCenterActivity.class);
			Bundle bundle = new Bundle();
			// 传递name参数为tinyphp
			bundle.putString("PageName", "MyTastShenSu");
			intent.putExtras(bundle);
			startActivity(intent);
		}
	}

}
