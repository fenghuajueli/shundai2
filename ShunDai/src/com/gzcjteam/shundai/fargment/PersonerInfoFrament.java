package com.gzcjteam.shundai.fargment;

import com.gzcjteam.shundai.PersonalCenterActivity;
import com.gzcjteam.shundai.R;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class PersonerInfoFrament extends Fragment implements OnClickListener {

	public final static String ARG_KEY = "ARG";

	public final static int ARG_TYPE_FIRST = 0;
	public final static int ARG_TYPE_LOGINED = 1;
	public int currentFlag = 0;
	private Button btnSignUp;
	private Button btnSignIn;

	private ImageView imgPersonInfoNext; // 个人信息下一步按钮
	private ImageView imgPersonRenZhenXinXiNext;// 认证信息下一步按钮
	private ImageView imgPersonShenSuNext; // 申诉下一步按钮
	private ImageView imgPersonAboutNext;// 关于顺带下一步按钮

	private Handler handler = new Handler();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tabperson_frament, container,
				false);

		initView(view);

		return view;
	}

	// 初始化界面
	private void initView(View view) {
		imgPersonInfoNext = (ImageView) view
				.findViewById(R.id.imgPersonInfoNext);
		imgPersonRenZhenXinXiNext = (ImageView) view
				.findViewById(R.id.imgPersonRenZhenXinXiNext);
		imgPersonShenSuNext = (ImageView) view
				.findViewById(R.id.imgPersonShenSuNext);
		imgPersonAboutNext = (ImageView) view
				.findViewById(R.id.imgPersonAboutNext);

		imgPersonInfoNext.setOnClickListener(this);
		imgPersonRenZhenXinXiNext.setOnClickListener(this);
		imgPersonShenSuNext.setOnClickListener(this);
		imgPersonAboutNext.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgPersonInfoNext:
			Intent intent1 = new Intent();
			intent1.putExtra("PageName", "PersonalInfo");
			intent1.setClass(getActivity(), PersonalCenterActivity.class);
			startActivity(intent1);
			break;
		case R.id.imgPersonRenZhenXinXiNext:
			Intent intent2 = new Intent();
			intent2.putExtra("PageName", "MyRenZhenInfo");
			intent2.setClass(getActivity(), PersonalCenterActivity.class);
			startActivity(intent2);
			break;
		case R.id.imgPersonShenSuNext:
			Intent intent3 = new Intent();
			intent3.putExtra("PageName", "MyTastShenSu");
			intent3.setClass(getActivity(), PersonalCenterActivity.class);
			startActivity(intent3);
			break;
		case R.id.imgPersonAboutNext:
			Intent intent4 = new Intent();
			intent4.putExtra("PageName", "MyAboutShunDai");
			intent4.setClass(getActivity(), PersonalCenterActivity.class);
			startActivity(intent4);
			break;

		default:
			break;
		}
	}
}
