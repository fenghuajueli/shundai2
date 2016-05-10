package com.gzcjteam.shundai;

import com.gzcjteam.shundai.fargment.PersonerInfoFrament;
import com.gzcjteam.shundai.fargment.TabAllRenWuFrament;
import com.gzcjteam.shundai.fargment.TabFaBuRenWuFrament;
import com.gzcjteam.shundai.fargment.TabMyRenWuFrament;
import com.gzcjteam.shundai.weight.MainMyRenwuTopBar;
import com.gzcjteam.shundai.weight.MainTopBar;
import com.gzcjteam.shundai.weight.NormalTopBar;
import com.gzcjteam.shundai.weight.TabIndicatorView;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.text.AndroidCharacter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTopBar = (MainTopBar) findViewById(R.id.activity_main_top_bar);
		myrenwuBar= (MainMyRenwuTopBar) findViewById(R.id.activity_main_renwu_bar);
		mTopBar.setBackVisibility(false);
		mTopBar.setTitle("任务发布");
		initTabHost();
	}

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
		tabDiscoverIndicator.setNoReadCount(12);
		spec.setIndicator(tabDiscoverIndicator);
		tabhost.addTab(spec, TabMyRenWuFrament.class, null);// 3.添加tab

		spec = tabhost.newTabSpec(TAG_ALL);
		tabContentIndicator = new TabIndicatorView(this);
		tabContentIndicator.setTabTitle("全部任务");
		tabContentIndicator.setTabIcon(R.drawable.tab_icon_discover_normal,
				R.drawable.tab_icon_discover_focus);
		tabContentIndicator.setNoReadCount(83);
		spec.setIndicator(tabContentIndicator);
		tabhost.addTab(spec, TabAllRenWuFrament.class, null);// 3.添加tab

		spec = tabhost.newTabSpec(TAG_PERSONER);
		tabMeIndicator = new TabIndicatorView(this);
		tabMeIndicator.setTabTitle("个人中心");
		tabMeIndicator.setTabIcon(R.drawable.tab_icon_me_normal,
				R.drawable.tab_icon_me_focus);
		tabMeIndicator.setNoReadCount(12);
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
			tabChatIndicator.setTabSelected(true);
			mTopBar.setVisibility(View.VISIBLE);
			myrenwuBar.setVisibility(View.GONE);
			mTopBar.setTitle("任务发布");
			break;
		case TAG_ALL:
			tabContentIndicator.setTabSelected(true);			
			mTopBar.setVisibility(View.VISIBLE);
			myrenwuBar.setVisibility(View.GONE);
			mTopBar.setTitle("全部任务");
			break;
		case TAG_MYRENWU:
			tabDiscoverIndicator.setTabSelected(true);			
			myrenwuBar.setVisibility(View.VISIBLE);
			mTopBar.setVisibility(View.GONE);
			myrenwuBar.setOnWeiWanListener(MainActivity.this);
			myrenwuBar.setOnHistoryListener(MainActivity.this);
			break;
		case TAG_PERSONER:
			tabMeIndicator.setTabSelected(true);
			mTopBar.setVisibility(View.VISIBLE);
			myrenwuBar.setVisibility(View.GONE);
			mTopBar.setTitle("个人中心");
			break;
		default:
			break;
		}

	}

	@Override
	public void onClick(View v) {        
		if (v==myrenwuBar.getWeiWanView()) {
			myrenwuBar.getWeiWanView().setBackgroundResource(R.drawable.myrenwu_xuanzhong);		
			myrenwuBar.getHistoryView().setBackgroundResource(R.drawable.myrenwu_weixuanzhong);
			//执行替换fragment为未完成页面
			
			
		}else if(v==myrenwuBar.getHistoryView()){
			myrenwuBar.getWeiWanView().setBackgroundResource(R.drawable.myrenwu_weixuanzhong);
			myrenwuBar.getHistoryView().setBackgroundResource(R.drawable.myrenwu_xuanzhong);
			//执行替换fragment为历史任务页面
		}
		
		
	}

}
