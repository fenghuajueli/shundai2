package com.gzcjteam.shundai.utils;

import java.util.List;
import java.util.zip.Inflater;

import com.gzcjteam.shundai.R;
import com.gzcjteam.shundai.RenWuInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RenWu_Adapter extends BaseAdapter {
	View[] itemViews;
	private Context context;

	public RenWu_Adapter(List<RenWuInfo> mlistInfo, Context context) {
		// TODO Auto-generated constructor stub
		itemViews = new View[mlistInfo.size()];
		for (int i = 0; i < mlistInfo.size(); i++) {
			RenWuInfo getInfo = (RenWuInfo) mlistInfo.get(i); // 获取第i个对象
			// 调用makeItemView，实例化一个Item
			itemViews[i] = makeItemView(getInfo.getTime(),
					getInfo.getKuaidiName(), getInfo.getsAddress(),getInfo.getTupianId());
		}
		this.context = context;

	}

	// 绘制Item的函数
	private View makeItemView(String time, String kuaidiName,String address, int resId) {
		//LayoutInflater inflater = LayoutInflater.from(context); 
		// 使用View的对象itemView与R.layout.item关联
		View itemView = View.inflate(context, R.layout.listview_item, null);
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
