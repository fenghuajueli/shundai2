package com.gzcjteam.shundai.fargment;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.interfaces.RSAKey;

import org.json.JSONException;
import org.json.JSONObject;

import com.gzcjteam.shundai.R;
import com.gzcjteam.shundai.bean.NetCallBack;
import com.gzcjteam.shundai.utils.BitmapListener;
import com.gzcjteam.shundai.utils.CircleTransform;
import com.gzcjteam.shundai.utils.RequestUtils;
import com.gzcjteam.shundai.utils.getUserInfo;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import android.R.string;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ThumbnailUtils;
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
	private BitmapListener bitmapListener;
	private static Bitmap returnBitmap;

	private String headPicUrl = "http://119.29.140.85"
			+ getUserInfo.getInstance().getHead_pic_url();

	public SetHeadPicFragment(BitmapListener bitmapListener) {
		this.bitmapListener = bitmapListener;
	}

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

		// 设置头像
		// AsyncHttpClient client = new AsyncHttpClient();
		// String[] allowedContentTypes = new String[] { "image/png",
		// "image/jpeg" };
		// client.get(headPicUrl, new BinaryHttpResponseHandler(
		// allowedContentTypes) {
		// @Override
		// public void onSuccess(byte[] fileData) {
		// Bitmap bitmap = BitmapFactory.decodeByteArray(fileData, 0,
		// fileData.length);
		// img_setheadpic.setImageBitmap(bitmap);
		// }
		// });

		Picasso.with(getActivity()).load(headPicUrl).error(R.drawable.shundai)
				.resize(100, 100).centerCrop().into(img_setheadpic);
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

			// 将bitmap进行压缩
			Bitmap zoomBitmap = imageZoom(bm);
			img_setheadpic.setImageBitmap(zoomBitmap);
			// 为回调用的结果赋值
			returnBitmap = zoomBitmap;
			// 把压缩后的bitmap以图片格式保存在SD卡
			// sendImage(bm);
			sendImageToService(sendImageUrl, saveCroppedImage(zoomBitmap));// 发送头像
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
	private void sendImageToService(String sendimageurl2, File file) {

		RequestParams params = new RequestParams();
		try {
			params.put("image", tempFile);
			params.put("id", getUserInfo.getInstance().getId());
			RequestUtils.ClientPost(sendImageUrl, params, new NetCallBack() {

				@Override
				public void onMySuccess(String result) {
					try {
						JSONObject json = new JSONObject(result);
						boolean status = json.getBoolean("status");
						JSONObject data;
						String newHeadPicUrl;
						if (status) {
							data = json.getJSONObject("data");
							newHeadPicUrl = data.getString("head_path");
							getUserInfo.getInstance().setHead_pic_url( // 更新头像图片地址
									newHeadPicUrl);
							bitmapListener.bitMapCallBack(returnBitmap);// 回调bitmap
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

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

	private Bitmap imageZoom(Bitmap bitMap) {
		// 图片允许最大空间 单位：KB
		double maxSize = 200.00;
		// 将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		// 将字节换成KB
		double mid = b.length / 1024;
		// 判断bitmap占用空间是否大于允许最大空间 如果大于则压缩 小于则不压缩
		if (mid > maxSize) {
			// 获取bitmap大小 是允许最大大小的多少倍
			double i = mid / maxSize;
			// 开始压缩 此处用到平方根 将宽带和高度压缩掉对应的平方根倍
			// （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
			Bitmap backBitMap = zoomImage(bitMap,
					bitMap.getWidth() / Math.sqrt(i),
					bitMap.getHeight() / Math.sqrt(i));
			return backBitMap;
		}
		return bitMap;
	}

	/***
	 * 图片的缩放方法
	 * 
	 * @param bgimage
	 *            ：源图片资源
	 * @param newWidth
	 *            ：缩放后宽度
	 * @param newHeight
	 *            ：缩放后高度
	 * @return
	 */
	public static Bitmap zoomImage(Bitmap bgimage, double newWidth,
			double newHeight) {
		// 获取这个图片的宽和高
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		// 计算宽高缩放率
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
				(int) height, matrix, true);
		return bitmap;
	}

	private File saveCroppedImage(Bitmap bmp) {
		File file = new File("/sdcard/myFolder");
		if (!file.exists())
			file.mkdir();

		file = new File("/sdcard/temp.jpg".trim());
		String fileName = file.getName();
		String mName = fileName.substring(0, fileName.lastIndexOf("."));
		String sName = fileName.substring(fileName.lastIndexOf("."));

		// /sdcard/myFolder/temp_cropped.jpg
		String newFilePath = "/sdcard/myFolder" + "/" + mName + "_cropped"
				+ sName;
		file = new File(newFilePath);
		try {
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			bmp.compress(CompressFormat.JPEG, 50, fos);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return file;
	}

	// 加入backstatus时调用
	@Override
	public void onPause() {
		super.onPause();
	}
}
