package com.morningtel.onekilo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Tab implements Parcelable {
	
	/**
	 * @hibernate.id generator-class="native"
	 */
	private int id;
	
	/**
	 * �˵�����
	 * @hibernate.property
	 */
	private String name;
	
	/**
	 * �˵�����
	 * 1-html5����  2-��ӵ�����  3-����΢��   4-����΢��   5-ȡ����ע   6-������ӱ�
	 * @hibernate.property
	 */
	private int menuType;
	
	/**
	 * �����
	 * @hibernate.property
	 */
	private int orderNum;
	
	/**
	 * ���ýӿ�
	 * @hibernate.property
	 */
	private String api;
	
	/**
	 * ����Id
	 * @hibernate.property
	 */
	private String belongId;
	
	/**
	 * Ȩ�޿ɼ�
	 * @hibernate.property
	 */
	private int userAuth;
	
	private int needSearch;
	
	public int getNeedSearch() {
		return needSearch;
	}

	public void setNeedSearch(int needSearch) {
		this.needSearch = needSearch;
	}
	
	private int needBar;

	public int getNeedBar() {
		return needBar;
	}

	public void setNeedBar(int needBar) {
		this.needBar = needBar;
	}
	
	private int viewType;
	
	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	private int needGeo;
	
	public int getNeedGeo() {
		return needGeo;
	}

	public void setNeedGeo(int needGeo) {
		this.needGeo = needGeo;
	}

	public int getId() {
		return id;
	}
    
	public void setId(int id) {
		this.id = id;
	}
    
	public String getName() {
		return name;
	}
    
	public void setName(String name) {
		this.name = name;
	}
    
	public int getMenuType() {
		return menuType;
	}
    
	public void setMenuType(int menuType) {
		this.menuType = menuType;
	}
    
	public int getOrderNum() {
		return orderNum;
	}
    
	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}
    
	public String getApi() {
		return api;
	}
    
	public void setApi(String api) {
		this.api = api;
	}
    
	public String getBelongId() {
		return belongId;
	}
    
	public void setBelongId(String belongId) {
		this.belongId = belongId;
	}
    
	public int getUserAuth() {
		return userAuth;
	}
    
	public void setUserAuth(int userAuth) {
		this.userAuth = userAuth;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(id);
		dest.writeString(name);
		dest.writeInt(menuType);
		dest.writeInt(orderNum);
		dest.writeString(api);
		dest.writeString(belongId);
		dest.writeInt(userAuth);
		dest.writeInt(needSearch);
		dest.writeInt(needBar);
		dest.writeInt(viewType);
		dest.writeInt(needGeo);
	}
	
	public static final Parcelable.Creator<Tab> CREATOR = new Parcelable.Creator<Tab>() {

		@Override
		public Tab createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			Tab tab=new Tab();
			tab.id=source.readInt();
			tab.name=source.readString();
			tab.menuType=source.readInt();
			tab.orderNum=source.readInt();
			tab.api=source.readString();
			tab.belongId=source.readString();
			tab.userAuth=source.readInt();
			tab.needSearch=source.readInt();
			tab.needBar=source.readInt();
			tab.needBar=source.readInt();
			tab.needBar=source.readInt();
			return tab;
		}

		@Override
		public Tab[] newArray(int size) {
			// TODO Auto-generated method stub
			return null;
		}
		
	};
}
