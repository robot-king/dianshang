package com.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;



/**
 * 
 * @author
 *
 */
public class Dict implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private Integer id;

	// 名称
	private String name;

	// 编码
	private String code;

	//
	private String iconUrl;

	// 父节点
	private String parentCode;

	// 父节点名称
	private String parentName;
	
	// 子节点
	private List<Map<String, Object>> childrenList;

	public List<Map<String, Object>> getChildrenList() {
		return childrenList;
	}

	public void setChildrenList(List<Map<String, Object>> childrenList) {
		this.childrenList = childrenList;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	// 创建时间
	private Date createTime;

	// 状态
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
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

}