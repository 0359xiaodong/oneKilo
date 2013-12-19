package com.morningtel.onekilo.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Serializable {
		
	private static final long serialVersionUID = 4004368314999598818L;

	/**
	 * @hibernate.id generator-class="uuid"
	 */
	private String id;
	
	/**
	 * ��֯����
	 * 1-����    2-ѧУ    3-�罻Ȧ
	 * @hibernate.property
	 */
	private int groupType;
	
	/**
	 * ������
	 * a.  groupType���罻Ȧ   1-ҵ��Ȧ  2-ҵ��ίԱ��Ȧ  3-����Ȧ   
	 * 
	 * @hibernate.property
	 */
	private int minType;
	
	/**
	 * @hibernate.property
	 */
	private String name;
	
	/**
	 * @hibernate.property
	 */
	private String createUserId;
	
	/**
	 * @hibernate.property
	 */
	private int createDate;
	
	/**
	 * @hibernate.property
	 */
	private String parentId;
	
	/**
	 * ���ڵ��Id
	 * @hibernate.property
	 */
	private String rootId;
	
	/**
	 * �ܲ�id
	 * @hibernate.property
	 */
	private String generalId;
	
	/**
	 * @hibernate.property
	 */
	private String iconUrl;
	
	/**
	 * ���
	 * @hibernate.property
	 */
	private String description;
	
	/**
	 * ΢�Ű��ʺŸ���
	 * @hibernate.property
	 */
	private int bindCount;
	
	private int userAuth;
	
	//��������
	private String imageUrl;
	
	private String icon;

	private String iconFileName;

	private String iconContentType;
	
	private ArrayList<Tab> tabs;
	
	private ArrayList<Menu> menus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getGroupType() {
		return groupType;
	}

	public void setGroupType(int groupType) {
		this.groupType = groupType;
	}

	public int getMinType() {
		return minType;
	}

	public void setMinType(int minType) {
		this.minType = minType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public int getCreateDate() {
		return createDate;
	}

	public void setCreateDate(int createDate) {
		this.createDate = createDate;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getRootId() {
		return rootId;
	}

	public void setRootId(String rootId) {
		this.rootId = rootId;
	}

	public String getGeneralId() {
		return generalId;
	}

	public void setGeneralId(String generalId) {
		this.generalId = generalId;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getBindCount() {
		return bindCount;
	}

	public void setBindCount(int bindCount) {
		this.bindCount = bindCount;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIconFileName() {
		return iconFileName;
	}

	public void setIconFileName(String iconFileName) {
		this.iconFileName = iconFileName;
	}

	public String getIconContentType() {
		return iconContentType;
	}

	public void setIconContentType(String iconContentType) {
		this.iconContentType = iconContentType;
	}

	public ArrayList<Tab> getTabs() {
		return tabs;
	}

	public void setTabs(ArrayList<Tab> tabs) {
		this.tabs = tabs;
	}

	public ArrayList<Menu> getMenus() {
		return menus;
	}

	public void setMenus(ArrayList<Menu> menus) {
		this.menus = menus;
	}

	public int getUserAuth() {
		return userAuth;
	}

	public void setUserAuth(int userAuth) {
		this.userAuth = userAuth;
	}
	
	public void createImageUrl(){}
}
