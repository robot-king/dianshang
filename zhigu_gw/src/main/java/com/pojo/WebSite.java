package com.pojo;


import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author 
 *
 */
public class WebSite implements Serializable{

	private static final long serialVersionUID = 1L;

	// id
	private Integer id;
	
	
	// 标题
	private String title;
	
	
	// 缩略图地址
	private String coverUrl;
	
	
	// 价格
	private String price;
	
	
	// 访问地址
	private String accessUrl;
	
	
	// 非本网访问地址
	private String feiAccessUrl;
	
	
	// 类型{"1":"案例","2":"模板"}
	private Integer type;
	
	public static int TYPE_ANLI = 1;
	public static int TYPE_MOBAN = 2;
	public static int TYPE_MORE = 3;
	
	// 网站类型
	private String webType;
	
	
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
	
	public String getCoverUrl() {
		return coverUrl;
	}
	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}
	
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getAccessUrl() {
		return accessUrl;
	}
	public void setAccessUrl(String accessUrl) {
		this.accessUrl = accessUrl;
	}
	
	public String getFeiAccessUrl() {
		return feiAccessUrl;
	}
	public void setFeiAccessUrl(String feiAccessUrl) {
		this.feiAccessUrl = feiAccessUrl;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getWebType() {
		return webType;
	}
	public void setWebType(String webType) {
		this.webType = webType;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}