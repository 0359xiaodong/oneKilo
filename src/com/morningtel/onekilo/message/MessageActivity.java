package com.morningtel.onekilo.message;

import com.baidu.mobstat.StatService;
import com.morningtel.onekilo.BaseActivity;

public class MessageActivity extends BaseActivity {

	public void onResume() {
		super.onResume();
		StatService.onResume(this);
	}

	public void onPause() {
		super.onPause();
		StatService.onPause(this);
	}
}
