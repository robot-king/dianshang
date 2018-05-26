package com.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pojo.Menu;

public interface MenuService {
	
	// 添加
	public int add(Menu record);

	// 修改
	public int update(Menu record);

	// 所有数据
	public List<Menu> list(Menu record);

	// 删除
	public int deleteByPrimaryKey(int id);

	// 分页查询
	public List<Menu> listPage(int pageNum, int pageSize,Menu record);
	
	// 查询单条数据
	public Menu queryById(int id);
	
	// 批量修改数据
	int updateByIds(@Param("status") Integer status,@Param("ids") String[] ids);
    
    // 批量删除数据
    int deleteByIds(@Param("ids") String[] ids);
    
    List<Menu> queryListByEmployeeId(int pageNum, int pageSize,Integer employeeId);
    
 // 分页查询
 	public List<Menu> menuData(Integer employeeId);
}