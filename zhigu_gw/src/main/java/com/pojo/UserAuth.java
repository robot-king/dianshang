package com.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author
 *
 */
public class UserAuth implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private Integer id;

	// 用户ID
	private Integer userId;

	// 登录类型{"1":"用户名","2":"手机","3":"邮箱","4":"微博","5":"QQ","6":"微信"}
	private Integer identityType;

	public static int IDENTITYTYPE_YOUXIANG = 3;
	public static int IDENTITYTYPE_SHOUJI = 2;
	public static int IDENTITYTYPE_YONGHUMING = 1;
	public static int IDENTITYTYPE_WEIXIN = 6;
	public static int IDENTITYTYPE_QQ = 5;
	public static int IDENTITYTYPE_WEIBO = 4;

	// 登录标识
	private String identifier;

	// 登录凭证（可以是密码，也可以是token）
	private String credential;

	// 创建时间
	private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getIdentityType() {
		return identityType;
	}

	public void setIdentityType(Integer identityType) {
		this.identityType = identityType;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getCredential() {
		return credential;
	}

	public void setCredential(String credential) {
		this.credential = credential;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}