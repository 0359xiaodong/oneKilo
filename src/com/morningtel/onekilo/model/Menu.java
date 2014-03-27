package com.morningtel.onekilo.model;

import java.io.Serializable;

public class Menu implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

}
