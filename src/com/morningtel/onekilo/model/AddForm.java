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
	 * 以title传输到后台
	 * 是否需要标题 是：提示的名字（请输入标题）   否：null or 空
	 * @hibernate.property
	 */
	private String needTitle;
	
	/**
	 * 以content传输到后台
	 * 是否需要内容  是：提示的名字（请输入内容）   否：null or 空
	 * @hibernate.property
	 */
	private String needContent;
	
	/**
	 * 以image传输到后台
	 * 是否需要图片上传  是：代表限制张数（3）   否：null or 空
	 * @hibernate.property
	 */
	private String needImage;
	
	/**
	 * 以geo为参数名传输到后台
	 * 是否需要gps数据  是：超时时间（5）   否：null or 空
	 * @hibernate.property
	 */
	private String needGeo;
	
	/**
	 * 以mapGeo为参数名传输到后台
	 * 是否需要展示地图  是：超时时间（5）   否：null or 空
	 * @hibernate.property
	 */
	private String needMap;
	
	/**
	 * 以isAnonymity为参数名传输到后台
	 * 是否需要匿名按钮  是：（1）   否：null or 空
	 * @hibernate.property
	 */
	private String needAnonymity;
	
	/**
	 * 提交的后台地址
	 * @hibernate.property
	 */
	private String api;
	
	/**
	 * 其他附加参数
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