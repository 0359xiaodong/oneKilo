package com.morningtel.onekilo.hot;

import java.util.ArrayList;

import com.morningtel.onekilo.OneKiloApplication;
import com.morningtel.onekilo.R;
import com.morningtel.onekilo.addform.AddFormActivity;
import com.morningtel.onekilo.model.AddForm;
import com.morningtel.onekilo.model.Hot;
import com.morningtel.onekilo.model.Menu;
import com.morningtel.onekilo.model.Tab;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class WebInfoTabActivity extends TabActivity {
	
	TabHost host=null;
	
	LinearLayout nav_back_layout=null;
	LinearLayout nav_add_layout=null;
	ImageView nav_add_image=null;
	TextView left_tab=null;
	TextView right_tab=null;
	
	LinearLayout menu_tab_layout=null;
	
	//加载的tab数量
	ArrayList<Tab> tabList=null;
	
	//判断是否执行动画效果
	boolean doAni=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_webtabmain);

		tabList=((ArrayList<Tab>) getIntent().getExtras().getSerializable("tabs"));
		
		if(getIntent().getExtras().getInt("btnType")==Hot.ADD_BUTTONTYPE) {
			doAni=true;
		}
		
		init();
	}
	
	public void init() {
		nav_back_layout=(LinearLayout) findViewById(R.id.nav_back_layout);
		nav_back_layout.setVisibility(View.VISIBLE);
		nav_back_layout.setOnClickListener(new LinearLayout.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}});
		nav_add_layout=(LinearLayout) findViewById(R.id.nav_add_layout);
		nav_add_image=(ImageView) findViewById(R.id.nav_add_image);
		if(doAni) {
			nav_add_image.setImageResource(R.drawable.nav_choice);
		}
		else {
			nav_add_image.setImageResource(R.drawable.nav_more);
		}
		left_tab=(TextView) findViewById(R.id.left_tab);
		left_tab.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				left_tab.setBackgroundResource(R.drawable.tab_left_press);
				right_tab.setBackgroundResource(R.drawable.tab_right_nor);
				setTab(0);
			}});
		right_tab=(TextView) findViewById(R.id.right_tab);
		right_tab.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				left_tab.setBackgroundResource(R.drawable.tab_left_nor);
				right_tab.setBackgroundResource(R.drawable.tab_right_press);
				setTab(1);
			}});
		
		host=getTabHost();
		
		left_tab.setText(tabList.get(0).getName());
		right_tab.setText(tabList.get(1).getName());
		
		for(int i=0;i<tabList.size();i++) {
			addTab("spec"+(i+1), WebInfoActivity.class, R.drawable.ic_launcher, i);
		}
		
		menu_tab_layout=(LinearLayout) findViewById(R.id.menu_tab_layout);
		if(getIntent().getExtras().getSerializable("menu")!=null&&((ArrayList<Menu>) getIntent().getExtras().getSerializable("menu")).size()>0) {
			nav_add_layout.setVisibility(View.VISIBLE);
			nav_add_layout.setOnClickListener(new LinearLayout.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ArrayList<Menu> menus=((ArrayList<Menu>) getIntent().getExtras().getSerializable("menu"));
					if(menus.size()==1) {
						menuJumpControll(menus, 0);
					}
					else {						
						if(menu_tab_layout.getVisibility()==View.VISIBLE) {
							menu_tab_layout.setVisibility(View.GONE);
							rotate(false);
						}
						else {
							menu_tab_layout.setVisibility(View.VISIBLE);
							rotate(true);
						}
					}
				}});
			addMenuLayout();
		}
		else {
			nav_add_layout.setVisibility(View.GONE);
		}
	}
	
	public void addTab(String tag, Class<?> cls, int drawable, int tabPos) {
		TabSpec spec=host.newTabSpec(tag);
		Intent intent=new Intent(WebInfoTabActivity.this, cls);
		Bundle bundle=new Bundle();
		bundle.putString("hotName", "");
		bundle.putInt("tabId", tabList.get(tabPos).getId());
		bundle.putInt("needBar", tabList.get(tabPos).getNeedBar());
		bundle.putString("api", tabList.get(tabPos).getApi());
		bundle.putParcelableArrayList("menu", null);
		if(getIntent().getExtras().getString("groupId")!=null) {
			bundle.putString("groupId", getIntent().getExtras().getString("groupId"));
		}		
		intent.putExtras(bundle);
		spec.setContent(intent);
		spec.setIndicator("", getResources().getDrawable(drawable));
		host.addTab(spec);
	}
	
	public void setTab(int index) {
		
		switch(index) {
		case 0:
			host.setCurrentTabByTag("spec1");
			break;
		case 1:
			host.setCurrentTabByTag("spec2");
			break;
		}
	}
	
	private void addMenuLayout() {
		final ArrayList<Menu> menus=((ArrayList<Menu>) getIntent().getExtras().getSerializable("menu"));
		for(int i=0;i<menus.size();i++) {
			View view=LayoutInflater.from(WebInfoTabActivity.this).inflate(R.layout.menu_view, null);
			TextView menu_text=(TextView) view.findViewById(R.id.menu_text);
			menu_text.setText(menus.get(i).getName());
			menu_tab_layout.addView(view);
			if(i!=(menus.size()-1)) {
				View view_split=LayoutInflater.from(WebInfoTabActivity.this).inflate(R.layout.view_splitline, null);
				menu_tab_layout.addView(view_split);
			}
			final int pos=i;
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					menuJumpControll(menus, pos);
				}});
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
			intent=new Intent(WebInfoTabActivity.this, WebInfoActivity.class);
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
			startActivityForResult(intent, 1102);
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
			intent=new Intent(WebInfoTabActivity.this, AddFormActivity.class);
			bundle=new Bundle();
			bundle.putString("hotName", menuName);
			bundle.putSerializable("AddForm", af);
			intent.putExtras(bundle);
			startActivityForResult(intent, 1101);
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK&&requestCode==1101) {
		
		}
		else if(resultCode==RESULT_OK&&requestCode==1102) {
			int needRefresh=data.getExtras().getInt("needRefresh");
			int tabindex=data.getExtras().getInt("tabindex");
			host.setCurrentTab(tabindex);
			if(needRefresh==1) {
				Intent intent=new Intent(OneKiloApplication.refreshWebInfoAction);
				Bundle bundle=new Bundle();
				bundle.putInt("hotId", tabList.get(tabindex).getId());
				intent.putExtras(bundle);
				sendBroadcast(intent);
			}
			System.out.println("onActivityResultTab");
		}
	}
	
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
}
