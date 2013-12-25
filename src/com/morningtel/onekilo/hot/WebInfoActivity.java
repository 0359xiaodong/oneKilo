package com.morningtel.onekilo.hot;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;
import com.morningtel.onekilo.BaseActivity;
import com.morningtel.onekilo.OneKiloApplication;
import com.morningtel.onekilo.R;
import com.morningtel.onekilo.addform.AddFormActivity;
import com.morningtel.onekilo.common.CommonUtils;
import com.morningtel.onekilo.mappois.MappoisActivity;
import com.morningtel.onekilo.model.AddForm;
import com.morningtel.onekilo.model.Hot;
import com.morningtel.onekilo.model.JsonParse;
import com.morningtel.onekilo.model.Menu;

public class WebInfoActivity extends BaseActivity {
	
	TextView nav_title=null;
	LinearLayout nav_back_layout=null;
	LinearLayout nav_add_layout=null;
	ImageView nav_add_image=null;
	RelativeLayout nav_view=null;
	
	PullToRefreshWebView mPullRefreshWebView=null;
	WebView activity_webview=null;
	RelativeLayout web_1_bottom_layout=null;
	ImageView webviewtab_forward=null;
	ImageView webviewtab_back=null;
	ImageView webviewtab_refresh=null;
	ImageView webviewtab_share=null;
	ProgressBar web_1_bottom_pb=null;
	LinearLayout menu_layout=null;
	
	//判断是否执行动画效果
	boolean doAni=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_hot_web);
		
		if(getIntent().getExtras().getInt("btnType")==Hot.ADD_BUTTONTYPE) {
			doAni=true;
		}
		
		init();
	}
	
	public void init() {

		nav_view=(RelativeLayout) findViewById(R.id.nav_view);
		nav_back_layout=(LinearLayout) findViewById(R.id.nav_back_layout);
		nav_back_layout.setVisibility(View.VISIBLE);
		nav_back_layout.setOnClickListener(new LinearLayout.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}});
		nav_title=(TextView) findViewById(R.id.nav_title);
		nav_title.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub		
				activity_webview.loadUrl("javascript:titleClick()");
			}});
		nav_add_layout=(LinearLayout) findViewById(R.id.nav_add_layout);
		nav_add_image=(ImageView) findViewById(R.id.nav_add_image);
		if(doAni) {
			nav_add_image.setImageResource(R.drawable.nav_choice);
		}
		else {
			nav_add_image.setImageResource(R.drawable.nav_more);
		}
				
		web_1_bottom_layout=(RelativeLayout) findViewById(R.id.web_1_bottom_layout);
		if(getIntent().getExtras().getInt("needBar")==1) {
			web_1_bottom_layout.setVisibility(View.VISIBLE);
		}
		else {
			web_1_bottom_layout.setVisibility(View.GONE);
		}
		webviewtab_forward=(ImageView) findViewById(R.id.webviewtab_forward);
		webviewtab_forward.setOnClickListener(new ImageView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(activity_webview.canGoForward()) {
					activity_webview.goForward();
				}
			}});
		webviewtab_back=(ImageView) findViewById(R.id.webviewtab_back);
		webviewtab_back.setOnClickListener(new ImageView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(activity_webview.canGoBack()) {
					activity_webview.goBack();
				}
			}});
		webviewtab_refresh=(ImageView) findViewById(R.id.webviewtab_refresh);
		webviewtab_refresh.setOnClickListener(new ImageView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}});
		webviewtab_share=(ImageView) findViewById(R.id.webviewtab_share);
		webviewtab_share.setOnClickListener(new ImageView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}});
		web_1_bottom_pb=(ProgressBar) findViewById(R.id.web_1_bottom_pb);
		web_1_bottom_pb.setMax(100);
		
		mPullRefreshWebView=(PullToRefreshWebView) findViewById(R.id.activity_webview);
		mPullRefreshWebView.setProgressBar(web_1_bottom_pb);
		activity_webview=mPullRefreshWebView.getRefreshableView();
		//无右侧滚动条
		activity_webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		//不使用缓存：
		activity_webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		activity_webview.getSettings().setDomStorageEnabled(true);
		WebSettings settings=activity_webview.getSettings();
		settings.setJavaScriptEnabled(true);
		activity_webview.addJavascriptInterface(this, "JumpActivity");
		activity_webview.setWebViewClient(new WebViewClient() {
			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// TODO Auto-generated method stub
				super.onReceivedError(view, errorCode, description, failingUrl);
				activity_webview.loadUrl("file:///android_asset/web/index.html");
			}
			
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				web_1_bottom_pb.setVisibility(View.VISIBLE);
				mPullRefreshWebView.setStart();
			}
			
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
				mPullRefreshWebView.setEnd();
			}
			
			@Override
            public boolean shouldOverrideUrlLoading(WebView view, String url){
				return false;
            }
		});
		settings.setBuiltInZoomControls(false);
		loadUrl();
		menu_layout=(LinearLayout) findViewById(R.id.menu_layout);
		
		if(getIntent().getExtras().getString("hotName").equals("")) {
			nav_view.setVisibility(View.GONE);
		}
		if(getIntent().getExtras().getParcelableArrayList("menu")!=null&&getIntent().getExtras().getParcelableArrayList("menu").size()>0) {
			nav_add_layout.setVisibility(View.VISIBLE);
			nav_add_layout.setOnClickListener(new LinearLayout.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ArrayList<Menu> menus=getIntent().getExtras().getParcelableArrayList("menu");
					if(menus.size()==1) {
						menuJumpControll(menus, 0);
					}
					else {						
						if(menu_layout.getVisibility()==View.VISIBLE) {
							menu_layout.setVisibility(View.GONE);
							rotate(false);
						}
						else {
							menu_layout.setVisibility(View.VISIBLE);
							rotate(true);
						}
					}
				}});
			addMenuLayout();
		}
		else {
			nav_add_layout.setVisibility(View.GONE);
		}
		nav_title.setText(getIntent().getExtras().getString("hotName"));
		IntentFilter filter=new IntentFilter();
		filter.addAction(OneKiloApplication.refreshWebInfoAction);
		registerReceiver(receiver, filter);
	}
	
	private void addMenuLayout() {
		final ArrayList<Menu> menus=getIntent().getExtras().getParcelableArrayList("menu");
		for(int i=0;i<menus.size();i++) {
			View view=LayoutInflater.from(WebInfoActivity.this).inflate(R.layout.menu_view, null);
			TextView menu_text=(TextView) view.findViewById(R.id.menu_text);
			menu_text.setText(menus.get(i).getName());
			final int pos=i;
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					menuJumpControll(menus, pos);
				}});
			menu_layout.addView(view);
			if(i!=(menus.size()-1)) {
				View view_split=LayoutInflater.from(WebInfoActivity.this).inflate(R.layout.view_splitline, null);
				menu_layout.addView(view_split);
			}
		}
	}
	
	private void menuJumpControll(ArrayList<Menu> menus, int i) {
		final int menuType=menus.get(i).getMenuType();
		final String menuName=menus.get(i).getName();
		final String api=menus.get(i).getApi();
		final AddForm af=menus.get(i).getAddForm();
		final int id=menus.get(i).getId();
		Intent intent=null;
		Bundle bundle=null;
		switch(menuType) {
		case 1:
			intent=new Intent(WebInfoActivity.this, WebInfoActivity.class);
			bundle=new Bundle();
			bundle.putInt("tabId", id);
			bundle.putString("hotName", menuName);
			bundle.putInt("needBar", 0);
			bundle.putString("api", api);
			bundle.putParcelableArrayList("menu", null);
			if(getIntent().getExtras().getString("groupId")!=null) {
				bundle.putString("groupId", getIntent().getExtras().getString("groupId"));
			}
			intent.putExtras(bundle);
			startActivityForResult(intent, 1002);
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		case 6:
			intent=new Intent(WebInfoActivity.this, AddFormActivity.class);
			bundle=new Bundle();
			bundle.putString("hotName", menuName);
			bundle.putParcelable("AddForm", af);
			intent.putExtras(bundle);
			startActivityForResult(intent, 1001);
			break;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK) {
			if(activity_webview.canGoBack()) {
				try {
					if(activity_webview.getUrl().equals("file:///android_asset/web/index.html")) {
						finish();
					}
					else {
						activity_webview.goBack();
					}
				} catch(Exception e) {
					finish();
				}				
				return true;
			}
			
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@JavascriptInterface
	/**
	 * 在js调用一个函数打开一个新浏览器
	 * @param title
	 * @param url
	 * @param needBar
	 */
	public void jump(String title, String url, String needBar) {
//		Intent intent2=new Intent(this, WebInfoActivity.class);
//		Bundle bundle2=new Bundle();
//		bundle2.putString("hotName", title);
//		bundle2.putString("htmlUrl", url);
//		bundle2.putString("needBar", needBar);
//		bundle2.putString("showTitleBar", ""+1);
//		bundle2.putString("addStatus", "");
//		bundle2.putString("needGeo", "0");
//		intent2.putExtras(bundle2);
//		startActivity(intent2);
	}
	
	@JavascriptInterface
	/**
	 * 在js调用一个函数打开一个新的不带下拉滑动的浏览器
	 * @param title
	 * @param url
	 * @param needBar
	 */
	public void jumpNoPullRefreshView(String title, String url, String needBar) {
//		Intent intent2=new Intent(this, WebInfoActivity_NoPullRefresh.class);
//		Bundle bundle2=new Bundle();
//		bundle2.putString("hotName", title);
//		bundle2.putString("htmlUrl", url);
//		bundle2.putString("needBar", needBar);
//		bundle2.putString("showTitleBar", ""+1);
//		bundle2.putString("addStatus", "");
//		bundle2.putString("needGeo", "0");
//		intent2.putExtras(bundle2);
//		startActivity(intent2);
	}
	
	@JavascriptInterface
	/**
	 * js修改当前浏览器的title，如果是选项卡则修改当前选项卡的title
	 * @param title
	 */
	public void setTitle(String title) {
		nav_title.setText(title);
	}
	
	@JavascriptInterface
	/**
	 * Js函数调用发帖功能
	 * @param addStatus
	 * @param hotId
	 */
	public void toAdd(String addStatus, String hotId) {
//		Intent intent=null;
//		Bundle bundle=new Bundle();
//		if(addStatus.indexOf("&&")!=-1) {
//			intent=new Intent(WebInfoActivity.this, FaTieHotTabActivity.class);
//		}
//		else {
//			intent=new Intent(WebInfoActivity.this, FaTieActivity.class);
//			bundle.putString("from", "hot");
//			bundle.putString("showTitleBar", ""+1);
//		}				
//		bundle.putString("addStatus", addStatus);
//		bundle.putString("hotId", hotId);
//		intent.putExtras(bundle);
//		startActivity(intent);
	}
	
	@JavascriptInterface
	/**
	 * Js调用评论功能
	 * @param title
	 * @param topicId
	 * @param receiveUserId
	 */
	public void addComment(String title, String commentTopicId, String receiveUserId) {
//		Intent intent=new Intent();
//		intent.setClass(WebInfoActivity.this, FaTieActivity.class);
//		Bundle bundle=new Bundle();
//		bundle.putString("from", "pinglun");
//		bundle.putString("commentTopicId", commentTopicId);
//		bundle.putString("receiveUserId", receiveUserId);
//		bundle.putString("title", title);
//		intent.putExtras(bundle);
//		startActivity(intent);
	}
	
	@JavascriptInterface
	/**
	 * 回复评论
	 * @param title
	 * @param commentTopicId
	 * @param parentId
	 * @param receiveUserId
	 */
	public void replayComment(String title, String commentTopicId, String parentId, String receiveUserId) {
//		Intent intent=new Intent();
//		intent.setClass(WebInfoActivity.this, FaTieActivity.class);
//		Bundle bundle=new Bundle();				
//		bundle.putString("commentTopicId", commentTopicId);
//		bundle.putString("parentId", parentId);
//		bundle.putString("receiveUserId", receiveUserId);
//		bundle.putString("from", "huifu");
//		bundle.putString("title", title);
//		intent.putExtras(bundle);
//		startActivity(intent);
	}

	@JavascriptInterface
	/**
	 * 跳转到发帖
	 * @param title
	 * @param addForm
	 */
	public void jumpToAddform(String title, String addForm) {
		Intent intent_6=new Intent(WebInfoActivity.this, AddFormActivity.class);
		Bundle bundle_6=new Bundle();
		bundle_6.putString("hotName", title);
		bundle_6.putParcelable("AddForm", JsonParse.getWebAddForm(addForm, false));
		intent_6.putExtras(bundle_6);
		startActivityForResult(intent_6, 501);
	}
	
	@JavascriptInterface
	/**
	 * 跳转到地图
	 * @param title
	 * @param geo
	 */
	public void jumpToMap(String title, String geo) {
		ArrayList<String> geo_list=JsonParse.getGeoList(geo);
		if(geo_list.size()>0) {
			Intent intent=new Intent(WebInfoActivity.this, MappoisActivity.class);
			Bundle bundle=new Bundle();
			bundle.putString("hotName", title);
			bundle.putStringArrayList("geo_list", geo_list);
			intent.putExtras(bundle);
			startActivity(intent);
		}
		else {
			showCustomToast("获取数据失败，请重新加载该页面");
		}
	}
	
	@JavascriptInterface
	/**
	 * 后退刷新
	 * @param needRefresh
	 * @param tabindex
	 */
	public void jumpback(int needRefresh, int tabindex) {
		Intent intent=getIntent();
		Bundle bundle=new Bundle();
		bundle.putInt("needRefresh", needRefresh);
		bundle.putInt("tabindex", tabindex);
		intent.putExtras(bundle);
		setResult(RESULT_OK, intent);
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK&&requestCode==1001) {
			loadUrl();
		}
		else if(resultCode==RESULT_OK&&requestCode==1002) {
			loadUrl();
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
	}
	
	BroadcastReceiver receiver=new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(intent.getAction().equals(OneKiloApplication.refreshWebInfoAction)) {
				if(intent.getExtras().getInt("hotId")==getIntent().getExtras().getInt("tabId")) {
					loadUrl();
				}
			}
		}};
	
	public void rotate(boolean forward) {
		if(doAni) {
			RotateAnimation animation=null;
			if(forward) {
				animation=new RotateAnimation(0f, 45f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
			}
			else {
				animation=new RotateAnimation(45f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
			}
			animation.setDuration(300);
			animation.setFillAfter(true);
			nav_add_image.setAnimation(animation);
			animation.startNow();
		}		
	}
	
	private void loadUrl() {
		if(getIntent().getExtras().getString("api").indexOf("?")!=-1) {
			String extra="";
			if(getIntent().getExtras().getString("groupId")!=null) {
				extra+="&groupId="+getIntent().getExtras().getString("groupId");
			}
			activity_webview.loadUrl(getIntent().getExtras().getString("api")+"&token="+CommonUtils.getLoginUser(WebInfoActivity.this).getToken()+extra);				
		}
		else {
			String extra="";
			if(getIntent().getExtras().getString("groupId")!=null) {
				extra+="&groupId="+getIntent().getExtras().getString("groupId");
			}
			activity_webview.loadUrl(getIntent().getExtras().getString("api")+"?token="+CommonUtils.getLoginUser(WebInfoActivity.this).getToken()+extra);
		}
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
