package com.morningtel.onekilo.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import cn.jpush.android.api.InstrumentedActivity;

import com.morningtel.onekilo.OneKiloApplication;
import com.morningtel.onekilo.R;

public class SplashActivity extends InstrumentedActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		
		if(((OneKiloApplication) getApplication()).isAppOpen) {
			System.out.println("已经有了实例了");
			finish();
		}
		else {
			Intent intent=new Intent(this, UserControllerActivity.class);
			startActivity(intent);
			finish();
		}
	}

}
