package com.gzcjteam.shundai.fargment;

import com.gzcjteam.shundai.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyRenZhenInfoFragment extends Fragment {
	private static String pageName = null;
	private Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.my_renzheninfo_fargment,
				container, false);
		initView();
		return view;
	}

	private void initView() {

	}

}
