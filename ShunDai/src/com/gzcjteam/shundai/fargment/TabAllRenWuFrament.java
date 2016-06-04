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
import com.gzcjteam.shundai.bean.NetCallBack;
import com.gzcjteam.shundai.utils.OnSureClickListener;
import com.gzcjteam.shundai.utils.RenWu_Adapter;
import com.gzcjteam.shundai.utils.RequestUtils;
import com.gzcjteam.shundai.utils.ToastUtil;
import com.gzcjteam.shundai.utils.getUserInfo;
import com.gzcjteam.shundai.weight.MainMyRenwuTopBar;
import com.gzcjteam.shundai.weight.MainTopBar;
import com.gzcjteam.shundai.weight.PullUpDialog;
import com.gzcjteam.shundai.weight.RefreshableView;
import com.gzcjteam.shundai.weight.ShaiXuanDialog;
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
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
	protected static final int REFASHWANBI = 5;
	MainActivity main = (MainActivity) getActivity();
	private JSONObject allJsondata;
	private int stop_position; // 记录滚动停止的位置
	Boolean isSuccess = false;
	private boolean isLastRow = false;// 判断是不是最后一行
	private int dangQianPage = 1;
	private int dangQianShaiXuan = 1;
	private MainTopBar mTopBar;
	private Boolean isQingKong = false;
	private static String[] school_spindata = { "全部学校","贵州财经大学花溪校区", "贵州师范大学花溪校区",
		"贵州医科大学花溪校区", "贵州城市学院", "贵州轻工业职业学校", "贵州民族大学花溪校区" };
private static String[] kuaidi_spindata = { "全部快递","韵达快递", "申通快递", "顺丰快递", "EMS",
		"圆通快递", "中通快递", "百世汇通", "京东" };
	private Spinner school_spinner;
	private Spinner kuaidi_spinner;
	private ArrayAdapter<String> school_adapter;
	private ArrayAdapter<String> kuaidi_adapter;
	private View view;
	private String SCHOOLCODE = "";
	private String KUAIDICODE = "";
	private String SCHOOLCODE1 = "";
	private String KUAIDICODE2 = "";
	RenWuInfo info;

	private Boolean isDiaLogRefash = false;

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
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
		view = inflater.inflate(R.layout.taball_frament, container, false);
		mTopBar = (MainTopBar) getActivity().findViewById(
				R.id.activity_main_top_bar);
		mTopBar.setOnBackListener(this);
		mTopBar.setBackVisibility(true);
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
					if (isDiaLogRefash) {
						dangQianPage = 2;
						isDiaLogRefash = false;
						if (requestPullUpData(SCHOOLCODE, KUAIDICODE,
								dangQianShaiXuan)) {
							dia.dismiss();
						} else {
							dia.dismiss();
						}
					} else {
						if (requestPullUpData(SCHOOLCODE, KUAIDICODE,
								dangQianPage)) {
							dia.dismiss();
						} else {
							dia.dismiss();
						}
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
				info = renwu.get(position);
				TiShiDialog dia = new TiShiDialog(getActivity(),
						R.style.tishidialog, info.getKuaidiName(), info
								.getsAddress(), info.getId(),
						new OnSureClickListener() {

							@Override
							public void tiaoZhuanInfo(String renwu_id) {
								// 领取成功后进入任务详情界面
								Intent intent = new Intent();
								intent.setClass(getActivity(),
										RenWuInfoActivity.class);
								intent.putExtra("task_id", info.getId());
								startActivity(intent);
							}

							@Override
							public void refalshData(String sCode,
									String kuaiCode, Boolean qingKong) {

							}
						});
				dia.show();
			}
		});
		refreshableView = (RefreshableView) view
				.findViewById(R.id.refreshable_view);
		refreshableView.setOnRefreshListener(new PullToRefreshListener() {
			@Override
			public void onRefresh() {
				System.out.println(SCHOOLCODE + "" + KUAIDICODE);
				if (isDiaLogRefash) {
					dangQianPage = 2;
					isDiaLogRefash = false;
					requestData(SCHOOLCODE, KUAIDICODE, dangQianShaiXuan);
				} else {
					requestData(SCHOOLCODE, KUAIDICODE, dangQianPage);
				}

			}
		}, 0);

	}

	/**
	 * 弹出对话框
	 */
	public void tanChuDialog() {
		school_adapter = new ArrayAdapter<String>(getActivity(),
				R.layout.spinner_item_layout, school_spindata);
		kuaidi_adapter = new ArrayAdapter<String>(getActivity(),
				R.layout.spinner_item_layout, kuaidi_spindata);
		// 创建一个AlertDialog对话框
		ShaiXuanDialog dia = new ShaiXuanDialog(getActivity(), R.style.adialog,
				school_adapter, kuaidi_adapter, new OnSureClickListener() {

					@Override
					public void refalshData(String sCode, String kuaiCode,
							Boolean qingKong) {
						// 在这里筛选刷新数据
						// refreshableView.fRefreshing();
						SCHOOLCODE1 = sCode;
						KUAIDICODE2 = kuaiCode;
						isQingKong = true;
						isDiaLogRefash = true;
						requestData(sCode, kuaiCode, 1);
					}

					@Override
					public void tiaoZhuanInfo(String renwu_id) {

					}
				});
		dia.show();
	}

	@Override
	public void onClick(View v) {
		if (v == mTopBar.getBackView()) {
			// 初始化spinner
			tanChuDialog();
		}

	}

	private void initEvent() {
		btnSignIn.setOnClickListener(this);
		btnSignUp.setOnClickListener(this);
	}

	/**
	 * @return 上拉加载更多数据
	 * 
	 */
	public Boolean requestPullUpData(String schoolCode, String kuaidiCode,
			int page) {
		String url = "http://119.29.140.85/index.php/task/task_list";
		RequestParams params = new RequestParams();
		if (!schoolCode.isEmpty()) {
			System.out.println(schoolCode.isEmpty());
			params.put("school_code", schoolCode);
		}
		if (!kuaidiCode.isEmpty()) {
			params.put("express_code", kuaidiCode);
		}
		params.put("status", "0");
		params.put("page", page + "");
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
						allJsondata = json;
						if (jsonarray.length() > 0) {
							Message msg = new Message();
							msg.what = PULLREFASHSUCCESS;
							handler.sendMessage(msg);
							SCHOOLCODE = SCHOOLCODE1;
							KUAIDICODE = KUAIDICODE2;
							if (!isQingKong) {
								dangQianPage++;
								dangQianShaiXuan++;
								changeListData();
							} else {
								isQingKong = false;
								dangQianPage = 2;
								dangQianShaiXuan = 2;
								shaixuanListData();
							}
							isSuccess = true;
						} else {
							ToastUtil.show(getActivity(), "数据全部加载完毕！");
						}
					} else {
						Message msg = new Message();
						msg.what = PULLREFASHFAILED;
						handler.sendMessage(msg);
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
			}
		});

		return isSuccess;
	}

	/**
	 * 请求全部任务的数据方法
	 */
	public Boolean requestData(String schoolCode, String kuaidiCode, int page) {
		String url = "http://119.29.140.85/index.php/task/task_list";
		RequestParams params = new RequestParams();
		if (!schoolCode.isEmpty()) {
			System.out.println(schoolCode);
			params.put("school_code", schoolCode);
		}
		if (!kuaidiCode.isEmpty()) {
			System.out.println(kuaidiCode);
			params.put("express_code", kuaidiCode);
		}
		System.out.println("当前页：" + dangQianPage);
		System.out.println("当前页：" + dangQianShaiXuan);
		params.put("status", "0");
		params.put("page", page + "");
		System.out.println("传入的page" + page);
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
						allJsondata = json;
						refreshableView.finishRefreshing();
						if (jsonarray.length() > 0) {
							Message msg = new Message();
							msg.what = REFASHSUCCESS;
							handler.sendMessage(msg);
							SCHOOLCODE = SCHOOLCODE1;
							KUAIDICODE = KUAIDICODE2;
							if (!isQingKong) {
								dangQianPage++;
								dangQianShaiXuan++;
								changeListData();
							} else {
								isQingKong = false;
								dangQianShaiXuan = 2;
								shaixuanListData();
							}
							isSuccess = true;
						} else {
							if (isDiaLogRefash) {
								shaixuanListData();
								Message msg = new Message();
								msg.what = REFASHWANBI;
								handler.sendMessage(msg);
							}
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

	/**
	 * 筛选更新数据的方法
	 */
	public void shaixuanListData() {
		renwu.clear();
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
					infodata.setSchoolName(js.getString("school_name"));
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
	 * 普通更新数据方法
	 */
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
					infodata.setSchoolName(js.getString("school_name"));
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

	// ViewHolder静态类
	static class ViewHolder {
		public ImageView img;
		public TextView kuaidigongsi;
		public TextView songhuodizhi;
		public TextView time;
		public TextView schoolName;
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
				holder.schoolName = (TextView) convertView
						.findViewById(R.id.schoolname);
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
