package com.gzcjteam.shundai;

import com.gzcjteam.shundai.fargment.HistoryRenWuFrament;
import com.gzcjteam.shundai.fargment.LoginFrament;
import com.gzcjteam.shundai.fargment.PersonerInfoFrament;
import com.gzcjteam.shundai.fargment.PwdNextFrament;
import com.gzcjteam.shundai.fargment.TabAllRenWuFrament;
import com.gzcjteam.shundai.fargment.TabFaBuRenWuFrament;
import com.gzcjteam.shundai.fargment.WeiWanChengRenWuFrament;
import com.gzcjteam.shundai.fargment.WoFaBuRenWuFrament;
import com.gzcjteam.shundai.utils.ToastUtil;
import com.gzcjteam.shundai.weight.MainMyRenwuTopBar;
import com.gzcjteam.shundai.weight.MainTopBar;
import com.gzcjteam.shundai.weight.ShaiXuanDialog;
import com.gzcjteam.shundai.weight.TabIndicatorView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class MainActivity extends FragmentActivity implements
		OnTabChangeListener, OnClickListener {

	private final static String TAG_FABU = "faburenwu";
	private final static String TAG_ALL = "allrenwu";
	private final static String TAG_MYRENWU = "myrenwu";
	private final static String TAG_PERSONER = "gerenzhongxin";
	private TabSpec spec;
	private FragmentTabHost tabhost;
	private MainTopBar mTopBar;
	private MainMyRenwuTopBar myrenwuBar;
	private TabIndicatorView tabChatIndicator;// 发布任务
	private TabIndicatorView tabContentIndicator;// 全部任务
	private TabIndicatorView tabDiscoverIndicator;// 我的任务
	private TabIndicatorView tabMeIndicator;// 个人中心
	private FragmentManager fm;
	private static String WeiWanTag = "weiwancheng";
	private static String HistoryTag = "history";

	private static boolean isExit = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTopBar = (MainTopBar) findViewById(R.id.activity_main_top_bar);
		myrenwuBar = (MainMyRenwuTopBar) findViewById(R.id.activity_main_renwu_bar);
		mTopBar.setOnBackListener(MainActivity.this);
		mTopBar.setBackVisibility(false);
		mTopBar.setTitle("任务发布");
		fm = getSupportFragmentManager();
		initTabHost();
	}

//	Handler mHandler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
//			isExit = false;
//		}
//	};

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			exit();
//			return false;
//		}
//		return super.onKeyDown(keyCode, event);
//	}
//
//	private void exit() {
//		if (!isExit) {
//			isExit = true;
//			ToastUtil.show(MainActivity.this, "再按一次退出程序");
//			// 利用handler延迟发送更改状态信息
//			mHandler.sendEmptyMessageDelayed(0, 2000);
//		} else {
//			finish();
//			System.exit(0);
//		}
//	}

	/**
	 * 初始化tabhost
	 */
	public void initTabHost() {
		// 1.初始化tabhost
		tabhost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		tabhost.setup(this, getSupportFragmentManager(), R.id.home_content);
		// 2.设置spec
		spec = tabhost.newTabSpec(TAG_FABU);
		tabChatIndicator = new TabIndicatorView(this);
		tabChatIndicator.setTabTitle("发布任务");
		tabChatIndicator.setTabIcon(R.drawable.tab_icon_chat_normal,
				R.drawable.tab_icon_chat_focus);
		spec.setIndicator(tabChatIndicator);
		tabhost.addTab(spec, TabFaBuRenWuFrament.class, null);// 3.添加tab

		spec = tabhost.newTabSpec(TAG_MYRENWU);
		tabDiscoverIndicator = new TabIndicatorView(this);
		tabDiscoverIndicator.setTabTitle("我的任务");
		tabDiscoverIndicator.setTabIcon(R.drawable.tab_icon_contact_normal,
				R.drawable.tab_icon_contact_focus);		
		spec.setIndicator(tabDiscoverIndicator);
		tabhost.addTab(spec, WeiWanChengRenWuFrament.class, null);// 3.添加tab

		spec = tabhost.newTabSpec(TAG_ALL);
		tabContentIndicator = new TabIndicatorView(this);
		tabContentIndicator.setTabTitle("全部任务");
		tabContentIndicator.setTabIcon(R.drawable.tab_icon_discover_normal,
				R.drawable.tab_icon_discover_focus);
		spec.setIndicator(tabContentIndicator);
		tabhost.addTab(spec, TabAllRenWuFrament.class, null);// 3.添加tab

		spec = tabhost.newTabSpec(TAG_PERSONER);
		tabMeIndicator = new TabIndicatorView(this);
		tabMeIndicator.setTabTitle("个人中心");
		tabMeIndicator.setTabIcon(R.drawable.tab_icon_me_normal,
				R.drawable.tab_icon_me_focus);
		spec.setIndicator(tabMeIndicator);
		tabhost.addTab(spec, PersonerInfoFrament.class, null);// 3.添加tab
		// 去掉tabhost的分割线
		tabhost.getTabWidget().setDividerDrawable(android.R.color.white);
		tabhost.setCurrentTabByTag(TAG_FABU);
		tabChatIndicator.setTabSelected(true);

		// 监听tabhost的选中事件
		tabhost.setOnTabChangedListener(this);
	}

	@Override
	public void onTabChanged(String tag) {
		tabChatIndicator.setTabSelected(false);
		tabContentIndicator.setTabSelected(false);
		tabDiscoverIndicator.setTabSelected(false);
		tabMeIndicator.setTabSelected(false);
		switch (tag) {
		case TAG_FABU:
			mTopBar.setBackVisibility(false);
			tabChatIndicator.setTabSelected(true);
			mTopBar.setVisibility(View.VISIBLE);
			myrenwuBar.setVisibility(View.GONE);
			mTopBar.setTitle("任务发布");
			yiChuFragment();
			break;
		case TAG_ALL:
			tabContentIndicator.setTabSelected(true);
			mTopBar.setBackVisibility(true);
			mTopBar.setVisibility(View.VISIBLE);
			myrenwuBar.setVisibility(View.GONE);
			mTopBar.setTitle("全部任务");
			yiChuFragment();
			break;
		case TAG_MYRENWU:
			tabDiscoverIndicator.setTabSelected(true);
			myrenwuBar.setVisibility(View.VISIBLE);
			mTopBar.setVisibility(View.GONE);
			myrenwuBar.setOnWeiWanListener(MainActivity.this);
			myrenwuBar.setOnHistoryListener(MainActivity.this);
			gotoWeiWan();
			break;
		case TAG_PERSONER:
			mTopBar.setBackVisibility(false);
			tabMeIndicator.setTabSelected(true);
			mTopBar.setVisibility(View.VISIBLE);
			myrenwuBar.setVisibility(View.GONE);
			mTopBar.setTitle("个人中心");
			yiChuFragment();
			break;
		default:
			break;
		}

	}

	public void yiChuFragment() {
		Fragment fragment = fm.findFragmentByTag(WeiWanTag);
		if (fragment != null) {
			FragmentTransaction ft = fm.beginTransaction();
			ft.remove(fragment);
			ft.commit();
		}
		Fragment fragment1 = fm.findFragmentByTag(HistoryTag);
		if (fragment1 != null) {
			FragmentTransaction ft = fm.beginTransaction();
			ft.remove(fragment1);
			ft.commit();
		}
	}

	/**
	 * 转向未完成页面
	 */
	public void gotoWeiWan() {
		myrenwuBar.getWeiWanView().setBackgroundResource(
				R.drawable.myrenwu_xuanzhong);
		myrenwuBar.getHistoryView().setBackgroundResource(
				R.drawable.myrenwu_weixuanzhong);
		// 执行替换fragment为未完成页面
		Fragment fragment = fm.findFragmentByTag(WeiWanTag);
		if (fragment == null) {
			fragment = new WeiWanChengRenWuFrament();
		}
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.home_content, fragment, WeiWanTag);
		ft.addToBackStack(WeiWanTag);
		ft.commit();
	}

	/**
	 * 转向历史任务页面
	 */
	public void gotoHistory() {
		myrenwuBar.getWeiWanView().setBackgroundResource(
				R.drawable.myrenwu_weixuanzhong);
		myrenwuBar.getHistoryView().setBackgroundResource(
				R.drawable.myrenwu_xuanzhong);
		// 执行替换fragment为历史任务页面
		Fragment fragment = fm.findFragmentByTag(HistoryTag);
		if (fragment == null) {
			fragment = new WoFaBuRenWuFrament();
		}
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.home_content, fragment, HistoryTag);
		ft.addToBackStack(HistoryTag);
		ft.commit();
	}

	@Override
	public void onClick(View v) {
		if (v == myrenwuBar.getWeiWanView()) {
			gotoWeiWan();
		} else if (v == myrenwuBar.getHistoryView()) {
			gotoHistory();
		} 
	}
	
	public  void  gotoShaiXuan(){		
		ToastUtil.show(MainActivity.this, "调用成功");
		
	}
	

	
}
