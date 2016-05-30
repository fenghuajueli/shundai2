package com.gzcjteam.shundai.utils;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.gzcjteam.shundai.bean.NetCallBack;
import com.loopj.android.http.RequestParams;

public class CheckYanZhengMa {
	private Boolean YZMISTRUE = false;

	public Boolean CheckYZM(String phone, String code, final Context context) {	
			String url = "http://119.29.140.85/index.php/user/valite_msg_code";
			System.out.println(code);
			RequestParams params = new RequestParams();
			params.put("phone", phone);
			params.put("code", code);
				RequestUtils.ClientPost(url, params, new NetCallBack() {

					@Override
					public void onMySuccess(String result) {
						try {
							JSONObject json = new JSONObject(result);
							Boolean status = json.getBoolean("status");
							String info = json.getString("info");
							System.out.println("状态" + status);
							if (status) {
								YZMISTRUE = true;
								System.out.println("验证陈宫后：" + YZMISTRUE);
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
				System.out.println("真确" + YZMISTRUE);
				return YZMISTRUE;		
	}		
	
}
