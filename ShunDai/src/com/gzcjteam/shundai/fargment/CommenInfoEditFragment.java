package com.gzcjteam.shundai.fargment;

import java.util.ArrayList;
import java.util.List;

import com.gzcjteam.shundai.R;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CommenInfoEditFragment extends Fragment implements OnClickListener {
	private String tabName;
	private String hintContent;
	private static Context context;
	private TextView my_commen_edit_tab;// tab文字
	private TextView my_commen_edittext;// hint
	private Button my_commen_exit_save;// 保存
	private ImageView img_commenpageback;// 返回上一步按钮
	private Spinner my_commen_spinner;

	private List<String> list = new ArrayList<>();
	private ArrayAdapter<String> adapter;

	private static String PAGE_TAG = null;

	public CommenInfoEditFragment(String tabName, String hintContent) {
		super();
		this.tabName = tabName;
		this.hintContent = hintContent;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.my_personal_commen_fragment,
				container, false);
		PAGE_TAG = getArguments().getString("param");
		Toast.makeText(getActivity(), PAGE_TAG, 1).show();

		initView(view);
		return view;
	}

	private void initData() {
		if (PAGE_TAG.equals("SCHOOL")) {
			list.add("贵州财经大学");
			list.add("贵州师范法学");
			list.add("贵州医科大学");
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
			break;
		default:
			break;
		}
	}

	// 发送网络请求
	private void saveDataRequest(String AGE_TAG_LOG) {
		if (AGE_TAG_LOG != null && !AGE_TAG_LOG.equals("")) {
			if (AGE_TAG_LOG.equals("NAME")) {

			} else if (AGE_TAG_LOG.equals("PHONE")) {

			}
		} else {
			Toast.makeText(getActivity(), "页面跳转错误 请检查源代码！", 1).show();
		}

	}

}
