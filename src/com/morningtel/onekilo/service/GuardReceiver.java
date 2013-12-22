package com.morningtel.onekilo.service;

import java.util.ArrayList;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class GuardReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		//ȫ�㲥������ɴ��ػ�����
		System.out.println("�� "+intent.getAction()+" ���ػ�����");
		//�ظ���
		if(!isServiceWorked(context, "com.morningtel.onekilo.service.GuardService")) {
			System.out.println("�ػ����񲻴��ڣ������ػ�����");
			Intent intent_guard = new Intent(context, GuardService.class);
			intent_guard.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startService(intent_guard);
		}
	}
	  
	/**
	 * �жϷ����Ƿ����
	 * @param context
	 * @return
	 */
	public static boolean isServiceWorked(Context context, String serviceName) {  
		ActivityManager myManager=(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);  
		ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) myManager.getRunningServices(30);  
		for(int i = 0 ; i<runningService.size();i++) {  
			if(runningService.get(i).service.getClassName().toString().equals(serviceName)) {  
				return true;  
			}  
		}  
		return false;  
	}
}
