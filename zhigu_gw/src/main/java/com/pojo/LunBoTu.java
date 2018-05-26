package com.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author
 *
 */
public class LunBoTu implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private Integer id;

	// 员工id
	private Integer employeeId;

	// 轮播图地址
	private String bannerUrl;

	// 目标链接
	private String targetUrl;

	// 状态{"1":"启用","2":"禁用","3":"删除"}
	private Integer status;

	public static int STATUS_SHANCHU = 3;
	public static int STATUS_JINYONG = 2;
	public static int STATUS_QIYONG = 1;

	// 创建时间
	private Date createTime;

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getBannerUrl() {
		return bannerUrl;
	}

	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}