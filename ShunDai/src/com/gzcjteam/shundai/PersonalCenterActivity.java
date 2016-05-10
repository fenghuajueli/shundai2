package com.gzcjteam.shundai;

import com.gzcjteam.shundai.fargment.PersonalInfoFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class PersonalCenterActivity extends FragmentActivity {
	private Fragment fragment;
	private FragmentManager fm;
	private static String backstatcTag="PERSONALCENTER";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_personal_center);
		
		fm = getSupportFragmentManager();
		fragment=new PersonalInfoFragment(getApplication(),backstatcTag);
		
		FragmentTransaction transaction=fm.beginTransaction();
		transaction.replace(R.id.fm_personal_center, fragment, backstatcTag);
		transaction.addToBackStack(backstatcTag);
		transaction.commit();
		
		
	}
}
