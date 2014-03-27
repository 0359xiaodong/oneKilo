package com.morningtel.onekilo.bundle;

import org.apkplug.Bundle.StartActivity;
import org.apkplug.app.FrameworkFactory;
import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.morningtel.onekilo.BaseActivity;
import com.morningtel.onekilo.R;
import com.morningtel.onekilo.common.CommonUtils;

import android.os.Bundle;
import android.content.Intent;
import android.view.Window;

public class LauncherActivity extends BaseActivity {
	
	//插件平台对外接口
	private FrameworkInstance frame=null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_launcher);
        
        try {       
        	frame=FrameworkFactory.getInstance().start(null, LauncherActivity.this, MyProperty.getInstance(this.getApplicationContext()));
        }
        catch (Exception ex) {
            System.err.println("Could not create : " + ex);
            ex.printStackTrace();
            StringBuffer buf=new StringBuffer();
			buf.append("插件平台启动失败：\n");
			buf.append(ex.getMessage());
			CommonUtils.showCustomToast(LauncherActivity.this, buf.toString());
			finish();
        }
        
        try {
			startActivity("com.google.zxing.client.android.CaptureActivity");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * 获取系统提供的StartActivity服务来启动一个插件中的Activity
     * 前提时插件中已在plugin.xml设置了Export-Package中添加了该
     * Activity完整包路径 否则会找不到该Activity
     * @param name
     * @throws Exception
     */
    public void startActivity(String ActivityClass) throws Exception{
    	System.out.println(ActivityClass);
		BundleContext mcontext=frame.getSystemBundleContext();
		ServiceReference reference=mcontext.getServiceReference(StartActivity.class.getName());
    	if(null!=reference){
    		StartActivity service=(StartActivity) mcontext.getService(reference);
    		if(service!=null){
    			Intent i=new Intent();
    			Bundle bundle=new Bundle();
    			bundle.putString("api", getIntent().getExtras().getString("api"));
				bundle.putString("hotName", getIntent().getExtras().getString("hotName"));
				bundle.putString("token", CommonUtils.getLoginUser(LauncherActivity.this).getToken());
				i.putExtras(bundle);
				i.setClassName(this, ActivityClass);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    			service.StartActivity(mcontext, i);
    		}
    		mcontext.ungetService(reference);
    	}
	}
    
    boolean isAlreadyStart=false;
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	if(isAlreadyStart) {
    		frame.shutdown();
			finish();
    	}
    	isAlreadyStart=true;
    }
}
