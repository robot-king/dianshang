package com.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author
 *
 */
public class AccessRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	// 编号
	private Integer id;

	// ip地址
	private String ipAddress;

	// 国家
	private String country;

	// 区域
	private String area;

	// 省份/直辖市
	private String province;

	// 城市
	private String city;

	// 区
	private String county;

	// 创建日期
	private Date createTime;

	private int accessCount;

	public int getAccessCount() {
		return accessCount;
	}

	public void setAccessCount(int accessCount) {
		this.accessCount = accessCount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}