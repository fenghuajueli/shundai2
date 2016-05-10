package com.gzcjteam.shundai.fargment;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import com.gzcjteam.shundai.utils.ToastUtil;
import com.gzcjteam.shundai.MainActivity;
import com.gzcjteam.shundai.R;
import com.gzcjteam.shundai.R.layout;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NextFrament extends Fragment implements OnClickListener{
	private EditText  tv_Phone;
	private EditText  tv_Yanzhengma;	
	private Button  btn_findyan;
	protected static final int USERISCUNZAI = 0;
	protected static final int REGIESTERFAILE = 1;
	protected static final int SERVERERROR = 2;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.next_frament, container,false);
//		tv_Phone=(EditText) view.findViewById(R.id.sign_phone);
//		tv_Yanzhengma=(EditText) view.findViewById(R.id.sign_yanzhengma);
//		btn_findyan=(Button) view.findViewById(R.id.btn_findyan);
		return view;
	}

	@Override
	public void onClick(View v) {
		if (v==btn_findyan) {
			//执行获取验证码
			FindYanZhengMa();	
		}
	}

	/**
	 * 获取验证码
	 */
	private void FindYanZhengMa() {
		
	}

}
