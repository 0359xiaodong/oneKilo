package com.morningtel.onekilo.common;

import java.util.HashMap;

import com.morningtel.onekilo.R;
import com.morningtel.onekilo.model.JsonParse;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.RemoteViews;

public class UploadWithOutFileTask extends AsyncTask<String, Integer, String> {
	
	String uploadType="";
	
	String token="";
	String[] filePath;
	String type="";
	String path="";
	
	String title="";
	String content="";
	String mapGeo="";
	String geo="";
	String url="";
	String groupId="";
	
	Context context;
	
	//通知id
	int notifyId=0;
	
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
	

	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
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
		result=CommonUtils.getWebData(map, url);
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
		view=new RemoteViews(context.getPackageName(), R.layout.view_upload_withoutfile);
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
	}

}
