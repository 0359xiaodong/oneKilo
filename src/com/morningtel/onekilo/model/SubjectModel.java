package com.morningtel.onekilo.model;

import java.io.Serializable;

public class SubjectModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * ������
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
	 * ����url  ����򿪸�url
	 * @hibernate.property 
	 */
	private String toActive;
	
	/**
	*ͼƬ��ַ 
	*smallImg  middleImg  maxImg ��Ӧ ldpi  hdpi  xdpi
	*/
	private String image;


}
