package com.pojo;


import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author 
 *
 */
public class EmployeeMenu implements Serializable{

	private static final long serialVersionUID = 1L;

	// 
	private Integer id;
	
	// 角色ID
	private Integer employeeId;
	
	// 权限ID
	private Integer menuId;
	
	// 状态(0:禁用,1:启用,2:删除)
	private Integer status;
	
	// 创建时间
	private Date createTime;
	
	// 管理员ID
	private Integer adminId;
	
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
	
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
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
	
	public Integer getAdminId() {
		return adminId;
	}
	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}
	
}