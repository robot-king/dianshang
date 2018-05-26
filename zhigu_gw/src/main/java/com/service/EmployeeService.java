package com.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.pojo.Employee;

public interface EmployeeService {

	// 添加
	public int add(Employee record);

	// 修改
	public int update(Employee record);

	// 所有数据
	public List<Employee> list(Employee record);

	// 删除
	public int deleteByPrimaryKey(int id);

	// 分页查询
	public List<Employee> listPage(int pageNum, int pageSize, Employee record);

	// 查询单条数据
	public Employee queryById(int id);

	// 批量修改数据
	int updateByIds(@Param("status") Integer status, @Param("ids") String[] ids);

	// 批量删除数据
	int deleteByIds(@Param("ids") String[] ids);

	// 修改密码
	public Map updatePwd(Integer userId, String oldPwd, String newPwd);
}