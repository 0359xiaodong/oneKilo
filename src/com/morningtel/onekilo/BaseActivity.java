package com.morningtel.onekilo;

import com.morningtel.onekilo.common.AppManager;
import com.morningtel.onekilo.common.CommonUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

public class BaseActivity extends Activity {
	
	AppManager appManager;
	
	private ProgressDialog _dialog = null;
	
	protected void showProgressDialog(String str) {
		if(_dialog == null) {
			_dialog = new ProgressDialog(this);
    	}
		if(!_dialog.isShowing()) {
			_dialog = ProgressDialog.show(this, "", str , true);
			_dialog.setCancelable(false);
			_dialog.setCanceledOnTouchOutside(false);
		}
	}
	
	protected void dismissProgressDialog() {
		if(_dialog != null&&_dialog.isShowing()) {
			_dialog.dismiss();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		appManager = AppManager.getAppManager();
		appManager.addActivity(this);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		appManager.finishActivity(this);
	}
}
