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
	
	//hot����
	//ҵ��hot
	public static final int YEZHU_HOT = 1;
	//��ҵhot
	public static final int WUYE_HOT = 2;
	
	//������������
	//��ͨ����  ����tab����  ��Ѷ
	public static final int WEBVIEW_VIEWTYPE = 2;
	// ��Ѷ+��Ѷ
	public static final int TABWEBVIEW_VIEWTYPE = 5;
	//��λ����
	public static final int LOCATION_VIEWTYPE = 11;
	//��Ƶ���붯��
	public static final int VOICE_VIEWTYPE = 12;
	//ɨ�붯��
	public static final int CODE_VIEWTYPE = 13;
	
	//������������
	//��Ƶ����
	public static final int VOICE_LONGPRESSTYPE = 22;
	
	//btn����
	//+��btn
	public static final int ADD_BUTTONTYPE = 1;
	//�������btn
	public static final int MORE_BUTTONTYPE = 2;
	//����btn
	public static final int SET_BUTTONTYPE = 3;
	
	/**
	 * @hibernate.id generator-class="native"
	 */
	private int id;
	
	/**
	 * �ȵ�����
	 * @hibernate.property
	 */
	private String hotName;
	
	/**
	 * �ȵ�����
	 * 1-ҵ���ȵ�  2-��ҵ�ȵ�   
	 * @hibernate.property
	 */
	private int hotType;
	
	/**
	 * �ȵ㷽�� 0-���з��� User->status
	 * @hibernate.property
	 */
	private int hotTo;
	
	/**
	 * �ȵ���Զ���    User->userType
	 * @hibernate.property
	 */
	private int hotFor;
	
	/**
	 * �ȵ���Ե��Ա�
	 * @hibernate.property
	 */
	private int hotForgender;
	
	/**
	 * �ȵ���Եĳ���
	 * @hibernate.property
	 */
	private int hotForCity;
	
	/**
	 * �ȵ���ȶ�  0 ������ʾ�ڽ�����
	 * @hibernate.property
	 */
	private int hotMore;
	
	/**
	 * �ȵ������
	 * @hibernate.property
	 */
	private String hotDesc;
	
	/**
	 * �Ƿ���Ҫ������� ����������
	 * 1-��Ҫ  0-����Ҫ
	 *  @hibernate.property
	 */
	private int needBar;
	
	/**
	 * ��ͼ����
	 *2-��Ѷ  5-��Ѷ+��Ѷ
	 *  @hibernate.property
	 */
	private int viewType;
	
	/**
	 * icon��ַ
	 * @hibernate.property
	 */
	private String iconUrl;

	/**
	 *	�����
	 * @hibernate.property
	 */
	private int orderNum;
	
	/**
	 * ��Ϣ����ͼ��
	 * @hibernate.property
	 */
	private String messageIconUrl;
	
	/**
	 * ������������
	 * @hibernate.property
	 */
	private int longPressType;
	
	/**
	 * ���Ͻ���ť������
	 * 1-�Ӻ�   2-����   3-���÷���
	 * @hibernate.property
	 */
	private int btnType;
	
	// ��������
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
