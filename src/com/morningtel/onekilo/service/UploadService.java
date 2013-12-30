package com.morningtel.onekilo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import com.morningtel.onekilo.common.CommonUtils;
import com.morningtel.onekilo.common.CustomMultiPartEntity;
import com.morningtel.onekilo.common.CustomMultiPartEntity.ProgressListener;
import com.morningtel.onekilo.model.JsonParse;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

public class UploadService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub		
		if(intent.getExtras()!=null) {
			if(intent.getExtras().getString("uploadType").equals("voiceSign")) {
				HashMap<String, String> map=new HashMap<String, String>();
				map.put("token", intent.getExtras().getString("token"));
				uploadFile(intent.getExtras().getString("path"), map, intent.getExtras().getStringArray("filePath"), intent.getExtras().getString("type"));			
			}
			else if(intent.getExtras().getString("uploadType").equals("addForm")) {
				HashMap<String, String> map=new HashMap<String, String>();
				if(intent.getExtras().getString("title")!=null) {
					map.put("title", intent.getExtras().getString("title"));
				}
				if(intent.getExtras().getString("content")!=null) {
					map.put("content", intent.getExtras().getString("content"));
				}
				if(intent.getExtras().getString("mapGeo")!=null) {
					map.put("mapGeo", intent.getExtras().getString("mapGeo"));
				}
				if(intent.getExtras().getString("geo")!=null) {
					map.put("geo", intent.getExtras().getString("geo"));
				}
				map.put("token", intent.getExtras().getString("token"));
				uploadFile(intent.getExtras().getString("path"), map, intent.getExtras().getStringArray("filePath"), intent.getExtras().getString("type"));			
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}
	

	/**
	 * 上传文件
	 * @param path
	 * @param map
	 * @param imageFile
	 * @param type
	 * @return
	 */
	public void uploadFile(final String path, final HashMap<String, String> map, final String[] imageFile, final String type) {
		
		final Handler handler=new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if(msg.what==1000) {
					String str=msg.obj.toString();
					if(CommonUtils.convertNull(str).equals("")) {
						CommonUtils.showCustomToast(UploadService.this, "网络异常，请稍后再试");
					}
					else {
						if(JsonParse.checkPermission(str, UploadService.this)) {
							CommonUtils.showCustomToast(UploadService.this, "提交成功");
						}
						else {
							CommonUtils.showCustomToast(UploadService.this, "提交失败");
						}				
					}
				}
				else if(msg.what==1001) {
					CommonUtils.showCustomToast(UploadService.this, "网络异常，请稍后再试");
				}
				else {
					System.out.println(msg.what);
				}
			}
		};
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String result="";
			    HttpClient httpclient = new DefaultHttpClient();
			    long totalSize=0;
			    for(int i=0;i<imageFile.length;i++) {
			    	totalSize+=new File(imageFile[i]).length();
			    }
			    final long temp=totalSize;
			    try {
			        HttpPost httppost = new HttpPost(path);
			        CustomMultiPartEntity reqEntity = new CustomMultiPartEntity(new ProgressListener() {  
		                public void transferred(long num) {  
		                    int percent=(int) ((num /(float) temp)*100); 
		                    Message m=new Message();
		                    m.what=percent>100?100:percent;
		                    handler.sendMessage(m);
		                }  
		            });
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
			        HttpResponse response=httpclient.execute(httppost);
			        HttpEntity resEntity=response.getEntity();
			        if (resEntity != null) {	        	
			        	BufferedReader reader=new BufferedReader(new InputStreamReader(resEntity.getContent()));
			        	String line="";
			        	while((line=reader.readLine())!=null) {
			        		result+=line;
			        	}	 
			        	resEntity.consumeContent();
			        }
			        result=new String(result.getBytes("utf-8"), "iso-8859-1");
			        Message m_=new Message();
			        m_.obj=result;
			        m_.what=1000;
			        handler.sendMessage(m_);
			    } catch(Exception e) {
			    	result=null;
			    	Message m_=new Message();
			        m_.what=1001;
			        handler.sendMessage(m_);
			    } finally {
			    	httpclient.getConnectionManager().shutdown();
			    }
			}}).start();
	}

}
