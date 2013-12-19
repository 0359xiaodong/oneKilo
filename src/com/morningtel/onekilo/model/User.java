package com.morningtel.onekilo.model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
	
	private static final long serialVersionUID = -1405827853446411033L;
	
	//������
	public static final int PREPARATION_OF_PREGNANCY_STATUS = 6;
	//������
	public static final int BE_PREGNANT_STATUS = 7;
	//����δ���׶�԰
	public static final int NOKINDERGARTEN_STATUS = 1;
	//�����������׶�԰
	public static final int KINDERGARTEN_STATUS = 2;
	//����������Сѧ
	public static final int PRIMARYSCHOOL_STATUS  = 3;
	//������������ѧ
	public static final int MIDDLESCHOOL_STATUS  = 4;
	
	//�ҳ�
	public static final int JIAZHANG_USERTYPE = 1;
	//��ʦ
	public static final int TEACHER_USERTYPE = 2;
	//����
	public static final int CHILD_USERTYPE = 3;
	//��ͨ�û�
	public static final int NORMAL_USERTYPE = 5;
	//��ͨ����Ա
	public static final int NORMALADMIN_USERTYPE = 4;
	//��������Ա
	public static final int ADMIN_USERTYPE = 9999;
	
	//һ�ν���״̬
	public static final int FIRST_USERSTATUS = 0; 
	//����״̬
	public static final int NORMAL_USERSTATUS = 1; 
	//��ֹ״̬
	public static final int STOP_USERSTATUS = 2;
	//����״̬
	public static final int CHASER_USERSTATUS = 3;
	//����״̬
	public static final int STAR_USERSTATUS = 4;
	//ר��״̬
	public static final int EXPERT_USERSTATUS = 5;
	//�ҳ���ٷ�
	public static final int JIAZHANGHUI_USERSTATUS = 6;
	//������Ա
	public static final int CHASER_TEAMER_USRESTATUS = 7;
	//�����鳤
	public static final int CHASER_ADMIN_USERSTATUS = 8;
	
	// �˺ŵĻ�����Ϣ
	/**
	 * ע��uid����id
	 * 
	 * @hibernate.id generator-class="uuid"
	 */
	private String id;
	
	/**
	 * ���
	 * @hibernate.property
	 */
	private String number;

	/**
	 * ��¼��
	 * 
	 * @hibernate.property
	 */
	private String loginName;

	/**
	 * ����
	 * 
	 * @hibernate.property
	 */
	private String password;

	/**
	 * �û�����0-׼�ҳ� 1-�ҳ� 2-��ʦ 3-����
	 * @hibernate.property
	 */
	private int userType;

	/**
	 * �û�ע������ 1������ 2��΢�� 3���ֻ��� 4:qq�� 5:΢��
	 * @hibernate.property
	 */
	private int regType;

	/**
	 * �˺���֤λ�� �ӵ�λ������û�е�0λ��
	 * @hibernate.property
	 */
	private int verification;

	/**
	 * ���伤����
	 * @hibernate.property
	 */
	private String emailActivateCode;

	/**
	 * �ֻ�������
	 * @hibernate.property
	 */
	private String mobilePhoneActivateCode;

	/**
	 * ע��ʱ��
	 * @hibernate.property
	 */
	private int regDate;

	/**
	 * ע��IP
	 * @hibernate.property
	 */
	private String regIp;

	/**
	 * �ϴε�¼ʱ��
	 * @hibernate.property
	 */
	private int lastTime;

	/**
	 * �ϴε�¼IP
	 * @hibernate.property
	 */
	private String lastIp;

	/**
	 * ��������
	 * @hibernate.property
	 */
	private String token;

	/**
	 * ��¼����
	 * @hibernate.property
	 */
	private String accessToken;

	/**
	 * ����΢��������Կ
	 * @hibernate.property
	 */
	private String accessTokenSecret;
	
	/**
	 * ����orqq��Ψһ��ʶ
	 * @hibernate.property
	 */
	private String loginId;
	
	// �û�������Ϣ
	/**
	 * �û���ʵ����
	 * @hibernate.property
	 */
	private String name;

	/**
	 * ����
	 * @hibernate.property
	 */
	private int birthDate;

	/**
	 * �Ա� 1-��  0-Ů
	 * 
	 * @hibernate.property
	 */
	private int gender;

	/**
	 * ���¹���
	 * 
	 * @hibernate.property
	 */
	private String job;

	/**
	 * �û�ǩ��
	 * 
	 * @hibernate.property
	 */
	private String description;

	/**
	 * �����ڡ�������ѧУ�ļҳ�
	 * 
	 * @hibernate.property
	 */
	private String comefromDesc;
	
	/**
	 * ������XXXѧУ����ʦ
	 * @hibernate.property
	 */
	private String tcomefromDesc;

	/**
	 * eg��������İְ�
	 * 
	 * @hibernate.property
	 */
	private String relationDesc;

	/**
	 * �û�����
	 * 
	 * @hibernate.property
	 */
	private int credit;

	/**
	 * ��������̳ ���-��KEForum id
	 * 
	 * @hibernate.property
	 */
	private int forumId;

	/**
	 * ͷ���ͼƬ
	 * 
	 * @hibernate.property
	 */
	private String iconUrl;

	/**
	 * ͼ������
	 * 
	 * @hibernate.property
	 */
	private String iconType;

	/**
	 * ��˿��
	 * 
	 * @hibernate.property
	 */
	private int fanCount;

	/**
	 * ��ע��
	 * 
	 * @hibernate.property
	 */
	private int starCount;

	/**
	 * ������
	 * 
	 * @hibernate.property
	 */
	private int topicCount;
	
	/**
	 * ��� 0-���裬1-�ְ֣�2-����
	 * 
	 * @hibernate.property
	 */
	private int identity;

	/**
	 * ������Ŀ
	 * 
	 * @hibernate.property
	 */
	private int childrenCount;

	// ��ϵ��Ϣ
	/**
	 * ����
	 * 
	 * @hibernate.property
	 */
	private String email;

	/**
	 * �û��ֻ�����
	 * 
	 * @hibernate.property
	 */
	private String mobilePhone;

	/**
	 * ����
	 * 
	 * @hibernate.property
	 */
	private int countryId;

	/**
	 * ʡ��
	 * 
	 * @hibernate.property
	 */
	private int provinceId;

	/**
	 * ����
	 * 
	 * @hibernate.property
	 */
	private int cityId;

	/**
	 * ��
	 * 
	 * @hibernate.property
	 */
	private int districtId;

	/**
	 * ��ͥסַ
	 * 
	 * @hibernate.property
	 */
	private String homeAddress;

	/**
	 * �����ص�
	 * @hibernate.property
	 */
	private String workplace;
	
	//������Ϣ
	/**
	 * �꼶
	 * 1-�а�,2-С�࣬3-�а࣬4-��࣬5-һ�꼶��6-���꼶����10-���꼶��11-��һ����13-������14-��һ����16-����
	 * @hibernate.property
	 */
	private int grade;
	
	/**
	 * ѧУ��ID
	 * @hibernate.property
	 */
	private int schoolId;
	
	/**
	 * �༶
	 * 1-20
	 * @hibernate.property
	 */
	private int keclass;
	
	/**
	 * ���׵�ID
	 * @hibernate.property
	 */
	private String fatherId;
	
	/**
	 * ĸ�׵�ID
	 *@hibernate.property 
	 */
	private String moutherId;
	
	/**
	 * ���������ϵ��ID
	 *@hibernate.property 
	 */
	private String otherId;
	
	/**
	 * ��������
	 * @hibernate.property
	 */
	private int week;
	
	/**
	 * ���ӵ�ǰ������ʱ��   
	 * 1-��δ���׶�԰     2-�������׶�԰      3-������Сѧ      4-��������ѧ
	 * @hibernate.property
	 */
	private int status;
	
	/**
	 * ios�豸����token
	 *@hibernate.property 
	 */
	private String iosToken;
	
	/**
	 * �û�״̬  0-Ϊ���ͻ�ӭ��Ϣ״̬ 1-����״̬   2-��ֹ״̬�����ɷ��������ɻ����� 3-ˮ��״̬  4-����״̬  5-ר��״̬ 6-�ҳ���ٷ�
	 *@hibernate.property 
	 */
	private int userStatus;
	
	/**
	 * �û��ĵ���λ����Ϣ
	 *@hibernate.property
	 */
	private String geo;

	// ��������
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

	//�û���Ⱥ���е�״̬
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
