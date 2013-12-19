package com.morningtel.onekilo.model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
	
	private static final long serialVersionUID = -1405827853446411033L;
	
	//备孕中
	public static final int PREPARATION_OF_PREGNANCY_STATUS = 6;
	//怀孕中
	public static final int BE_PREGNANT_STATUS = 7;
	//孩子未上幼儿园
	public static final int NOKINDERGARTEN_STATUS = 1;
	//孩子正在上幼儿园
	public static final int KINDERGARTEN_STATUS = 2;
	//孩子正在上小学
	public static final int PRIMARYSCHOOL_STATUS  = 3;
	//孩子正在上中学
	public static final int MIDDLESCHOOL_STATUS  = 4;
	
	//家长
	public static final int JIAZHANG_USERTYPE = 1;
	//老师
	public static final int TEACHER_USERTYPE = 2;
	//孩子
	public static final int CHILD_USERTYPE = 3;
	//普通用户
	public static final int NORMAL_USERTYPE = 5;
	//普通管理员
	public static final int NORMALADMIN_USERTYPE = 4;
	//超级管理员
	public static final int ADMIN_USERTYPE = 9999;
	
	//一次进入状态
	public static final int FIRST_USERSTATUS = 0; 
	//正常状态
	public static final int NORMAL_USERSTATUS = 1; 
	//禁止状态
	public static final int STOP_USERSTATUS = 2;
	//猎手状态
	public static final int CHASER_USERSTATUS = 3;
	//明星状态
	public static final int STAR_USERSTATUS = 4;
	//专家状态
	public static final int EXPERT_USERSTATUS = 5;
	//家长会官方
	public static final int JIAZHANGHUI_USERSTATUS = 6;
	//狩猎组员
	public static final int CHASER_TEAMER_USRESTATUS = 7;
	//狩猎组长
	public static final int CHASER_ADMIN_USERSTATUS = 8;
	
	// 账号的基本信息
	/**
	 * 注意uid不是id
	 * 
	 * @hibernate.id generator-class="uuid"
	 */
	private String id;
	
	/**
	 * 编号
	 * @hibernate.property
	 */
	private String number;

	/**
	 * 登录名
	 * 
	 * @hibernate.property
	 */
	private String loginName;

	/**
	 * 密码
	 * 
	 * @hibernate.property
	 */
	private String password;

	/**
	 * 用户类型0-准家长 1-家长 2-老师 3-孩子
	 * @hibernate.property
	 */
	private int userType;

	/**
	 * 用户注册类型 1：邮箱 2：微博 3：手机号 4:qq号 5:微信
	 * @hibernate.property
	 */
	private int regType;

	/**
	 * 账号验证位标 从低位起（这里没有第0位）
	 * @hibernate.property
	 */
	private int verification;

	/**
	 * 邮箱激活码
	 * @hibernate.property
	 */
	private String emailActivateCode;

	/**
	 * 手机激活码
	 * @hibernate.property
	 */
	private String mobilePhoneActivateCode;

	/**
	 * 注册时间
	 * @hibernate.property
	 */
	private int regDate;

	/**
	 * 注册IP
	 * @hibernate.property
	 */
	private String regIp;

	/**
	 * 上次登录时间
	 * @hibernate.property
	 */
	private int lastTime;

	/**
	 * 上次登录IP
	 * @hibernate.property
	 */
	private String lastIp;

	/**
	 * 访问令牌
	 * @hibernate.property
	 */
	private String token;

	/**
	 * 登录密令
	 * @hibernate.property
	 */
	private String accessToken;

	/**
	 * 新浪微博令牌密钥
	 * @hibernate.property
	 */
	private String accessTokenSecret;
	
	/**
	 * 新浪orqq的唯一标识
	 * @hibernate.property
	 */
	private String loginId;
	
	// 用户基本信息
	/**
	 * 用户真实名称
	 * @hibernate.property
	 */
	private String name;

	/**
	 * 生日
	 * @hibernate.property
	 */
	private int birthDate;

	/**
	 * 性别 1-男  0-女
	 * 
	 * @hibernate.property
	 */
	private int gender;

	/**
	 * 从事工作
	 * 
	 * @hibernate.property
	 */
	private String job;

	/**
	 * 用户签名
	 * 
	 * @hibernate.property
	 */
	private String description;

	/**
	 * 来自于××××学校的家长
	 * 
	 * @hibernate.property
	 */
	private String comefromDesc;
	
	/**
	 * 来自于XXX学校的老师
	 * @hibernate.property
	 */
	private String tcomefromDesc;

	/**
	 * eg：刘晓峰的爸爸
	 * 
	 * @hibernate.property
	 */
	private String relationDesc;

	/**
	 * 用户积分
	 * 
	 * @hibernate.property
	 */
	private int credit;

	/**
	 * 所属的论坛 外键-》KEForum id
	 * 
	 * @hibernate.property
	 */
	private int forumId;

	/**
	 * 头像的图片
	 * 
	 * @hibernate.property
	 */
	private String iconUrl;

	/**
	 * 图标类型
	 * 
	 * @hibernate.property
	 */
	private String iconType;

	/**
	 * 粉丝数
	 * 
	 * @hibernate.property
	 */
	private int fanCount;

	/**
	 * 关注数
	 * 
	 * @hibernate.property
	 */
	private int starCount;

	/**
	 * 帖子数
	 * 
	 * @hibernate.property
	 */
	private int topicCount;
	
	/**
	 * 身份 0-妈妈，1-爸爸，2-其他
	 * 
	 * @hibernate.property
	 */
	private int identity;

	/**
	 * 孩子数目
	 * 
	 * @hibernate.property
	 */
	private int childrenCount;

	// 联系信息
	/**
	 * 邮箱
	 * 
	 * @hibernate.property
	 */
	private String email;

	/**
	 * 用户手机号码
	 * 
	 * @hibernate.property
	 */
	private String mobilePhone;

	/**
	 * 国家
	 * 
	 * @hibernate.property
	 */
	private int countryId;

	/**
	 * 省份
	 * 
	 * @hibernate.property
	 */
	private int provinceId;

	/**
	 * 城市
	 * 
	 * @hibernate.property
	 */
	private int cityId;

	/**
	 * 区
	 * 
	 * @hibernate.property
	 */
	private int districtId;

	/**
	 * 家庭住址
	 * 
	 * @hibernate.property
	 */
	private String homeAddress;

	/**
	 * 工作地点
	 * @hibernate.property
	 */
	private String workplace;
	
	//孩子信息
	/**
	 * 年级
	 * 1-托班,2-小班，3-中班，4-大班，5-一年级，6-二年级。。10-六年级，11-初一。。13-初三，14-高一。。16-高三
	 * @hibernate.property
	 */
	private int grade;
	
	/**
	 * 学校的ID
	 * @hibernate.property
	 */
	private int schoolId;
	
	/**
	 * 班级
	 * 1-20
	 * @hibernate.property
	 */
	private int keclass;
	
	/**
	 * 父亲的ID
	 * @hibernate.property
	 */
	private String fatherId;
	
	/**
	 * 母亲的ID
	 *@hibernate.property 
	 */
	private String moutherId;
	
	/**
	 * 其他身份联系人ID
	 *@hibernate.property 
	 */
	private String otherId;
	
	/**
	 * 怀孕周数
	 * @hibernate.property
	 */
	private int week;
	
	/**
	 * 孩子当前所处的时期   
	 * 1-还未上幼儿园     2-正在上幼儿园      3-正在上小学      4-正在上中学
	 * @hibernate.property
	 */
	private int status;
	
	/**
	 * ios设备推送token
	 *@hibernate.property 
	 */
	private String iosToken;
	
	/**
	 * 用户状态  0-为发送欢迎信息状态 1-正常状态   2-禁止状态（不可发帖，不可回帖） 3-水军状态  4-明星状态  5-专家状态 6-家长会官方
	 *@hibernate.property 
	 */
	private int userStatus;
	
	/**
	 * 用户的地理位置信息
	 *@hibernate.property
	 */
	private String geo;

	// 辅助参数
	private String imageUrl;

	private ArrayList<User> children;
	
	private ArrayList<Group> groups;

	private String icon;

	private String iconFileName;

	private String iconContentType;

	private String[] keclasses;
	
	private User linkman;
	
	private String nickName="";
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	private String account="";
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
	public String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	//用户在群组中的状态
	private int ingroupStatus;
	
	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public String getFatherId() {
		return fatherId;
	}

	public void setFatherId(String fatherId) {
		this.fatherId = fatherId;
	}

	public String getMoutherId() {
		return moutherId;
	}

	public void setMoutherId(String moutherId) {
		this.moutherId = moutherId;
	}

	public int getWeek() {
		return week;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getKeclass() {
		return keclass;
	}

	public void setKeclass(int keclass) {
		this.keclass = keclass;
	}

	public String[] getKeclasses() {
		return keclasses;
	}

	public void setKeclasses(String[] keclasses) {
		this.keclasses = keclasses;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public int getChildrenCount() {
		return childrenCount;
	}

	public void setChildrenCount(int childrenCount) {
		this.childrenCount = childrenCount;
	}

	public String getIosToken() {
		return iosToken;
	}

	public void setIosToken(String iosToken) {
		this.iosToken = iosToken;
	}

	public ArrayList<User> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<User> children) {
		this.children = children;
	}

	public ArrayList<Group> getGroups() {
		return groups;
	}

	public void setGroups(ArrayList<Group> groups) {
		this.groups = groups;
	}

	public int getIdentity() {
		return identity;
	}

	public void setIdentity(int identity) {
		this.identity = identity;
	}

	public String getIconType() {
		return iconType;
	}

	public String getTcomefromDesc() {
		return tcomefromDesc;
	}

	public void setTcomefromDesc(String tcomefromDesc) {
		this.tcomefromDesc = tcomefromDesc;
	}

	public void setIconType(String iconType) {
		this.iconType = iconType;
	}

	public int getFanCount() {
		return fanCount;
	}

	public int getCountryId() {
		return countryId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public void setFanCount(int fanCount) {
		this.fanCount = fanCount;
	}

	public int getStarCount() {
		return starCount;
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

	public void setStarCount(int starCount) {
		this.starCount = starCount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getRegDate() {
		return regDate;
	}

	public void setRegDate(int regDate) {
		this.regDate = regDate;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getRegIp() {
		return regIp;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}

	public int getLastTime() {
		return lastTime;
	}

	public void setLastTime(int lastTime) {
		this.lastTime = lastTime;
	}

	public String getLastIp() {
		return lastIp;
	}

	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public void setAccessTokenSecret(String accessTokenSecret) {
		this.accessTokenSecret = accessTokenSecret;
	}

	public String getAccessTokenSecret() {
		return accessTokenSecret;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setRegType(int regType) {
		this.regType = regType;
	}

	public int getRegType() {
		return regType;
	}

	public void setVerification(int verification) {
		this.verification = verification;
	}

	public int getVerification() {
		return verification;
	}

	public void setEmailActivateCode(String emailActivateCode) {
		this.emailActivateCode = emailActivateCode;
	}

	public String getEmailActivateCode() {
		return emailActivateCode;
	}

	public void setMobilePhoneActivateCode(String mobilePhoneActivateCode) {
		this.mobilePhoneActivateCode = mobilePhoneActivateCode;
	}

	public String getMobilePhoneActivateCode() {
		return mobilePhoneActivateCode;
	}

	public boolean isWeiboBind() {
		return (this.verification & 2) > 0;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getWorkplace() {
		return workplace;
	}

	public void setWorkplace(String workplace) {
		this.workplace = workplace;
	}

	public int getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(int birthDate) {
		this.birthDate = birthDate;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getComefromDesc() {
		return comefromDesc;
	}

	public void setComefromDesc(String comefromDesc) {
		this.comefromDesc = comefromDesc;
	}

	public String getRelationDesc() {
		return relationDesc;
	}

	public void setRelationDesc(String relationDesc) {
		this.relationDesc = relationDesc;
	}
	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public int getTopicCount() {
		return topicCount;
	}

	public void setTopicCount(int topicCount) {
		this.topicCount = topicCount;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	public int getIngroupStatus() {
		return ingroupStatus;
	}

	public void setIngroupStatus(int ingroupStatus) {
		this.ingroupStatus = ingroupStatus;
	}

	public String getGeo() {
		return geo;
	}

	public void setGeo(String geo) {
		this.geo = geo;
	}
	
	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public User getLinkman() {
		return linkman;
	}

	public void setLinkman(User linkman) {
		this.linkman = linkman;
	}

	public String getOtherId() {
		return otherId;
	}

	public void setOtherId(String otherId) {
		this.otherId = otherId;
	}
}
