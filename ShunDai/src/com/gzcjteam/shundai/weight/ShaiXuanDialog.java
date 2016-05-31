package com.gzcjteam.shundai.weight;

import com.gzcjteam.shundai.MainActivity;
import com.gzcjteam.shundai.R;
import com.gzcjteam.shundai.fargment.TabAllRenWuFrament;
import com.gzcjteam.shundai.utils.OnSureClickListener;
import com.gzcjteam.shundai.utils.ToastUtil;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.view.View.OnClickListener;

public class ShaiXuanDialog extends Dialog implements OnClickListener {
	private static String[] school_spindata = { "全部学校","贵州财经大学花溪校区", "贵州师范大学花溪校区",
			"贵州医科大学花溪校区", "贵州城市学院", "贵州轻工业职业学校", "贵州民族大学花溪校区" };
	private static String[] kuaidi_spindata = { "全部快递","韵达快递", "申通快递", "顺丰快递", "EMS",
			"圆通快递", "中通快递", "百世汇通", "京东" };
	private Spinner school_spinner;
	private Spinner kuaidi_spinner;
	private ArrayAdapter<String> school_adapter;
	private ArrayAdapter<String> kuaidi_adapter;
	private Button btn_queding;
	private Context context;
	private OnSureClickListener mlistener;

	private String schCode = "";
	private String kuaiCode = "";

	public ShaiXuanDialog(Context context, int theme,
			ArrayAdapter<String> school_adapter,
			ArrayAdapter<String> kuaidi_adapter, OnSureClickListener listerner) {
		super(context, theme);
		this.school_adapter = school_adapter;
		this.kuaidi_adapter = kuaidi_adapter;
		this.context = context;
		this.mlistener = listerner;
		Window window = getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		window.setAttributes(lp);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shaixuan_dialog);
		school_spinner = (Spinner) findViewById(R.id.school);
		kuaidi_spinner = (Spinner) findViewById(R.id.kuaidigongsi);
		btn_queding = (Button) findViewById(R.id.queding);
		btn_queding.setOnClickListener(this);
		school_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		school_spinner.setAdapter(school_adapter);
		kuaidi_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		kuaidi_spinner.setAdapter(kuaidi_adapter);
		kuaidi_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String tag = kuaidi_spindata[position].toString();
				switch (tag) {
				case "韵达快递":
					kuaiCode="1";
					break;
				case "申通快递":
					kuaiCode="2";
					break;
				case "顺丰快递":
					kuaiCode="3";
					break;
				case "EMS":
					kuaiCode="4";
					break;
				case "圆通快递":
					kuaiCode="5";
					break;
				case "中通快递":
					kuaiCode="6";
					break;
				case "百世汇通":
					kuaiCode="7";
					break;
				case "京东":
					kuaiCode="8";
					break;
				case "全部快递":
					kuaiCode="";
					break;
				default:
					break;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		school_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				String tag = school_spindata[position].toString();

				switch (tag) {
				case "贵州财经大学花溪校区":
					schCode="1";
					break;
				case "贵州师范大学花溪校区":
					schCode="3";
					break;
				case "贵州医科大学花溪校区":
					schCode="2";
					break;
				case "贵州城市学院花溪校区":
					schCode="4";
					break;
				case "贵州轻工业职业学校":
					schCode="5";
					break;
				case "贵州民族大学花溪校区":
					schCode="6";
				case "全部学校":
					schCode="";
					break;
				default:
					break;
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	@Override
	public void onClick(View v) {
		if (v == btn_queding) {
			mlistener.refalshData(schCode, kuaiCode, true);
			this.dismiss();
		}

	}

}
