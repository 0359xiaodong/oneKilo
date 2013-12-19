package com.morningtel.onekilo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Hot implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public static final int YEZHU_HOT = 1;
	
	public static final int WUYE_HOT = 2;
	
	/**
	 * @hibernate.id generator-class="native"
	 */
	private int id;
	
	/**
	 * 热点名称
	 * @hibernate.property
	 */
	private String hotName;
	
	/**
	 * 热点类型
	 * 1-业主热点  2-物业热点   
	 * @hibernate.property
	 */
	private int hotType;
	
	/**
	 * 热点方向 0-所有方向 User->status
	 * @hibernate.property
	 */
	private int hotTo;
	
	/**
	 * 热点针对对象    User->userType
	 * @hibernate.property
	 */
	private int hotFor;
	
	/**
	 * 热点针对的性别
	 * @hibernate.property
	 */
	private int hotForgender;
	
	/**
	 * 热点针对的城市
	 * @hibernate.property
	 */
	private int hotForCity;
	
	/**
	 * 热点的热度  0 代表不显示在界面上
	 * @hibernate.property
	 */
	private int hotMore;
	
	/**
	 * 热点的描述
	 * @hibernate.property
	 */
	private String hotDesc;
	
	/**
	 * 是否需要浏览器的 底栏工具条
	 * 1-需要  0-不需要
	 *  @hibernate.property
	 */
	private int needBar;
	
	/**
	 * 视图类型
	 * 1-论坛   2-资讯    3-论坛+资讯    4-资讯+论坛   5-资讯+资讯   6-论坛+论坛
	 *  @hibernate.property
	 */
	private int viewType;
	
	/**
	 * icon地址
	 * @hibernate.property
	 */
	private String iconUrl;

	/**
	 *	排序号
	 * @hibernate.property
	 */
	private int orderNum;
	
	/**
	 * 消息界面图标
	 * @hibernate.property
	 */
	private String messageIconUrl;
	
	// 辅助参数
	private String String;
	
	private String messageUrl;
	
	private ArrayList<Menu> menus;
	
	private ArrayList<Tab> tabs;
	
	private String icon;

	private String iconFileName;

	private String iconContentType;
	
	private String imageUrl;
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHotName() {
		return hotName;
	}

	public void setHotName(String hotName) {
		this.hotName = hotName;
	}

	public int getHotTo() {
		return hotTo;
	}

	public void setHotTo(int hotTo) {
		this.hotTo = hotTo;
	}

	public int getHotMore() {
		return hotMore;
	}

	public void setHotMore(int hotMore) {
		this.hotMore = hotMore;
	}

	public int getHotFor() {
		return hotFor;
	}

	public void setHotFor(int hotFor) {
		this.hotFor = hotFor;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
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

	public String getHotTopic() {
		return hotDesc;
	}

	public void setHotTopic(String hotTopic) {
		this.hotDesc = hotTopic;
	}
	
	public String getString() {
		return String;
	}

	public void setString(String String) {
		this.String = String;
	}

	public int getHotForgender() {
		return hotForgender;
	}

	public void setHotForgender(int hotForgender) {
		this.hotForgender = hotForgender;
	}

	public int getHotForCity() {
		return hotForCity;
	}

	public void setHotForCity(int hotForCity) {
		this.hotForCity = hotForCity;
	}

	public ArrayList<Menu> getMenus() {
		return menus;
	}

	public void setMenus(ArrayList<Menu> menus) {
		this.menus = menus;
	}

	public int getHotType() {
		return hotType;
	}

	public void setHotType(int hotType) {
		this.hotType = hotType;
	}

	public int getNeedBar() {
		return needBar;
	}

	public void setNeedBar(int needBar) {
		this.needBar = needBar;
	}

	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getMessageIconUrl() {
		return messageIconUrl;
	}

	public void setMessageIconUrl(String messageIconUrl) {
		this.messageIconUrl = messageIconUrl;
	}

	public String getMessageUrl() {
		return messageUrl;
	}

	public void setMessageUrl(String messageUrl) {
		this.messageUrl = messageUrl;
	}

	public ArrayList<Tab> getTabs() {
		return tabs;
	}

	public void setTabs(ArrayList<Tab> tabs) {
		this.tabs = tabs;
	}

	public String getHotDesc() {
		return hotDesc;
	}

	public void setHotDesc(String hotDesc) {
		this.hotDesc = hotDesc;
	}
}
