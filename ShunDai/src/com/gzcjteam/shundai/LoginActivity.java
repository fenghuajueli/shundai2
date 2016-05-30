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
import com.gzcjteam.shundai.weight.PullUpDialog;
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

public class LoginActivity extends FragmentActivity implements OnClickListener {

	private NormalTopBar mTopBar;
	private Fragment currentFra;
	private String currentTag;
	private FragmentManager fm;
	public static final String TAG_LOGO = "logo";
	public static final String TAG_SIGN_IN = "sign_in";
	public static final String TAG_SIGN_UP = "sign_up";
	public static final String TAG_FILL_INFO = "fill_info";
	private static final String TAG_NEXT = "next";
	private SharedPreferences shp;
	private String FLAG = "";
    private SignUpFrament  signup=new SignUpFrament();
    PullUpDialog  dia;
    private  String  PWD="";
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mTopBar = (NormalTopBar) findViewById(R.id.activity_login_top_bar);
		mTopBar.setOnBackListener(LoginActivity.this);
		mTopBar.setOnActionListener(LoginActivity.this);
		fm = getSupportFragmentManager();
		// mTopBar.setVisibility(View.GONE);
		mTopBar.setBackVisibility(false);
		mTopBar.setTitle("用户登录");
		currentFra = new LoginFrament();
		Bundle args = new Bundle();
		currentTag = TAG_SIGN_IN;
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.replace(R.id.contanier_login, currentFra, currentTag);
		transaction.addToBackStack(currentTag);
		transaction.commit();
       	
		
	}

	@Override
	public void onClick(View v) {
		if (v == mTopBar.getBackView()) {
			clickBack();
		} 
	}

	public void go2SignIn(String user, String pwd) {
		// 执行登陆
		PWD=pwd;
		dia=new PullUpDialog(LoginActivity.this, R.style.adialog, "登录中。。");
		dia.show();
		String url = "http://119.29.140.85/index.php/user/login";
		RequestParams params = new RequestParams();
		params.put("phone", user);
		params.put("password", pwd);
		RequestUtils.ClientPost(url, params, new NetCallBack() {

			@Override
			public void onMySuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					Boolean status = json.getBoolean("status");
					String info = json.getString("info");
					JSONObject data;
					if (status) {
						// 登陆成功
						data = json.getJSONObject("data");
						saveConfig(data,PWD);
						setUserInfo(data);
						startActivity(new Intent().setClass(LoginActivity.this,
								MainActivity.class));
						dia.dismiss();
						finish();
					} else {
						ToastUtil.show(LoginActivity.this, info);
						dia.dismiss();
					}
				} catch (JSONException e) {
					e.printStackTrace();
					dia.dismiss();
				}
			}

			@Override
			public void onMyFailure(Throwable arg0) {
				dia.dismiss();
				ToastUtil.show(LoginActivity.this, "未联网或服务器错误！");
			}
		});
	}

	/**
	 * @param data
	 * @throws JSONException
	 *             保存用户信息到手机
	 * 
	 */
	public void saveConfig(JSONObject data,String pwd) throws JSONException {
		shp = getSharedPreferences("USERCONFIG", MODE_PRIVATE);
		Editor editor = shp.edit();
		editor.putString("id", data.getString("id"));
		editor.putString("phone", data.getString("phone"));
		editor.putString("password", pwd);
		editor.putString("nick", data.getString("nick"));
		editor.putString("head_pic_url", data.getString("head_pic_url"));
		editor.putString("rank", data.getString("rank"));
		editor.putString("identity_status", data.getString("identity_status"));
		editor.putString("register_time", data.getString("register_time"));
		editor.putString("school_name", data.getString("school_name"));
		editor.putString("floor_address", data.getString("floor_address"));
		editor.commit();
	}

	/**
	 * @param data
	 * @throws JSONException
	 *             设置用户信息，方便调用
	 */
	public void setUserInfo(JSONObject data) throws JSONException {
		getUserInfo userinf = getUserInfo.getInstance();
		userinf.setId(data.getString("id"));
		userinf.setPhone(data.getString("phone"));
		userinf.setPassword(data.getString("password"));
		userinf.setFloor_address(data.getString("floor_address"));
		userinf.setHead_pic_url(data.getString("head_pic_url"));
		userinf.setIdentity_status(data.getString("identity_status"));
		userinf.setNick(data.getString("nick"));
		userinf.setRank(data.getString("rank"));
		userinf.setRegister_time(data.getString("register_time"));
		userinf.setSchool_name(data.getString("school_name"));
	}

	public void go2SignUp() {
		Fragment fragment = fm.findFragmentByTag(TAG_SIGN_UP);
		if (fragment == null) {
			fragment = new SignUpFrament();
		}
		// 设置Topbar		
		FLAG = "zhuce";
		mTopBar.setVisibility(View.VISIBLE);
		mTopBar.setTitle("顺带用户注册");
		mTopBar.setBackVisibility(true);
		mTopBar.setActionText("下一步");
		mTopBar.setActionTextVisibility(true);
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.contanier_login, fragment, TAG_SIGN_UP);
		ft.addToBackStack(TAG_SIGN_UP);
		ft.commit();
	}

	public void go2Login() {
		
		Fragment fragment = fm.findFragmentByTag(TAG_SIGN_IN);
		fragment.isRemoving();
		if (fragment == null) {
			fragment = new LoginFrament();
		}
		// 设置Topbar		
		FLAG = "zhuce";
		mTopBar.setBackVisibility(false);
		mTopBar.setTitle("用户登录");
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.contanier_login, fragment, TAG_SIGN_IN);
		ft.addToBackStack(TAG_SIGN_IN);
		ft.commit();
	}
	
	
	
	public void go2forgivePwd() {
		Fragment fragment = fm.findFragmentByTag(TAG_SIGN_UP);
		if (fragment == null) {
			fragment = new ForgivePwdFrament();
		}
		// 设置Topbar
		FLAG = "forgive";
		mTopBar.setVisibility(View.VISIBLE);
		mTopBar.setTitle("忘记密码");
		mTopBar.setBackVisibility(true);
		mTopBar.setActionText("下一步");
		mTopBar.setActionTextVisibility(true);
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.contanier_login, fragment, TAG_SIGN_UP);
		ft.addToBackStack(TAG_SIGN_UP);
		ft.commit();
	}

	public void go2Next() {

		Fragment fragment = fm.findFragmentByTag(TAG_NEXT);
		if (fragment == null) {
			fragment = new NextFrament();
		}
		
		// 设置Topbar
		mTopBar.setVisibility(View.VISIBLE);
		mTopBar.setTitle("注册");
		mTopBar.setBackVisibility(true);
		mTopBar.setActionTextVisibility(false);
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.contanier_login, fragment, TAG_NEXT);
		ft.addToBackStack(TAG_NEXT);
		ft.commit();
	}

	public void go2Forgive() {

		Fragment fragment = fm.findFragmentByTag(TAG_NEXT);
		if (fragment == null) {
			fragment = new PwdNextFrament();
		}
		// 设置Topbar
		mTopBar.setVisibility(View.VISIBLE);
		mTopBar.setTitle("修改密码");
		mTopBar.setBackVisibility(true);
		mTopBar.setActionTextVisibility(false);
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.contanier_login, fragment, TAG_NEXT);
		ft.addToBackStack(TAG_NEXT);
		ft.commit();
	}

	@Override
	public void onBackPressed() {
		clickBack();
	}

	private void clickBack() {
		int count = fm.getBackStackEntryCount();
		System.out.println("个数" + count);
		if (count <= 1) {
			finish();
		} else {
			fm.popBackStack();
			if (count == 2) {
				BackStackEntry entry = fm.getBackStackEntryAt(0);
				String name = entry.getName();
				System.out.println("当前：" + name);
				if (TAG_LOGO.equals(name)) {
					mTopBar.setVisibility(View.GONE);
				} else if (TAG_SIGN_IN.equals(name)) {
					mTopBar.setBackVisibility(false);
					mTopBar.setTitle("用户登录");
					mTopBar.setActionTextVisibility(false);
				} else if (TAG_SIGN_UP.equals(name)) {
					mTopBar.setVisibility(View.VISIBLE);
					mTopBar.setTitle("顺带用户注册");
					mTopBar.setBackVisibility(true);
					mTopBar.setActionText("下一步");
					mTopBar.setActionTextVisibility(true);
				}
			} else if (count == 3) {
				BackStackEntry entry = fm.getBackStackEntryAt(1);
				String name = entry.getName();
				if (TAG_LOGO.equals(name)) {
					mTopBar.setVisibility(View.GONE);
				} else if (TAG_SIGN_IN.equals(name)) {
					mTopBar.setBackVisibility(false);
					mTopBar.setTitle("用户登录");
					mTopBar.setActionTextVisibility(false);
				} else if (TAG_SIGN_UP.equals(name)) {
					mTopBar.setVisibility(View.VISIBLE);
					mTopBar.setTitle("顺带用户注册");
					mTopBar.setBackVisibility(true);
					mTopBar.setActionText("下一步");
					mTopBar.setActionTextVisibility(true);
				} else if (TAG_NEXT.equals(name)) {
					mTopBar.setVisibility(View.VISIBLE);
					mTopBar.setTitle("注册");
					mTopBar.setBackVisibility(true);
					mTopBar.setActionTextVisibility(false);
				}
			}
		}
	}

}
