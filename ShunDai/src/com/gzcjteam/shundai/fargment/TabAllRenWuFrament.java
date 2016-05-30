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
import com.gzcjteam.shundai.bean.NetCallBack;
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
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class TabAllRenWuFrament extends Fragment implements OnClickListener {

	public final static String ARG_KEY = "ARG";

	public final static int ARG_TYPE_FIRST = 0;
	public final static int ARG_TYPE_LOGINED = 1;
	public int currentFlag = 0;
	private Button btnSignUp;
	private Button btnSignIn;
	private ListView listview;
	public List<RenWuInfo> renwu = new ArrayList<RenWuInfo>();
	RenWu_Adapter1 renwu_adapter;
	RefreshableView refreshableView;
	private final static int REFASHFAILED = 1;
	private final static int REFASHSUCCESS = 2;
	private LinearLayout allrenwu;
	private static final int CHANGEDATA = 0;
	protected static final int PULLREFASHSUCCESS = 3;
	protected static final int PULLREFASHFAILED = 4;
	MainActivity main = (MainActivity) getActivity();
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
		View view = inflater.inflate(R.layout.taball_frament, container, false);
		allrenwu = (LinearLayout) view.findViewById(R.id.allrenwu);
		initView(view);
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
					System.out.println("dfdsgdsg " + stop_position);

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
				RenWuInfo info = renwu.get(position);
				ToastUtil.show(getActivity(), info.getId());
				TiShiDialog dia = new TiShiDialog(getActivity(),
						R.style.tishidialog, info.getKuaidiName(), info
								.getsAddress(), info.getId());
				dia.show();
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

	private void initEvent() {
		btnSignIn.setOnClickListener(this);
		btnSignUp.setOnClickListener(this);
	}

	/**
	 * @return 上拉加载更多数据
	 * 
	 */
	public Boolean requestPullUpData() {
		String url = "http://119.29.140.85/index.php/task/task_list";
		RequestParams params = new RequestParams();
		params.put("status", "0");
		params.put("page", dangQianPage+"");
		params.put("page_size","8");
		RequestUtils.ClientPost(url, params, new NetCallBack() {
			@Override
			public void onMySuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					Boolean status = json.getBoolean("status");
					String info = json.getString("info");
					JSONObject data;
					if (status) {
						Message msg = new Message();
						msg.what = PULLREFASHSUCCESS;
						handler.sendMessage(msg);
						refreshableView.finishRefreshing();
						allJsondata = json;
						dangQianPage++;
						changeListData();
						isSuccess = true;
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
		String url = "http://119.29.140.85/index.php/task/task_list";
		RequestParams params = new RequestParams();
		params.put("status", "0");
		params.put("page", dangQianPage+"");
		params.put("page_size","8");
		RequestUtils.ClientPost(url, params, new NetCallBack() {
			@Override
			public void onMySuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					Boolean status = json.getBoolean("status");
					String info = json.getString("info");
					JSONObject data;
					if (status) {
						Message msg = new Message();
						msg.what = REFASHSUCCESS;
						handler.sendMessage(msg);
						refreshableView.finishRefreshing();
						allJsondata = json;
						dangQianPage++;
						changeListData();
						isSuccess = true;
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
					infodata.setTime(js.getString("launch_time"));
					infodata.setKuaidiName(js.getString("express_name"));
					infodata.setsAddress(js.getString("receive_address"));
					infodata.setId(js.getString("id"));
					System.out.println("id为：" + js.getString("id"));

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

	/**
	 * 领取任务方法
	 */
	public void receiveRenWu(String id) {
		String url = "http://119.29.140.85/index.php/task/start_task";
		RequestParams params = new RequestParams();
		params.put("task_id", id);
		params.put("complete_user_id", getUserInfo.getInstance().getId());
		RequestUtils.ClientPost(url, null, new NetCallBack() {
			@Override
			public void onMySuccess(String result) {
				try {
					JSONObject json = new JSONObject(result);
					Boolean status = json.getBoolean("status");
					String info = json.getString("info");
					JSONObject data;
					if (status) {
						Message msg = new Message();
						msg.what = PULLREFASHSUCCESS;
						handler.sendMessage(msg);
						// refreshableView.finishRefreshing();
						allJsondata = json;
					} else {
						Message msg = new Message();
						msg.what = PULLREFASHFAILED;
						handler.sendMessage(msg);
						// refreshableView.finishRefreshing();
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

	}

	// ViewHolder静态类
	static class ViewHolder {
		public ImageView img;
		public TextView kuaidigongsi;
		public TextView songhuodizhi;
		public TextView time;
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
			return convertView;
		}

	}

}
