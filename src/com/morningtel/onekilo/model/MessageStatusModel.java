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
	 *��Ϣ������Id
	 *
	 *
	 */
	private String fromId;
		
	/**
	 * ��Ϣ����������
	 * 
	 */
	private String fromName;
	
	/**
	 *��Ϣ���
	 * 
	 */
	private String msgDesc;
	
	/**
	 * ��Ϣ��������
	 * 
	 */
	private int sendDate;
	
	/**
	 * ��Ϣ����
	 * 
	 */
	private String action;

	/**
	 * δ����Ϣ��
	 * 
	 */
	private int noReadCount;
	
	/**
	 * ��Ϣͼ��
	 *
	 */
	private String icon;
	private String toId;
	private String msgId;
	private String title;
	private String content;
	private int msgType;
	private int msgMinType;

	public int getMsgMinType() {
		return msgMinType;
	}

	public void setMsgMinType(int msgMinType) {
		this.msgMinType = msgMinType;
	}

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

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
