package com.morningtel.onekilo.main;

import cn.jpush.android.api.JPushInterface;

import com.morningtel.onekilo.R;
import com.morningtel.onekilo.common.CommonUtils;
import com.morningtel.onekilo.community.CommunityActivity;
import com.morningtel.onekilo.localService.LocalServiceActivity;
import com.morningtel.onekilo.login.LoginActivity;
import com.morningtel.onekilo.message.MessageActivity;
import com.morningtel.onekilo.setting.SettingActivity;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends TabActivity {
	
	TabHost host=null;
	
	ImageView xiaoxi=null;
	LinearLayout xiaoxi_layout=null;
	ImageView shouye=null;
	LinearLayout shouye_layout=null;
	ImageView xuexiao=null;
	LinearLayout xuexiao_layout=null;
	ImageView shezhi=null;
	LinearLayout shezhi_layout=null;
	
	private static MainActivity instance=null;
	
	public static MainActivity getInstance() {
		return instance;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_tabmain);
		
		instance=this;
		
		CommonUtils.setLoginState(MainActivity.this, CommonUtils.getLoginUser(MainActivity.this).getToken(), true);
		
		JPushInterface.resumePush(getApplicationContext());
		XGPushManager.registerPush(getApplicationContext(), CommonUtils.getLoginUser(MainActivity.this).getId(), new XGIOperateCallback() {

			@Override
			public void onFail(Object arg0, int arg1, String arg2) {
				// TODO Auto-generated method stub
				System.out.println("ע��ʧ�ܣ������룺" + arg1 + ",������Ϣ��" + arg2);
			}

			@Override
			public void onSuccess(Object arg0, int arg1) {
				// TODO Auto-generated method stub
				System.out.println("ע��ɹ����豸tokenΪ��" + arg0+", �˺�Ϊ��"+CommonUtils.getLoginUser(MainActivity.this).getId());
			}});
		
		init();
	}
	
	public void init() {
		xiaoxi_layout=(LinearLayout) findViewById(R.id.xiaoxi_layout);
		xiaoxi_layout.setOnClickListener(new ImageView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setTab(0);
			}});
		shouye_layout=(LinearLayout) findViewById(R.id.shouye_layout);
		shouye_layout.setOnClickListener(new ImageView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setTab(1);
			}});
		xuexiao_layout=(LinearLayout) findViewById(R.id.xuexiao_layout);
		xuexiao_layout.setOnClickListener(new ImageView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setTab(2);
			}});
		shezhi_layout=(LinearLayout) findViewById(R.id.shezhi_layout);
		shezhi_layout.setOnClickListener(new ImageView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setTab(3);
			}});
		
		xiaoxi=(ImageView) findViewById(R.id.xiaoxi);
		shouye=(ImageView) findViewById(R.id.shouye);
		xuexiao=(ImageView) findViewById(R.id.xuexiao);
		shezhi=(ImageView) findViewById(R.id.shezhi);
		
		host=getTabHost();
		addTab("spec1", MessageActivity.class, R.drawable.xiaoxi);
		addTab("spec2", LocalServiceActivity.class, R.drawable.shouye);
		addTab("spec3", CommunityActivity.class, R.drawable.xuexiao);
		addTab("spec4", SettingActivity.class, R.drawable.shezhi);
		setTab(1);
	}
	
	public void addTab(String tag, Class<?> cls, int drawable) {
		TabSpec spec=host.newTabSpec(tag);
		spec.setContent(new Intent(MainActivity.this, cls));
		spec.setIndicator("", getResources().getDrawable(drawable));
		host.addTab(spec);
	}
	
	public void setTab(int index) {		
		xiaoxi.setImageResource(R.drawable.xiaoxi);
		shouye.setImageResource(R.drawable.shouye);
		xuexiao.setImageResource(R.drawable.xuexiao);
		shezhi.setImageResource(R.drawable.shezhi);
		switch(index) {
		case 0:
			xiaoxi.setImageResource(R.drawable.xiaoxi_2);
			host.setCurrentTabByTag("spec1");
			break;
		case 1:
			shouye.setImageResource(R.drawable.shouye_2);
			host.setCurrentTabByTag("spec2");
			break;
		case 2:
			xuexiao.setImageResource(R.drawable.xuexiao_2);
			host.setCurrentTabByTag("spec3");
			break;
		case 3:
			shezhi.setImageResource(R.drawable.shezhi_2);
			host.setCurrentTabByTag("spec4");
			break;
		}
	}
	
	public void loginOut() {
		CommonUtils.setLoginState(MainActivity.this, CommonUtils.getLoginUser(MainActivity.this).getToken(), false);
		finish();
		Intent intent=new Intent(MainActivity.this, LoginActivity.class);
		startActivity(intent);
	}
	
}
