package com.gzcjteam.shundai.fargment;

import com.gzcjteam.shundai.LoginActivity;
import com.gzcjteam.shundai.PersonalCenterActivity;
import com.gzcjteam.shundai.R;
import com.gzcjteam.shundai.utils.getUserInfo;

import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
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

	private Button btn_exit;// 退出
	private ImageView imgPersonHead;// 头像
	private String headPicUrl;

	private Handler handler = new Handler();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tabperson_frament, container,
				false);
		initData();
		initView(view);

		return view;
	}

	private void initData() {
		headPicUrl = "http://119.29.140.85"
				+ getUserInfo.getInstance().getHead_pic_url();

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
		imgPersonHead = (ImageView) view.findViewById(R.id.imgPersonHead);
		btn_exit = (Button) view.findViewById(R.id.btn_exit);

		imgPersonInfoNext.setOnClickListener(this);
		imgPersonRenZhenXinXiNext.setOnClickListener(this);
		imgPersonShenSuNext.setOnClickListener(this);
		imgPersonAboutNext.setOnClickListener(this);
		imgPersonHead.setOnClickListener(this);
		btn_exit.setOnClickListener(this);

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
		case R.id.btn_exit:
			AlertDialog.Builder builder = new Builder(getActivity());
			builder.setMessage("确认退出吗？");
			builder.setTitle("提示");
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							Intent intent = new Intent(getActivity(),
									LoginActivity.class);
							startActivity(intent);
							getActivity().finish();
						}
					});
			builder.setNegativeButton("取消",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {

						}
					});
			builder.create().show();

			break;
		default:
			break;
		}
	}
}
