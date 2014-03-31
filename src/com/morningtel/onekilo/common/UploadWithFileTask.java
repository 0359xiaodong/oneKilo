package com.morningtel.onekilo.common;

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

import com.morningtel.onekilo.R;
import com.morningtel.onekilo.common.CustomMultiPartEntity.ProgressListener;
import com.morningtel.onekilo.model.JsonParse;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.RemoteViews;

public class UploadWithFileTask extends AsyncTask<String, Integer, String> {

	String uploadType="";
	
	String token="";
	String[] filePath;
	String type="";
	String path="";
	
	String title="";
	String content="";
	String mapGeo="";
	String geo="";
	String groupId="";
	
	Context context;
	
	//通知id
	int notifyId=0;
	//上一次下载进度
	int lastPercent=0;
	
	NotificationManager manager=null;
	Notification no=null;
	RemoteViews view=null;

	public int getNotifyId() {
		return notifyId;
	}


	public void setNotifyId(int notifyId) {
		this.notifyId = notifyId;
	}


	public Context getContext() {
		return context;
	}


	public void setContext(Context context) {
		this.context = context;
	}


	public String getUploadType() {
		return uploadType;
	}


	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public String[] getFilePath() {
		return filePath;
	}


	public void setFilePath(String[] filePath) {
		this.filePath = filePath;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getPath() {
		return path;
	}


	public void setPath(String path) {
		this.path = path;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getMapGeo() {
		return mapGeo;
	}


	public void setMapGeo(String mapGeo) {
		this.mapGeo = mapGeo;
	}


	public String getGeo() {
		return geo;
	}


	public void setGeo(String geo) {
		this.geo = geo;
	}


	public String getGroupId() {
		return groupId;
	}


	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}


	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		String result="";
		if(uploadType.equals("voiceSign")) {
			HashMap<String, String> map=new HashMap<String, String>();
			map.put("token", token);
			result=uploadFile(path, map, filePath, type);			
		}
		else if(uploadType.equals("addForm")) {
			HashMap<String, String> map=new HashMap<String, String>();
			if(!title.equals("")) {
				map.put("title", title);
			}
			if(!content.equals("")) {
				map.put("content", content);
			}
			if(!mapGeo.equals("")) {
				map.put("mapGeo", mapGeo);
			}
			if(!geo.equals("")) {
				map.put("geo", geo);
			}
			if(!groupId.equals("")) {
				map.put("groupId", groupId);
			}
			map.put("token", token);
			result=uploadFile(path, map, filePath, type);			
		}
		return result;
	}


	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		manager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		no=new Notification();
		no.flags=Notification.FLAG_AUTO_CANCEL;
		no.icon=R.drawable.ic_launcher;
		no.when=System.currentTimeMillis();
		no.tickerText="正在发送中。。。";
		view=new RemoteViews(context.getPackageName(), R.layout.view_upload_withfile);
		view.setProgressBar(R.id.upload_pb, 100, 0, false);
		view.setTextViewText(R.id.upload_rate, "0%");
		no.contentView=view;
		Intent intent=new Intent(context, NotificationActivity.class);
		PendingIntent pi=PendingIntent.getActivity(context, 0, intent, 0);
		no.contentIntent=pi;
		manager.notify(notifyId, no);
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		String str=result;
		if(CommonUtils.convertNull(str).equals("")) {
			CommonUtils.showCustomToast(context, "网络异常，请稍后再试");
		}
		else {
			if(JsonParse.checkPermission(str, context)) {
				CommonUtils.showCustomToast(context, "提交成功");
			}
			else {
				CommonUtils.showCustomToast(context, "提交失败");
			}				
		}
		no.defaults=Notification.DEFAULT_SOUND;
		manager.cancel(notifyId);
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
		System.out.println(values[0]);
		if(values[0]-lastPercent>10) {
			lastPercent=values[0];
		}		
		else {
			return ;
		}
		view.setProgressBar(R.id.upload_pb, 100, values[0], false);
		view.setTextViewText(R.id.upload_rate, values[0]+"%");
		no.contentView=view;
		manager.notify(notifyId, no);
	}

	/**
	 * 上传文件
	 * @param path
	 * @param map
	 * @param imageFile
	 * @param type
	 * @return
	 */
	public String uploadFile(final String path, final HashMap<String, String> map, final String[] imageFile, final String type) {

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
                    publishProgress(percent>100?100:percent);
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
	    } catch(Exception e) {
	    	result=null;
	    } finally {
	    	httpclient.getConnectionManager().shutdown();
	    }
	    return result;
	}

}
