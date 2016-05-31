package com.gzcjteam.shundai;

import org.json.JSONException;
import org.json.JSONObject;

import com.gzcjteam.shundai.bean.NetCallBack;
import com.gzcjteam.shundai.utils.RequestUtils;
import com.gzcjteam.shundai.utils.ToastUtil;
import com.gzcjteam.shundai.utils.getUserInfo;
import com.gzcjteam.shundai.weight.NormalTopBar;
import com.gzcjteam.shundai.weight.PullUpDialog;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WoFaBuInfoActivity extends Activity implements OnClickListener {

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
	private String launch_id="";
	private Button btn_cancel;
	private Button btn_shouhuo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wo_fa_bu_info);
		tv_launch_name = (TextView) findViewById(R.id.launch_name);
		tv_renwucode = (TextView) findViewById(R.id.renwucode);
		tv_myphone = (TextView) findViewById(R.id.myphone);
		tv_kuaidigonsi = (TextView) findViewById(R.id.kuaidigonsi);
		tv_xiaoqu = (TextView) findViewById(R.id.xiaoqu);
		tv_myaddress = (TextView) findViewById(R.id.myaddress);
		tv_expressinfo = (TextView) findViewById(R.id.expressinfo);
		tv_lingqu_name = (TextView) findViewById(R.id.lingqu_name);
		lingqu_phone = (TextView) findViewById(R.id.lingqu_phone);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_shouhuo = (Button) findViewById(R.id.btn_shouhuo);
		btn_shensu = (Button) findViewById(R.id.btn_shensu);
		mTopBar = (NormalTopBar) findViewById(R.id.activity_login_top_bar);
		btn_shensu.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		btn_shouhuo.setOnClickListener(this);
		mTopBar.setOnBackListener(WoFaBuInfoActivity.this);
		mTopBar.setOnActionListener(WoFaBuInfoActivity.this);
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
		params.put("task_id", ta_id);
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
							launch_id=jsono
									.getString("launch_user_id");
							tv_launch_name.setText(jsono
									.getString("launch_user_name"));
							tv_renwucode.setText("任务编号："
									+ jsono.getString("task_number"));
							tv_myphone.setText("手机："
									+ jsono.getString("launch_phone"));
							tv_kuaidigonsi.setText("快递公司："
									+ jsono.getString("express_name"));
							tv_xiaoqu.setText("校区："
									+ jsono.getString("school_name"));
							tv_myaddress.setText("收货地址："
									+ jsono.getString("receive_address"));
							tv_expressinfo.setText(jsono
									.getString("express_info"));
							tv_lingqu_name.setText("领取人："
									+ jsono.getString("receive_express_name"));
							lingqu_phone.setText("手机："
									+ jsono.getString("receive_express_phone"));
							String express_status = jsono
									.getString("express_status");
							switch (express_status) {
							case "0":// 取消任务按钮可用
								btn_shouhuo.setEnabled(false);
								btn_shouhuo.setBackgroundColor(0x99DFDFDF);
								btn_shensu.setEnabled(false);
								btn_shensu.setBackgroundColor(0x99DFDFDF);
								break;
							case "1":								
								btn_shouhuo.setEnabled(false);
								btn_shouhuo.setBackgroundColor(0x99DFDFDF);
								btn_cancel.setEnabled(false);
								btn_cancel.setBackgroundColor(0x99DFDFDF);
								break;
							case "2":
								btn_cancel.setEnabled(false);
								btn_cancel.setBackgroundColor(0x99DFDFDF);
								btn_shouhuo.setEnabled(true);
								break;
							default:
								break;
							}

						} else {
							ToastUtil.show(WoFaBuInfoActivity.this,
									"未查找到该任务的详细信息！");
						}
					} else {
						dia.dismiss();
						ToastUtil
								.show(WoFaBuInfoActivity.this, "未查找到该任务的详细信息！");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onMyFailure(Throwable arg0) {
				dia.dismiss();
				ToastUtil.show(WoFaBuInfoActivity.this, "查询失败！");
			}
		});

	}

	public void cancelRenWu(String ta_id, String user_id) {
		dia = new PullUpDialog(this, R.style.adialog, "任务取消中。。");
		dia.show();
		String url = "http://119.29.140.85/index.php/task/cancel_task";
		RequestParams params = new RequestParams();
		params.put("complete_user_id", user_id);
		params.put("task_id", ta_id);
		RequestUtils.ClientPost(url, params, new NetCallBack() {
			@Override
			public void onMySuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					Boolean status = json.getBoolean("status");
					String info = json.getString("info");
					if (status) {
						dia.dismiss();
						ToastUtil.show(WoFaBuInfoActivity.this, "任务取消成功！");
					} else {
						dia.dismiss();
						ToastUtil.show(WoFaBuInfoActivity.this, "任务取消失败！");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onMyFailure(Throwable arg0) {
				dia.dismiss();
				ToastUtil.show(WoFaBuInfoActivity.this, "任务取消失败！");
			}
		});

	}

	public void querenShouJian(String ta_id, String launch_id) {
		dia = new PullUpDialog(this, R.style.adialog, "确认收件中。。");
		dia.show();
		String url = "http://119.29.140.85/index.php/task/sure_pay_package";
		RequestParams params = new RequestParams();
		params.put("launch_user_id", launch_id);
		params.put("task_id", ta_id);
		params.put("pay_type", "1");
		RequestUtils.ClientPost(url, params, new NetCallBack() {
			@Override
			public void onMySuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					Boolean status = json.getBoolean("status");
					String info = json.getString("info");
					if (status) {
						dia.dismiss();
						ToastUtil.show(WoFaBuInfoActivity.this, "收件成功！");
					} else {
						dia.dismiss();
						ToastUtil.show(WoFaBuInfoActivity.this, "收件失败！");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void onMyFailure(Throwable arg0) {
				dia.dismiss();
				ToastUtil.show(WoFaBuInfoActivity.this, "收件失败！");
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
			intent.setClass(WoFaBuInfoActivity.this,
					PersonalCenterActivity.class);
			Bundle bundle = new Bundle();
			// 传递name参数为tinyphp
			bundle.putString("PageName", "MyTastShenSu");
			intent.putExtras(bundle);
			startActivity(intent);
		} else if (v == btn_shouhuo) {
			//ToastUtil.show(WoFaBuInfoActivity.this, "可用");
			querenShouJian(task_id,launch_id);
		} else if (v == btn_cancel) {
			cancelRenWu(task_id, getUserInfo.getInstance().getId());
			// ToastUtil.show(WoFaBuInfoActivity.this, "可用");
		}
	}
}
