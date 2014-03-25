package com.morningtel.onekilo.login;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
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
import com.tencent.android.tpush.XGBasicPushNotificationBuilder;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

public class SplashActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		
		XGPushConfig.enableDebug(this, true);
		initNotificationBuilder(getApplicationContext());
		
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
	
	/**
	 * 设置自定义样式，这样在下发通知时可以指定build_id。编号由开发者自己维护
	 * 
	 * @param context
	 */
	private void initNotificationBuilder(Context context) {
		// 新建自定义样式
		XGBasicPushNotificationBuilder build = new XGBasicPushNotificationBuilder();
		// 设置自定义样式属性，该属性对对应的编号生效，指定后不能修改。
		build.setIcon(R.drawable.ic_launcher)
				.setSound(
						RingtoneManager.getActualDefaultRingtoneUri(
								getApplicationContext(),
								RingtoneManager.TYPE_ALARM)) // 设置声音
				.setDefaults(Notification.DEFAULT_VIBRATE) // 振动
				.setFlags(Notification.FLAG_NO_CLEAR); // 是否可清除
		// 设置通知样式，样式编号为2，即build_id为2，可通过后台脚本指定
		XGPushManager.setPushNotificationBuilder(this, 2, build);

		// 下同
		XGBasicPushNotificationBuilder build11 = new XGBasicPushNotificationBuilder();
		build11.setIcon(R.drawable.ic_launcher)
				.setSound(
						RingtoneManager
								.getDefaultUri(RingtoneManager.TYPE_ALARM))
				.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
				.setFlags(Notification.FLAG_NO_CLEAR);
		XGPushManager.setPushNotificationBuilder(this, 5, build11);
	}
}
