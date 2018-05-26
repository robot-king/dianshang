package com.dao;

import java.util.List;
import com.pojo.Kehu;
import org.apache.ibatis.annotations.Param;

public interface KehuMapper {

	int deleteByPrimaryKey(int id);

    int insertSelective(Kehu record);

    Kehu selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(Kehu record);

    List<Kehu> queryList(Kehu record);
    
    int updateByIds(@Param("status") Integer status, @Param("ids") String[] ids);
    
    int deleteByIds(@Param("ids") String[] ids);
    
    int getCount(Kehu record);
    
    int updateByPrimaryKey(Kehu record);
}