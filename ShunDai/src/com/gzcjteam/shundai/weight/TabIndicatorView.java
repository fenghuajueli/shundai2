package com.gzcjteam.shundai.weight;

import com.gzcjteam.shundai.R;
import com.gzcjteam.shundai.R.id;
import com.gzcjteam.shundai.R.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TabIndicatorView extends RelativeLayout {

	private ImageView ivChatImg;
	private TextView tvChatHint;
	private TextView tvChatUnRead;

	private int iconNormaiId;
	private int iconFocusId;

	public TabIndicatorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 1.将布局文件和代码进行绑定
		View.inflate(context, R.layout.tab_indicator, this);
		ivChatImg = (ImageView) findViewById(R.id.tab_indicator_icon);
		tvChatHint = (TextView) findViewById(R.id.tab_indicator_hint);
		tvChatUnRead = (TextView) findViewById(R.id.tab_indicator_unread);
		tvChatUnRead.setVisibility(View.GONE);
		//setNoReadCount(0);
	}

	public TabIndicatorView(Context context) {
		this(context, null);
	}

	public void setTabTitle(String title) {
		tvChatHint.setText(title);
	}

	public void setTabTitle(int titleid) {
		tvChatHint.setId(titleid);
	}

	/**
	 * @param iconNormaiId
	 *            //正常状态下的icon
	 * @param iconUnReadId
	 *            设置tabhost的icon
	 */
	public void setTabIcon(int iconNormaiId, int iconFocusId) {
		this.iconNormaiId = iconNormaiId;
		this.iconFocusId = iconFocusId;
		ivChatImg.setImageResource(iconNormaiId);
	}

	public void setNoReadCount(int count) {
		if (count <= 0) {
			tvChatUnRead.setVisibility(View.GONE);
		} else {
			if (count <= 99) {
				tvChatUnRead.setText(count + "");
			} else {
				tvChatUnRead.setText("99+");
			}
		}

	}

	/**
	 * @param selected
	 * 设置选中或者未选中的图标
	 */
	public void setTabSelected(Boolean selected) {
		if (selected) {
			ivChatImg.setImageResource(iconFocusId);
		} else {
			ivChatImg.setImageResource(iconNormaiId);
		}
	}

}
