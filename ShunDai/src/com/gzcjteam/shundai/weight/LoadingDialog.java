package com.gzcjteam.shundai.weight;

import com.gzcjteam.shundai.R;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

public class LoadingDialog extends Dialog {
	private String text;
	private Context mContext;
	private TextView txtConten;

	public LoadingDialog(Context context, String text) {
		super(context);
		mContext = context;
		this.text = text;
	}

	protected void onCreate(android.os.Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.loading_dialog);

		txtConten = (TextView) findViewById(R.id.txt_londing_text);
		txtConten.setText(text);
		//findViewById(R.id.loading_dialog).setAlpha(122);
	};
}
