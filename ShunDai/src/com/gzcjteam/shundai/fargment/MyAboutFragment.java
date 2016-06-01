package com.gzcjteam.shundai.fargment;

import com.gzcjteam.shundai.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyAboutFragment extends Fragment implements OnClickListener {
	private ImageView img_PersonalABoutSD_back;
	private TextView txtBack;
	private RelativeLayout update;
	private RelativeLayout clear;
	private RelativeLayout conlection;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.my_aboutshundai_fragment,
				container, false);
		initView(view);

		return view;
	}

	private void initView(View view) {
		img_PersonalABoutSD_back = (ImageView) view
				.findViewById(R.id.img_PersonalABoutSD_back);
		txtBack = (TextView) view.findViewById(R.id.txt_about_back);
		update = (RelativeLayout) view.findViewById(R.id.rtv_about_checkupdata);
		clear = (RelativeLayout) view.findViewById(R.id.rtv_about_cleckflash);
		conlection = (RelativeLayout) view.findViewById(R.id.rtv_about_fankui);

		img_PersonalABoutSD_back.setOnClickListener(this);
		txtBack.setOnClickListener(this);
		update.setOnClickListener(this);
		clear.setOnClickListener(this);
		conlection.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_PersonalABoutSD_back:
			getActivity().onBackPressed();// 绑定系统返回按钮
			break;
		case R.id.txt_about_back:
			getActivity().onBackPressed();// 绑定系统返回按钮
			break;
		case R.id.rtv_about_checkupdata:
			Toast.makeText(getActivity(), "已是最新版本", Toast.LENGTH_LONG).show();
			break;
		case R.id.rtv_about_cleckflash:
			Toast.makeText(getActivity(), "正在清理数据", Toast.LENGTH_LONG).show();
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					Toast.makeText(getActivity(), "清理数据完毕", Toast.LENGTH_LONG)
							.show();
				}
			}, 2000);
			break;
		case R.id.rtv_about_fankui:
			  Intent intent = new Intent();        
		        intent.setAction("android.intent.action.VIEW");    
		        Uri content_url = Uri.parse("http://119.29.140.85/");   
		        intent.setData(content_url);  
		        startActivity(intent);
			break;
		default:
			break;
		}
	}
}
