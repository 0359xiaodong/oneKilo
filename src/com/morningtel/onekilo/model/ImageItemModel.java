package com.morningtel.onekilo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageItemModel implements Parcelable {
	
	public String imageId;
	public String thumbnailPath;
	public String imagePath;
	public int isSelected = 0;
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(imageId);
		dest.writeString(thumbnailPath);
		dest.writeString(imagePath);
		dest.writeInt(isSelected);
	}
	
	public static final Parcelable.Creator<ImageItemModel> CREATOR = new Parcelable.Creator<ImageItemModel>() {   
	 
        @Override  
        public ImageItemModel createFromParcel(Parcel source) {   
        	ImageItemModel model = new ImageItemModel();   
        	model.imageId=source.readString();
        	model.thumbnailPath=source.readString();
        	model.imagePath=source.readString();
        	model.isSelected=source.readInt();
            return model;   
        }   
  
        @Override  
        public ImageItemModel[] newArray(int size) {   
            // TODO Auto-generated method stub   
            return null;   
        }   
    }; 
}
