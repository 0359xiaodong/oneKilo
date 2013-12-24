package com.morningtel.onekilo.localService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.lidroid.xutils.BitmapUtils;
import com.morningtel.onekilo.BaseActivity;
import com.morningtel.onekilo.OneKiloApplication;
import com.morningtel.onekilo.R;
import com.morningtel.onekilo.common.BitmapHelp;
import com.morningtel.onekilo.common.CommonUtils;
import com.morningtel.onekilo.hot.WebInfoActivity;
import com.morningtel.onekilo.hot.WebInfoTabActivity;
import com.morningtel.onekilo.model.Hot;
import com.morningtel.onekilo.model.JsonParse;
import com.morningtel.onekilo.sign.SignActivity;
import com.morningtel.onekilo.voice.VoiceSignActivity;

public class LocalServiceActivity extends BaseActivity {
	
	TextView nav_title=null;
	LinearLayout service_layout=null;
	
	//服务列表
	ArrayList<Hot> hot_list=null;
	
	public static BitmapUtils bitmapUtils_ser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_localservice);
		
		bitmapUtils_ser=BitmapHelp.getBitmapUtils(getApplicationContext());
		bitmapUtils_ser.configDefaultLoadingImage(R.drawable.ser_loading);
		bitmapUtils_ser.configDefaultLoadFailedImage(R.drawable.ser_loading);
		bitmapUtils_ser.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		
		hot_list=new ArrayList<Hot>();
		
		init();
	}
	
	public void init() {
		
		nav_title=(TextView) findViewById(R.id.nav_title);
		nav_title.setText("服务");
		
		service_layout=(LinearLayout) findViewById(R.id.service_layout);
		
		getLocalService();
	}
	
	/**
	 * 获取本地服务列表
	 */
	public void getLocalService() {
		final Handler handler=new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if(msg.obj==null) {
					showCustomToast("网络异常，请稍后再试");
					return;
				}
				String str=msg.obj.toString();
				if(CommonUtils.convertNull(str).equals("")) {
					showCustomToast("网络异常，请稍后再试");
				}
				else {
					ArrayList<Hot> hot_list_temp=JsonParse.getServiceList(str, LocalServiceActivity.this);
					if(hot_list_temp==null) {
						showCustomToast("数据解析异常，请稍后再试");
					}
					else {
						hot_list.addAll(hot_list_temp);
						addServiceLayoutView();
						setJpushInfo();
					}					
				}
			}
		};
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message m=new Message();
				HashMap<String, String> map=new HashMap<String, String>();
				map.put("token", CommonUtils.getLoginUser(LocalServiceActivity.this).getToken());
				String result=CommonUtils.getWebData(map, ((OneKiloApplication) getApplicationContext()).webUrl+"hot_searchMyHots.do");
				m.obj=result;
				handler.sendMessage(m);
			}}).start();
	}
	
	/**
	 * 根据加载总数显示滑动层布局
	 */
	public void addServiceLayoutView() {
		int totalNum=hot_list.size();
		int line=totalNum%3==0?totalNum/3:totalNum/3+1;
		for(int i=0;i<line;i++) {
			View view_line=LayoutInflater.from(this).inflate(R.layout.localservice_chatlayoutline, null);
			LinearLayout chat_layout_line=(LinearLayout) view_line.findViewById(R.id.chat_layout_line);
			int row=3;
			if(i==(line-1)) {
				row=totalNum-3*(line-1);
			}
			for(int j=0;j<3;j++) {
				View view_row=LayoutInflater.from(this).inflate(R.layout.localservice_chatlayoutrow, null);
				LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				params.weight=1;
				if(j>=row) {
					view_row.setVisibility(View.INVISIBLE);
					chat_layout_line.addView(view_row, params);
					continue;
				}
				ImageView chat_layout_item_image=(ImageView) view_row.findViewById(R.id.chat_layout_item_image);
				bitmapUtils_ser.display(chat_layout_item_image, hot_list.get(i*3+j).getImageUrl());
				final int position_=i*3+j;
				chat_layout_item_image.setOnClickListener(new ImageView.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						Intent intent=null;
						Bundle bundle=new Bundle();
						switch(hot_list.get(position_).getViewType()) {
						case Hot.WEBVIEW_VIEWTYPE:
							intent=new Intent(LocalServiceActivity.this, WebInfoActivity.class);
							bundle.putInt("tabId", hot_list.get(position_).getTabs().get(0).getId());
							bundle.putString("hotName", hot_list.get(position_).getHotName());
							bundle.putInt("needBar", hot_list.get(position_).getTabs().get(0).getNeedBar());
							bundle.putString("api", hot_list.get(position_).getTabs().get(0).getApi());
							bundle.putInt("btnType", hot_list.get(position_).getBtnType());
							bundle.putParcelableArrayList("menu", hot_list.get(position_).getMenus());
							break;
						case Hot.TABWEBVIEW_VIEWTYPE:
							intent=new Intent(LocalServiceActivity.this, WebInfoTabActivity.class);
							bundle.putParcelableArrayList("tabs", hot_list.get(position_).getTabs());
							bundle.putString("hotName", hot_list.get(position_).getHotName());
							bundle.putParcelableArrayList("menu", hot_list.get(position_).getMenus());
							bundle.putInt("btnType", hot_list.get(position_).getBtnType());
							break;
						case Hot.LOCATION_VIEWTYPE:
							intent=new Intent(LocalServiceActivity.this, SignActivity.class);
							bundle.putString("api", hot_list.get(position_).getTabs().get(0).getApi());
							bundle.putString("hotName", hot_list.get(position_).getTabs().get(0).getName());
							break;
						case Hot.VOICE_VIEWTYPE:
							break;
						case Hot.CODE_VIEWTYPE:
							break;
						}
						intent.putExtras(bundle);
						startActivity(intent);
					}});
				chat_layout_item_image.setOnLongClickListener(new OnLongClickListener() {
					
					@Override
					public boolean onLongClick(View v) {
						// TODO Auto-generated method stub
						if(hot_list.get(position_).getLongPressTab()!=null) {
							switch(hot_list.get(position_).getLongPressTab().getViewType()) {
							case Hot.VOICE_LONGPRESSTYPE:
								Intent intent=new Intent(LocalServiceActivity.this, VoiceSignActivity.class);
								Bundle bundle=new Bundle();
								bundle.putString("api", hot_list.get(position_).getLongPressTab().getApi());
								intent.putExtras(bundle);
								startActivity(intent);
								break;
							}
						}
						return false;
					}
				});
				TextView chat_layout_item_text=(TextView) view_row.findViewById(R.id.chat_layout_item_text);
				String temp_name=hot_list.get(i*3+j).getHotName();
				chat_layout_item_text.setText(temp_name);
				ImageView chat_new_notify=(ImageView) view_row.findViewById(R.id.chat_new_notify);
				chat_new_notify.setVisibility(View.GONE);
				chat_layout_line.addView(view_row, params);
			}
			service_layout.addView(view_line);
		}
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
	}
}
