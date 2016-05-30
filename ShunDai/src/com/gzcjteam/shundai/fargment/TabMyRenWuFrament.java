package com.gzcjteam.shundai.fargment;


import com.gzcjteam.shundai.MainActivity;
import com.gzcjteam.shundai.LoginActivity;
import com.gzcjteam.shundai.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TabMyRenWuFrament extends Fragment implements OnClickListener {
	
	public final static String ARG_KEY = "ARG";

	public final static int ARG_TYPE_FIRST = 0;
	public final static int ARG_TYPE_LOGINED = 1;
	public int currentFlag = 0;
	private Button btnSignUp;
	private Button btnSignIn;
	private Handler handler = new Handler();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.tabmy_frament, container,false);
		// initView(view);
		// initEvent();
		return view;
	}
	
	private void initView(View view) {
		
	}

	@Override
	public void onClick(View v) {
	
	}
	
	private void initEvent() {
		btnSignIn.setOnClickListener(this);
		btnSignUp.setOnClickListener(this);
	}
	

}
