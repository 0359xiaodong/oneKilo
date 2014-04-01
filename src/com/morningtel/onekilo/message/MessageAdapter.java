package com.morningtel.onekilo.message;

import java.util.LinkedList;

import com.lidroid.xutils.BitmapUtils;
import com.morningtel.onekilo.OneKiloApplication;
import com.morningtel.onekilo.R;
import com.morningtel.onekilo.common.BitmapHelp;
import com.morningtel.onekilo.model.MessageStatusModel;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MessageAdapter extends BaseAdapter {
	
	LinkedList<MessageStatusModel> model_list=null;
	Context context=null;
	
	public static BitmapUtils bitmapUtils;
	
	public MessageAdapter(LinkedList<MessageStatusModel> model_list, Context context) {
		this.model_list=model_list;
		this.context=context;
		
		bitmapUtils = BitmapHelp.getBitmapUtils(context.getApplicationContext());
        bitmapUtils.configDefaultLoadingImage(R.drawable.ic_launcher);
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_launcher);
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return model_list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return model_list.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MessageHolder holder=null;
		if(convertView==null) {
			convertView=LayoutInflater.from(context).inflate(R.layout.adapter_message, null);
			holder=new MessageHolder();
			convertView.setTag(holder);
			
			holder.adapter_message_image=(ImageView) convertView.findViewById(R.id.adapter_message_image);
			holder.adapter_message_noread=(TextView) convertView.findViewById(R.id.adapter_message_noread);
			holder.adapter_message_text=(TextView) convertView.findViewById(R.id.adapter_message_text);
			holder.adapter_message_title=(TextView) convertView.findViewById(R.id.adapter_message_title);
		}
		else {
			holder=(MessageHolder) convertView.getTag();
		}
		bitmapUtils.display(holder.adapter_message_image, ((OneKiloApplication) context.getApplicationContext()).pushIconUrl+model_list.get(position).getIcon());
		holder.adapter_message_text.setText(model_list.get(position).getContent());
		holder.adapter_message_title.setText(model_list.get(position).getTitle());
		if(model_list.get(position).getNoReadCount()>0) {
			holder.adapter_message_noread.setVisibility(View.VISIBLE);
			holder.adapter_message_noread.setText(""+model_list.get(position).getNoReadCount());
		}
		else {
			holder.adapter_message_noread.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}

}

class MessageHolder {
	ImageView adapter_message_image=null;
	TextView adapter_message_noread=null;
	TextView adapter_message_text=null;
	TextView adapter_message_title=null;
}
