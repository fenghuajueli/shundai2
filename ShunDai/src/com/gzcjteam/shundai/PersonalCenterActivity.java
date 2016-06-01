package com.gzcjteam.shundai;

import com.gzcjteam.shundai.fargment.MyAboutFragment;
import com.gzcjteam.shundai.fargment.MyRenZhenInfoFragment;
import com.gzcjteam.shundai.fargment.MyInfoFragment;
import com.gzcjteam.shundai.fargment.MyTastShenSuFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;
import android.widget.Toast;

public class PersonalCenterActivity extends FragmentActivity {
	private Fragment fragment;
	private FragmentManager fm;
	private static String backstatcTag = "PERSONALCENTER";
	private static String pageName = null;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_center);
		Intent intent = getIntent();
		pageName = intent.getStringExtra("PageName");

		SkipToFragment();

	}
	
	//个人中心页面 跳转到不同的fragment页面
	private void SkipToFragment() {
		fm = getSupportFragmentManager();
		if (pageName.equals("PersonalInfo") && pageName != null) {
			fragment = new MyInfoFragment(getApplication(), backstatcTag);
			FragmentTransaction transaction = fm.beginTransaction();
			transaction.add(R.id.fm_personal_center, fragment, backstatcTag);
			// transaction.addToBackStack(backstatcTag);
			transaction.commit();
		} else if (pageName.equals("MyRenZhenInfo") && pageName != null) {
			fragment = new MyRenZhenInfoFragment(this);
			FragmentTransaction transaction = fm.beginTransaction();
			transaction.add(R.id.fm_personal_center, fragment);
			transaction.commit();
		}else if (pageName.equals("MyTastShenSu")&&pageName!=null) {
			fragment=new MyTastShenSuFragment();
			FragmentTransaction transaction=fm.beginTransaction();
			transaction.add(R.id.fm_personal_center, fragment);
			transaction.commit();
		} else if (pageName.equals("MyAboutShunDai")&&pageName!=null) {
			fragment=new MyAboutFragment();
			FragmentTransaction transaction=fm.beginTransaction();
			transaction.add(R.id.fm_personal_center, fragment);
			transaction.commit();
		}else {
			Toast.makeText(this, "系统错误，请检查源代码！", 1).show();
		}
	}
}
