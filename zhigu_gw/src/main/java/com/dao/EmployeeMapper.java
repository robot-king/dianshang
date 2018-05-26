package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pojo.Employee;

public interface EmployeeMapper {

	int deleteByPrimaryKey(int id);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);
    
    List<Employee> queryList(Employee record);
    
    int updateByIds(@Param("status") Integer status, @Param("ids") String[] ids);
    
    int deleteByIds(@Param("ids") String[] ids);
}