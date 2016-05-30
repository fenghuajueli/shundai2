package com.gzcjteam.shundai.weight;

import com.gzcjteam.shundai.R;
import com.gzcjteam.shundai.utils.ToastUtil;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class PullUpDialog extends Dialog implements OnClickListener {

	private TextView  tv_zhuangtai;
	private String zhuangTai;
	public PullUpDialog(Context context, int theme,String zhuangtai) {
		super(context, theme);	
		Window window = getWindow();
		zhuangTai=zhuangtai;
		WindowManager.LayoutParams lp = window.getAttributes();
		window.setAttributes(lp);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pulluprefash_launch);
		tv_zhuangtai=(TextView) findViewById(R.id.zhuangtai);
		tv_zhuangtai.setText(zhuangTai);
		setCancelable(false);
	}

	@Override
	public void onClick(View v) {
	
	}

}
