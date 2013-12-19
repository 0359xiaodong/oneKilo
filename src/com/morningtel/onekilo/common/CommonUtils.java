package com.morningtel.onekilo.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.morningtel.onekilo.OneKiloApplication;
import com.morningtel.onekilo.model.User;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import android.widget.Toast;

public class CommonUtils {
	public static String getWebData(HashMap<String, String> map, String url) {
		ArrayList<NameValuePair> params=new ArrayList<NameValuePair>();
		Iterator<Entry<String, String>> it=map.entrySet().iterator();
		while(it.hasNext()) {
			Entry<String, String> entry=it.next();
			params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}
		HttpPost post=new HttpPost(url);
		try {
			post.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));//设置post参数 并设置编码格式
			post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
			DefaultHttpClient client=new DefaultHttpClient();
			client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
			client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
			HttpResponse resp=client.execute(post);
			if(resp.getStatusLine().getStatusCode()==200) {
				return EntityUtils.toString(resp.getEntity());
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public static String post(String path, HashMap<String, String> map, String[] imageFile, String type) {
		String result="";
	    HttpClient httpclient = new DefaultHttpClient();
	    try {
	        HttpPost httppost = new HttpPost(path);
	        MultipartEntity reqEntity = new MultipartEntity();
	        Iterator<Entry<String, String>> it=map.entrySet().iterator();
	        while(it.hasNext()) {
	        	Entry<String, String> entry=it.next();
	        	reqEntity.addPart(entry.getKey(), new StringBody(entry.getValue(), Charset.forName("utf-8")));
	        }
	        for(int i=0;i<imageFile.length;i++) {
	        	reqEntity.addPart(type, new FileBody(new File(imageFile[i])));
            }
	        httppost.setEntity(reqEntity);
	        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);
	        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
	        HttpResponse response = httpclient.execute(httppost);
	        HttpEntity resEntity = response.getEntity();
	        if (resEntity != null) {	        	
	        	BufferedReader reader = new BufferedReader(new InputStreamReader(resEntity.getContent()));
	        	String line = "";
	        	while((line = reader.readLine()) != null) {
	        		result+=line;
	        	}	 
	        	resEntity.consumeContent();
	        }
	        //EntityUtils.consume(resEntity);
	        result=new String(result.getBytes("utf-8"), "iso-8859-1");
	    } catch(Exception e) {
	    	result=null;
	    } finally {
	    	httpclient.getConnectionManager().shutdown();
	    }
	    return result;
	}
	
	public static String convertNull(String returnValue) {
        try {
            returnValue = (returnValue==null||(returnValue!=null&&returnValue.equals("null")))?"":returnValue;
        } catch (Exception e) {
            returnValue = "";
        }
        return returnValue;
    }
	
	/**
	 * 获取屏幕密度信息
	 * @param context
	 * @return
	 */
	public static float getDisplayParams(Context context) {
		DisplayMetrics dm=new DisplayMetrics();
		dm=context.getApplicationContext().getResources().getDisplayMetrics();
		return dm.density;
	}
	
	/**
	 * 获取用户对象
	 * @param context
	 * @return
	 */
	public static User getLoginUser(Context context) {
		if(((OneKiloApplication) context.getApplicationContext()).user==null) {
			return Conn.getInstance(context).getLoginUser();
		}
		else {
			return ((OneKiloApplication) context.getApplicationContext()).user;
		}
	}
	
	/**
	 * 自定义弹出框
	 * @param context
	 * @param str
	 */
	public static void showCustomToast(Context context, String str) {
		Toast.makeText(context, str, Toast.LENGTH_LONG).show();
	}
	
	/**
	 * 设置登陆状态
	 * @param context
	 * @param token
	 * @param flag
	 */
	public static void setLoginState(Context context, String token, boolean flag) {
		SharedPreferences sp=context.getSharedPreferences("onekilo", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor=sp.edit();
		editor.putBoolean(token, flag);
		editor.commit();
	};
	
	/**
	 * 判断是否已经下线
	 * @param context
	 * @param token
	 * @return
	 */
	public static boolean isAlreadyLoginOut(Context context, String token) {
		SharedPreferences sp=context.getSharedPreferences("onekilo", Activity.MODE_PRIVATE);
		return sp.getBoolean(token, false);
	}
}
