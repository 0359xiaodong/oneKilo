package com.morningtel.onekilo.community;

import java.util.ArrayList;

import com.lidroid.xutils.BitmapUtils;
import com.morningtel.onekilo.R;
import com.morningtel.onekilo.common.BitmapHelp;
import com.morningtel.onekilo.model.Group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommunityAdapter extends BaseAdapter {
	
	ArrayList<Group> group_list=null;
	Context context=null;
	
	public static BitmapUtils bitmapUtils;
	
	public CommunityAdapter(ArrayList<Group> group_list, Context context) {
		this.group_list=group_list;
		this.context=context;
		
		bitmapUtils=BitmapHelp.getBitmapUtils(context.getApplicationContext());
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return group_list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return group_list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		messageHolder holder=null;
		if(convertView==null) {
			convertView=LayoutInflater.from(context).inflate(R.layout.adapter_message, null);
			holder=new messageHolder();
			convertView.setTag(holder);
			
			holder.adapter_message_image=(ImageView) convertView.findViewById(R.id.adapter_message_image);
			holder.adapter_message_noread=(TextView) convertView.findViewById(R.id.adapter_message_noread);
			holder.adapter_message_text=(TextView) convertView.findViewById(R.id.adapter_message_text);
			holder.adapter_message_title=(TextView) convertView.findViewById(R.id.adapter_message_title);
		}
		else {
			holder=(messageHolder) convertView.getTag();
		}
		bitmapUtils.display(holder.adapter_message_image, group_list.get(position).getImageUrl());
		holder.adapter_message_text.setText(group_list.get(position).getDescription());
		holder.adapter_message_title.setText(group_list.get(position).getName());
		holder.adapter_message_text.setVisibility(View.GONE);
		return convertView;
	}

}

class messageHolder {
	ImageView adapter_message_image=null;
	TextView adapter_message_noread=null;
	TextView adapter_message_text=null;
	TextView adapter_message_title=null;
}
