package com.gzcjteam.shundai.fargment;

import java.util.ArrayList;
import java.util.List;

import com.gzcjteam.shundai.MainActivity;
import com.gzcjteam.shundai.LoginActivity;
import com.gzcjteam.shundai.R;
import com.gzcjteam.shundai.RenWuInfo;
import com.gzcjteam.shundai.utils.RenWu_Adapter;
import com.gzcjteam.shundai.utils.ToastUtil;
import com.gzcjteam.shundai.weight.RefreshableView;
import com.gzcjteam.shundai.weight.TiShiDialog;
import com.gzcjteam.shundai.weight.RefreshableView.PullToRefreshListener;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class HistoryRenWuFrament extends Fragment implements OnClickListener {

	public final static String ARG_KEY = "ARG";

	public final static int ARG_TYPE_FIRST = 0;
	public final static int ARG_TYPE_LOGINED = 1;
	public int currentFlag = 0;
	private Button btnSignUp;
	private Button btnSignIn;
	private Handler handler = new Handler();
	private ListView listview;
	private List<RenWuInfo> renwu = new ArrayList<RenWuInfo>();
	RenWu_Adapter1   renwu_adapter;
	RefreshableView refreshableView;

	MainActivity main = (MainActivity) getActivity();

	 @Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,
	 Bundle savedInstanceState) {
	 View view = inflater.inflate(R.layout.history_frament, container, false);
	 initView(view);
	 // initEvent();
	 return view;
	 }


	private void initView(View view) {
		listview = (ListView) view.findViewById(R.id.all_renwu_listView);
		setInfo();
		renwu_adapter=new RenWu_Adapter1(renwu, main);
		listview.setAdapter(renwu_adapter);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				
			}
		});
		refreshableView = (RefreshableView) view
				.findViewById(R.id.refreshable_view);
		refreshableView.setOnRefreshListener(new PullToRefreshListener() {
			@Override
			public void onRefresh() {
				try {
					Thread.sleep(3000);
					
					
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				refreshableView.finishRefreshing();
			}
		}, 0);

	}

	@Override
	public void onClick(View v) {

	}

	private void initEvent() {
		btnSignIn.setOnClickListener(this);
		btnSignUp.setOnClickListener(this);
	}

	public void setInfo() {
		renwu.clear();
		int i = 0;
		while (i < 10) {
			RenWuInfo information = new RenWuInfo();
			information.setTime("16:25");
			information.setKuaidiName("顺丰");
			information.setsAddress("贵州财经大学丹桂苑3栋");
			information.setTupianId(R.drawable.kuaidi3);
			renwu.add(information); // 将新的info对象加入到信息列表中
			i++;
		}
	}

	class RenWu_Adapter1 extends BaseAdapter {
		View[] itemViews;
		private Context context;

		public RenWu_Adapter1(List<RenWuInfo> mlistInfo, Context context) {
			// TODO Auto-generated constructor stub
			itemViews = new View[mlistInfo.size()];
			for (int i = 0; i < mlistInfo.size(); i++) {
				RenWuInfo getInfo = (RenWuInfo) mlistInfo.get(i); // 获取第i个对象
				// 调用makeItemView，实例化一个Item
				itemViews[i] = makeItemView(getInfo.getTime(),
						getInfo.getKuaidiName(), getInfo.getsAddress(),
						getInfo.getTupianId());
			}
			this.context = context;

		}

		// 绘制Item的函数
		private View makeItemView(String time, String kuaidiName,
				String address, int resId) {
			// LayoutInflater inflater = LayoutInflater.from(context);
			// 使用View的对象itemView与R.layout.item关联
			View itemView = View.inflate(getActivity(), R.layout.listview_item,
					null);
			// 通过findViewById()方法实例R.layout.item内各组件
			TextView kuaidi = (TextView) itemView.findViewById(R.id.kuaidiname);
			kuaidi.setText(kuaidiName); // 填入相应的值
			TextView sendtime = (TextView) itemView.findViewById(R.id.sendtime);
			sendtime.setText(time);
			TextView saddress = (TextView) itemView.findViewById(R.id.saddress);
			saddress.setText(address);
			ImageView image = (ImageView) itemView.findViewById(R.id.tupian);
			image.setImageResource(resId);
			return itemView;
		}

		@Override
		public int getCount() {
			return itemViews.length;
		}

		@Override
		public Object getItem(int position) {
			return itemViews[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// 使用View的对象itemView与R.layout.item关联
			if (convertView == null)
				return itemViews[position];
			return convertView;
		}

	}

}
