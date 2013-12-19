package com.morningtel.onekilo.voice;

import java.io.File;
import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.morningtel.onekilo.BaseActivity;
import com.morningtel.onekilo.R;

public class VoiceActivity extends BaseActivity {
	
	TextView voice_record_button=null;
	TextView voice_play_button=null;
	
	private String fileName="";
	private MediaRecorder recorder=null;
	private MediaPlayer player=null;
	
	//�Ƿ�ʼ¼��
	private boolean isStart=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_voice);
		
		init();
	}
	
	public void init() {
		voice_play_button=(TextView) findViewById(R.id.voice_play_button);
		voice_play_button.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startPlay();
			}});
		voice_record_button=(TextView) findViewById(R.id.voice_record_button);
		voice_record_button.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isStart) {
					fileName=Environment.getExternalStorageDirectory().getPath()+"/onekilo/temp"+"/"+System.currentTimeMillis()+".3gp";
					File file=new File(fileName);
					if(!file.exists()) {
						try {
							file.createNewFile();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					voice_record_button.setText("ֹͣ¼��");
					startRecording();
				}
				else {
					voice_record_button.setText("��ʼ¼��");
					stopRecording();
				}
				isStart=!isStart;
			}});
	}
	
	private void startPlay() {
		player=new MediaPlayer();
		try {
			player.setDataSource(fileName);
			player.prepare();
			player.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void stopPlaying() {
		player.release();
		player = null;
    }
	
	private void startRecording() {
		recorder=new MediaRecorder();
		//������ԴΪMicphone  
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		//���÷�װ��ʽ 
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setOutputFile(fileName);
		//���ñ����ʽ
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		try {
			recorder.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		recorder.start();
		getMicroPhoneVoice();
	}
	
	private void stopRecording() {
		recorder.stop();
		recorder.release();
		recorder=null;
	}
	
	public void getMicroPhoneVoice() {
		final Handler handler=new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
			}
		};
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				final float minAngle=(float) Math.PI*4/11;
				float angle=0;
				while(true) {
					if(recorder!=null) {
						angle=100*minAngle*recorder.getMaxAmplitude()/32768;
						System.out.println(angle);
					}
					try {
						Thread.sleep(400);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}}).start();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		isStart=false;
		
		if(recorder!=null) { 
			recorder.release(); 
			recorder=null; 
        } 
 
        if (player!=null) { 
        	player.release(); 
        	player=null; 
        } 
	}

}
