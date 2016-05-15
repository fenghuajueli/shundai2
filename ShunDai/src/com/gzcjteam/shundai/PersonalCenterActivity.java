package com.gzcjteam.shundai;

import com.gzcjteam.shundai.fargment.PersonalInfoFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

public class PersonalCenterActivity extends FragmentActivity {
	private Fragment fragment;
	private FragmentManager fm;
	private static String backstatcTag = "PERSONALCENTER";
	private static String pageName = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_center);
		Intent intent = getIntent();
		pageName = intent.getStringExtra("PageName");

		SkipToFragment();


	}

	private void SkipToFragment() {
		if (pageName.equals("PersonalInfo") && pageName != null) {
			fm = getSupportFragmentManager();
			fragment = new PersonalInfoFragment(getApplication(), backstatcTag);
			FragmentTransaction transaction = fm.beginTransaction();
			transaction.add(R.id.fm_personal_center, fragment, backstatcTag);
			// transaction.addToBackStack(backstatcTag);
			transaction.commit();
		} else {
			Toast.makeText(this, "系统错误，请检查源代码！", 1).show();
		}
	}
}
