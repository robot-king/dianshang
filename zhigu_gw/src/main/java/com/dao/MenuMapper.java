package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pojo.Menu;

public interface MenuMapper {

	int deleteByPrimaryKey(int id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);
    
    List<Menu> queryList(Menu record);
    
    int updateByIds(@Param("status") Integer status, @Param("ids") String[] ids);
    
    int deleteByIds(@Param("ids") String[] ids);
    
    List<Menu> queryListByEmployeeId(@Param("employeeId") Integer employeeId);
}