package com.morningtel.onekilo;

import java.io.File;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.morningtel.onekilo.model.User;

import android.app.Application;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

public class OneKiloApplication extends Application {
	
	public String webUrl="http://yiguanjia.duapp.com/";
	public String strKey="HaajpyDaUMB7EryGVRuu7bYz";
	public String pushIconUrl="http://bcs.duapp.com/";
	public boolean isAppOpen=false;
	public static final String refreshWebInfoAction="refreshWebInfoAction";
	//通知栏notify序号
	public int no_num=0;
	
	public BMapManager mBMapManager=null;
	
	public User user=null;
	
	public ImageView newMessage=null;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			File file=new File(Environment.getExternalStorageDirectory().getPath()+"/onekilo/temp");
			if(!file.exists()) {
				file.mkdirs();
			}
			File file_=new File(Environment.getExternalStorageDirectory().getPath()+"/onekilo/download_pic");
			if(!file_.exists()) {
				file_.mkdirs();
			}
		}
		
		if(mBMapManager==null) {
			mBMapManager=new BMapManager(this);
		}
        if(!mBMapManager.init(strKey, new MyGeneralListener() {})) {
        	Toast.makeText(getApplicationContext(), "初始化地图引擎失败", Toast.LENGTH_LONG).show();
        }
        
        JPushInterface.setDebugMode(true); 	//设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
        JPushInterface.setAliasAndTags(getApplicationContext(), "", null, new TagAliasCallback() {

			@Override
			public void gotResult(int arg0, String arg1, Set<String> arg2) {
				// TODO Auto-generated method stub
				System.out.println("极光推送返回"+arg0);
			}});
	}
	
	public class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int arg0) {
			// TODO Auto-generated method stub
			if (arg0 == MKEvent.ERROR_NETWORK_CONNECT) {
                Toast.makeText(getApplicationContext(), "您的网络出错啦！", Toast.LENGTH_LONG).show();
            }
            else if (arg0 == MKEvent.ERROR_NETWORK_DATA) {
                Toast.makeText(getApplicationContext(), "输入正确的检索条件！", Toast.LENGTH_LONG).show();
            }
		}

		@Override
		public void onGetPermissionState(int arg0) {
			// TODO Auto-generated method stub
			if(arg0!=0) {
				Toast.makeText(getApplicationContext(), "地图引擎未获取到足够的权限", Toast.LENGTH_LONG).show();
			}
			
		}
		
	}

}
