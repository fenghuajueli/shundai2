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
import android.view.View.OnClickListener;

public class ShaiXuanDialog extends Dialog implements OnClickListener{
	private static String[] school_spindata = { "贵州财经大学花溪校区", "贵州师范大学花溪校区", "贵州医科大学花溪校区", "城市学院", "贵州轻工业职业学校", "贵州民族大学花溪校区" };
	private static String[] kuaidi_spindata = { "顺丰快递", "圆通快递", "申通", "中通快递", "天天快递", "韵达快递" };
	private Spinner school_spinner;
	private Spinner kuaidi_spinner;
    private ArrayAdapter<String> school_adapter;
    private ArrayAdapter<String> kuaidi_adapter;
    private Button  btn_queding;
	
	public ShaiXuanDialog(Context context,int theme,ArrayAdapter<String> school_adapter,ArrayAdapter<String> kuaidi_adapter) {
		super(context,theme);		
		this.school_adapter=school_adapter;
		this.kuaidi_adapter=kuaidi_adapter;
		Window window = getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.x = -100;
		lp.y = -220;
		window.setAttributes(lp);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shaixuan_dialog);
		school_spinner = (Spinner) findViewById(R.id.school);
		kuaidi_spinner = (Spinner)findViewById(R.id.kuaidigongsi);
		btn_queding=(Button) findViewById(R.id.queding);
		btn_queding.setOnClickListener(this);
		school_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		school_spinner.setAdapter(school_adapter);
		kuaidi_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		kuaidi_spinner.setAdapter(kuaidi_adapter);
	}

	@Override
	public void onClick(View v) {
		if (v==btn_queding) {
			this.dismiss();
		}
		
	}
	
	
	
	

}
