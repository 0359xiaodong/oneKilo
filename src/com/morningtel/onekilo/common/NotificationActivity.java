package com.morningtel.onekilo.common;

import android.os.Bundle;
import android.view.Window;

import com.morningtel.onekilo.BaseActivity;

public class NotificationActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		finish();
	}

}
