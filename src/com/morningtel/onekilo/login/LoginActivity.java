package com.morningtel.onekilo.login;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.morningtel.onekilo.BaseActivity;
import com.morningtel.onekilo.OneKiloApplication;
import com.morningtel.onekilo.R;
import com.morningtel.onekilo.common.CommonUtils;
import com.morningtel.onekilo.common.Conn;
import com.morningtel.onekilo.common.DES;
import com.morningtel.onekilo.main.MainActivity;
import com.morningtel.onekilo.model.JsonParse;
import com.morningtel.onekilo.model.User;
import com.tencent.android.tpush.XGPushManager;

public class LoginActivity extends BaseActivity {
	
	TextView nav_title=null;
	
	EditText name=null;
	EditText pass=null;
	TextView login=null;
	TextView regist=null;
	TextView no_user_login=null;
	ImageView login_show_mima=null;
	RelativeLayout account_icon_weibo=null;
	RelativeLayout account_icon_qzone=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		
		init();
	}
	
	public void init() {
		nav_title=(TextView) findViewById(R.id.nav_title);
		nav_title.setText("登 陆");
		
		name=(EditText) findViewById(R.id.name);
		if(Conn.getInstance(LoginActivity.this).getLoginUser()!=null) {
			name.setText(Conn.getInstance(LoginActivity.this).getLoginUser().getAccount());
		}
		pass=(EditText) findViewById(R.id.pass);
		pass.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					login_show_mima.setVisibility(View.VISIBLE);
				}
				return false;
			}
		});
		login=(TextView) findViewById(R.id.login);
		login.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(name.getText().toString().equals("")) {
					name.startAnimation(AnimationUtils.loadAnimation(LoginActivity.this, R.anim.shakex));
					showCustomToast("您的用户名不能为空");
					return;
				}
				if(pass.getText().toString().equals("")) {
					pass.startAnimation(AnimationUtils.loadAnimation(LoginActivity.this, R.anim.shakex));
					showCustomToast("您的密码不能为空");
					return;
				}
				login(name.getText().toString(), pass.getText().toString());
			}});
		regist=(TextView) findViewById(R.id.regist);
		login_show_mima=(ImageView) findViewById(R.id.login_show_mima);
		login_show_mima.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance()); 
					break;
				case MotionEvent.ACTION_UP:
					pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
					break;
				}
				pass.postInvalidate();
				return true;
			}
		});
		account_icon_weibo=(RelativeLayout) findViewById(R.id.account_icon_weibo);
		account_icon_qzone=(RelativeLayout) findViewById(R.id.account_icon_qzone);
		no_user_login=(TextView) findViewById(R.id.no_user_login);
	}
	
	//登录
	public void login(final String uName, final String passw) {
		showProgressDialog("正在登录中。。。");
		final Handler handler=new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				dismissProgressDialog();
				if(msg.obj==null) {
					showCustomToast("网络异常，请稍后再试");
					return;
				}
				String result=msg.obj.toString();
				User user=JsonParse.getLoginUser(result, LoginActivity.this);
				if(user!=null) {
					((OneKiloApplication) getApplicationContext()).user=user;
					Conn.getInstance(LoginActivity.this).deleteUser();
					Conn.getInstance(LoginActivity.this).insertModel(user);
					Intent intent=new Intent();
					intent.setClass(LoginActivity.this, MainActivity.class);
					startActivity(intent);
					finish();				
				}
				else {
					showCustomToast(result);
				}
			}
		};
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message m=new Message();
				try {
					HashMap<String, String> map=new HashMap<String, String>();
					map.put("account", uName);
					map.put("password", DES.encryptDES(passw, "yguanjia"));
					String result=CommonUtils.getWebData(map, ((OneKiloApplication) getApplication()).webUrl+"/openuser_appLogin.do");					
					System.out.println(new String(result.getBytes("iso-8859-1"), "utf-8"));
					m.obj=result;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					m.obj=null;
				}
				handler.sendMessage(m);
			}}).start();
		
	}
	
	public void onResume() {
		super.onResume();
		StatService.onResume(this);
	}

	public void onPause() {
		super.onPause();
		StatService.onPause(this);
	}

}
