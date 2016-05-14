package com.gzcjteam.shundai.fargment;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.gzcjteam.shundai.utils.RequestUtils;
import com.gzcjteam.shundai.utils.ToastUtil;
import com.gzcjteam.shundai.LoginActivity;
import com.gzcjteam.shundai.MainActivity;
import com.gzcjteam.shundai.R;
import com.gzcjteam.shundai.R.layout;
import com.gzcjteam.shundai.bean.NetCallBack;
import com.loopj.android.http.RequestParams;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class PwdNextFrament extends Fragment implements OnClickListener{
	private EditText  tv_pwd;
	private EditText  tv_cpwd;	
	private Button  btn_change;
	protected static final int USERISCUNZAI = 0;
	protected static final int REGIESTERFAILE = 1;
	protected static final int SERVERERROR = 2;
	private SharedPreferences shp;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.pwd_next_frament, container,false);
		tv_pwd=(EditText) view.findViewById(R.id.password);
		tv_cpwd=(EditText) view.findViewById(R.id.confium_password);
		btn_change=(Button) view.findViewById(R.id.btn_changepwd);
		btn_change.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		if (v==btn_change) {
			//修改密码
			ChangePWD();	
		}
	}

	/**
	 * 确认修改密码
	 */
	private void ChangePWD() {
		shp=getActivity().getSharedPreferences("PHONEANDYAN", getActivity().MODE_PRIVATE);
		String  phone=shp.getString("phone", "");
		String pwd=tv_pwd.getText().toString();//密码
		String cpwd=tv_cpwd.getText().toString();//再次确认的密码
		if (pwd.isEmpty()) {
			ToastUtil.show(getActivity(), "密码不能为空");
			return;
		}
		if (cpwd.isEmpty()) {
			ToastUtil.show(getActivity(), "密码不能为空");
			return;
		}		
		if (!pwd.equals(cpwd)) {
			ToastUtil.show(getActivity(), "两次输入的密码不一致！");
			return;
		}
		String url = "http://119.29.140.85/index.php/user/reset_password";
		RequestParams params = new RequestParams();
		params.put("phone",phone);
		params.put("password", pwd);		
		RequestUtils.ClientPost(url, params, new NetCallBack() {

			@Override
			public void onMySuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					Boolean status = json.getBoolean("status");
					String info = json.getString("info");
					if (status) {	
						//修改成功后跳转到登录界面						
						ToastUtil.show(getActivity(), info);
						startActivity(new Intent(getActivity(), LoginActivity.class));
						getActivity().finish();					
					} else {
						ToastUtil.show(getActivity(), info);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void onMyFailure(Throwable arg0) {
				ToastUtil.show(getActivity(), "服务器错误！");
			}
		});		
		
	}

}
