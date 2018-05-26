package com.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author
 *
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private Integer id;

	// 昵称
	private String nickName;

	// 匿名
	private String niMing;

	// 真实姓名
	private String name;

	// 头像
	private String iconUrl;

	// 会员类型（vip_type_1：普通会员，2：……）
	private String vipType;

	// 登录状态(1:在线，2：离线,3:封禁)
	private Integer loginStatus;
	public static int LONGIN_STATUS_ZAIXAIN = 1;
	public static int LONGIN_STATUS_XIAXIAN = 2;
	public static int LONGIN_STATUS_FENGJIN = 3;

	// 备注
	private String comment;

	// 账户余额
	private String money;

	// 余额密码
	private String balancePwd;

	// 签名
	private String signature;

	// 资源
	private String resource;

	// 员工ID
	private Integer employeeId;

	// 曾用名
	private String passedName;

	// 国家地区
	private String national;

	// 省份
	private String province;

	// 自定义URL
	private String customeUrl;

	// 描述
	private String des;

	// 资料状态，1为私密，2为仅限好友，3为公开
	private Integer infoStatus;
	public static final Integer INFO_PRIVATE = 1;
	public static final Integer INFO_FRIEND = 2;
	public static final Integer INFO_PUBLIC = 3;

	// 留言状态，1为私密，2为仅限好友，3为公开
	private Integer messageStatus;
	public static final Integer MESSAGE_PRIVATE = 1;
	public static final Integer MESSAGE_FRIEND = 2;
	public static final Integer MESSAGE_PUBLIC = 3;

	// 库存状态，1为私密，2为仅限好友，3为公开
	private Integer inventoryStatus;
	public static final Integer INVENTORY_PRIVATE = 1;
	public static final Integer INVENTORY_FRIEND = 2;
	public static final Integer INVENTORY_PUBLIC = 3;

	// 积分
	private Integer integration;

	// 经验
	private Integer jingYanVal;

	// 积分级别
	private String jiFenType;

	// 计算出当前级别到下一个级别完整积分进度值
	private String jiFenChazhi;

	// 计算出当前级别到下一个级别已完成积分差值
	private String jiFenFinished;

	// 积分颜色
	private String jiFenColor;

	// 登录时间
	private Date loginTime;

	// 创建时间
	private Date createTime;

	// 手机
	private String mobile;

	public Integer getJingYanVal() {
		return jingYanVal;
	}

	public void setJingYanVal(Integer jingYanVal) {
		this.jingYanVal = jingYanVal;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getJiFenColor() {
		return jiFenColor;
	}

	public void setJiFenColor(String jiFenColor) {
		this.jiFenColor = jiFenColor;
	}

	public String getJiFenType() {
		return jiFenType;
	}

	public void setJiFenType(String jiFenType) {
		this.jiFenType = jiFenType;
	}

	public String getJiFenChazhi() {
		return jiFenChazhi;
	}

	public void setJiFenChazhi(String jiFenChazhi) {
		this.jiFenChazhi = jiFenChazhi;
	}

	public String getJiFenFinished() {
		return jiFenFinished;
	}

	public void setJiFenFinished(String jiFenFinished) {
		this.jiFenFinished = jiFenFinished;
	}

	public String getPassedName() {
		return passedName;
	}

	public void setPassedName(String passedName) {
		this.passedName = passedName;
	}

	public String getNational() {
		return national;
	}

	public void setNational(String national) {
		this.national = national;
	}

	public String getProvince() {
		return province;
	}

	public String getNiMing() {
		return niMing;
	}

	public void setNiMing(String niMing) {
		this.niMing = niMing;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCustomeUrl() {
		return customeUrl;
	}

	public void setCustomeUrl(String customeUrl) {
		this.customeUrl = customeUrl;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public Integer getInfoStatus() {
		return infoStatus;
	}

	public void setInfoStatus(Integer infoStatus) {
		this.infoStatus = infoStatus;
	}

	public Integer getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(Integer messageStatus) {
		this.messageStatus = messageStatus;
	}

	public Integer getInventoryStatus() {
		return inventoryStatus;
	}

	public void setInventoryStatus(Integer inventoryStatus) {
		this.inventoryStatus = inventoryStatus;
	}

	public Integer getIntegration() {
		return integration;
	}

	public void setIntegration(Integer integration) {
		this.integration = integration;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getVipType() {
		return vipType;
	}

	public void setVipType(String vipType) {
		this.vipType = vipType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Integer getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(Integer loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getBalancePwd() {
		return balancePwd;
	}

	public void setBalancePwd(String balancePwd) {
		this.balancePwd = balancePwd;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

}