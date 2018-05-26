package com.pojo;


import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author 
 *
 */
public class GenjinJilu implements Serializable{

	private static final long serialVersionUID = 1L;

	// id
	private Integer id;
	
	
	// 单子ID
	private Integer danziId;
	
	
	// 描述
	private String des;
	
	
	// 创建时间
	private Date createTime;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getDanziId() {
		return danziId;
	}
	public void setDanziId(Integer danziId) {
		this.danziId = danziId;
	}
	
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}