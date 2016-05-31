package com.gzcjteam.shundai.fargment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.gzcjteam.shundai.MainActivity;
import com.gzcjteam.shundai.LoginActivity;
import com.gzcjteam.shundai.R;
import com.gzcjteam.shundai.bean.NetCallBack;
import com.gzcjteam.shundai.utils.RequestUtils;
import com.gzcjteam.shundai.utils.getUserInfo;
import com.gzcjteam.shundai.weight.LoadingDialog;
import com.loopj.android.http.RequestParams;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.R.anim;
import android.R.integer;
import android.content.Intent;
import android.content.Loader;
import android.net.wifi.WifiConfiguration.Status;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

public class TabFaBuRenWuFrament extends Fragment implements OnClickListener,
		OnTabChangeListener, OnTouchListener {

	public final static String ARG_KEY = "ARG";
	public final static int ARG_TYPE_FIRST = 0;
	public final static int ARG_TYPE_LOGINED = 1;
	public int currentFlag = 0;
	private Button btnSignUp;
	private Button btnSignIn;
	private EditText edit_lianxirenshouji;// 联系人手机
	private EditText edit_shouhuo_phone;// 收货人手机
	private EditText edit_lianxi_xinming;// 收货人姓名
	private Spinner spn_shouhuo_school;// 取货学校
	private Spinner spn_kuaidigongsi;// 快递公司
	private EditText edit_shouhuo_dizhi;// 收货地址
	private EditText edit_renwu_choulao;// 任务酬劳
	private EditText edit_kuaidi_duanxing;// 快递酬劳
	private Button btn_fabu_sava;// 发布
	private List schoolList;
	private List kuaiDiList;
	private ArrayAdapter<String> schoolAdapter;
	private ArrayAdapter<String> kuaidiAdapter;

	private final static String fabuUrl = "http://119.29.140.85/index.php/task/new_task";
	private String id;
	private LoadingDialog dialog;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				dialog.show();
				break;
			case 2:
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						Toast.makeText(getActivity(), "任务发布成功",
								Toast.LENGTH_SHORT).show();
						dialog.dismiss();
					}
				}, 1000);
				break;
			default:
				break;
			}
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.tabfabu_frament, container, false);
		dialog = new LoadingDialog(getActivity(), "正在发布");
		initView(view);
		initData();
		initEvent();
		return view;
	}

	private void initData() {

		schoolList = new ArrayList<>();
		schoolList.add("贵州财经大学(花溪校区)");// 1
		schoolList.add("贵州医科大学(花溪校区)");// 2
		schoolList.add("贵州师范大学(花溪校区)");// 3
		schoolList.add("贵州城市学院(花溪校区)");// 4
		schoolList.add("贵州轻工职业技术学院");// 5
		schoolList.add("贵州民族大学花溪校区(花溪校区)");// 6

		kuaiDiList = new ArrayList<>();
		kuaiDiList.add("韵达快递");
		kuaiDiList.add("申通快递");
		kuaiDiList.add("顺丰快递");
		kuaiDiList.add("EMS");
		kuaiDiList.add("圆通快递");
		kuaiDiList.add("中通快递");
		kuaiDiList.add("百事汇通");
		kuaiDiList.add("京东");
		kuaiDiList.add("韵达快递");

		schoolAdapter = new ArrayAdapter<>(getActivity(),
				android.R.layout.simple_spinner_item, schoolList);
		schoolAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spn_shouhuo_school.setAdapter(schoolAdapter);
		spn_shouhuo_school.setSelection(0);

		kuaidiAdapter = new ArrayAdapter<>(getActivity(),
				android.R.layout.simple_spinner_item, kuaiDiList);
		kuaidiAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spn_kuaidigongsi.setAdapter(kuaidiAdapter);
		spn_kuaidigongsi.setSelection(0);

	}

	private void initEvent() {
		edit_lianxirenshouji.setOnClickListener(this);
		edit_shouhuo_phone.setOnClickListener(this);
		edit_lianxi_xinming.setOnClickListener(this);
		edit_shouhuo_dizhi.setOnClickListener(this);
		edit_renwu_choulao.setOnClickListener(this);
		edit_kuaidi_duanxing.setOnClickListener(this);
		btn_fabu_sava.setOnClickListener(this);
	}

	private void initView(View view) {
		edit_lianxirenshouji = (EditText) view
				.findViewById(R.id.edit_lianxirenshouji);
		edit_shouhuo_phone = (EditText) view
				.findViewById(R.id.edit_shouhuo_phone);
		edit_lianxi_xinming = (EditText) view
				.findViewById(R.id.edit_lianxi_xinming);
		spn_shouhuo_school = (Spinner) view
				.findViewById(R.id.spn_shouhuo_school);
		spn_kuaidigongsi = (Spinner) view.findViewById(R.id.spn_kuaidigongsi);
		edit_shouhuo_dizhi = (EditText) view
				.findViewById(R.id.edit_shouhuo_dizhi);
		edit_renwu_choulao = (EditText) view
				.findViewById(R.id.edit_renwu_choulao);
		edit_kuaidi_duanxing = (EditText) view
				.findViewById(R.id.edit_kuaidi_duanxing);
		btn_fabu_sava = (Button) view.findViewById(R.id.btn_fabu_sava);

		btn_fabu_sava.setOnTouchListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_fabu_sava:
			id = getUserInfo.getInstance().getId();
			String phone = edit_lianxirenshouji.getText().toString();
			String kuaiDiGongSi = (String) spn_kuaidigongsi.getSelectedItem();
			String diZhi = edit_shouhuo_dizhi.getText().toString();
			String duanXin = edit_kuaidi_duanxing.getText().toString();
			String Money = edit_renwu_choulao.getText().toString();
			String schoolString = (String) spn_shouhuo_school.getSelectedItem();

			int schoolCode = 0;
			int kuaiDiCode = 0;
			if (schoolString.equals("贵州财经大学(花溪校区)")) {
				schoolCode = 1;
			} else if (schoolString.equals("贵州医科大学(花溪校区)")) {
				schoolCode = 2;
			} else if (schoolString.equals("贵州师范大学(花溪校区)")) {
				schoolCode = 3;
			} else if (schoolString.equals("贵州城市学院(花溪校区)")) {
				schoolCode = 4;
			} else if (schoolString.equals("贵州轻工职业技术学院")) {
				schoolCode = 5;
			} else if (schoolString.equals("贵州民族大学(花溪校区)")) {
				schoolCode = 6;
			}

			if (kuaiDiGongSi.equals("韵达快递")) {
				kuaiDiCode = 1;
			} else if (kuaiDiGongSi.equals("申通快递")) {
				kuaiDiCode = 2;
			} else if (kuaiDiGongSi.equals("顺丰快递")) {
				kuaiDiCode = 3;
			} else if (kuaiDiGongSi.equals("EMS")) {
				kuaiDiCode = 4;
			} else if (kuaiDiGongSi.equals("圆通快递")) {
				kuaiDiCode = 5;
			} else if (kuaiDiGongSi.equals("中通快递")) {
				kuaiDiCode = 6;
			} else if (kuaiDiGongSi.equals("百事汇通")) {
				kuaiDiCode = 7;
			} else if (kuaiDiGongSi.equals("京东")) {
				kuaiDiCode = 8;
			} else if (kuaiDiGongSi.equals("韵达快递")) {
				kuaiDiCode = 9;
			}

			RequestParams params = new RequestParams();
			params.put("launch_user_id", id);
			params.put("receive_express_phone", phone);
			params.put("express_code", kuaiDiCode + "");
			params.put("receive_address", diZhi);
			params.put("express_info", duanXin);
			params.put("expect_money", Money);
			params.put("school_code", schoolCode + "");

			Message msg1 = new Message();
			msg1.what = 1;
			handler.sendMessage(msg1);
			RequestUtils.ClientPost(fabuUrl, params, new NetCallBack() {

				@Override
				public void onMySuccess(String result) {
					Log.e("test", result);
					JSONObject json;
					try {
						json = new JSONObject(result);
						boolean status = json.getBoolean("status");
						Toast.makeText(getActivity(), "" + status, 1).show();
						if (status) {
							Message msg2 = new Message();
							msg2.what = 2;
							handler.sendMessage(msg2);
						} else {
							Message msg2 = new Message();
							msg2.what = 2;
							handler.sendMessage(msg2);
							Toast.makeText(getActivity(), "任务发布失败 请输入正确的手机号码！",
									Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e1) {
						e1.printStackTrace();
					}

				}

				@Override
				public void onMyFailure(Throwable arg0) {
					Toast.makeText(getActivity(), "任务发布失败 网络错误！",
							Toast.LENGTH_SHORT).show();
				}
			});

			break;

		default:
			break;
		}
	}

	@Override
	public void onTabChanged(String tabId) {

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		
		return false;
	}

}
