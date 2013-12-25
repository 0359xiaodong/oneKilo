package com.morningtel.onekilo.voice;

import java.io.IOException;

import net.frakbot.imageviewex.Converters;
import net.frakbot.imageviewex.ImageViewEx;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.buihha.audiorecorder.Mp3Recorder;
import com.buihha.audiorecorder.Mp3Recorder.OnVolumnReceiverListener;
import com.morningtel.onekilo.BaseActivity;
import com.morningtel.onekilo.R;

public class VoiceSignActivity extends BaseActivity {
	
	TextView voice_record_state=null;
	TextView voice_record_button=null;
	TextView voice_stop_button=null;
	ImageViewEx voice_imageViewEx1=null;
	ImageViewEx voice_imageViewEx2=null;
	ImageViewEx voice_imageViewEx3=null;
	
	Mp3Recorder recorder=null;
	
	//是否开始录音
	private boolean isStart=false;
	//当前录音开始时间
	private long recordStartTime=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_voice);
		
		recorder=new Mp3Recorder();
		recorder.setOnVolumnReceiverListener(new OnVolumnReceiverListener() {
			
			@Override
			public void getVolumnReceiver(int max) {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(max);	
			}
		});
		
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
				try {
					recorder.stopRecording();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				voice_record_state.setText("正在发送");
			}});
		voice_record_state=(TextView) findViewById(R.id.voice_record_state);
		voice_record_state.setText("开始说话");		
	}
	
	Handler handler=new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			int max=msg.what;
			//以1000为界限，如果超过1000，则开始记录时间
			if(max>1000&&!isStart) {
				isStart=true;
				voice_record_button.setVisibility(View.VISIBLE);
				recordStartTime=System.currentTimeMillis();
			}
			if(isStart) {
				voice_record_state.setText("正在记录。。。"+(System.currentTimeMillis()-recordStartTime)/1000+"秒");
				if(System.currentTimeMillis()-recordStartTime<1000) {
					return;
				}
				if(max<5000) {
					voice_imageViewEx1.setVisibility(View.GONE);
					voice_imageViewEx2.setVisibility(View.GONE);
					voice_imageViewEx3.setVisibility(View.VISIBLE);
				}
				else if(max>=10000&&max<20000) {
					voice_imageViewEx1.setVisibility(View.GONE);
					voice_imageViewEx2.setVisibility(View.VISIBLE);
					voice_imageViewEx3.setVisibility(View.GONE);
				}
				else if(max>=20000) {
					voice_imageViewEx1.setVisibility(View.VISIBLE);
					voice_imageViewEx2.setVisibility(View.GONE);
					voice_imageViewEx3.setVisibility(View.GONE);
				}
			}	
		}
	};
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		StatService.onResume(this);
		try {
			recorder.startRecording();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		StatService.onPause(this);
		if(recorder.isRecording()) {
			try {
				recorder.stopRecording();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
