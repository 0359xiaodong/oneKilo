package com.morningtel.onekilo.hot;

import java.util.ArrayList;

import com.morningtel.onekilo.OneKiloApplication;
import com.morningtel.onekilo.R;
import com.morningtel.onekilo.addform.AddFormActivity;
import com.morningtel.onekilo.model.AddForm;
import com.morningtel.onekilo.model.Menu;
import com.morningtel.onekilo.model.Tab;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class WebInfoTabActivity extends TabActivity {
	
	TabHost host=null;
	
	ImageView nav_back=null;
	ImageView nav_add=null;
	TextView left_tab=null;
	TextView right_tab=null;
	
	LinearLayout menu_tab_layout=null;
	
	//加载的tab数量
	ArrayList<Tab> tabList=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_webtabmain);

		tabList=getIntent().getExtras().getParcelableArrayList("tabs");
		
		init();
	}
	
	public void init() {
		nav_back=(ImageView) findViewById(R.id.nav_back);
		nav_back.setVisibility(View.VISIBLE);
		nav_back.setOnClickListener(new ImageView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}});
		nav_add=(ImageView) findViewById(R.id.nav_add);
		left_tab=(TextView) findViewById(R.id.left_tab);
		left_tab.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setTab(0);
			}});
		right_tab=(TextView) findViewById(R.id.right_tab);
		right_tab.setOnClickListener(new TextView.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setTab(1);
			}});
		
		host=getTabHost();
		
		left_tab.setText(tabList.get(0).getName());
		right_tab.setText(tabList.get(1).getName());
		
		for(int i=0;i<tabList.size();i++) {
			addTab("spec"+(i+1), WebInfoActivity.class, R.drawable.ic_launcher, i);
		}
		
		menu_tab_layout=(LinearLayout) findViewById(R.id.menu_tab_layout);
		if(getIntent().getExtras().getParcelableArrayList("menu")!=null&&getIntent().getExtras().getParcelableArrayList("menu").size()>0) {
			nav_add.setVisibility(View.VISIBLE);
			nav_add.setOnClickListener(new ImageView.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					ArrayList<Menu> menus=getIntent().getExtras().getParcelableArrayList("menu");
					if(menus.size()==1) {
						menuJumpControll(menus, 0);
					}
					else {
						if(menu_tab_layout.getVisibility()==View.VISIBLE) {
							menu_tab_layout.setVisibility(View.GONE);
						}
						else {
							menu_tab_layout.setVisibility(View.VISIBLE);
						}
					}
				}});
			addMenuLayout();
		}
		else {
			nav_add.setVisibility(View.GONE);
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
		final ArrayList<Menu> menus=getIntent().getExtras().getParcelableArrayList("menu");
		for(int i=0;i<menus.size();i++) {
			View view=LayoutInflater.from(WebInfoTabActivity.this).inflate(R.layout.menu_view, null);
			TextView menu_text=(TextView) view.findViewById(R.id.menu_text);
			menu_text.setText(menus.get(i).getName());
			menu_tab_layout.addView(view);
			final int pos=i;
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					menu_tab_layout.setVisibility(View.GONE);
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
			bundle.putParcelable("AddForm", af);
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

}
