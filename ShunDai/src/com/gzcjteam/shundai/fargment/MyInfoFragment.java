package com.gzcjteam.shundai.fargment;

import com.gzcjteam.shundai.R;
import com.gzcjteam.shundai.weight.ChosePicDialog;
import android.content.Context;
import android.content.Intent;
import android.net.IpPrefix;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MyInfoFragment extends Fragment implements OnClickListener {

	private static String backstatcTag = "PERSONALINFO";
	private static String personalCenterTag;
	private Context context;
	private ImageView img_PersonalInfo_back;
	private ImageView img_change_head_pic;
	private static int CAMERA_REQUEST_CODE = 1;
	private static int GALLERY_REQUEST_CODE = 2;
	private static int CROP_REQUEST_CODE = 3;
	private FragmentManager manager;
	private RelativeLayout rlv_personinfo_chenni;// 称妮
	private RelativeLayout rlv_personinfo_phone;// 手机
	private RelativeLayout rlv_personinfo_sex;// 性别
	private RelativeLayout rlv_personinfo_school;// 学校
	private RelativeLayout rlv_personinfo_address;// 地址管理
	private RelativeLayout rlv_personinfo_log;// 个性签名
	private RelativeLayout rlv_personinfo_email;// 邮箱
	private RelativeLayout rlv_personinfo_QQ;// QQ

	public MyInfoFragment(Context context, String tag) {
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
		img_change_head_pic = (ImageView) view
				.findViewById(R.id.img_change_head_pic);
		rlv_personinfo_chenni = (RelativeLayout) view
				.findViewById(R.id.rlv_personinfo_chenni);
		rlv_personinfo_phone = (RelativeLayout) view
				.findViewById(R.id.rlv_personinfo_phone);
		rlv_personinfo_sex = (RelativeLayout) view
				.findViewById(R.id.rlv_personinfo_sex);
		rlv_personinfo_school = (RelativeLayout) view
				.findViewById(R.id.rlv_personinfo_school);
		rlv_personinfo_address = (RelativeLayout) view
				.findViewById(R.id.rlv_personinfo_address);
		rlv_personinfo_log = (RelativeLayout) view
				.findViewById(R.id.rlv_personinfo_log);
		rlv_personinfo_email = (RelativeLayout) view
				.findViewById(R.id.rlv_personinfo_email);
		rlv_personinfo_QQ = (RelativeLayout) view
				.findViewById(R.id.rlv_personinfo_QQ);

		img_PersonalInfo_back.setOnClickListener(this);
		img_change_head_pic.setOnClickListener(this);
		rlv_personinfo_chenni.setOnClickListener(this);
		rlv_personinfo_phone.setOnClickListener(this);
		rlv_personinfo_sex.setOnClickListener(this);
		rlv_personinfo_school.setOnClickListener(this);
		rlv_personinfo_address.setOnClickListener(this);
		rlv_personinfo_log.setOnClickListener(this);
		rlv_personinfo_email.setOnClickListener(this);
		rlv_personinfo_QQ.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		manager = getActivity().getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();

		switch (v.getId()) {
		case R.id.img_PersonalInfo_back:
			getActivity().onBackPressed();// 绑定系统的back按钮
			break;
		// 头像设置
		case R.id.img_change_head_pic:
			transaction.replace(R.id.rlv_personal_info_center,
					new SetHeadPicFragment());
			transaction.addToBackStack("TO_PERSONAL_INFO");
			transaction.commit();
			break;
		// 称妮
		case R.id.rlv_personinfo_chenni:
			CommenInfoEditFragment namefragment = newInstance("名字", "请输入用户名",
					"NAME");
			transaction.replace(R.id.rlv_personal_info_center, namefragment);
			transaction.addToBackStack("TO_Edit_NAME");
			transaction.commit();
			break;
		// 电话
		case R.id.rlv_personinfo_phone:
			CommenInfoEditFragment phonefragment = newInstance("手机", "请输入手机号",
					"PHONE");
			transaction.replace(R.id.rlv_personal_info_center, phonefragment);
			transaction.addToBackStack("TO_Edit_PHONE");
			transaction.commit();
			break;
		// 性别
		case R.id.rlv_personinfo_sex:
			CommenInfoEditFragment sexfragment = newInstance("性别", "请选择性别",
					"SEX");
			transaction.replace(R.id.rlv_personal_info_center, sexfragment);
			transaction.addToBackStack("TO_Edit_SXE");
			transaction.commit();
			break;
		// 学校
		case R.id.rlv_personinfo_school:
			CommenInfoEditFragment schoolfragment = newInstance("学校", "请选择学校",
					"SCHOOL");
			transaction.replace(R.id.rlv_personal_info_center, schoolfragment);
			transaction.addToBackStack("TO_Edit_SCHOOL");
			transaction.commit();
			break;
		// 地址管理
		case R.id.rlv_personinfo_address:
			CommenInfoEditFragment addressfragment = newInstance("地址管理", "请输入您的纤细取货地址",
					"ADDRESS");
			transaction.replace(R.id.rlv_personal_info_center, addressfragment);
			transaction.addToBackStack("TO_Edit_ADDRESS");
			transaction.commit();
			break;
		// 个性签名
		case R.id.rlv_personinfo_log:
			CommenInfoEditFragment logfragment = newInstance("个性签名", "请输入您的个性签名",
					"LOG");
			transaction.replace(R.id.rlv_personal_info_center, logfragment);
			transaction.addToBackStack("TO_Edit_LOG");
			transaction.commit();
			break;
		// 邮箱
		case R.id.rlv_personinfo_email:
			CommenInfoEditFragment emailfragment = newInstance("电子邮件", "请输入您的电子邮件地址",
					"EMAIL");
			transaction.replace(R.id.rlv_personal_info_center, emailfragment);
			transaction.addToBackStack("TO_Edit_EMAIL");
			transaction.commit();
			break;
		// QQ
		case R.id.rlv_personinfo_QQ:
			CommenInfoEditFragment QQfragment = newInstance("QQ", "请输入您的常用QQ账号",
					"QQ");
			transaction.replace(R.id.rlv_personal_info_center, QQfragment);
			transaction.addToBackStack("TO_Edit_QQ");
			transaction.commit();
			break;
		default:
			break;
		}
	}

	public static CommenInfoEditFragment newInstance(String tabName,
			String hint, String param) {
		CommenInfoEditFragment fragment = new CommenInfoEditFragment(tabName,
				hint);
		Bundle args = new Bundle();
		args.putString("param", param);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

	}

}
