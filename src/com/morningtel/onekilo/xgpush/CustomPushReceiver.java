package com.morningtel.onekilo.xgpush;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

/**
 * ������Ҫ���ڽ�����Ϣ�ʹ���������<br>
 * APP���Բο����࣬ʵ���Լ���Receiver<br>
 * 
 * �����Ĵ����룺<br>
 * 0����ʾ�ɹ�<br>
 * 1��ϵͳ����ָ��Ƿ����ڴ����� <br>
 * 2���Ƿ�����<br>
 * �������ڲ�����<br>
 * 
 * 
 * Copyright (c) 1998-2014 Tencent
 * 
 * @author foreachli Email: foreachli@tencent.com
 */
public class CustomPushReceiver extends XGPushBaseReceiver {
	public static final String LogTag = "TPushReceiver";

	/**
	 * ע����
	 * 
	 * @param context
	 *            APP�����Ķ���
	 * @param errorCode
	 *            �����룬{@link XGPushBaseReceiver#SUCCESS}��ʾ�ɹ���������ʾʧ��
	 * @param registerMessage
	 *            ע��������
	 */
	@Override
	public void onRegisterResult(Context context, int errorCode,
			XGPushRegisterResult registerMessage) {
		String text = null;
		if (errorCode == XGPushBaseReceiver.SUCCESS) {
			text = registerMessage + "ע��ɹ�";
			// ��������token
			String token = registerMessage.getToken();
		} else {
			text = registerMessage + "ע��ʧ�ܣ������룺" + errorCode;
		}
		Log.d(LogTag, text);
		
	}

	/**
	 * ��ע����
	 * 
	 * @param context
	 *            APP�����Ķ���
	 * @param errorCode
	 *            �����룬{@link XGPushBaseReceiver#SUCCESS}��ʾ�ɹ���������ʾʧ��
	 */
	@Override
	public void onUnregisterResult(Context context, int errorCode) {
		String text = null;
		if (errorCode == XGPushBaseReceiver.SUCCESS) {
			text = "��ע��ɹ�";
		} else {
			text = "��ע��ʧ��" + errorCode;
		}
		Log.d(LogTag, text);
		
	}

	/**
	 * ���ñ�ǩ�������
	 * 
	 * @param context
	 *            APP�����Ķ���
	 * @param errorCode
	 *            �����룬{@link XGPushBaseReceiver#SUCCESS}��ʾ�ɹ���������ʾʧ��
	 * @tagName ��ǩ����
	 */
	@Override
	public void onSetTagResult(Context context, int errorCode, String tagName) {
		String text = null;
		if (errorCode == XGPushBaseReceiver.SUCCESS) {
			text = "\"" + tagName + "\"���óɹ�";
		} else {
			text = "\"" + tagName + "\"����ʧ��,�����룺" + errorCode;
		}
		Log.d(LogTag, text);
		
	}

	/**
	 * ɾ����ǩ�������
	 * 
	 * @param context
	 *            APP�����Ķ���
	 * @param errorCode
	 *            �����룬{@link XGPushBaseReceiver#SUCCESS}��ʾ�ɹ���������ʾʧ��
	 * @tagName ��ǩ����
	 */
	@Override
	public void onDeleteTagResult(Context context, int errorCode, String tagName) {
		String text = null;
		if (errorCode == XGPushBaseReceiver.SUCCESS) {
			text = "\"" + tagName + "\"ɾ���ɹ�";
		} else {
			text = "\"" + tagName + "\"ɾ��ʧ��,�����룺" + errorCode;
		}
		Log.d(LogTag, text);
		
	}

	/**
	 * �յ���Ϣ<br>
	 * 
	 * @param context
	 *            APP�����Ķ���
	 * @param message
	 *            �յ�����Ϣ
	 */
	@Override
	public void onTextMessage(Context context, XGPushTextMessage message) {
		String text = "�յ���Ϣ:" + message.toString();
		// ��ȡ�Զ���key-value
		String customContent = message.getCustomContent();
		if (customContent != null && customContent.length() != 0) {
			try {
				JSONObject obj = new JSONObject(customContent);
				// key1Ϊǰ̨���õ�key
				if (!obj.isNull("key")) {
					String value = obj.getString("key");
					Log.d(LogTag, "get custom value:" + value);
				}
				// ...
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		// APP����������Ϣ�Ĺ��̡�����
		Log.d(LogTag, text);
		
	}

	/**
	 * ֪ͨ���򿪽������
	 * 
	 * @param context
	 *            APP�����Ķ���
	 * @param message
	 *            ���򿪵���Ϣ����
	 */
	@Override
	public void onNotifactionClickedResult(Context context,
			XGPushClickedResult message) {
		String text = "֪ͨ���� :" + message;
		// ��ȡ�Զ���key-value
		String customContent = message.getCustomContent();
		if (customContent != null && customContent.length() != 0) {
			try {
				JSONObject obj = new JSONObject(customContent);
				// key1Ϊǰ̨���õ�key
				if (!obj.isNull("key")) {
					String value = obj.getString("key");
					Log.d(LogTag, "get custom value:" + value);
				}
				// ...
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		// APP��������Ĺ��̡�����
		Log.d(LogTag, text);
//		
	}

	@Override
	public void onNotifactionShowedResult(Context context,
			XGPushShowedResult notifiShowedRlt) {
		String text = "��չʾ֪ͨ :" + notifiShowedRlt;
		Log.d(LogTag, text);
	}
}
