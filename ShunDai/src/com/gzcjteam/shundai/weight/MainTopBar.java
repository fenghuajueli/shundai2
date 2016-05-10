package com.gzcjteam.shundai.weight;

import com.gzcjteam.shundai.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainTopBar extends RelativeLayout {
	private ImageView ivBack;
	private TextView tvTitle;
	private TextView tvmiaosu;

	public MainTopBar(Context context) {
		this(context, null);
	}

	public MainTopBar(Context context, AttributeSet attrs) {
		super(context, attrs);

		View.inflate(context, R.layout.mainbar_normal, this);
		ivBack = (ImageView) findViewById(R.id.bar_shaixuan);
		tvTitle = (TextView) findViewById(R.id.bar_title);
		tvmiaosu = (TextView) findViewById(R.id.miaosu);
		
	}

	
	public void setBackVisibility(boolean show) {
		ivBack.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
		tvmiaosu.setVisibility(show ? View.VISIBLE : View.INVISIBLE);		
	}
	
	
	public void setOnBackListener(OnClickListener listener) {
		ivBack.setOnClickListener(listener);
	}

	public void setTitle(String title) {
		tvTitle.setText(title);
	}

	public void setTitle(int titleId) {
		tvTitle.setText(titleId);
	}

	public ImageView getBackView() {
		return ivBack;
	}

	public TextView getTitleView() {
		return tvTitle;
	}

}
