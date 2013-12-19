package com.morningtel.onekilo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Menu implements Parcelable {
	
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
	
	private AddForm addForm;

	public AddForm getAddForm() {
		return addForm;
	}

	public void setAddForm(AddForm addForm) {
		this.addForm = addForm;
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
		dest.writeParcelable(addForm, flags);
	}
	
	public static final Parcelable.Creator<Menu> CREATOR = new Parcelable.Creator<Menu>() {

		@Override
		public Menu createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			Menu menu=new Menu();
			menu.id=source.readInt();
			menu.name=source.readString();
			menu.menuType=source.readInt();
			menu.orderNum=source.readInt();
			menu.api=source.readString();
			menu.belongId=source.readString();
			menu.userAuth=source.readInt();
			menu.addForm=source.readParcelable(getClass().getClassLoader());
			return menu;
		}

		@Override
		public Menu[] newArray(int size) {
			// TODO Auto-generated method stub
			return null;
		}
		
	};
}
