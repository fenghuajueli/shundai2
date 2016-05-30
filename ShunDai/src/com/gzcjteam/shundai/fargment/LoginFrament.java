package com.gzcjteam.shundai.fargment;


import com.gzcjteam.shundai.MainActivity;
import com.gzcjteam.shundai.LoginActivity;
import com.gzcjteam.shundai.R;
import com.gzcjteam.shundai.utils.ToastUtil;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginFrament extends Fragment implements OnClickListener {
	
	public final static String ARG_KEY = "ARG";

	public final static int ARG_TYPE_FIRST = 0;
	public final static int ARG_TYPE_LOGINED = 1;
	public int currentFlag = 0;
	private Button btnSignUp;
	private Button btnSignIn;
	private Button btn_forgive_password;
	private EditText  userName;
	private EditText password;
	private Handler handler = new Handler();
	private SharedPreferences  shp;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.login_frament, container,false);
		initView(view);
		initEvent();
		shp=getActivity().getSharedPreferences("USERCONFIG", getActivity().MODE_PRIVATE);
		userName.setText(shp.getString("phone", ""));
		
		System.out.println("密码为："+shp.getString("password", ""));
		password.setText(shp.getString("password", ""));
		return view;
	}
	
	private void initView(View view) {
		btnSignIn = (Button) view.findViewById(R.id.btn_sign_in);
		btnSignUp = (Button) view.findViewById(R.id.btn_sign_up);
		btn_forgive_password = (Button) view.findViewById(R.id.btn_forgive_password);
        userName=(EditText) view.findViewById(R.id.Account);
		password=(EditText) view.findViewById(R.id.password);		
		if (currentFlag == ARG_TYPE_FIRST) {
			btnSignIn.setVisibility(View.VISIBLE);
			btnSignUp.setVisibility(View.VISIBLE);
		} else {
			btnSignIn.setVisibility(View.GONE);
			btnSignUp.setVisibility(View.GONE);

			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					startActivity(new Intent(getActivity(), MainActivity.class));
					getActivity().finish();
				}
			}, 1500);
		}
	}

	@Override
	public void onClick(View v) {
		if (btnSignIn == v) {
			String user=userName.getText().toString();
			String pwd=password.getText().toString();
			if (user.isEmpty()) {
				ToastUtil.show(getActivity(), "用户名不能为空！");
				return;
			}
			if (pwd.isEmpty()) {
				ToastUtil.show(getActivity(), "密码不能为空！");
				return;
			}			
			signIn(user,pwd);
		} else if (btnSignUp == v) {
			signUp();
		}else if(btn_forgive_password==v){
			//忘记密码
			forgivePwd();
		}		
	}
	
	private void initEvent() {
		btnSignIn.setOnClickListener(this);
		btnSignUp.setOnClickListener(this);
		btn_forgive_password.setOnClickListener(this);
		
	}
	
	private void signIn(String user,String pwd) {
		FragmentActivity activity = getActivity();
		if (activity != null) {
			((LoginActivity) activity).go2SignIn(user,pwd);
		}
	}

	private void signUp() {
		FragmentActivity activity = getActivity();
		if (activity != null) {
			((LoginActivity) activity).go2SignUp();
		}
	}
	
	private void forgivePwd() {
		FragmentActivity activity = getActivity();
		if (activity != null) {
			((LoginActivity) activity).go2forgivePwd();
		}
	}
	

}
