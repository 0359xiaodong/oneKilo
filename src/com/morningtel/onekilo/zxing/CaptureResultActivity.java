package com.morningtel.onekilo.zxing;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.morningtel.onekilo.BaseActivity;
import com.morningtel.onekilo.R;

public class CaptureResultActivity extends BaseActivity {
	
	TextView capture_result=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_captureresult);
		
		init();
	}
	
	public void init() {
		capture_result=(TextView) findViewById(R.id.capture_result);
		capture_result.setText(getIntent().getExtras().getString("result"));
	}

}
