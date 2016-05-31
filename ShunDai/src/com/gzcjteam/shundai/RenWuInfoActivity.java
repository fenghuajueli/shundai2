package com.gzcjteam.shundai;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
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
	private String launchuserid;//发布者id
	private String launchusernick;//发布者昵称
	private String launchuserheadpicurl;//头像地址
	private String launch_user_phone;//发布者手机
    private ImageView  img;
    private Button btn_cancel;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_renwuinfo);
		tv_launch_name = (TextView) findViewById(R.id.launch_name);
		img=(ImageView) findViewById(R.id.tupian);
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
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		btn_shensu.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		mTopBar.setOnBackListener(RenWuInfoActivity.this);
		mTopBar.setOnActionListener(RenWuInfoActivity.this);
		mTopBar.setBackVisibility(true);
		mTopBar.setTitle("任务详情");
		Intent intent = getIntent();
		task_id = intent.getStringExtra("task_id");
		launchuserid = intent.getStringExtra("launch_user_id");
		launchusernick = intent.getStringExtra("launch_user_nick");
		launchuserheadpicurl = intent.getStringExtra("launch_user_head_pic_url");
		launch_user_phone = intent.getStringExtra("launch_user_phone");		
		System.out.println(task_id);
		task_Info(task_id, getUserInfo.getInstance().getId());
//		 Bitmap bitmap =
//				 getHttpBitmap("http://119.29.140.85"+launchuserheadpicurl);
//		  img.setImageBitmap(bitmap);	//设置Bitmap
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
							tv_lingqu_name.setText("领取人："+getUserInfo.getInstance().getNick());
							lingqu_phone.setText("手机："+getUserInfo.getInstance().getPhone());
							String express_status = jsono
									.getString("express_status");
							switch (express_status) {
							case "0":// 取消任务按钮可用
								btn_shensu.setEnabled(false);
								btn_shensu.setBackgroundColor(0x99DFDFDF);
								break;
							case "1":

								break;
							case "2":
								btn_cancel.setEnabled(false);
								btn_cancel.setBackgroundColor(0x99DFDFDF);
								btn_shensu.setEnabled(false);
								btn_shensu.setBackgroundColor(0x99DFDFDF);
								break;
							default:
								break;
							}
							
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
		}else if(v==btn_cancel){
			 cancelRenWu(task_id, getUserInfo.getInstance().getId());	
		}
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
						ToastUtil.show(RenWuInfoActivity.this, "任务取消成功！");
					} else {
						dia.dismiss();
						ToastUtil.show(RenWuInfoActivity.this, "任务取消失败！");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onMyFailure(Throwable arg0) {
				dia.dismiss();
				ToastUtil.show(RenWuInfoActivity.this, "任务取消失败！");
			}
		});

	}
	
	/**
	* 从服务器取图片
	*http://bbs.3gstdy.com
	* @param url
	* @return
	*/
	public static Bitmap getHttpBitmap(String url) {
	     URL myFileUrl = null;
	     Bitmap bitmap = null;
	     try {	         
	          myFileUrl = new URL(url);
	     } catch (MalformedURLException e) {
	          e.printStackTrace();
	     }
	     try {
	          HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
	          conn.setConnectTimeout(0);
	          conn.setDoInput(true);
	          conn.connect();
	          InputStream is = conn.getInputStream();
	          bitmap = BitmapFactory.decodeStream(is);
	          is.close();
	     } catch (IOException e) {
	          e.printStackTrace();
	     }
	     return bitmap;
	}

}
