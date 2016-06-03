package com.gzcjteam.shundai.fargment;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.gzcjteam.shundai.R;
import com.gzcjteam.shundai.utils.DataCallBack;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CommenInfoEditFragment extends Fragment implements
		OnClickListener, OnTouchListener {
	private String tabName;
	private String hintContent;
	private static Context context;
	private TextView my_commen_edit_tab;// tab文字
	private TextView my_commen_edittext;// 输入框
	private Button my_commen_exit_save;// 保存
	private ImageView img_commenpageback;// 返回上一步按钮
	private Spinner my_commen_spinner;// 选择框
	private List<String> list = new ArrayList<>();
	private ArrayAdapter<String> adapter;
	private static String PAGE_TAG = null;

	private String returnText;
	private String returnTag;

	private DataCallBack dataCallBack;

	public CommenInfoEditFragment(String tabName, String hintContent,
			DataCallBack dataCallBack) {
		super();
		this.tabName = tabName;
		this.hintContent = hintContent;
		this.dataCallBack = dataCallBack;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.my_personal_commen_fragment,
				container, false);
		PAGE_TAG = getArguments().getString("param");

		initView(view);
		view.setOnTouchListener(this);
		return view;
	}

	private void initData() {
		if (PAGE_TAG.equals("SCHOOL")) {
			list.add("贵州财经大学(花溪校区)");
			list.add("贵州医科大学(花溪校区)");
			list.add("贵州师范大学(花溪校区)");
			list.add("贵州城市学院(花溪校区)");
			list.add("贵州轻工职业技术学院");
			list.add("贵州民族大学(花溪校区)");

		} else if (PAGE_TAG.equals("SEX")) {
			list.add("男");
			list.add("女");
		} else {

		}
	}

	private void initView(View view) {
		my_commen_edit_tab = (TextView) view
				.findViewById(R.id.my_commen_edit_tab);
		my_commen_exit_save = (Button) view
				.findViewById(R.id.my_commen_exit_save);
		img_commenpageback = (ImageView) view
				.findViewById(R.id.img_commenpageback);

		my_commen_edit_tab.setText(tabName);

		my_commen_spinner = (Spinner) view.findViewById(R.id.my_commen_spinner);
		my_commen_edittext = (TextView) view
				.findViewById(R.id.my_commen_edittext);

		if (PAGE_TAG.equals("SEX") || PAGE_TAG.equals("SCHOOL")) {
			initData();
			my_commen_edittext.setVisibility(View.GONE);
			my_commen_spinner.setVisibility(View.VISIBLE);
			adapter = new ArrayAdapter<>(getActivity(),
					android.R.layout.simple_spinner_item, list);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			my_commen_spinner.setAdapter(adapter);
			my_commen_spinner.setSelection(0);

		} else {
			my_commen_edittext.setVisibility(View.VISIBLE);
			my_commen_spinner.setVisibility(View.GONE);
			my_commen_edittext.setHint(hintContent);
		}

		img_commenpageback.setOnClickListener(this);
		my_commen_exit_save.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_commenpageback:
			getActivity().onBackPressed();
			break;
		case R.id.my_commen_exit_save:
			saveDataRequest(PAGE_TAG);
			getActivity().onBackPressed();
			dataCallBack.retrunEditData(returnTag, returnText);
			break;
		default:
			break;
		}
	}

	// 发送网络请求
	private void saveDataRequest(String AGE_TAG_LOG) {
		if (AGE_TAG_LOG != null && !AGE_TAG_LOG.equals("")) {
			if (AGE_TAG_LOG.equals("SCHOOL") || AGE_TAG_LOG.equals("SEX")) {
				returnText = (String) my_commen_spinner.getSelectedItem();
				returnTag = AGE_TAG_LOG;
			} else {
				returnText = my_commen_edittext.getText().toString();
				returnTag = AGE_TAG_LOG;
			}
		} else {
			Toast.makeText(getActivity(), "页面跳转码错误 请检查源代码！", 1).show();
		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return true;
	}

}
