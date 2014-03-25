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
				System.out.println("�Ѿ�����ʵ����");
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
	 * �����Զ�����ʽ���������·�֪ͨʱ����ָ��build_id������ɿ������Լ�ά��
	 * 
	 * @param context
	 */
	private void initNotificationBuilder(Context context) {
		// �½��Զ�����ʽ
		XGBasicPushNotificationBuilder build = new XGBasicPushNotificationBuilder();
		// �����Զ�����ʽ���ԣ������ԶԶ�Ӧ�ı����Ч��ָ�������޸ġ�
		build.setIcon(R.drawable.ic_launcher)
				.setSound(
						RingtoneManager.getActualDefaultRingtoneUri(
								getApplicationContext(),
								RingtoneManager.TYPE_ALARM)) // ��������
				.setDefaults(Notification.DEFAULT_VIBRATE) // ��
				.setFlags(Notification.FLAG_NO_CLEAR); // �Ƿ�����
		// ����֪ͨ��ʽ����ʽ���Ϊ2����build_idΪ2����ͨ����̨�ű�ָ��
		XGPushManager.setPushNotificationBuilder(this, 2, build);

		// ��ͬ
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
