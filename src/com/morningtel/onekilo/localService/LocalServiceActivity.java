package com.morningtel.onekilo.localService;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class LocalServiceActivity extends BaseActivity {
	
	ImageView nav_back=null;
	TextView nav_title=null;
	ImageView nav_add=null;
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
		
		bitmapUtils_ser = BitmapHelp.getBitmapUtils(getApplicationContext());
		bitmapUtils_ser.configDefaultLoadingImage(R.drawable.ser_loading);
		bitmapUtils_ser.configDefaultLoadFailedImage(R.drawable.ser_loading);
		bitmapUtils_ser.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		
		hot_list=new ArrayList<Hot>();
		
		init();
	}
	
	public void init() {
		
		nav_back=(ImageView) findViewById(R.id.nav_back);
		nav_back.setOnClickListener(new ImageView.OnClickListener() {
	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}});
		nav_title=(TextView) findViewById(R.id.nav_title);
		nav_title.setText("服务");
		nav_add=(ImageView) findViewById(R.id.nav_add);
		nav_add.setOnClickListener(new ImageView.OnClickListener() {
	
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}});
		
		service_layout=(LinearLayout) findViewById(R.id.service_layout);
		
		getLocalService();
	}
	
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
						if(hot_list.get(position_).getTabs().size()==1) {
							intent=new Intent(LocalServiceActivity.this, WebInfoActivity.class);
							bundle.putInt("tabId", hot_list.get(position_).getTabs().get(0).getId());
							bundle.putString("hotName", hot_list.get(position_).getHotName());
							bundle.putInt("needBar", hot_list.get(position_).getTabs().get(0).getNeedBar());
							bundle.putString("api", hot_list.get(position_).getTabs().get(0).getApi());
							bundle.putParcelableArrayList("menu", hot_list.get(position_).getMenus());							
						}
						else if(hot_list.get(position_).getTabs().size()==2) {
							intent=new Intent(LocalServiceActivity.this, WebInfoTabActivity.class);
							for(int k=0;k<2;k++) {
								System.out.println(hot_list.get(position_).getTabs().get(k).getName());
							}
							bundle.putParcelableArrayList("tabs", hot_list.get(position_).getTabs());
							bundle.putString("hotName", hot_list.get(position_).getHotName());
							bundle.putParcelableArrayList("menu", hot_list.get(position_).getMenus());
						}
						intent.putExtras(bundle);
						startActivity(intent);
					}});
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
}
