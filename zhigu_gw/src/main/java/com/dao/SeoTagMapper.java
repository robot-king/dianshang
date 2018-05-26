package com.dao;

import java.util.List;
import com.pojo.SeoTag;
import org.apache.ibatis.annotations.Param;

public interface SeoTagMapper {

	int deleteByPrimaryKey(int id);

    int insertSelective(SeoTag record);

    SeoTag selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(SeoTag record);

    List<SeoTag> queryList(SeoTag record);
    
    int updateByIds(@Param("status") Integer status, @Param("ids") String[] ids);
    
    int deleteByIds(@Param("ids") String[] ids);
    
    int getCount(SeoTag record);
    
    int updateByPrimaryKey(SeoTag record);
}