package com.morningtel.onekilo.bundle;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apkplug.app.PropertyInstance;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class MyProperty implements PropertyInstance{
	
	private Context context;
	private static MyProperty _instance=null;
	
	public MyProperty(Context context){
		this.context=context;
	}
	
	synchronized public static MyProperty getInstance(Context context){
		if(_instance==null){
			_instance=new MyProperty(context);
		}
		return _instance;
    } 

	public String getProperty(String key) {
		// TODO Auto-generated method stub
		SharedPreferences sharedata = PreferenceManager.getDefaultSharedPreferences(this.context);
		String data = sharedata.getString(key, null);
		return data;
	}
	
	public void setProperty(String key, String v) {
		// TODO Auto-generated method stub
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this.context); 
		Editor edit = settings.edit();
		edit.putString(key, v);
		edit.commit();
	}
	
	public String[] AutoInstall() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String[] AutoStart() {
		//把apk从assets文件夹中移至应用安装目录中
		File f3=null;
		
        //插件Activiyt
        try {
			InputStream in=context.getAssets().open("CaptureActivity.apk");
			f3=new File(context.getFilesDir(),"CaptureActivity.apk");
			if(!f3.exists())
			copy(in, f3);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
        return new String[]{"file:"+f3.getAbsolutePath()};
	}
	
	private void copy(InputStream is, File outputFile) throws IOException {
	        OutputStream os = null;
	        try {
	            os = new BufferedOutputStream(
	                new FileOutputStream(outputFile),4096);
	            byte[] b = new byte[4096];
	            int len = 0;
	            while ((len = is.read(b)) != -1)
	                os.write(b, 0, len);
	        }
	        finally {
	            if (is != null) is.close();
	            if (os != null) os.close();
	        }
	    }
	
	@Override
	public boolean Debug() {
		// TODO Auto-generated method stub
		return false;
	}
		
}
