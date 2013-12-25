package com.morningtel.onekilo.setting;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import cn.jpush.android.api.JPushInterface;

import com.baidu.mobstat.StatService;
import com.lidroid.xutils.BitmapUtils;
import com.morningtel.onekilo.BaseActivity;
import com.morningtel.onekilo.R;
import com.morningtel.onekilo.common.BitmapHelp;
import com.morningtel.onekilo.common.CommonUtils;
import com.morningtel.onekilo.main.MainActivity;

public class SettingActivity extends BaseActivity {
	
	TextView nav_title=null;
	
	ImageView user_face=null;
	TextView user_title=null;
	TextView user_address=null;
	TextView user_loginout=null;
	
	public static BitmapUtils bitmapUtils;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_setting);
		
		bitmapUtils = BitmapHelp.getBitmapUtils(getApplicationContext());
		bitmapUtils.configDefaultLoadingImage(R.drawable.ser_loading);
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.ser_loading);
		bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		
		init();
	}
	
	public void init() {
		nav_title=(TextView) findViewById(R.id.nav_title);
		nav_title.setText("我");
		
		user_face=(ImageView) findViewById(R.id.user_face);
		user_face.setOnClickListener(new ImageView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}});
		bitmapUtils.display(user_face, CommonUtils.getLoginUser(SettingActivity.this).getImageUrl());
		user_title=(TextView) findViewById(R.id.user_title);
		user_title.setText(CommonUtils.getLoginUser(SettingActivity.this).getNickName());
		user_address=(TextView) findViewById(R.id.user_address);
		user_address.setText(CommonUtils.getLoginUser(SettingActivity.this).getAddress());
		user_loginout=(TextView) findViewById(R.id.user_loginout);
		user_loginout.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.getInstance().loginOut();
				//极光推送关闭
				JPushInterface.stopPush(getApplicationContext());
			}});
	}
	
	public void onResume() {
		super.onResume();
		StatService.onResume(this);
	}

	public void onPause() {
		super.onPause();
		StatService.onPause(this);
	}

}
