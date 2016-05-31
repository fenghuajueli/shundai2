package com.gzcjteam.shundai.weight;

import org.json.JSONException;
import org.json.JSONObject;

import com.gzcjteam.shundai.R;
import com.gzcjteam.shundai.RenWuInfoActivity;
import com.gzcjteam.shundai.bean.NetCallBack;
import com.gzcjteam.shundai.fargment.TabAllRenWuFrament;
import com.gzcjteam.shundai.utils.OnSureClickListener;
import com.gzcjteam.shundai.utils.RequestUtils;
import com.gzcjteam.shundai.utils.ToastUtil;
import com.gzcjteam.shundai.utils.getUserInfo;
import com.loopj.android.http.RequestParams;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.sax.StartElementListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class TiShiDialog extends Dialog implements OnClickListener {

	private TextView tv_kuaidiGongSi;
	private TextView tv_songHuoAddress;
	private Button btn_queding;
	private Button btn_cancel;
	private String kuadiGongSi;
	private String songHuoAddress;
	private String id;
	private Boolean isSuccess = false;
	private Context context;
	private OnSureClickListener mListener;
	PullUpDialog dia;

	public TiShiDialog(Context context, int theme, String kuaidigongsi,
			String address, String task_id, OnSureClickListener listener) {
		super(context, theme);
		this.context = context;
		this.id = task_id;
		this.kuadiGongSi = kuaidigongsi;
		this.songHuoAddress = address;
		mListener = listener;
		Window window = getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		window.setAttributes(lp);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tishi_dialog);
		tv_kuaidiGongSi = (TextView) findViewById(R.id.kuaidicompany);
		tv_kuaidiGongSi.setText(kuadiGongSi);
		tv_songHuoAddress = (TextView) findViewById(R.id.songhuoaddress);
		tv_songHuoAddress.setText(songHuoAddress);
		btn_queding = (Button) findViewById(R.id.queding);
		btn_cancel = (Button) findViewById(R.id.cancel);
		btn_queding.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == btn_queding) {
			dia = new PullUpDialog(context, R.style.adialog, "领取中。。");
			this.dismiss();
			dia.show();
			String url = "http://119.29.140.85/index.php/task/start_task";
			RequestParams params = new RequestParams();
			params.put("task_id", id);
			params.put("complete_user_id", getUserInfo.getInstance().getId());
			System.out.println("task_id:" + id);
			System.out.println("complete_user_id:"
					+ getUserInfo.getInstance().getId());
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
							mListener.tiaoZhuanInfo("");
							dia.dismiss();
						} else {
							dia.dismiss();
							ToastUtil.show(context, info);
						}
					} catch (JSONException e) {
						dia.dismiss();
						e.printStackTrace();
					}
				}

				@Override
				public void onMyFailure(Throwable arg0) {
					dia.dismiss();
					ToastUtil.show(context, "领取任务失败！");
				}
			});
			// this.dismiss();
		} else if (v == btn_cancel) {
			this.dismiss();
		}
	}
}
