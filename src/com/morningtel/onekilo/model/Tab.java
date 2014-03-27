package com.morningtel.onekilo.model;

import java.io.Serializable;

public class Tab implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @hibernate.id generator-class="native"
	 */
	private int id;
	
	/**
	 * 菜单名称
	 * @hibernate.property
	 */
	private String name;
	
	/**
	 * 菜单类型
	 * 1-html5链接  2-添加到桌面  3-分享到微信   4-分享到微博   5-取消关注   6-进入添加表单
	 * @hibernate.property
	 */
	private int menuType;
	
	/**
	 * 排序号
	 * @hibernate.property
	 */
	private int orderNum;
	
	/**
	 * 调用接口
	 * @hibernate.property
	 */
	private String api;
	
	/**
	 * 所属Id
	 * @hibernate.property
	 */
	private String belongId;
	
	/**
	 * 权限可见
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

}
