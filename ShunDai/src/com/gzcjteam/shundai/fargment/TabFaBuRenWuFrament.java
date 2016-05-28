package com.gzcjteam.shundai.fargment;

import java.util.ArrayList;
import java.util.List;

import com.gzcjteam.shundai.MainActivity;
import com.gzcjteam.shundai.LoginActivity;
import com.gzcjteam.shundai.R;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.R.anim;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class TabFaBuRenWuFrament extends Fragment implements OnClickListener {

	public final static String ARG_KEY = "ARG";
	public final static int ARG_TYPE_FIRST = 0;
	public final static int ARG_TYPE_LOGINED = 1;
	public int currentFlag = 0;
	private Button btnSignUp;
	private Button btnSignIn;
	private Handler handler = new Handler();
	private EditText edit_lianxirenshouji;// 联系人手机
	private EditText edit_shouhuo_phone;// 收货人手机
	private EditText edit_lianxi_xinming;// 收货人姓名
	private Spinner spn_shouhuo_school;// 取货学校
	private Spinner spn_kuaidigongsi;// 快递公司
	private EditText edit_shouhuo_dizhi;// 收货地址
	private EditText edit_renwu_choulao;// 任务酬劳
	private EditText edit_kuaidi_duanxing;// 快递酬劳
	private Button btn_fabu_sava;
	private List schoolList;
	private List kuaiDiList;
	private ArrayAdapter<String> schoolAdapter;
	private ArrayAdapter<String> kuaidiAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.tabfabu_frament, container, false);
		initView(view);
		initData();
		initEvent();
		return view;
	}

	private void initData() {
		schoolList = new ArrayList<>();
		schoolList.add("贵州财经大学");
		schoolList.add("贵州师范大学");
		schoolList.add("贵州医科大学");

		kuaiDiList = new ArrayList<>();
		kuaiDiList.add("申通快递");
		kuaiDiList.add("顺丰快递");
		kuaiDiList.add("中通快递");
		kuaiDiList.add("圆通快递");
		kuaiDiList.add("京东快递");
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
		spn_kuaidigongsi.setAdapter(schoolAdapter);
		spn_kuaidigongsi.setSelection(0);

		// adapter = new ArrayAdapter<>(getActivity(),
		// android.R.layout.simple_spinner_item, list);
		// adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// my_commen_spinner.setAdapter(adapter);
		// my_commen_spinner.setSelection(0);

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

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.edit_lianxirenshouji:

			break;

		default:
			break;
		}
	}

}
