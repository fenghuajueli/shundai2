package com.gzcjteam.shundai.utils;

import com.gzcjteam.shundai.bean.NetCallBack;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

public class RequestUtils {
	public static AsyncHttpClient client = new AsyncHttpClient();
	

	public static void ClinetGet(String url, NetCallBack cb) {
		client.get(url, cb);
	}

	public static void ClientPost(String url, RequestParams params,
			NetCallBack cb) {
		client.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		client.post(url, params, cb);
	}
}

/*网络请求调用案列如下*/
/*
  private void asynchttpPost() {
		String url = "http://apis.juhe.cn/mobile/get?";
		RequestParams params = new RequestParams();
		params.put("phone", "13666666666");
		params.put("key", "335adcc4e891ba4e4be6d7534fd54c5d");
		RequestUtils.ClientPost(url, params, new NetCallBack() {

			@Override
			public void onMySuccess(String result) {
				Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG)
						.show();
			}

			@Override
			public void onMyFailure(Throwable arg0) {
				Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_LONG)
						.show();
			}
		});
	}

	private void asynchttpGet() {
		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://apis.juhe.cn/mobile/get?phone=13666666666&key=335adcc4e891ba4e4be6d7534fd54c5d";
		client.get(url, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0);
				Toast.makeText(MainActivity.this, arg0, Toast.LENGTH_LONG)
						.show();
			}

			@Override
			public void onFailure(Throwable arg0) {
				Toast.makeText(MainActivity.this, "网络请求失败", Toast.LENGTH_LONG)
						.show();
				super.onFailure(arg0);
			}
		});
	}
 */
 