package com.morningtel.onekilo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AddForm implements Parcelable {
	
	/**
     * @hibernate.id generator-class="uuid"
     */
	private String id;
	
	/**
	 * ��title���䵽��̨
	 * �Ƿ���Ҫ���� �ǣ���ʾ�����֣���������⣩   ��null or ��
	 * @hibernate.property
	 */
	private String needTitle;
	
	/**
	 * ��content���䵽��̨
	 * �Ƿ���Ҫ����  �ǣ���ʾ�����֣����������ݣ�   ��null or ��
	 * @hibernate.property
	 */
	private String needContent;
	
	/**
	 * ��image���䵽��̨
	 * �Ƿ���ҪͼƬ�ϴ�  �ǣ���������������3��   ��null or ��
	 * @hibernate.property
	 */
	private String needImage;
	
	/**
	 * ��geoΪ���������䵽��̨
	 * �Ƿ���Ҫgps����  �ǣ���ʱʱ�䣨5��   ��null or ��
	 * @hibernate.property
	 */
	private String needGeo;
	
	/**
	 * ��mapGeoΪ���������䵽��̨
	 * �Ƿ���Ҫչʾ��ͼ  �ǣ���ʱʱ�䣨5��   ��null or ��
	 * @hibernate.property
	 */
	private String needMap;
	
	/**
	 * ��isAnonymityΪ���������䵽��̨
	 * �Ƿ���Ҫ������ť  �ǣ���1��   ��null or ��
	 * @hibernate.property
	 */
	private String needAnonymity;
	
	/**
	 * �ύ�ĺ�̨��ַ
	 * @hibernate.property
	 */
	private String api;
	
	/**
	 * �������Ӳ���
	 * @hibernate.property
	 */
	private String otherParam;
	
	private int belongId;
	
	public int getBelongId() {
		return belongId;
	}

	public void setBelongId(int belongId) {
		this.belongId = belongId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNeedTitle() {
		return needTitle;
	}

	public void setNeedTitle(String needTitle) {
		this.needTitle = needTitle;
	}

	public String getNeedContent() {
		return needContent;
	}

	public void setNeedContent(String needContent) {
		this.needContent = needContent;
	}

	public String getNeedImage() {
		return needImage;
	}

	public void setNeedImage(String needImage) {
		this.needImage = needImage;
	}

	public String getNeedGeo() {
		return needGeo;
	}

	public void setNeedGeo(String needGeo) {
		this.needGeo = needGeo;
	}

	public String getNeedMap() {
		return needMap;
	}

	public void setNeedMap(String needMap) {
		this.needMap = needMap;
	}

	public String getNeedAnonymity() {
		return needAnonymity;
	}

	public void setNeedAnonymity(String needAnonymity) {
		this.needAnonymity = needAnonymity;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public String getOtherParam() {
		return otherParam;
	}

	public void setOtherParam(String otherParam) {
		this.otherParam = otherParam;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(id);
		dest.writeString(needTitle);
		dest.writeString(needContent);
		dest.writeString(needImage);
		dest.writeString(needGeo);
		dest.writeString(needMap);
		dest.writeString(needAnonymity);
		dest.writeString(api);
		dest.writeString(otherParam);
		dest.writeInt(belongId);
	}
	
	public static final Parcelable.Creator<AddForm> CREATOR = new Parcelable.Creator<AddForm>() {

		@Override
		public AddForm createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			AddForm af=new AddForm();
			af.id=source.readString();
			af.needTitle=source.readString();
			af.needContent=source.readString();
			af.needImage=source.readString();
			af.needGeo=source.readString();
			af.needMap=source.readString();
			af.needAnonymity=source.readString();
			af.api=source.readString();
			af.otherParam=source.readString();
			af.belongId=source.readInt();
			return af;
		}

		@Override
		public AddForm[] newArray(int size) {
			// TODO Auto-generated method stub
			return null;
		}};
}