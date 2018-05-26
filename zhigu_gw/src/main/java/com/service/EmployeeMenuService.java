package com.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pojo.EmployeeMenu;

public interface EmployeeMenuService {
	
	// 添加
	public int add(EmployeeMenu record);

	// 修改
	public int update(EmployeeMenu record);

	// 所有数据
	public List<EmployeeMenu> list(EmployeeMenu record);

	// 删除
	public int deleteByPrimaryKey(int id);

	// 分页查询
	public List<EmployeeMenu> listPage(int pageNum, int pageSize,EmployeeMenu record);
	
	// 查询单条数据
	public EmployeeMenu queryById(int id);
	
	// 批量修改数据
	int updateByIds(@Param("status") Integer status,@Param("ids") String[] ids);
    
    // 批量删除数据
    int deleteByIds(@Param("ids") String[] ids);
}