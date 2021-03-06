package com.morningtel.onekilo.community;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.lidroid.xutils.BitmapUtils;
import com.morningtel.onekilo.BaseActivity;
import com.morningtel.onekilo.OneKiloApplication;
import com.morningtel.onekilo.R;
import com.morningtel.onekilo.bundle.LauncherActivity;
import com.morningtel.onekilo.common.BitmapHelp;
import com.morningtel.onekilo.common.CommonUtils;
import com.morningtel.onekilo.common.Conn;
import com.morningtel.onekilo.hot.WebInfoActivity;
import com.morningtel.onekilo.hot.WebInfoTabActivity;
import com.morningtel.onekilo.model.Group;
import com.morningtel.onekilo.model.Hot;
import com.morningtel.onekilo.model.JsonParse;
import com.morningtel.onekilo.sign.SignActivity;

public class CommunityActivity extends BaseActivity {
	
	TextView nav_title=null;
	
	PullToRefreshListView community_listview=null;
	ListView actualListView=null;
	CommunityAdapter adapter=null;
	
	ArrayList<Group> group_list=null;
	
	public static BitmapUtils bitmapUtils;
	
	boolean isLoad=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_community);
		
		group_list=new ArrayList<Group>();
		
		bitmapUtils=BitmapHelp.getBitmapUtils(CommunityActivity.this);
        bitmapUtils.configDefaultLoadingImage(R.drawable.ic_launcher);
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_launcher);
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		
		init();
	}
	
	public void init() {
		nav_title=(TextView) findViewById(R.id.nav_title);
		nav_title.setText("圈子");
		nav_title.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub		
				if(!isLoad) {
					community_listview.setStart();
					community_listview.setRefreshing(false);
				}
			}});
		
		community_listview=(PullToRefreshListView) findViewById(R.id.community_listview);
		adapter=new CommunityAdapter(group_list, CommunityActivity.this);
		if(Conn.getInstance(getApplicationContext()).getGroupModelList().size()>0) {
			group_list.addAll(Conn.getInstance(getApplicationContext()).getGroupModelList());
		}
		community_listview.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				// Do work to refresh the list here.
				if(!isLoad) {
					getCommunity();
					isLoad=true;
				}
				else {
					CommonUtils.showCustomToast(CommunityActivity.this, "正在加载中，请稍后");
				}
			}});
		community_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				int position_=arg2-1;
				if(group_list.get(position_).getViewType()==Hot.CODE_VIEWTYPE&&android.os.Build.VERSION.SDK_INT<15) {
					CommonUtils.showCustomToast(CommunityActivity.this, "您的系统版本过低，暂时不支持"+group_list.get(position_).getTabs().get(0).getName()+"功能");
					return ;
				}
				Intent intent=null;
				Bundle bundle=new Bundle();
				switch(group_list.get(position_).getViewType()) {
				case Hot.WEBVIEW_VIEWTYPE:
					intent=new Intent(CommunityActivity.this, WebInfoActivity.class);
					bundle.putInt("tabId", group_list.get(position_).getTabs().get(0).getId());
					bundle.putString("hotName", group_list.get(position_).getName());
					bundle.putInt("needBar", group_list.get(position_).getTabs().get(0).getNeedBar());
					bundle.putString("api", group_list.get(position_).getTabs().get(0).getApi());
					bundle.putSerializable("menu", group_list.get(position_).getMenus());	
					bundle.putString("groupId", group_list.get(position_).getId());
					bundle.putInt("btnType", group_list.get(position_).getBtnType());
					break;
				case Hot.TABWEBVIEW_VIEWTYPE:
					intent=new Intent(CommunityActivity.this, WebInfoTabActivity.class);
					bundle.putSerializable("tabs", group_list.get(position_).getTabs());
					bundle.putString("hotName", group_list.get(position_).getName());
					bundle.putSerializable("menu", group_list.get(position_).getMenus());
					bundle.putString("groupId", group_list.get(position_).getId());
					bundle.putInt("btnType", group_list.get(position_).getBtnType());
					break;
				case Hot.LOCATION_VIEWTYPE:
					intent=new Intent(CommunityActivity.this, SignActivity.class);
					bundle.putString("api", group_list.get(position_).getTabs().get(0).getApi());
					bundle.putString("hotName", group_list.get(position_).getTabs().get(0).getName());
					break;
				case Hot.VOICE_VIEWTYPE:
					break;
				case Hot.CODE_VIEWTYPE:
					intent=new Intent(CommunityActivity.this, LauncherActivity.class);
					bundle.putString("api", group_list.get(position_).getTabs().get(0).getApi());
					bundle.putString("hotName", group_list.get(position_).getTabs().get(0).getName());
					break;
				}
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		actualListView=community_listview.getRefreshableView();
		actualListView.setAdapter(adapter);
		// Need to use the Actual ListView when registering for Context Menu
		registerForContextMenu(actualListView);
		
		doRefresh();
	}
	
	/**
	 * 页面需要加载完毕之后才能显示headerview
	 */
	public void doRefresh() {
		final Handler handler=new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				nav_title.performClick();
			}
		};
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message m=new Message();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				handler.sendMessage(m);
			}}).start();
	}
	
	public void getCommunity() {
		final Handler handler=new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if(msg.obj==null) {
					CommonUtils.showCustomToast(CommunityActivity.this, "网络异常，请稍后再试");
				}
				else {
					String str=msg.obj.toString();
					if(CommonUtils.convertNull(str).equals("")) {
						CommonUtils.showCustomToast(CommunityActivity.this, "网络异常，请稍后再试");
					}
					else {
						ArrayList<Group> group_list_temp=JsonParse.getCommunicateList(str, CommunityActivity.this);
						if(group_list_temp==null) {
							CommonUtils.showCustomToast(CommunityActivity.this, "数据解析异常，请稍后再试");
						}
						else {
							group_list.clear();
							group_list.addAll(group_list_temp);
							adapter.notifyDataSetChanged();
						}					
					}
				}
				community_listview.onRefreshComplete();
				isLoad=false;
			}
		};
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message m=new Message();
				HashMap<String, String> map=new HashMap<String, String>();
				map.put("token", CommonUtils.getLoginUser(CommunityActivity.this).getToken());
				map.put("updateTime", CommonUtils.getCommunicateUpdateTime(CommunityActivity.this));
				String result=CommonUtils.getWebData(map, ((OneKiloApplication) getApplicationContext()).webUrl+"group_doSearchByUser.do");
				m.obj=result;
				handler.sendMessage(m);
			}}).start();
	}
	
	public void onResume() {
		super.onResume();
        bitmapUtils.configDefaultLoadingImage(R.drawable.ic_launcher);
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_launcher);
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		StatService.onResume(this);
	}

	public void onPause() {
		super.onPause();
		StatService.onPause(this);
	}

}
