package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pojo.EmployeeMenu;

public interface EmployeeMenuMapper {

	int deleteByPrimaryKey(int id);

    int insert(EmployeeMenu record);

    int insertSelective(EmployeeMenu record);

    EmployeeMenu selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(EmployeeMenu record);

    int updateByPrimaryKey(EmployeeMenu record);
    
    List<EmployeeMenu> queryList(EmployeeMenu record);
    
    int updateByIds(@Param("status") Integer status, @Param("ids") String[] ids);
    
    int deleteByIds(@Param("ids") String[] ids);
}