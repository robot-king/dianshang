package com.pojo;


import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author 
 *
 */
public class SeoTag implements Serializable{

	private static final long serialVersionUID = 1L;

	// id
	private Integer id;
	
	
	// 标题
	private String title;
	
	
	// 创建时间
	private Date createTime;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}