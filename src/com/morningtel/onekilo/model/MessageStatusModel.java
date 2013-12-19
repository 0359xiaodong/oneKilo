package com.morningtel.onekilo.model;

import java.io.Serializable;

public class MessageStatusModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	private int id;
	
	/**
	 *消息发送者Id
	 *
	 *
	 */
	private String fromId;
		
	/**
	 * 消息发送者名称
	 * 
	 */
	private String fromName;
	
	/**
	 *消息简介
	 * 
	 */
	private String msgDesc;
	
	/**
	 * 消息生成日期
	 * 
	 */
	private int sendDate;
	
	/**
	 * 消息动作
	 * 
	 */
	private String action;

	/**
	 * 未读消息数
	 * 
	 */
	private int noReadCount;
	
	/**
	 * 消息图标
	 *
	 */
	private String icon;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getMsgDesc() {
		return msgDesc;
	}

	public void setMsgDesc(String msgDesc) {
		this.msgDesc = msgDesc;
	}

	public int getSendDate() {
		return sendDate;
	}

	public void setSendDate(int sendDate) {
		this.sendDate = sendDate;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getNoReadCount() {
		return noReadCount;
	}

	public void setNoReadCount(int noReadCount) {
		this.noReadCount = noReadCount;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
}
