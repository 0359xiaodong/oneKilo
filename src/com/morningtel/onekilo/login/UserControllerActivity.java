package com.morningtel.onekilo.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;

import com.morningtel.onekilo.BaseActivity;
import com.morningtel.onekilo.OneKiloApplication;
import com.morningtel.onekilo.R;
import com.morningtel.onekilo.common.CommonUtils;
import com.morningtel.onekilo.common.Conn;
import com.morningtel.onekilo.main.MainActivity;
import com.morningtel.onekilo.model.User;

public class UserControllerActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		
		handler_enter.sendEmptyMessageDelayed(0, 3000);
	}
	
	Handler handler_enter=new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Intent intent=null;
			User user=Conn.getInstance(UserControllerActivity.this).getLoginUser();
			if(user!=null&&CommonUtils.isAlreadyLoginOut(UserControllerActivity.this, user.getToken())) {
				((OneKiloApplication) getApplicationContext()).user=user;
				intent=new Intent(UserControllerActivity.this, MainActivity.class);
			}
			else {
				intent=new Intent(UserControllerActivity.this, LoginActivity.class);
			}
			startActivity(intent);
			finish();	
		}
	};
	
}
