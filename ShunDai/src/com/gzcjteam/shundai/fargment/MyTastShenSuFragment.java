package com.gzcjteam.shundai.fargment;

import org.json.JSONException;
import org.json.JSONObject;

import com.gzcjteam.shundai.R;
import com.gzcjteam.shundai.bean.NetCallBack;
import com.gzcjteam.shundai.utils.RequestUtils;
import com.gzcjteam.shundai.utils.getUserInfo;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyTastShenSuFragment extends Fragment implements OnClickListener {
	private ImageView img_PersonalShenSu_back;

	private static String danhao;
	private static String content;

	private static EditText editDanHao;
	private static EditText editContent;
	private static TextView txtBack;
	private static Button btnCommit;
	private static final String commitUrl = "http://119.29.140.85/index.php/task/new_task_appeal";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.my_tastshensu_fragment,
				container, false);
		initView(view);

		return view;
	}

	private void initView(View view) {
		img_PersonalShenSu_back = (ImageView) view
				.findViewById(R.id.img_PersonalShenSu_back);
		editDanHao = (EditText) view.findViewById(R.id.edit_shensu_danhao);
		editContent = (EditText) view.findViewById(R.id.edit_shensu_neirong);
		txtBack = (TextView) view.findViewById(R.id.txt_shensu_back);
		btnCommit = (Button) view.findViewById(R.id.btn_shensu_commit);

		txtBack.setOnClickListener(this);
		img_PersonalShenSu_back.setOnClickListener(this);
		btnCommit.setOnClickListener(this);

		danhao = editDanHao.getText().toString();
		content = editContent.getText().toString();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_PersonalShenSu_back:
			getActivity().onBackPressed(); // 绑定系统返回按钮
			break;
		case R.id.txt_shensu_back:
			getActivity().onBackPressed();
			break;

		case R.id.btn_shensu_commit:
			RequestParams params = new RequestParams();
			params.put("appeal_user_id", getUserInfo.getInstance().getId());
			params.put("task_number", danhao);
			params.put("appeal_reason", content);

			RequestUtils.ClientPost(commitUrl, params, new NetCallBack() {

				@Override
				public void onMySuccess(String result) {
					JSONObject json;
					try {
						json = new JSONObject(result);
						boolean status = json.getBoolean("status");
						if (status) {
							Toast.makeText(getActivity(), "你的申诉已提交，请耐心等待处理结果",
									Toast.LENGTH_LONG).show();
						}else {
							Toast.makeText(getActivity(), "申诉失败！ 请填写正确的任务单号",
									Toast.LENGTH_LONG).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onMyFailure(Throwable arg0) {
					// TODO Auto-generated method stub

				}
			});

			break;
		default:
			break;
		}
	}
}
