package com.gzcjteam.shundai.fargment;

import org.json.JSONException;
import org.json.JSONObject;

import com.gzcjteam.shundai.R;
import com.gzcjteam.shundai.bean.NetCallBack;
import com.gzcjteam.shundai.utils.BitmapListener;
import com.gzcjteam.shundai.utils.DataCallBack;
import com.gzcjteam.shundai.utils.RequestUtils;
import com.gzcjteam.shundai.utils.ToastUtil;
import com.gzcjteam.shundai.utils.getUserInfo;
import com.gzcjteam.shundai.weight.ChosePicDialog;
import com.gzcjteam.shundai.weight.TiShiDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.R.bool;
import android.R.integer;
import android.R.string;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.IpPrefix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyInfoFragment extends Fragment implements OnClickListener {

	private static String backstatcTag = "PERSONALINFO";
	private static String personalCenterTag;
	private static Context context;
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
	private static String returnText = "";
	private static TextView txtName;
	private static TextView txtPhone;
	private static TextView txtSex;
	private static TextView txtSchool;
	private static TextView txtAddress;
	private static TextView txtLog;
	private static TextView txtEmail;
	private static TextView txtQQ;
	private static Button btnMySave;
	private static String schoolCode = "0";
	private static String updataUserInfoUrl = "http://119.29.140.85/index.php/user/update_info";

	private static String name;
	private static String phone;
	private static String sex;
	private static String schoolStr;
	private static String address;
	private static String log;
	private static String email;
	private static String QQ;

	private TextView txt_mt_centent;// 个人中心tab
	private String headPicUrl = "http://119.29.140.85"
			+ getUserInfo.getInstance().getHead_pic_url();

	public MyInfoFragment(Context context, String tag) {
		super();
		this.context = context;
		this.personalCenterTag = tag;
	}

	public MyInfoFragment() {

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
		txt_mt_centent = (TextView) view.findViewById(R.id.txt_mt_centent);
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

		txtName = (TextView) view.findViewById(R.id.txt_my_name);
		txtPhone = (TextView) view.findViewById(R.id.txt_my_phone);
		txtSex = (TextView) view.findViewById(R.id.txt_my_sex);
		txtSchool = (TextView) view.findViewById(R.id.txt_my_school);
		txtAddress = (TextView) view.findViewById(R.id.txt_my_address);
		txtLog = (TextView) view.findViewById(R.id.txt_my_log);
		txtEmail = (TextView) view.findViewById(R.id.txt_my_email);
		txtQQ = (TextView) view.findViewById(R.id.txt_my_QQ);
		btnMySave = (Button) view.findViewById(R.id.btn_my_save);

		// myName.setText(returnText);

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
		txt_mt_centent.setOnClickListener(this);
		btnMySave.setOnClickListener(this);
		txtPhone.setText(getUserInfo.getInstance().getPhone());

		// 设置头像
		AsyncHttpClient client = new AsyncHttpClient();
		String[] allowedContentTypes = new String[] { "image/png", "image/jpeg" };
		client.get(headPicUrl, new BinaryHttpResponseHandler(
				allowedContentTypes) {
			@Override
			public void onSuccess(byte[] fileData) {
				Bitmap bitmap = BitmapFactory.decodeByteArray(fileData, 0,
						fileData.length);
				img_change_head_pic.setImageBitmap(bitmap);
			}
		});
	}

	@Override
	public void onClick(View v) {
		manager = getActivity().getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();

		switch (v.getId()) {
		case R.id.txt_mt_centent:
			getActivity().onBackPressed();// 绑定系统的back按钮
			break;
		case R.id.img_PersonalInfo_back:
			getActivity().onBackPressed();// 绑定系统的back按钮
			break;
		// 头像设置
		case R.id.img_change_head_pic:
			transaction.replace(R.id.rlv_personal_info_center,
					new SetHeadPicFragment(new BitmapListener() {

						@Override
						public void bitMapCallBack(Bitmap bitmap) {
							img_change_head_pic.setImageBitmap(bitmap);
						}
					}));
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
			CommenInfoEditFragment addressfragment = newInstance("地址管理",
					"请输入您的纤细取货地址", "ADDRESS");
			transaction.replace(R.id.rlv_personal_info_center, addressfragment);
			transaction.addToBackStack("TO_Edit_ADDRESS");
			transaction.commit();
			break;
		// 个性签名
		case R.id.rlv_personinfo_log:
			CommenInfoEditFragment logfragment = newInstance("个性签名",
					"请输入您的个性签名", "LOG");
			transaction.replace(R.id.rlv_personal_info_center, logfragment);
			transaction.addToBackStack("TO_Edit_LOG");
			transaction.commit();
			break;
		// 邮箱
		case R.id.rlv_personinfo_email:
			CommenInfoEditFragment emailfragment = newInstance("电子邮件",
					"请输入您的电子邮件地址", "EMAIL");
			transaction.replace(R.id.rlv_personal_info_center, emailfragment);
			transaction.addToBackStack("TO_Edit_EMAIL");
			transaction.commit();
			break;
		// QQ
		case R.id.rlv_personinfo_QQ:
			CommenInfoEditFragment QQfragment = newInstance("QQ",
					"请输入您的常用QQ账号", "QQ");
			transaction.replace(R.id.rlv_personal_info_center, QQfragment);
			transaction.addToBackStack("TO_Edit_QQ");
			transaction.commit();
			break;

		case R.id.btn_my_save:
			RequestParams params = new RequestParams();
			params.put("id", getUserInfo.getInstance().getId());
			if (!txtName.getText().toString().equals("")
					|| txtName.getText().toString() != null) {
				params.put("nick", txtName.getText().toString());
			} else if (!txtSchool.getText().toString().equals("")
					|| txtSchool.getText().toString() != null) {
				if (txtSchool.getText().toString().equals("贵州财经大学(花溪校区)")) {
					schoolCode = "1";
				} else if (txtSchool.getText().toString()
						.equals("贵州医科大学(花溪校区)")) {
					schoolCode = "2";
				} else if (txtSchool.getText().toString()
						.equals("贵州师范大学(花溪校区)")) {
					schoolCode = "3";
				} else if (txtSchool.getText().toString()
						.equals("贵州城市学院(花溪校区)")) {
					schoolCode = "4";
				} else if (txtSchool.getText().toString().equals("贵州轻工职业技术学院")) {
					schoolCode = "5";
				} else if (txtSchool.getText().toString()
						.equals("贵州民族大学(花溪校区)")) {
					schoolCode = "6";
				}

				params.put("school_code", schoolCode);
			} else if (!txtAddress.getText().toString().equals("")
					|| txtAddress.getText().toString() != null) {
				params.put("floor_address", txtAddress.getText().toString());
			}

			RequestUtils.ClientPost(updataUserInfoUrl, params,
					new NetCallBack() {

						@Override
						public void onMySuccess(String result) {
							try {
								JSONObject json = new JSONObject(result);
								boolean status = json.getBoolean("status");
								if (status) {
									Toast.makeText(getActivity(), "用户信息更新成功",
											Toast.LENGTH_SHORT).show();
								}else {
									Toast.makeText(getActivity(), "用户信息更新失败！",
											Toast.LENGTH_SHORT).show();
								}

							} catch (JSONException e) {
								e.printStackTrace();
							}

						}

						@Override
						public void onMyFailure(Throwable arg0) {

						}
					});

			break;
		default:
			break;

		}

	}

	public static CommenInfoEditFragment newInstance(String tabName,
			String hint, String param) {
		CommenInfoEditFragment fragment = new CommenInfoEditFragment(tabName,
				hint, new DataCallBack() {

					@Override
					public void retrunEditData(String TAG, String text) {
						String MTAG = TAG;
						if (MTAG.equals("NAME")) {
							txtName.setText(text);
						} else if (MTAG.equals("PHONE")) {
							txtPhone.setText(text);
						} else if (MTAG.equals("SEX")) {
							Toast.makeText(context, MTAG + text, 1).show();
							txtSex.setText(text);
						} else if (MTAG.equals("SCHOOL")) {
							Toast.makeText(context, MTAG + text, 1).show();
							txtSchool.setText(text);
						} else if (MTAG.equals("ADDRESS")) {
							txtAddress.setText(text);
						} else if (MTAG.equals("LOG")) {
							txtLog.setText(text);
						} else if (MTAG.equals("EMAIL")) {
							txtEmail.setText(text);
						} else if (MTAG.equals("QQ")) {
							txtQQ.setText(text);
						} else {
							Toast.makeText(context, "返回码错误:" + TAG,
									Toast.LENGTH_SHORT).show();
						}
					}
				});
		Bundle args = new Bundle();
		args.putString("param", param);
		fragment.setArguments(args);
		return fragment;
	}
}
