package com.gzcjteam.shundai.weight;

import com.gzcjteam.shundai.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainMyRenwuTopBar extends RelativeLayout {
	private Button weiwancheng;
	private Button history;

	public MainMyRenwuTopBar(Context context) {
		this(context, null);
	}

	public MainMyRenwuTopBar(Context context, AttributeSet attrs) {
		super(context, attrs);

		View.inflate(context, R.layout.myrenwubarl, this);
		weiwancheng = (Button) findViewById(R.id.weiwancheng);
		history = (Button) findViewById(R.id.history);	
		
	}

	
//	public void setBackVisibility(boolean show) {
//		ivBack.setVisibility(show ? View.VISIBLE : View.INVISIBLE);			
//	}
	
	
	public void setOnWeiWanListener(OnClickListener listener) {
		weiwancheng.setOnClickListener(listener);
	}

	public void setOnHistoryListener(OnClickListener listener) {
		history.setOnClickListener(listener);
	}
//	public void setTitle(String title) {
//		tvTitle.setText(title);
//	}
//
//	public void setTitle(int titleId) {
//		tvTitle.setText(titleId);
//	}

	public Button getWeiWanView() {
		return weiwancheng;
	}

	public Button getHistoryView() {
		return history;
	}

}
