package com.morningtel.onekilo.localService;

import java.util.ArrayList;

import com.lidroid.xutils.BitmapUtils;
import com.morningtel.onekilo.R;
import com.morningtel.onekilo.bundle.LauncherActivity;
import com.morningtel.onekilo.common.BitmapHelp;
import com.morningtel.onekilo.common.CommonUtils;
import com.morningtel.onekilo.hot.WebInfoActivity;
import com.morningtel.onekilo.hot.WebInfoTabActivity;
import com.morningtel.onekilo.model.Hot;
import com.morningtel.onekilo.sign.SignActivity;
import com.morningtel.onekilo.voice.VoiceSignActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LocalServiceAdapter extends BaseAdapter {
	
	ArrayList<Hot> hot_list=null;
	Context context=null;
	
	public static BitmapUtils bitmapUtils_ser;
	
	public LocalServiceAdapter(ArrayList<Hot> hot_list, Context context) {
		this.hot_list=hot_list;
		this.context=context;
		
		bitmapUtils_ser=BitmapHelp.getBitmapUtils(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return hot_list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return hot_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ServiceHolder holder=null;
		if(convertView==null) {
			convertView=LayoutInflater.from(context).inflate(R.layout.localservice_chatlayoutrow, null);
			holder=new ServiceHolder();
			holder.chat_layout_item_image=(ImageView) convertView.findViewById(R.id.chat_layout_item_image);
			holder.chat_new_notify=(ImageView) convertView.findViewById(R.id.chat_new_notify);
			holder.chat_layout_item_text=(TextView) convertView.findViewById(R.id.chat_layout_item_text);
			convertView.setTag(holder);
		}
		else {
			holder=(ServiceHolder) convertView.getTag();
		}
		bitmapUtils_ser.display(holder.chat_layout_item_image, hot_list.get(position).getImageUrl());
		final int position_=position;
		holder.chat_layout_item_image.setOnClickListener(new ImageView.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(hot_list.get(position_).getViewType()==Hot.CODE_VIEWTYPE&&android.os.Build.VERSION.SDK_INT<15) {
					CommonUtils.showCustomToast(context, "您的系统版本过低，暂时不支持"+hot_list.get(position_).getTabs().get(0).getName()+"功能");
					return ;
				}
				Intent intent=null;
				Bundle bundle=new Bundle();
				switch(hot_list.get(position_).getViewType()) {
				case Hot.WEBVIEW_VIEWTYPE:
					intent=new Intent(context, WebInfoActivity.class);
					bundle.putInt("tabId", hot_list.get(position_).getTabs().get(0).getId());
					bundle.putString("hotName", hot_list.get(position_).getHotName());
					bundle.putInt("needBar", hot_list.get(position_).getTabs().get(0).getNeedBar());
					bundle.putString("api", hot_list.get(position_).getTabs().get(0).getApi());
					bundle.putInt("btnType", hot_list.get(position_).getBtnType());
					bundle.putSerializable("menu", hot_list.get(position_).getMenus());
					break;
				case Hot.TABWEBVIEW_VIEWTYPE:
					intent=new Intent(context, WebInfoTabActivity.class);
					bundle.putSerializable("tabs", hot_list.get(position_).getTabs());
					bundle.putString("hotName", hot_list.get(position_).getHotName());
					bundle.putSerializable("menu", hot_list.get(position_).getMenus());
					bundle.putInt("btnType", hot_list.get(position_).getBtnType());
					break;
				case Hot.LOCATION_VIEWTYPE:
					intent=new Intent(context, SignActivity.class);
					bundle.putString("api", hot_list.get(position_).getTabs().get(0).getApi());
					bundle.putString("hotName", hot_list.get(position_).getTabs().get(0).getName());
					break;
				case Hot.VOICE_VIEWTYPE:
					break;
				case Hot.CODE_VIEWTYPE:
					intent=new Intent(context, LauncherActivity.class);
					bundle.putString("api", hot_list.get(position_).getTabs().get(0).getApi());
					bundle.putString("hotName", hot_list.get(position_).getTabs().get(0).getName());
					break;
				}
				intent.putExtras(bundle);
				context.startActivity(intent);
			}});
		holder.chat_layout_item_image.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				if(hot_list.get(position_).getLongPressTab()!=null) {
					switch(hot_list.get(position_).getLongPressTab().getViewType()) {
					case Hot.VOICE_LONGPRESSTYPE:
						Intent intent=new Intent(context, VoiceSignActivity.class);
						Bundle bundle=new Bundle();
						bundle.putString("api", hot_list.get(position_).getLongPressTab().getApi());
						intent.putExtras(bundle);
						context.startActivity(intent);
						break;
					}
				}
				return false;
			}
		});
		String temp_name=hot_list.get(position).getHotName();
		holder.chat_layout_item_text.setText(temp_name);
		holder.chat_new_notify.setVisibility(View.GONE);
		return convertView;
	}

}

class ServiceHolder {
	ImageView chat_layout_item_image=null;
	ImageView chat_new_notify=null;
	TextView chat_layout_item_text=null;
}
