package com.morningtel.onekilo.voice;

import java.io.File;
import java.io.IOException;

import net.frakbot.imageviewex.Converters;
import net.frakbot.imageviewex.ImageViewEx;

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
	
	TextView voice_record_state=null;
	TextView voice_record_button=null;
	TextView voice_stop_button=null;
	ImageViewEx voice_imageViewEx1=null;
	ImageViewEx voice_imageViewEx2=null;
	ImageViewEx voice_imageViewEx3=null;
	
	private String fileName="";
	private MediaRecorder recorder=null;
	private MediaPlayer player=null;
	
	//是否开始录音
	private boolean isStart=false;
	//当前录音开始时间
	long recordStartTime=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_voice);
		
		init();
	}
	
	public void init() {
		
		voice_imageViewEx1=(ImageViewEx) findViewById(R.id.voice_imageViewEx1);
		voice_imageViewEx1.setSource(Converters.assetToByteArray(getAssets(), "voice_1.gif"));
		voice_imageViewEx1.setVisibility(View.INVISIBLE);
		voice_imageViewEx2=(ImageViewEx) findViewById(R.id.voice_imageViewEx2);
		voice_imageViewEx2.setSource(Converters.assetToByteArray(getAssets(), "voice_2.gif"));
		voice_imageViewEx2.setVisibility(View.INVISIBLE);
		voice_imageViewEx3=(ImageViewEx) findViewById(R.id.voice_imageViewEx3);
		voice_imageViewEx3.setSource(Converters.assetToByteArray(getAssets(), "voice_3.gif"));
		voice_imageViewEx3.setVisibility(View.INVISIBLE);
		voice_stop_button=(TextView) findViewById(R.id.voice_stop_button);
		voice_stop_button.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}});
		voice_record_button=(TextView) findViewById(R.id.voice_record_button);
		voice_record_button.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stopRecording();
				voice_record_state.setText("正在发送");
			}});
		voice_record_state=(TextView) findViewById(R.id.voice_record_state);
		voice_record_state.setText("开始说话");
		fileName=Environment.getExternalStorageDirectory().getPath()+"/onekilo/temp"+"/"+System.currentTimeMillis()+".amr";
		File file=new File(fileName);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		startRecording();
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
		player=null;
    }
	
	private void startRecording() {
		recorder=new MediaRecorder();
		//设置音源为Micphone  
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		//设置封装格式 
		recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
		recorder.setOutputFile(fileName);
		//设置编码格式
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
		if(recorder!=null) {
			recorder.stop();
			recorder.reset();
			recorder.release();
			recorder=null;
		}		
	}
	
	public void getMicroPhoneVoice() {
		final Handler handler=new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				//以20为界限，如果超过20，则开始记录时间
				if(msg.what>20&&!isStart) {
					isStart=true;
					voice_record_button.setVisibility(View.VISIBLE);
					recordStartTime=System.currentTimeMillis();
				}
				if(isStart) {
					voice_record_state.setText("正在记录。。。"+(System.currentTimeMillis()-recordStartTime)/1000+"秒");
					if(msg.what<30) {
						voice_imageViewEx1.setVisibility(View.GONE);
						voice_imageViewEx2.setVisibility(View.GONE);
						voice_imageViewEx3.setVisibility(View.VISIBLE);
					}
					else if(msg.what>=30&&msg.what<70) {
						voice_imageViewEx1.setVisibility(View.GONE);
						voice_imageViewEx2.setVisibility(View.VISIBLE);
						voice_imageViewEx3.setVisibility(View.GONE);
					}
					else if(msg.what>=70) {
						voice_imageViewEx1.setVisibility(View.VISIBLE);
						voice_imageViewEx2.setVisibility(View.GONE);
						voice_imageViewEx3.setVisibility(View.GONE);
					}
				}				
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
						Message m=new Message();
						angle=100*minAngle*recorder.getMaxAmplitude()/32768;
						System.out.println(angle);
						m.what=(int) angle;
						handler.sendMessage(m);
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
		stopRecording();  
        if (player!=null) { 
        	player.release(); 
        	player=null; 
        } 
	}

}
