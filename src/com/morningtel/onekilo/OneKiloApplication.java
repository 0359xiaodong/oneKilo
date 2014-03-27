package com.morningtel.onekilo;

import java.io.File;

import cn.jpush.android.api.JPushInterface;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;
import com.morningtel.onekilo.model.User;

import android.app.Application;
import android.os.Environment;
import android.widget.Toast;

public class OneKiloApplication extends Application {
	
	public String webUrl="http://yiguanjia.duapp.com/";
	public String strKey="HaajpyDaUMB7EryGVRuu7bYz";
	public boolean isAppOpen=false;
	public static final String refreshWebInfoAction="refreshWebInfoAction";
	//֪ͨ��notify���
	public int no_num=0;
	
	public BMapManager mBMapManager=null;
	
	public User user=null;
	
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
        	Toast.makeText(getApplicationContext(), "��ʼ����ͼ����ʧ��", Toast.LENGTH_LONG).show();
        }
        
        JPushInterface.setDebugMode(false); 	//���ÿ�����־,����ʱ��ر���־
        JPushInterface.init(this);
	}
	
	public class MyGeneralListener implements MKGeneralListener {

		@Override
		public void onGetNetworkState(int arg0) {
			// TODO Auto-generated method stub
			if (arg0 == MKEvent.ERROR_NETWORK_CONNECT) {
                Toast.makeText(getApplicationContext(), "���������������", Toast.LENGTH_LONG).show();
            }
            else if (arg0 == MKEvent.ERROR_NETWORK_DATA) {
                Toast.makeText(getApplicationContext(), "������ȷ�ļ���������", Toast.LENGTH_LONG).show();
            }
		}

		@Override
		public void onGetPermissionState(int arg0) {
			// TODO Auto-generated method stub
			if(arg0!=0) {
				Toast.makeText(getApplicationContext(), "��ͼ����δ��ȡ���㹻��Ȩ��", Toast.LENGTH_LONG).show();
			}
			
		}
		
	}

}
