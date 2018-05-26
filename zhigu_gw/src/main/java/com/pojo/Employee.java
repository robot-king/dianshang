package com.pojo;


import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author 
 *
 */
public class Employee implements Serializable{

	private static final long serialVersionUID = 1L;

	// id
	private Integer id;
	
	// 昵称
	private String nickName;
	
	// 头像
	private String iconUrl;
	
	// 创建时间
	private Date createTime;
	
	// 状态
	private Integer status;
	
	// 登录时间
	private Date loginTime;
	
	// 密码
	private String pwd;
	
	// 是否是管理员
	private Integer isManager;
	
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
	
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public Integer getIsManager() {
		return isManager;
	}
	public void setIsManager(Integer isManager) {
		this.isManager = isManager;
	}
	
}