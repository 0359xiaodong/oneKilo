package com.morningtel.onekilo.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @hibernate.class table="hot"
 * @author lhl
 */
public class Hot implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//hot类型
	//业主hot
	public static final int YEZHU_HOT = 1;
	//物业hot
	public static final int WUYE_HOT = 2;
	
	//单击动作类型
	//普通动作  触发tab界面  资讯
	public static final int WEBVIEW_VIEWTYPE = 2;
	// 资讯+资讯
	public static final int TABWEBVIEW_VIEWTYPE = 5;
	//定位动作
	public static final int LOCATION_VIEWTYPE = 11;
	//音频输入动作
	public static final int VOICE_VIEWTYPE = 12;
	//扫码动作
	public static final int CODE_VIEWTYPE = 13;
	
	//长按动作类型
	//音频输入
	public static final int VOICE_LONGPRESSTYPE = 22;
	
	//btn类型
	//+号btn
	public static final int ADD_BUTTONTYPE = 1;
	//三点更多btn
	public static final int MORE_BUTTONTYPE = 2;
	//设置btn
	public static final int SET_BUTTONTYPE = 3;
	
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
	 *2-资讯  5-资讯+资讯
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
	
	/**
	 * 长安动作类型
	 * @hibernate.property
	 */
	private int longPressType;
	
	/**
	 * 右上交按钮的类型
	 * 1-加号   2-三点   3-设置符号
	 * @hibernate.property
	 */
	private int btnType;
	
	// 辅助参数
	private String imageUrl;
	
	private String messageUrl;
	
	private ArrayList<Menu> menus;
	
	private ArrayList<Tab> tabs;
	
	private Tab longPressTab;
	
	private File icon;

	private String iconFileName;

	private String iconContentType;
	
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

	public File getIcon() {
		return icon;
	}

	public void setIcon(File icon) {
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
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

	public int getLongPressType() {
		return longPressType;
	}

	public void setLongPressType(int longPressType) {
		this.longPressType = longPressType;
	}

	public Tab getLongPressTab() {
		return longPressTab;
	}

	public void setLongPressTab(Tab longPressTab) {
		this.longPressTab = longPressTab;
	}

	public int getBtnType() {
		return btnType;
	}

	public void setBtnType(int btnType) {
		this.btnType = btnType;
	}
}
