package com.morningtel.onekilo.addform;

import java.io.File;
import java.util.ArrayList;

import com.lidroid.xutils.BitmapUtils;
import com.morningtel.onekilo.R;
import com.morningtel.onekilo.common.BitmapHelp;
import com.morningtel.onekilo.common.CommonUtils;
import com.morningtel.onekilo.model.ImageBucketModel;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageChoiceAdapter extends BaseAdapter {
	
	ArrayList<ImageBucketModel> model_list=null;
	Context context=null;
	
	public static BitmapUtils bitmapUtils;
	
	public ImageChoiceAdapter(ArrayList<ImageBucketModel> model_list, Context context) {
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
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return model_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageChoiceHolder holder=null;
		if(convertView==null) {
			convertView=LayoutInflater.from(context).inflate(R.layout.adapter_imagechoice, null);
			holder=new ImageChoiceHolder();
			holder.imagechoice_image=(ImageView) convertView.findViewById(R.id.imagechoice_image);
			holder.imagechoice_title=(TextView) convertView.findViewById(R.id.imagechoice_title);
			convertView.setTag(holder);
		}
		else {
			holder=(ImageChoiceHolder) convertView.getTag();
		}
		holder.imagechoice_title.setText(model_list.get(position).bucketName+"("+model_list.get(position).imageList.size()+")");
		String imageSrc="";
		for(int i=0;i<model_list.get(position).imageList.size();i++) {
			if(!CommonUtils.convertNull(model_list.get(position).imageList.get(i).thumbnailPath).equals("")) {
				File file=new File(model_list.get(position).imageList.get(i).thumbnailPath);
				if(file!=null&&file.exists()) {
					imageSrc=model_list.get(position).imageList.get(i).thumbnailPath;
					break;
				}
			}			
		}
		if(imageSrc.equals("")) {
			holder.imagechoice_image.setImageResource(R.drawable.ic_launcher);
		}
		else {
			bitmapUtils.display(holder.imagechoice_image, imageSrc);
		}
		return convertView;
	}

}

class ImageChoiceHolder {
	ImageView imagechoice_image=null;
	TextView imagechoice_title=null;
}
