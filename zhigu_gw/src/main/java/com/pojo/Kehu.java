package com.pojo;


import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author 
 *
 */
public class Kehu implements Serializable{

	private static final long serialVersionUID = 1L;

	// id
	private Integer id;
	
	
	// 姓名
	private String xingming;
	
	
	// 手机
	private String shouji;
	
	
	// QQ
	private String qq;
	
	
	// 微信
	private String weixin;
	
	
	// 地区
	private String diqu;
	
	
	// 备注
	private String beizhu;
	
	
	// 创建时间
	private Date createTime;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getXingming() {
		return xingming;
	}
	public void setXingming(String xingming) {
		this.xingming = xingming;
	}
	
	public String getShouji() {
		return shouji;
	}
	public void setShouji(String shouji) {
		this.shouji = shouji;
	}
	
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	
	public String getWeixin() {
		return weixin;
	}
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	
	public String getDiqu() {
		return diqu;
	}
	public void setDiqu(String diqu) {
		this.diqu = diqu;
	}
	
	public String getBeizhu() {
		return beizhu;
	}
	public void setBeizhu(String beizhu) {
		this.beizhu = beizhu;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}