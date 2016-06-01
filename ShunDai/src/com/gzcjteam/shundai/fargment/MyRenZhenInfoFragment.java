package com.gzcjteam.shundai.fargment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.gzcjteam.shundai.R;
import com.gzcjteam.shundai.bean.NetCallBack;
import com.gzcjteam.shundai.utils.RequestUtils;
import com.gzcjteam.shundai.utils.getUserInfo;
import com.loopj.android.http.RequestParams;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.StaticLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MyRenZhenInfoFragment extends Fragment implements OnClickListener {
	private static String pageName = null;
	private Context context;
	private static String name;
	private static String id;
	private static String idcard;
	private static String studentId;
	private static String schoolStr;
	private static String schoolCode;
	private static String suShe;
	private static String saveUrl = "http://119.29.140.85/index.php/user/validate_user";

	private static EditText editName;
	private static EditText editStudentId;
	private static EditText editIdcard;
	private static Spinner spnSchool;
	private static EditText editSuShe;
	private static TextView geRenZhongXin;

	private Button btnSave;
	private ImageView back;

	private List<String> list;
	private ArrayAdapter<String> adapter;

	public MyRenZhenInfoFragment(Context context) {
		super();
		this.context = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.my_renzheninfo_fargment,
				container, false);
		initData();
		initView(view);
		return view;
	}

	private void initView(View view) {
		editName = (EditText) view.findViewById(R.id.edt_renzhen_username);
		editStudentId = (EditText) view.findViewById(R.id.edt_renzhen_xuehao);
		editIdcard = (EditText) view.findViewById(R.id.edt_renzhen_zhenjian);
		spnSchool = (Spinner) view.findViewById(R.id.spn_renzhen_school);
		editSuShe = (EditText) view.findViewById(R.id.edt_renzhen_room);
		btnSave = (Button) view.findViewById(R.id.btn_renzhen_save);
		back = (ImageView) view.findViewById(R.id.img_PersonalIdentif_back);
		geRenZhongXin = (TextView) view.findViewById(R.id.txt_gerenzhongxin);
		adapter = new ArrayAdapter<>(getActivity(),
				android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnSchool.setAdapter(adapter);
		spnSchool.setSelection(0);

		name = editName.getText().toString();
		idcard = editIdcard.getText().toString();
		studentId = editStudentId.getText().toString();
		schoolStr = (String) spnSchool.getSelectedItem();
		suShe = editSuShe.getText().toString();

		if (schoolStr.equals("贵州财经大学(花溪校区)")) {
			schoolCode = "1";
		} else if (schoolStr.equals("贵州医科大学(花溪校区)")) {
			schoolCode = "2";
		} else if (schoolStr.equals("贵州师范大学(花溪校区)")) {
			schoolCode = "3";
		} else if (schoolStr.equals("贵州城市学院(花溪校区)")) {
			schoolCode = "4";
		} else if (schoolStr.equals("贵州轻工职业技术学院")) {
			schoolCode = "5";
		} else if (schoolStr.equals("贵州民族大学(花溪校区)")) {
			schoolCode = "6";
		}

		btnSave.setOnClickListener(this);
		back.setOnClickListener(this);
		geRenZhongXin.setOnClickListener(this);

		if (getUserInfo.getInstance().getIdentity_status().equals("1")) {
			Toast.makeText(getActivity(), "您已是认证用户,无需再次认证", Toast.LENGTH_LONG)
					.show();
		}
	}

	private void initData() {
		id = getUserInfo.getInstance().getId();
		list = new ArrayList<>();
		list.add("贵州财经大学(花溪校区)");
		list.add("贵州医科大学(花溪校区)");
		list.add("贵州师范大学(花溪校区)");
		list.add("贵州城市学院(花溪校区)");
		list.add("贵州轻工职业技术学院");
		list.add("贵州民族大学(花溪校区)");

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_renzhen_save:
			RequestParams params = new RequestParams();
			params.put("user_id", id);
			params.put("name", name);
			params.put("idcard", idcard);
			params.put("student_id", studentId);
			params.put("school_code", schoolCode);
			params.put("card_positive_pic_url", "http://119.29.140.85"
					+ getUserInfo.getInstance().getHead_pic_url());
			params.put("card_inverse_pic_url", "http://119.29.140.85"
					+ getUserInfo.getInstance().getHead_pic_url());

			RequestUtils.ClientPost(saveUrl, params, new NetCallBack() {

				@Override
				public void onMySuccess(String result) {
					try {
						JSONObject json = new JSONObject(result);
						boolean status = json.getBoolean("status");
						if (status) {
							Toast.makeText(getActivity(), "信息已提交,请等待认证",
									Toast.LENGTH_LONG).show();
						} else {
							Toast.makeText(
									getActivity(),
									"信息提交失败!" + json.getString("info") + result,
									Toast.LENGTH_LONG).show();
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
		case R.id.img_PersonalIdentif_back:
			getActivity().onBackPressed();

			break;
		case R.id.txt_gerenzhongxin:
			getActivity().onBackPressed();
			break;

		default:
			break;
		}
	}
}
