package com.gzcjteam.shundai.utils;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.gzcjteam.shundai.bean.NetCallBack;
import com.loopj.android.http.RequestParams;

public class CheckYanZhengMa {
	private Boolean  YZMISTRUE=true;
	
	public Boolean CheckYZM(String phone,String code,final Context context){
		String url = "http://119.29.140.85/index.php/user/check_phone";
		RequestParams params = new RequestParams();
		params.put("phone",phone);
		params.put("code",code);
		RequestUtils.ClientPost(url, params, new NetCallBack() {

			@Override
			public void onMySuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					Boolean status = json.getBoolean("status");
					String info = json.getString("info");
					if (status) {
						ToastUtil.show(context, "验证码正确");
						YZMISTRUE = true;
					} else {
						ToastUtil.show(context, "验证码错误");
						YZMISTRUE = false;
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
		return YZMISTRUE;		
	}
}
