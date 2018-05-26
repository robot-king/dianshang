package com.dao;

import java.util.List;
import com.pojo.Danzi;
import org.apache.ibatis.annotations.Param;

public interface DanziMapper {

	int deleteByPrimaryKey(int id);

    int insertSelective(Danzi record);

    Danzi selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(Danzi record);

    List<Danzi> queryList(Danzi record);
    
    int updateByIds(@Param("status") Integer status, @Param("ids") String[] ids);
    
    int deleteByIds(@Param("ids") String[] ids);
    
    int getCount(Danzi record);
    
    int updateByPrimaryKey(Danzi record);
}