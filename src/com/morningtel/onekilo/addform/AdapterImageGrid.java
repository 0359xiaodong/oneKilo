package com.morningtel.onekilo.addform;

import java.util.ArrayList;

import com.lidroid.xutils.BitmapUtils;
import com.morningtel.onekilo.R;
import com.morningtel.onekilo.common.BitmapHelp;
import com.morningtel.onekilo.common.CommonUtils;
import com.morningtel.onekilo.model.ImageItemModel;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class AdapterImageGrid extends BaseAdapter {
	
	ArrayList<ImageItemModel> imageItemModelList=null;
	Context context=null;
	boolean withCamera=false;
	
	public static BitmapUtils bitmapUtils;
	
	public AdapterImageGrid(ArrayList<ImageItemModel> imageItemModelList, Context context) {
		this.imageItemModelList=imageItemModelList;
		this.context=context;
		
		bitmapUtils = BitmapHelp.getBitmapUtils(context.getApplicationContext());
        bitmapUtils.configDefaultLoadingImage(R.drawable.ic_launcher);
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_launcher);
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
	}
	
	public AdapterImageGrid(ArrayList<ImageItemModel> imageItemModelList, Context context, boolean withCamera) {
		this.imageItemModelList=imageItemModelList;
		this.context=context;
		this.withCamera=withCamera;
		
		bitmapUtils = BitmapHelp.getBitmapUtils(context.getApplicationContext());
        bitmapUtils.configDefaultLoadingImage(R.drawable.ic_launcher);
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_launcher);
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imageItemModelList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return imageItemModelList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		WholeImageHolder holder=null;
		if(convertView==null) {
			convertView=LayoutInflater.from(context).inflate(R.layout.adapter_image_grid, null);
			holder=new WholeImageHolder();
			holder.whole_image=(ImageView) convertView.findViewById(R.id.whole_image);
			holder.whole_image_isselect=(ImageView) convertView.findViewById(R.id.whole_image_isselect);
			convertView.setTag(holder);
		}
		else {
			holder=(WholeImageHolder)convertView.getTag();
		}
		if(withCamera&&position==0) {
			holder.whole_image.setImageResource(R.drawable.btn_photo_album_camera);
		}
		else {
			if(CommonUtils.convertNull(imageItemModelList.get(position).imagePath).equals("")) {
				holder.whole_image.setImageResource(R.drawable.ic_launcher);
			}
			else {
				bitmapUtils.display(holder.whole_image, imageItemModelList.get(position).imagePath);
				
			}
			if(imageItemModelList.get(position).isSelected==1) {
				holder.whole_image_isselect.setVisibility(View.VISIBLE);
			}
			else {
				holder.whole_image_isselect.setVisibility(View.GONE);
			}
		}				
		return convertView;
	}

}

class WholeImageHolder {
	ImageView whole_image=null;
	ImageView whole_image_isselect=null;
}
