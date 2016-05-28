package com.gzcjteam.shundai.fargment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.gzcjteam.shundai.R;
import com.gzcjteam.shundai.bean.NetCallBack;
import com.gzcjteam.shundai.utils.RequestUtils;
import com.gzcjteam.shundai.utils.getUserInfo;
import com.loopj.android.http.RequestParams;

import android.R.string;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SetHeadPicFragment extends Fragment implements OnClickListener {
	private RelativeLayout llPicCamera;
	private RelativeLayout llPicZoom;
	private ImageView img_setheadpic;
	private static int CAMERA_REQUEST_CODE = 1;
	private static int GALLERY_REQUEST_CODE = 2;
	private static int CROP_REQUEST_CODE = 3;
	private static final String sendImageUrl = "http://119.29.140.85/index.php/user/upload_head";
	private static Uri uri;
	private static File tempFile;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.set_head_pic_fragment, container,
				false);
		initView(view);
		return view;
	}

	private void initView(View view) {
		img_setheadpic = (ImageView) view.findViewById(R.id.img_setheadpic);
		llPicCamera = (RelativeLayout) view.findViewById(R.id.ll_piccamera);
		llPicZoom = (RelativeLayout) view.findViewById(R.id.ll_piczoom);

		img_setheadpic.setOnClickListener(this);
		llPicCamera.setOnClickListener(this);
		llPicZoom.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 相机
		case R.id.ll_piccamera:
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intent, CAMERA_REQUEST_CODE);
			break;
		// 图库
		case R.id.ll_piczoom:
			Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
			intent1.setType("image/*");
			startActivityForResult(intent1, GALLERY_REQUEST_CODE);
			break;
		default:
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 相机
		if (requestCode == CAMERA_REQUEST_CODE) {
			if (data == null) {
				return;
			} else {
				Bundle extras = data.getExtras();
				if (extras != null) {
					Bitmap bm = extras.getParcelable("data");
					uri = saveBitmap(bm);
					startImageZoom(uri);
				}
			}
		} else if (requestCode == GALLERY_REQUEST_CODE) { // 图库activity
			if (data == null) {
				return;
			}
			Uri uri;
			uri = data.getData();
			Uri fileUri = convertUri(uri);
			startImageZoom(fileUri);
		} else if (requestCode == CROP_REQUEST_CODE) {// 裁剪activity
			if (data == null) {
				return;
			}
			Bundle extras = data.getExtras();
			if (extras == null) {
				return;
			}
			Bitmap bm = extras.getParcelable("data");
			img_setheadpic.setImageBitmap(bm);
			// sendImage(bm);
			sendImageToService(sendImageUrl, uri);// 发送头像
		}

		// Toast.makeText(getActivity(), "sss", 1).show();
	}

	// 将bitmap保存在sd卡 并返回该文件的路径
	private Uri saveBitmap(Bitmap bm) {
		File tmpDir = new File(Environment.getExternalStorageDirectory()
				+ "/com.shundai.avater");
		if (!tmpDir.exists()) {
			tmpDir.mkdir();
		}
		File img = new File(tmpDir.getAbsolutePath() + "avater.png");
		try {
			FileOutputStream fos = new FileOutputStream(img);
			bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.flush();
			fos.close();

			tempFile = img;

			return Uri.fromFile(img);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 将content的uri转成file的uri
	private Uri convertUri(Uri uri) {
		InputStream is = null;
		try {
			is = getActivity().getContentResolver().openInputStream(uri);
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			is.close();
			return saveBitmap(bitmap);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private void startImageZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, CROP_REQUEST_CODE);
	}

	// 向服务器发送头像图片 存在Id为空的问题
	private void sendImageToService(String sendimageurl2, Uri uri2) {
		RequestParams params = new RequestParams();
		try {
			params.put("image", tempFile);
			params.put("id", new getUserInfo().getId());
			RequestUtils.ClientPost(sendImageUrl, params, new NetCallBack() {

				@Override
				public void onMySuccess(String result) {
					// Log.e("Test","头像上传成功"+result);
					Toast.makeText(getActivity(), "头像上传成功" + result, 1).show();
				}

				@Override
				public void onMyFailure(Throwable arg0) {
					// Log.e("Test","头像上传失败"+arg0.toString());
					Toast.makeText(getActivity(), "头像上传失败", 1).show();
				}
			});
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
