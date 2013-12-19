package com.morningtel.onekilo.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.morningtel.onekilo.model.ImageBucketModel;
import com.morningtel.onekilo.model.ImageItemModel;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Images.Thumbnails;

public class AlbumHelper {
	
	private static AlbumHelper instance;
	ContentResolver cr=null;
	Context context=null;
	//缩略图列表
	HashMap<String, String> thumbnailList=new HashMap<String, String>();
	//专辑列表
	HashMap<String, ImageBucketModel> bucketList=new HashMap<String, ImageBucketModel>();
	//选中图片列表
	ArrayList<ImageItemModel> choiceList=new ArrayList<ImageItemModel>();
	
	public static AlbumHelper getHelper() {
		if(instance==null) {
			instance=new AlbumHelper();
		}
		return instance;
	}
	
	public void init(Context context) {
		if(this.context==null) {
			this.context=context;
		}
		cr=context.getContentResolver();
	}
	
	/**
	 * 获取所有相册图片
	 * @param uploadImage
	 * @return
	 */
	public ArrayList<ImageBucketModel> getImageBucketModelList(String[] uploadImage) {
		thumbnailList.clear();
		bucketList.clear();
		ArrayList<ImageBucketModel> tempList=new ArrayList<ImageBucketModel>();
		buildImagesBucketList(uploadImage);

		Iterator<Entry<String, ImageBucketModel>> it=bucketList.entrySet().iterator();
		while(it.hasNext()) {
			Entry<String, ImageBucketModel> entry=it.next();
			ImageBucketModel model=entry.getValue();
			if(model.bucketName.toLowerCase().indexOf("camera")!=-1||model.bucketName.toLowerCase().indexOf("onekilo")!=-1) {
				tempList.add(0, model);
			}
			else {
				tempList.add(model);
			}
		}
		return tempList;
	}
	
	/**
	 * 获取选中图片
	 * @return
	 */
	public ArrayList<ImageItemModel> getChoiceList() {
		return choiceList;
	}
	
	/**
	 * 清除缓存
	 */
	public void clearWhole() {
		thumbnailList.clear();
		bucketList.clear();
		choiceList.clear();
	}
	
	private void buildImagesBucketList(String[] uploadImage) {
		ArrayList<String> imageList=new ArrayList<String>();
		for(int i=0;i<uploadImage.length;i++) {
			imageList.add(uploadImage[i]);
		}
		getThumbnail();
		String[] projection={Media._ID, Media.BUCKET_ID, Media.PICASA_ID, Media.DATA, Media.DISPLAY_NAME, Media.TITLE, Media.SIZE, Media.BUCKET_DISPLAY_NAME};
		Cursor cs=cr.query(Media.EXTERNAL_CONTENT_URI, projection, null, null, Media._ID+" DESC");
		if(cs.moveToFirst()) {
			int photoIDIndex=cs.getColumnIndex(Media._ID);
			int photoPathIndex=cs.getColumnIndex(Media.DATA);
			int photoNameIndex=cs.getColumnIndex(Media.DISPLAY_NAME);
			int photoTitleIndex=cs.getColumnIndex(Media.TITLE);
			int photoSizeIndex=cs.getColumnIndex(Media.SIZE);
			int bucketDisplayNameIndex=cs.getColumnIndex(Media.BUCKET_DISPLAY_NAME);
			int bucketIdIndex=cs.getColumnIndex(Media.BUCKET_ID);
			int picasaIdIndex=cs.getColumnIndex(Media.PICASA_ID);
			do {
				String _id=cs.getString(photoIDIndex);
				String name=cs.getString(photoNameIndex);
				String path=cs.getString(photoPathIndex);
				String title=cs.getString(photoTitleIndex);
				String size=cs.getString(photoSizeIndex);
				String bucketName=cs.getString(bucketDisplayNameIndex);
				String bucketId=cs.getString(bucketIdIndex);
				String picasaId=cs.getString(picasaIdIndex);
				
				ImageBucketModel ib=bucketList.get(bucketId);
				if(ib==null) {
					ib=new ImageBucketModel();
					bucketList.put(bucketId, ib);
					ib.imageList=new ArrayList<ImageItemModel>();
					ib.bucketName=bucketName;
				}
				ImageItemModel im=new ImageItemModel();
				im.imageId=_id;
				im.imagePath=path;
				if(imageList.contains(path)) {
					im.isSelected=1;
				}
				else {
					im.isSelected=0;
				}
				im.thumbnailPath=thumbnailList.get(_id);
				ib.imageList.add(im);
				if(imageList.contains(path)) {
					choiceList.add(im);
				}
				
			}while(cs.moveToNext());
			
		}
	}
	
	/**
	 * 得到缩略图
	 */
	private void getThumbnail() {
		String[] projection={Thumbnails._ID, Thumbnails.IMAGE_ID, Thumbnails.DATA};
		Cursor cs=cr.query(Thumbnails.EXTERNAL_CONTENT_URI, projection, null, null, Thumbnails.IMAGE_ID+" DESC");
		getThumbnailColumnData(cs);
	}
	
	/**
	 * 从数据库中得到缩略图
	 * @param cs
	 */
	private void getThumbnailColumnData(Cursor cs) {
		if(cs.moveToFirst()) {
			int image_id=0;
			String image_path="";
			int image_idColumn=cs.getColumnIndex(Thumbnails.IMAGE_ID);
			int dataColumn=cs.getColumnIndex(Thumbnails.DATA);
			do {
				image_id=cs.getInt(image_idColumn);
				image_path=cs.getString(dataColumn);
				thumbnailList.put(""+image_id, image_path);
			} while(cs.moveToNext());
		}
	}
	
	public String getOriginalImagePath(String image_id) {
		String path="";
		String projection[]={Media._ID, Media.DATA};
		Cursor cs=cr.query(Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
		cs.moveToFirst();
		if(cs.getCount()>0) {
			path=cs.getString(cs.getColumnIndex(Media.DATA));
		}
		return path;
	}
	
}
