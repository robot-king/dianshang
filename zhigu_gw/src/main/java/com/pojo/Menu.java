package com.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author
 *
 */
public class Menu implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private Integer id;

	// 编码
	private String code;

	// 名称
	private String name;

	// 地址
	private String url;

	// 父节点
	private String parentCode;

	// 排序数字
	private Integer sortNum;

	// 状态
	private Integer status;

	// 创建时间
	private Date createTime;

	// 父节点名称
	private String parentName;

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	private Integer parentId;

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
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