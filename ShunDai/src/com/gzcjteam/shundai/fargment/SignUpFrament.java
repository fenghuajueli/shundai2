package com.gzcjteam.shundai.fargment;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import com.gzcjteam.shundai.utils.CheckYanZhengMa;
import com.gzcjteam.shundai.utils.MyTimerTask;
import com.gzcjteam.shundai.utils.RequestUtils;
import com.gzcjteam.shundai.utils.ToastUtil;
import com.gzcjteam.shundai.utils.getYanZhengMa;
import com.gzcjteam.shundai.LoginActivity;
import com.gzcjteam.shundai.MainActivity;
import com.gzcjteam.shundai.R;
import com.gzcjteam.shundai.R.layout;
import com.gzcjteam.shundai.bean.NetCallBack;
import com.loopj.android.http.RequestParams;

import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.AvoidXfermode.Mode;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpFrament extends Fragment implements OnClickListener {
	private EditText tv_Phone;
	private EditText tv_Yanzhengma;
	private Button btn_findyan;
	private Button btn_next;
	protected static final int USERISCUNZAI = 0;
	protected static final int REGIESTERFAILE = 1;
	protected static final int SERVERERROR = 2;
	protected static final int DAOJISAHI = 0;
	protected static final int DAOJISAHI1 = 1;
	private Boolean regiester = true;
	private Timer time;
	private TimerTask timer;
	private SharedPreferences shp;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DAOJISAHI:
				btn_findyan.setText("获取验证码");
				btn_findyan.setEnabled(true);
				btn_findyan.setBackgroundResource(R.drawable.btn_normal);
				break;
			case DAOJISAHI1:
				btn_findyan.setText(MyTimerTask.timeover + "s");
				btn_findyan
						.setBackgroundResource(R.drawable.g_green_btn_normal);
				break;
			default:
				break;
			}

		}

	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.signup_frament, container, false);
		tv_Phone = (EditText) view.findViewById(R.id.sign_phone);
		tv_Yanzhengma = (EditText) view.findViewById(R.id.sign_yanzhengma);
		btn_findyan = (Button) view.findViewById(R.id.btn_findyan);
		btn_findyan.setOnClickListener(this);
		btn_next = (Button) getActivity().findViewById(R.id.bar_action);
		btn_next.setOnClickListener(this);
		return view;
	}

	@Override
	public void onClick(View v) {
		if (v == btn_findyan) {
			// 执行获取验证码
			if (tv_Phone.getText().toString().isEmpty()) {
				ToastUtil.show(getActivity(), "手机号不能为空！");
			} else {
				String url = "http://119.29.140.85/index.php/user/check_phone";
				RequestParams params = new RequestParams();
				params.put("phone", tv_Phone.getText().toString());
				RequestUtils.ClientPost(url, params, new NetCallBack() {

					@Override
					public void onMySuccess(String result) {
						try {
							JSONObject json = new JSONObject(result);
							Boolean status = json.getBoolean("status");
							String info = json.getString("info");
							JSONObject data;
							if (status) {	
								btn_findyan.setText(60 + "s");
								btn_findyan.setEnabled(false);
								Timer time = new Timer();
								time.schedule(new MyTimerTask(60, handler, time), 0, 1000);
								new getYanZhengMa().FindYanZhengMa(tv_Phone.getText()
										.toString(), getActivity());
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
		if (v == btn_next) {
			if (tv_Phone.getText().toString().isEmpty()) {
				ToastUtil.show(getActivity(), "手机号不能为空！");
				return;
			}
			if (tv_Yanzhengma.getText().toString().isEmpty()) {
				ToastUtil.show(getActivity(), "验证码不能为空！");
				return;
			}
			String url = "http://119.29.140.85/index.php/user/valite_msg_code";
			RequestParams params = new RequestParams();
			params.put("phone", tv_Phone.getText().toString());
			params.put("code", tv_Yanzhengma.getText().toString());
			RequestUtils.ClientPost(url, params, new NetCallBack() {

				@Override
				public void onMySuccess(String result) {
					try {
						JSONObject json = new JSONObject(result);
						Boolean status = json.getBoolean("status");
						String info = json.getString("info");
						if (status) {
							ToastUtil.show(getActivity(), info);
							shp = getActivity().getSharedPreferences("PHONEANDYAN",
									getActivity().MODE_PRIVATE);
							Editor editor = shp.edit();
							editor.putString("phone", tv_Phone.getText().toString());
							editor.commit();
							((LoginActivity) getActivity()).go2Next();
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

}
