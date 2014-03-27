package com.morningtel.onekilo.model;

import java.io.Serializable;

public class AddForm implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * @hibernate.id generator-class="uuid"
     */
	private String id;
	
	/**
	 * ��title���䵽��̨
	 * �Ƿ���Ҫ���� �ǣ���ʾ�����֣���������⣩   ��null or ��
	 * @hibernate.property
	 */
	private String needTitle;
	
	/**
	 * ��content���䵽��̨
	 * �Ƿ���Ҫ����  �ǣ���ʾ�����֣����������ݣ�   ��null or ��
	 * @hibernate.property
	 */
	private String needContent;
	
	/**
	 * ��image���䵽��̨
	 * �Ƿ���ҪͼƬ�ϴ�  �ǣ���������������3��   ��null or ��
	 * @hibernate.property
	 */
	private String needImage;
	
	/**
	 * ��geoΪ���������䵽��̨
	 * �Ƿ���Ҫgps����  �ǣ���ʱʱ�䣨5��   ��null or ��
	 * @hibernate.property
	 */
	private String needGeo;
	
	/**
	 * ��mapGeoΪ���������䵽��̨
	 * �Ƿ���Ҫչʾ��ͼ  �ǣ���ʱʱ�䣨5��   ��null or ��
	 * @hibernate.property
	 */
	private String needMap;
	
	/**
	 * ��isAnonymityΪ���������䵽��̨
	 * �Ƿ���Ҫ������ť  �ǣ���1��   ��null or ��
	 * @hibernate.property
	 */
	private String needAnonymity;
	
	/**
	 * �ύ�ĺ�̨��ַ
	 * @hibernate.property
	 */
	private String api;
	
	/**
	 * �������Ӳ���
	 * @hibernate.property
	 */
	private String otherParam;
	
	private String btnName;
	
	public String getBtnName() {
		return btnName;
	}

	public void setBtnName(String btnName) {
		this.btnName = btnName;
	}

	private int belongId;
	
	public int getBelongId() {
		return belongId;
	}

	public void setBelongId(int belongId) {
		this.belongId = belongId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNeedTitle() {
		return needTitle;
	}

	public void setNeedTitle(String needTitle) {
		this.needTitle = needTitle;
	}

	public String getNeedContent() {
		return needContent;
	}

	public void setNeedContent(String needContent) {
		this.needContent = needContent;
	}

	public String getNeedImage() {
		return needImage;
	}

	public void setNeedImage(String needImage) {
		this.needImage = needImage;
	}

	public String getNeedGeo() {
		return needGeo;
	}

	public void setNeedGeo(String needGeo) {
		this.needGeo = needGeo;
	}

	public String getNeedMap() {
		return needMap;
	}

	public void setNeedMap(String needMap) {
		this.needMap = needMap;
	}

	public String getNeedAnonymity() {
		return needAnonymity;
	}

	public void setNeedAnonymity(String needAnonymity) {
		this.needAnonymity = needAnonymity;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public String getOtherParam() {
		return otherParam;
	}

	public void setOtherParam(String otherParam) {
		this.otherParam = otherParam;
	}

}