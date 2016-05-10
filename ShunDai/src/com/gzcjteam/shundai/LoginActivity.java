package com.gzcjteam.shundai;

import com.gzcjteam.shundai.fargment.LoginFrament;
import com.gzcjteam.shundai.fargment.NextFrament;
import com.gzcjteam.shundai.fargment.SignUpFrament;
import com.gzcjteam.shundai.weight.NormalTopBar;

import android.app.Activity;
import android.content.Intent;
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

public class LoginActivity extends FragmentActivity implements OnClickListener{

	private NormalTopBar mTopBar;
	private Fragment currentFra;
	private String currentTag;
	private FragmentManager fm;
	public static final String TAG_LOGO = "logo";
	public static final String TAG_SIGN_IN = "sign_in";
	public static final String TAG_SIGN_UP = "sign_up";
	public static final String TAG_FILL_INFO = "fill_info";
	private static final String TAG_NEXT = "next";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mTopBar = (NormalTopBar) findViewById(R.id.activity_login_top_bar);
		mTopBar.setOnBackListener(LoginActivity.this);
		mTopBar.setOnActionListener(LoginActivity.this);
		fm = getSupportFragmentManager();
		//mTopBar.setVisibility(View.GONE);
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
		}else if(v==mTopBar.getActionView()){
			//跳转到下一步界面
			System.out.println("进给。。。。。。。。。。。。。。。。");
			go2Next();
		}
		
	}
	
	public void go2SignIn() {		
		Intent  intent=new Intent();
		intent.setClass(LoginActivity.this, MainActivity.class);
		startActivity(intent);	
		finish();
		
//		Fragment fragment = fm.findFragmentByTag(TAG_SIGN_IN);
//		if (fragment == null) {
//			fragment = new SignInFrament();
//		}
//
//		// 设置 Topbar
//		mTopBar.setVisibility(View.VISIBLE);
//		mTopBar.setTitle("登录");
//		mTopBar.setBackVisibility(true);
//		FragmentTransaction ft = fm.beginTransaction();
//		ft.replace(R.id.contanier_login, fragment, TAG_SIGN_IN);
//		ft.addToBackStack(TAG_SIGN_IN);
//		ft.commit();
	}

	public void go2SignUp() {
				
		Fragment fragment = fm.findFragmentByTag(TAG_SIGN_UP);
		if (fragment == null) {
			fragment = new SignUpFrament();
		}
		// 设置Topbar		
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
	
	@Override
	public void onBackPressed() {
		clickBack();
	}
	
	private void clickBack() {
		int count = fm.getBackStackEntryCount();	
		System.out.println("个数"+count);
		if (count <= 1) {
			finish();
		} else {
			fm.popBackStack();
			if (count == 2) {
				BackStackEntry entry = fm.getBackStackEntryAt(0);
				String name = entry.getName();
				System.out.println("当前："+name);
				if (TAG_LOGO.equals(name)) {
					mTopBar.setVisibility(View.GONE);
				}
				else if(TAG_SIGN_IN.equals(name)){
					System.out.println("进入、、、、、、、");
					mTopBar.setBackVisibility(false);
					mTopBar.setTitle("用户登录");
					mTopBar.setActionTextVisibility(false);
				}else if(TAG_SIGN_UP.equals(name)){
					mTopBar.setVisibility(View.VISIBLE);
					mTopBar.setTitle("顺带用户注册");
					mTopBar.setBackVisibility(true);
					mTopBar.setActionText("下一步");
					mTopBar.setActionTextVisibility(true);			
				}	
			}else if (count == 3) {
				BackStackEntry entry = fm.getBackStackEntryAt(1);
				String name = entry.getName();
				System.out.println("当前："+name);
				if (TAG_LOGO.equals(name)) {
					mTopBar.setVisibility(View.GONE);
				}
				else if(TAG_SIGN_IN.equals(name)){
					System.out.println("进入、、、、、、、");
					mTopBar.setBackVisibility(false);
					mTopBar.setTitle("用户登录");
					mTopBar.setActionTextVisibility(false);
				}else if(TAG_SIGN_UP.equals(name)){
					mTopBar.setVisibility(View.VISIBLE);
					mTopBar.setTitle("顺带用户注册");
					mTopBar.setBackVisibility(true);
					mTopBar.setActionText("下一步");
					mTopBar.setActionTextVisibility(true);
				}else if(TAG_NEXT.equals(name)){
					mTopBar.setVisibility(View.VISIBLE);
					mTopBar.setTitle("注册");
					mTopBar.setBackVisibility(true);
					mTopBar.setActionTextVisibility(false);
				}	
			}
		}
	}

}
