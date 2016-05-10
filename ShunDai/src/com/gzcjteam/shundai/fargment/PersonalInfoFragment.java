package com.gzcjteam.shundai.fargment;

import com.gzcjteam.shundai.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PersonalInfoFragment extends Fragment {
	
	private static String backstatcTag="PERSONALINFO";
	private static String personalCenterTag;
	private Context context;
	
	public PersonalInfoFragment(Context context,String tag) {
		super();
		this.context=context;
		this.personalCenterTag=tag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.personal_info_fragment, container,false);
		
		return view;
	}

}
