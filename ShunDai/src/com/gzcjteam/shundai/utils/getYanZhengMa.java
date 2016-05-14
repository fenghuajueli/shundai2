package com.gzcjteam.shundai.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.gzcjteam.shundai.bean.NetCallBack;
import com.loopj.android.http.RequestParams;

import android.app.Fragment;
import android.content.Context;

public class getYanZhengMa extends Fragment {
	/**
	 * 获取验证码
	 */
	public void FindYanZhengMa(String  phone,final Context  context) {
		String url = "http://119.29.140.85/index.php/user/request_msg";
		RequestParams params = new RequestParams();
		params.put("phone", phone);
		RequestUtils.ClientPost(url, params, new NetCallBack() {

			@Override
			public void onMySuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					Boolean status = json.getBoolean("status");
					String info = json.getString("info");
					JSONObject data;
					if (status) {						
						ToastUtil.show(context, info);
					} else {
						ToastUtil.show(context, info);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			@Override
			public void onMyFailure(Throwable arg0) {
				ToastUtil.show(context, "服务器错误！");
			}
		});
	}	
}
