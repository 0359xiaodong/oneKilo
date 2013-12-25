package com.morningtel.onekilo.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

import com.baidu.mobstat.StatService;
import com.morningtel.onekilo.OneKiloApplication;
import com.morningtel.onekilo.R;
import com.morningtel.onekilo.common.CommonUtils;
import com.morningtel.onekilo.common.Conn;
import com.morningtel.onekilo.main.MainActivity;
import com.morningtel.onekilo.model.User;

public class SplashActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		
		StatService.setDebugOn(true);
		
		handler.sendEmptyMessageDelayed(0, 3000);
	}

	public void onResume() {
		super.onResume();
		StatService.onResume(this);
	}

	public void onPause() {
		super.onPause();
		StatService.onPause(this);
	}
	
	Handler handler=new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(((OneKiloApplication) getApplication()).isAppOpen) {
				System.out.println("已经有了实例了");
				finish();
			}
			else {
				Intent intent=null;
				User user=Conn.getInstance(SplashActivity.this).getLoginUser();
				if(user!=null&&CommonUtils.isAlreadyLoginOut(SplashActivity.this, user.getToken())) {
					((OneKiloApplication) getApplicationContext()).user=user;
					intent=new Intent(SplashActivity.this, MainActivity.class);
				}
				else {
					intent=new Intent(SplashActivity.this, LoginActivity.class);
				}
				startActivity(intent);
				finish();
			}
		}
	};
}
