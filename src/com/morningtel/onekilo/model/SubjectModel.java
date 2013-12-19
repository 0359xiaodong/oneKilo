package com.morningtel.onekilo.model;

import java.io.Serializable;

public class SubjectModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主题广告
	 * @hibernate.class table="subject"
	 * @author lhl
	 */

	
	public String getToActive() {
		return toActive;
	}

	public void setToActive(String toActive) {
		this.toActive = toActive;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * 动作url  点击打开该url
	 * @hibernate.property 
	 */
	private String toActive;
	
	/**
	*图片地址 
	*smallImg  middleImg  maxImg 对应 ldpi  hdpi  xdpi
	*/
	private String image;


}
