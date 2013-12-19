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
	 * 组织类型
	 * 1-社区    2-学校    3-社交圈
	 * @hibernate.property
	 */
	private int groupType;
	
	/**
	 * 子类型
	 * a.  groupType：社交圈   1-业主圈  2-业主委员会圈  3-邻里圈   
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
	 * 根节点的Id
	 * @hibernate.property
	 */
	private String rootId;
	
	/**
	 * 总部id
	 * @hibernate.property
	 */
	private String generalId;
	
	/**
	 * @hibernate.property
	 */
	private String iconUrl;
	
	/**
	 * 简介
	 * @hibernate.property
	 */
	private String description;
	
	/**
	 * 微信绑定帐号个数
	 * @hibernate.property
	 */
	private int bindCount;
	
	private int userAuth;
	
	//辅助参数
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
