package com.gzcjteam.shundai.fargment;

import com.gzcjteam.shundai.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MyTastShenSuFragment extends Fragment implements OnClickListener {
	private ImageView img_PersonalShenSu_back;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.my_tastshensu_fragment,
				container, false);
		initView(view);
		
		return view;
	}

	private void initView(View view) {
		img_PersonalShenSu_back=(ImageView) view.findViewById(R.id.img_PersonalShenSu_back);
		
		img_PersonalShenSu_back.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_PersonalShenSu_back:
			getActivity().onBackPressed(); //绑定系统返回按钮
			break;

		default:
			break;
		}
	}
}
