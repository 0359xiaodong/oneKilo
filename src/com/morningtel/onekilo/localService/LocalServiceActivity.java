package com.morningtel.onekilo.localService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.widget.GridView;
import android.widget.TextView;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.baidu.mobstat.StatService;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.lidroid.xutils.BitmapUtils;
import com.morningtel.onekilo.BaseActivity;
import com.morningtel.onekilo.OneKiloApplication;
import com.morningtel.onekilo.R;
import com.morningtel.onekilo.common.BitmapHelp;
import com.morningtel.onekilo.common.CommonUtils;
import com.morningtel.onekilo.common.Conn;
import com.morningtel.onekilo.model.Hot;
import com.morningtel.onekilo.model.JsonParse;
import com.tencent.android.tpush.XGPushManager;

public class LocalServiceActivity extends BaseActivity {
	
	TextView nav_title=null;
	PullToRefreshGridView service_grid=null;
	GridView source_grid=null;
	LocalServiceAdapter adapter=null;
	
	//服务列表
	ArrayList<Hot> hot_list=null;
	//是否现在正在加载
	boolean isAleradyLoad=false;
	
	public static BitmapUtils bitmapUtils_ser;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_localservice);
		
		bitmapUtils_ser=BitmapHelp.getBitmapUtils(LocalServiceActivity.this);
		bitmapUtils_ser.configDefaultLoadingImage(R.drawable.ser_loading);
		bitmapUtils_ser.configDefaultLoadFailedImage(R.drawable.ser_loading);
		bitmapUtils_ser.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		
		hot_list=new ArrayList<Hot>();
		
		init();
	}
	
	public void init() {
		
		nav_title=(TextView) findViewById(R.id.nav_title);
		nav_title.setText("服务");
		
		service_grid=(PullToRefreshGridView) findViewById(R.id.service_grid_);
		service_grid.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
		source_grid=service_grid.getRefreshableView();
		service_grid.setOnRefreshListener(new OnRefreshListener<GridView>() {

			@Override
			public void onRefresh(PullToRefreshBase<GridView> refreshView) {
				// TODO Auto-generated method stub
				if(!isAleradyLoad) {
					getLocalService();
				}
			}
		});
		adapter=new LocalServiceAdapter(hot_list, LocalServiceActivity.this);
		source_grid.setAdapter(adapter);
		
		if(Conn.getInstance(LocalServiceActivity.this).getHotModelList().size()>0) {
			hot_list.addAll(Conn.getInstance(LocalServiceActivity.this).getHotModelList());
			adapter.notifyDataSetChanged();
		}
		getLocalService();
	}
	
	/**
	 * 获取本地服务列表
	 */
	public void getLocalService() {
		isAleradyLoad=true;
		final Handler handler=new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if(msg.obj==null) {
					CommonUtils.showCustomToast(LocalServiceActivity.this, "网络异常，请稍后再试");
					return;
				}
				String str=msg.obj.toString();
				if(CommonUtils.convertNull(str).equals("")) {
					CommonUtils.showCustomToast(LocalServiceActivity.this, "网络异常，请稍后再试");
				}
				else {
					ArrayList<Hot> hot_list_temp=JsonParse.getServiceList(str, LocalServiceActivity.this);
					if(hot_list_temp==null) {
						CommonUtils.showCustomToast(LocalServiceActivity.this, "数据解析异常，请稍后再试");
					}
					else {
						hot_list.clear();						
						hot_list.addAll(hot_list_temp);
						setJpushInfo();
						
						adapter.notifyDataSetChanged();
						service_grid.onRefreshComplete();
					}					
				}
				isAleradyLoad=false;
			}
		};
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message m=new Message();
				HashMap<String, String> map=new HashMap<String, String>();
				map.put("token", CommonUtils.getLoginUser(LocalServiceActivity.this).getToken());
				map.put("updateTime", CommonUtils.getHotUpdateTime(LocalServiceActivity.this));	
				String result=CommonUtils.getWebData(map, ((OneKiloApplication) getApplicationContext()).webUrl+"hot_searchMyHots.do");
				m.obj=result;
				handler.sendMessage(m);
			}}).start();
	}
	
	public void setJpushInfo() {
		String tag="";
		for(int i=0;i<hot_list.size();i++) {
			tag+=hot_list.get(i).getId()+",";
		}
		tag=tag.substring(0, tag.length()-1);
		String[] sArray=tag.split(",");
		Set<String> tagSet=new LinkedHashSet<String>();
		for(String sTagItme:sArray) {
			tagSet.add(sTagItme);
		}
		//调用JPush API设置Tag
		JPushInterface.setAliasAndTags(getApplicationContext(), ((OneKiloApplication) getApplication()).user.getId(), tagSet, new TagAliasCallback() {

			@Override
			public void gotResult(int arg0, String arg1, Set<String> arg2) {
				// TODO Auto-generated method stub
				System.out.println("极光推送返回"+arg0);
			}});
		XGPushManager.setTag(getApplicationContext(), ((OneKiloApplication) getApplication()).user.getId());
	}
	
	public void onResume() {
		super.onResume();
		bitmapUtils_ser.configDefaultLoadingImage(R.drawable.ser_loading);
		bitmapUtils_ser.configDefaultLoadFailedImage(R.drawable.ser_loading);
		bitmapUtils_ser.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		StatService.onResume(this);
	}

	public void onPause() {
		super.onPause();
		StatService.onPause(this);
	}
}
