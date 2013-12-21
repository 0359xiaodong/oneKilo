package com.morningtel.onekilo.sign;

import java.util.HashMap;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.morningtel.onekilo.BaseActivity;
import com.morningtel.onekilo.OneKiloApplication;
import com.morningtel.onekilo.R;
import com.morningtel.onekilo.common.CommonUtils;
import com.morningtel.onekilo.model.JsonParse;

public class SignActivity extends BaseActivity {
	
	TextView geo_record_state=null;
	TextView geo_record_button=null;
	TextView geo_stop_button=null;
	
	OneKiloApplication app=null;
	
	private LocationClient mLocClient;
	public MyLocationListenner myListener=null;
	
	//是否已经完成定位
	boolean isGPSing=false;
	
	public class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int arg0) {
			// TODO Auto-generated method stub
			showCustomToast("网络连接失败，请检查您的网络连接是否通畅");
		}

		@Override
		public void onGetPermissionState(int arg0) {
			// TODO Auto-generated method stub
			if(arg0!=0) {
				showCustomToast("地图引擎未获取到足够的权限");
			}			
		}
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		app=(OneKiloApplication) getApplication();
        if (app.mBMapManager==null) {
            app.mBMapManager=new BMapManager(this);
            app.mBMapManager.init(((OneKiloApplication) getApplication()).strKey, new MyGeneralListener());
        }
        
		setContentView(R.layout.activity_sign);
		
		init();
	}
	
	public void init() {
		
		myListener=new MyLocationListenner();
		mLocClient=new LocationClient(getApplicationContext());
        mLocClient.setAK(((OneKiloApplication) getApplication()).strKey);
        mLocClient.registerLocationListener(myListener);
        
		geo_record_state=(TextView) findViewById(R.id.geo_record_state);
		geo_record_state.setText(getIntent().getExtras().getString("hotName"));
		geo_record_button=(TextView) findViewById(R.id.geo_record_button);
		geo_record_button.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isGPSing=false;
				geo_record_state.setText(getIntent().getExtras().getString("hotName"));
				geo_record_button.setVisibility(View.GONE);
				setLocationOption();
			}});
		geo_stop_button=(TextView) findViewById(R.id.geo_stop_button);
		geo_stop_button.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}});
		
		setLocationOption();
	}
	
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			if(!isGPSing) {
				isGPSing=true;
				if(location==null) {
					geo_record_state.setText("签到失败");
					geo_record_button.setVisibility(View.VISIBLE);
					return;
				}
				signUp(""+location.getLongitude()+","+location.getLatitude());
			}
		}

		@Override
		public void onReceivePoi(BDLocation arg0) {
			// TODO Auto-generated method stub
			if(arg0==null) {
				return;
			}
		}
		
	}
	
	/**
	 * 开启定位服务
	 */
	private void setLocationOption() {
		LocationClientOption option=new LocationClientOption();
		option.setOpenGps(true);
		option.setCoorType("bd09ll");
		option.setServiceName("com.baidu.location.service_v2.9");
		option.setPoiExtraInfo(true);
		option.setAddrType("all");
		option.setScanSpan(24*60*60*1000);
		option.setPriority(LocationClientOption.NetWorkFirst);
		option.setPoiNumber(30);
		option.disableCache(true);		
		mLocClient.setLocOption(option);		
		
		mLocClient.start();
		mLocClient.requestLocation();
	}
	
	/**
	 * 上传签到
	 * @param value
	 */
	private void signUp(final String value) {
		final Handler handler=new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if(JsonParse.isSignOK(msg.obj.toString(), SignActivity.this)) {
					showCustomToast("签到成功");
					finish();
				}
				else {
					geo_record_state.setText("签到失败");
					geo_record_button.setVisibility(View.VISIBLE);
				}
			}
		};
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message m=new Message();
				HashMap<String, String> map=new HashMap<String, String>();
				map.put("token", CommonUtils.getLoginUser(SignActivity.this).getToken());
				map.put("geo", value);
				String result=CommonUtils.getWebData(map, getIntent().getExtras().getString("api"));
				m.obj=result;
				handler.sendMessage(m);
			}}).start();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mLocClient.stop();
		super.onDestroy();
	}
	
}
