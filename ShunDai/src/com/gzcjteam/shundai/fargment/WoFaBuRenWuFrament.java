package com.gzcjteam.shundai.fargment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gzcjteam.shundai.MainActivity;
import com.gzcjteam.shundai.LoginActivity;
import com.gzcjteam.shundai.R;
import com.gzcjteam.shundai.RenWuInfo;
import com.gzcjteam.shundai.RenWuInfoActivity;
import com.gzcjteam.shundai.WoFaBuInfoActivity;
import com.gzcjteam.shundai.bean.NetCallBack;
import com.gzcjteam.shundai.fargment.TabAllRenWuFrament.ViewHolder;
import com.gzcjteam.shundai.utils.RenWu_Adapter;
import com.gzcjteam.shundai.utils.RequestUtils;
import com.gzcjteam.shundai.utils.ToastUtil;
import com.gzcjteam.shundai.utils.getUserInfo;
import com.gzcjteam.shundai.weight.PullUpDialog;
import com.gzcjteam.shundai.weight.RefreshableView;
import com.gzcjteam.shundai.weight.TiShiDialog;
import com.gzcjteam.shundai.weight.RefreshableView.PullToRefreshListener;
import com.loopj.android.http.RequestParams;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class WoFaBuRenWuFrament extends Fragment implements OnClickListener {

	public final static String ARG_KEY = "ARG";

	public final static int ARG_TYPE_FIRST = 0;
	public final static int ARG_TYPE_LOGINED = 1;
	public int currentFlag = 0;
	private Button btnSignUp;
	private Button btnSignIn;
	private ListView listview;
	private List<RenWuInfo> renwu = new ArrayList<RenWuInfo>();
	RenWu_Adapter1 renwu_adapter;
	RefreshableView refreshableView;
	protected static final int PULLREFASHSUCCESS = 3;
	protected static final int PULLREFASHFAILED = 4;
	private final static int REFASHFAILED = 1;
	private final static int REFASHSUCCESS = 2;
	private static final int CHANGEDATA = 0;
	protected static final int REFASHWANBI = 5;
	private JSONObject allJsondata;
	private int stop_position; // 记录滚动停止的位置
	Boolean isSuccess = false;
	private boolean isLastRow = false;// 判断是不是最后一行
	private int dangQianPage = 1;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);

			switch (msg.what) {
			case REFASHFAILED:
				ToastUtil.show(getActivity(), "刷新失败！");
				break;
			case REFASHSUCCESS:
				ToastUtil.show(getActivity(), "刷新成功！");
				break;
			case PULLREFASHFAILED:
				ToastUtil.show(getActivity(), "加载失败！");
				break;
			case PULLREFASHSUCCESS:
				ToastUtil.show(getActivity(), "加载成功！");
				break;
			case REFASHWANBI:
				ToastUtil.show(getActivity(), "数据全部加载完毕！");
				break;
			case CHANGEDATA:
				renwu_adapter.mList = renwu;
				renwu_adapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater
				.inflate(R.layout.history_frament, container, false);
		initView(view);
		// initEvent();
		return view;
	}

	private void initView(View view) {
		listview = (ListView) view.findViewById(R.id.all_renwu_listView);
		// setInfo();
		renwu_adapter = new RenWu_Adapter1(renwu, getActivity());
		listview.setAdapter(renwu_adapter);
		listview.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (isLastRow
						&& scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					stop_position = listview.getFirstVisiblePosition();
					PullUpDialog dia = new PullUpDialog(getActivity(),
							R.style.adialog, "正在加载更多数据中。。");
					dia.show();
					if (requestPullUpData()) {
						dia.dismiss();
					} else {
						dia.dismiss();
					}
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if ((firstVisibleItem + visibleItemCount) >= totalItemCount) {
					isLastRow = true;
				}
			}
		});

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), WoFaBuInfoActivity.class);
				intent.putExtra("task_id",renwu.get(position).getId());	
				intent.putExtra("complete_user_id",renwu.get(position).getCompleteuserid());	
				intent.putExtra("complete_user_head_pic_url",renwu.get(position).getCompleteuserheadpicurl());	
				intent.putExtra("complete_user_nick",renwu.get(position).getCompleteusernick());	
				intent.putExtra("complete_user_phone",renwu.get(position).getComplete_user_phone());					
				startActivity(intent);
			}
		});
		refreshableView = (RefreshableView) view
				.findViewById(R.id.refreshable_view);
		refreshableView.setOnRefreshListener(new PullToRefreshListener() {
			@Override
			public void onRefresh() {
				requestData();
			}
		}, 0);

	}

	@Override
	public void onClick(View v) {

	}

	/**
	 * @return 上拉加载更多数据
	 * 
	 */
	public Boolean requestPullUpData() {
		String url = "http://119.29.140.85/index.php/task/list_launch";
		RequestParams params = new RequestParams();
		params.put("launch_user_id", getUserInfo.getInstance().getId());
		params.put("page", dangQianPage + "");
		params.put("page_size", "8");
		RequestUtils.ClientPost(url, params, new NetCallBack() {
			@Override
			public void onMySuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					Boolean status = json.getBoolean("status");
					String info = json.getString("info");
					JSONArray jsonarray = new JSONArray(json.getString("data"));
					if (status) {
						if (jsonarray.length() > 0) {
							Message msg = new Message();
							msg.what = PULLREFASHSUCCESS;
							handler.sendMessage(msg);
							refreshableView.finishRefreshing();
							allJsondata = json;
							dangQianPage++;
							changeListData();
							isSuccess = true;
						} else {
							ToastUtil.show(getActivity(), "数据全部加载完毕！");
						}
					} else {
						Message msg = new Message();
						msg.what = PULLREFASHFAILED;
						handler.sendMessage(msg);
						refreshableView.finishRefreshing();
						allJsondata = null;
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onMyFailure(Throwable arg0) {
				Message msg = new Message();
				msg.what = REFASHFAILED;
				handler.sendMessage(msg);
				refreshableView.finishRefreshing();
			}
		});

		return isSuccess;
	}

	/**
	 * 请求全部任务的数据方法
	 */
	public Boolean requestData() {
		String url = "http://119.29.140.85/index.php/task/list_launch";
		RequestParams params = new RequestParams();
		params.put("launch_user_id", getUserInfo.getInstance().getId());
		params.put("page", dangQianPage + "");
		params.put("page_size", "8");
		RequestUtils.ClientPost(url, params, new NetCallBack() {
			@Override
			public void onMySuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					Boolean status = json.getBoolean("status");
					String info = json.getString("info");
					JSONArray jsonarray = new JSONArray(json.getString("data"));
					if (status) {
						refreshableView.finishRefreshing();
						if (jsonarray.length() > 0) {
							Message msg = new Message();
							msg.what = REFASHSUCCESS;
							handler.sendMessage(msg);
							allJsondata = json;
							changeListData();
						
							dangQianPage++;
							isSuccess = true;
						} else {
							Message msg = new Message();
							msg.what = REFASHWANBI;
							handler.sendMessage(msg);
						}
					} else {
						Message msg = new Message();
						msg.what = REFASHFAILED;
						handler.sendMessage(msg);
						refreshableView.finishRefreshing();
						allJsondata = null;
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onMyFailure(Throwable arg0) {
				Message msg = new Message();
				msg.what = REFASHFAILED;
				handler.sendMessage(msg);
				refreshableView.finishRefreshing();
			}
		});

		return isSuccess;
	}

	public void changeListData() {
		// renwu.clear();
		if (allJsondata != null) {
			try {
				JSONArray jsonarray = new JSONArray(
						allJsondata.getString("data"));
				int count = jsonarray.length();
				int i = 0;
				while (i < count) {
					JSONObject js = jsonarray.getJSONObject(i);
					RenWuInfo infodata = new RenWuInfo();
					System.out.println(js.getString("express_name"));
					infodata.setTime(js.getString("launch_time"));
					infodata.setKuaidiName(js.getString("express_name"));
					infodata.setsAddress(js.getString("receive_address"));
					infodata.setId(js.getString("id"));
					infodata.setSchoolName(js.getString("school_name"));
					infodata.setComplete_user_phone(js.getString("complete_user_phone"));
					infodata.setCompleteuserheadpicurl(js.getString("complete_user_head_pic_url"));
					infodata.setCompleteuserid(js.getString("complete_user_id"));
					infodata.setCompleteusernick(js.getString("complete_user_nick"));				
					infodata.setTupianId(R.drawable.kuaidi3);
					renwu.add(infodata); // 将新的info对象加入到信息列表中
					i++;
				}
				System.out.println(renwu.size());
				Message msg = new Message();
				msg.what = CHANGEDATA;
				handler.sendMessage(msg);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}

	private void initEvent() {
		btnSignIn.setOnClickListener(this);
		btnSignUp.setOnClickListener(this);
	}

	class RenWu_Adapter1 extends BaseAdapter {
		public View[] itemViews;
		public Context context;
		public List<RenWuInfo> mList;
		private LayoutInflater mInflater = null;

		public RenWu_Adapter1(List<RenWuInfo> mlistInfo, Context context) {
			// TODO Auto-generated constructor stub
			this.context = context;
			this.mInflater = LayoutInflater.from(context);
			mList = mlistInfo;
		}

		public void refresh(List<RenWuInfo> list) {
			mList = list;
		}

		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			// return itemViews[position];
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			// 如果缓存convertView为空，则需要创建View
			if (convertView == null) {
				holder = new ViewHolder();
				// 根据自定义的Item布局加载布局
				convertView = mInflater.inflate(R.layout.listview_item, null);
				holder.img = (ImageView) convertView.findViewById(R.id.tupian);
				holder.kuaidigongsi = (TextView) convertView
						.findViewById(R.id.kuaidiname);
				holder.songhuodizhi = (TextView) convertView
						.findViewById(R.id.saddress);
				holder.time = (TextView) convertView
						.findViewById(R.id.sendtime);
				holder.schoolName=(TextView) convertView.findViewById(R.id.schoolname);
				// 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.img.setBackgroundResource((Integer) mList.get(position)
					.getTupianId());
			holder.kuaidigongsi.setText((String) mList.get(position)
					.getKuaidiName());
			holder.time.setText((String) mList.get(position).getTime());
			holder.songhuodizhi.setText((String) mList.get(position)
					.getsAddress());
			holder.schoolName.setText((String) mList.get(position)
					.getSchoolName());
			return convertView;
		}
	}
}
