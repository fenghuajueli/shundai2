package com.gzcjteam.shundai.weight;

import com.gzcjteam.shundai.R;
import com.gzcjteam.shundai.fargment.MyInfoFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

public class ChosePicDialog extends Dialog implements
		android.view.View.OnClickListener {
	private Context context;
	private RelativeLayout rlCamera;
	private RelativeLayout rlPicZoom;
	private RelativeLayout rlCance;
	private static int CAMERA_REQUEST_CODE = 1;
	private static int GALLERY_REQUEST_CODE = 2;
	private static int CROP_REQUEST_CODE = 3;

	public ChosePicDialog(Context myInfoFragment) {
		super(myInfoFragment);
		this.context = myInfoFragment;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chose_pic_dialog);

		initView();

	}

	private void initView() {
		rlCamera = (RelativeLayout) findViewById(R.id.rlv_head_canmer);
		rlPicZoom = (RelativeLayout) findViewById(R.id.rlv_head_picketure);
		rlCance = (RelativeLayout) findViewById(R.id.rlv_head_cancer);

		rlCamera.setOnClickListener(this);
		rlPicZoom.setOnClickListener(this);
		rlCance.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rlv_head_canmer:
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			MyInfoFragment mInfoFragment = new MyInfoFragment(
					context, "PERSONALCENTER");
			mInfoFragment.startActivityForResult(intent, CAMERA_REQUEST_CODE);
			break;
		case R.id.rlv_head_picketure:
			break;

		case R.id.rlv_head_cancer:
			break;

		default:
			break;
		}
	}
}
