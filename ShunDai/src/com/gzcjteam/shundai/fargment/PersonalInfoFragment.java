package com.gzcjteam.shundai.fargment;

import com.gzcjteam.shundai.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class PersonalInfoFragment extends Fragment implements OnClickListener {

	private static String backstatcTag = "PERSONALINFO";
	private static String personalCenterTag;
	private Context context;
	private ImageView img_PersonalInfo_back;

	public PersonalInfoFragment(Context context, String tag) {
		super();
		this.context = context;
		this.personalCenterTag = tag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.personal_info_fragment,
				container, false);

		initView(view);

		return view;
	}

	private void initView(View view) {
		img_PersonalInfo_back = (ImageView) view
				.findViewById(R.id.img_PersonalInfo_back);

		img_PersonalInfo_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_PersonalInfo_back:
			getActivity().onBackPressed();//绑定系统的back按钮
			break;

		default:
			break;
		}
	}

}
