package com.morningtel.onekilo.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicService extends Service {
	
	MediaPlayer mp=null;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		if(mp!=null) {
			mp.reset();
			mp.release();
			mp=null;
		}
		mp=new MediaPlayer();
		mp.setLooping(false);
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);		
		mp.reset();
		try {
			int musicId=intent.getExtras().getInt("music");
			mp=MediaPlayer.create(MusicService.this, musicId);
			mp.start();
		} catch(Exception e) {
			
		} 
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mp!=null) {
			mp.stop();
			mp=null;
		}
	}

}
