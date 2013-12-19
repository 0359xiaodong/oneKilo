package com.morningtel.onekilo.addform;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.morningtel.onekilo.BaseActivity;
import com.morningtel.onekilo.R;
import com.morningtel.onekilo.common.AlbumHelper;
import com.morningtel.onekilo.common.BitmapHelp;
import com.morningtel.onekilo.common.CommonUtils;
import com.morningtel.onekilo.model.ImageBucketModel;
import com.morningtel.onekilo.model.ImageItemModel;

public class ImageChoiceActivity extends BaseActivity {
	
	//专辑合集
	ArrayList<ImageBucketModel> model_list=null;
	//最近经常使用的图片合集
	ArrayList<ImageItemModel> recent_imageItemModelList=null;
	//专辑中图片合集
	ArrayList<ImageItemModel> imageItemModelList=null;
	//选中的图片合集
	ArrayList<ImageItemModel> choiceImageList=null;
	
	ImageView nav_back=null;
	TextView nav_title=null;
	
	ListView imagechoice_list=null;
	ImageChoiceAdapter adapter=null;
	GridView recent_image_grid=null;
	AdapterImageGrid recent_adapter=null;
	GridView whole_image_grid=null;
	AdapterImageGrid adapter_=null;
	LinearLayout image_haschoice=null;
	LinearLayout image_add_commit=null;
	TextView image_add_commitnum=null;
	
	AlbumHelper helper=null;
	
	Uri cameraUrl=null;
	File cameraFile=null;
	
	//之前选中的图片
	String[] uploadImage;
	
	public static BitmapUtils bitmapUtils;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_imagechoice);
		
		uploadImage=getIntent().getExtras().getStringArray("uploadImage");
		
        helper=AlbumHelper.getHelper();
        helper.init(getApplicationContext());
        
        recent_imageItemModelList=new ArrayList<ImageItemModel>();
        imageItemModelList=new ArrayList<ImageItemModel>();
        choiceImageList=new ArrayList<ImageItemModel>();
        
        bitmapUtils = BitmapHelp.getBitmapUtils(getApplicationContext());
        bitmapUtils.configDefaultLoadingImage(R.drawable.ic_launcher);
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_launcher);
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        
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
		nav_title=(TextView) findViewById(R.id.nav_title);
		nav_title.setText("相册");
		
		model_list=helper.getImageBucketModelList(uploadImage);
		choiceImageList.addAll(helper.getChoiceList());
					
		View view=LayoutInflater.from(ImageChoiceActivity.this).inflate(R.layout.headgrid_view, null);
		recent_image_grid=(GridView) view.findViewById(R.id.recent_image_grid);
		recent_image_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(arg2==0) {
					if(checkAddPermit()) {
						image_add_commit.startAnimation(AnimationUtils.loadAnimation(ImageChoiceActivity.this, R.anim.shakex));
						return ;
					}
					cameraFile=new File(Environment.getExternalStorageDirectory()+"/onekilo/"+System.currentTimeMillis()+".jpg");
					if(!cameraFile.exists()) {
						try {
							cameraFile.createNewFile();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					cameraUrl=Uri.fromFile(cameraFile);
					Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, cameraUrl);
					startActivityForResult(intent, 100);
				}
				else {					
					if(recent_imageItemModelList.get(arg2).isSelected==0) {
						if(checkAddPermit()) {
							image_add_commit.startAnimation(AnimationUtils.loadAnimation(ImageChoiceActivity.this, R.anim.shakex));
							return ;
						}
						else {
							recent_imageItemModelList.get(arg2).isSelected=1;	
							choiceImageList.add(recent_imageItemModelList.get(arg2));		
						}									
					}
					else {
						recent_imageItemModelList.get(arg2).isSelected=0;
						choiceImageList.remove(recent_imageItemModelList.get(arg2));					
					}
					addCommitLayout();
					recent_adapter.notifyDataSetChanged();
				}			
			}
		});
		//加载顶部headview中得图片
		loadHeadImage();
		recent_adapter=new AdapterImageGrid(recent_imageItemModelList, ImageChoiceActivity.this, true);
		recent_image_grid.setAdapter(recent_adapter);
		
		imagechoice_list=(ListView) findViewById(R.id.imagechoice_list);				
		adapter=new ImageChoiceAdapter(model_list, ImageChoiceActivity.this);
		imagechoice_list.addHeaderView(view);
		imagechoice_list.setAdapter(adapter);
		imagechoice_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				ArrayList<ImageItemModel> temp=model_list.get(arg2-1).imageList;
				imageItemModelList.clear();
				imageItemModelList.addAll(temp);
				adapter_.notifyDataSetChanged();
				whole_image_grid.setVisibility(View.VISIBLE);
				imagechoice_list.setVisibility(View.GONE);
			}
		});	
		
		whole_image_grid=(GridView) findViewById(R.id.whole_image_grid);
		adapter_=new AdapterImageGrid(imageItemModelList, ImageChoiceActivity.this);
		whole_image_grid.setAdapter(adapter_);
		whole_image_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub				
				if(imageItemModelList.get(position).isSelected==0) {
					if(checkAddPermit()) {
						image_add_commit.startAnimation(AnimationUtils.loadAnimation(ImageChoiceActivity.this, R.anim.shakex));
						return ;
					}
					else {
						imageItemModelList.get(position).isSelected=1;	
						choiceImageList.add(imageItemModelList.get(position));
					}					
				}
				else {
					imageItemModelList.get(position).isSelected=0;
					choiceImageList.remove(imageItemModelList.get(position));
				}
				addCommitLayout();
				adapter_.notifyDataSetChanged();
				if(recent_imageItemModelList.contains(imageItemModelList.get(position))) {
					recent_adapter.notifyDataSetChanged();
				}
												
			}
		});
		
		image_haschoice=(LinearLayout) findViewById(R.id.image_haschoice);
		image_add_commit=(LinearLayout) findViewById(R.id.image_add_commit);
		image_add_commit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=getIntent();
				Bundle bundle=new Bundle();
				bundle.putParcelableArrayList("upload", choiceImageList);
				intent.putExtras(bundle);
				setResult(RESULT_OK, intent);
				finish();
			}});
		image_add_commitnum=(TextView) findViewById(R.id.image_add_commitnum);
		
		addCommitLayout();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		helper.clearWhole();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK) {
			if(whole_image_grid.getVisibility()==View.VISIBLE) {
				whole_image_grid.setVisibility(View.GONE);
				imagechoice_list.setVisibility(View.VISIBLE);
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK) {
			if(requestCode==100) {
				Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);     
				Uri uri = Uri.fromFile(cameraFile);     
				intent.setData(uri);     
				sendBroadcast(intent);
				
				//判断是否存在照相文件
				ImageBucketModel containModel=null;
				for(int i=0;i<model_list.size();i++) {
					if(model_list.get(i).bucketName.equals("onekilo")) {
						containModel=model_list.get(i);
						break;
					}
				}
				ImageItemModel iiModel=new ImageItemModel();
				iiModel.imageId="";
				iiModel.imagePath=cameraFile.getPath();
				iiModel.isSelected=1;
				iiModel.thumbnailPath=cameraFile.getPath();
				//不存在文件，则直接添加进入
				if(containModel==null) {
					containModel=new ImageBucketModel();
					containModel.bucketName="onekilo";
					containModel.imageList=new ArrayList<ImageItemModel>();					
					containModel.imageList.add(iiModel);
					model_list.add(0, containModel);
				}
				else {
					containModel.imageList.add(0, iiModel);
				}
				//刷新头部最新添加视图
				loadHeadImage();
				recent_adapter.notifyDataSetChanged();
				//刷新列表
				adapter.notifyDataSetChanged();
				//刷新当前选中图片
				choiceImageList.add(iiModel);
				addCommitLayout();				
			}
		}
	}
	
	/**
	 * 加载顶部headview中得图片
	 */
	private void loadHeadImage() {
		recent_imageItemModelList.clear();
		recent_imageItemModelList.add(null);
		int tempCount=0;
		back:
		for(int i=0;i<model_list.size();i++) {
			for(int j=0;j<model_list.get(i).imageList.size();j++) {
				if(!CommonUtils.convertNull(model_list.get(i).imageList.get(j).thumbnailPath).equals("")) {
					recent_imageItemModelList.add(model_list.get(i).imageList.get(j));
					tempCount++;
					if(tempCount==5) {
						break back;
					}
				}
			}
		}
	}
	
	/**
	 * 添加选中图片
	 */
	public void addCommitLayout() {
		image_haschoice.removeAllViews();
		for(int i=0;i<choiceImageList.size();i++) {
			View view=LayoutInflater.from(ImageChoiceActivity.this).inflate(R.layout.choice_image_view, null);
			final int currentNum=i;
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					boolean isRefresh=recent_imageItemModelList.contains(choiceImageList.get(currentNum));	
					boolean isShowWholeRefresh=false;
					if(whole_image_grid.getVisibility()==View.VISIBLE&&imageItemModelList.contains(choiceImageList.get(currentNum))) {
						isShowWholeRefresh=true;
					}
					choiceImageList.get(currentNum).isSelected=0;
					choiceImageList.remove(choiceImageList.get(currentNum));
					addCommitLayout();
					//判断最近使用的图片中是否包含当前的图片
					if(isRefresh) {
						recent_adapter.notifyDataSetChanged();
					}
					if(isShowWholeRefresh) {
						adapter_.notifyDataSetChanged();
					}
				}});
			ImageView choice_image_commit=(ImageView) view.findViewById(R.id.choice_image_commit);
			bitmapUtils.display(choice_image_commit, choiceImageList.get(i).imagePath);
			image_haschoice.addView(view);
		}
		image_add_commitnum.setText(""+choiceImageList.size()+"/"+3);
	}
	
	private boolean checkAddPermit() {
		if(choiceImageList.size()==3) {
			return true;
		}
		return false;
	}
	
}
