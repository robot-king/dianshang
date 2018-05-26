package com.dao;

import java.util.List;
import com.pojo.GenjinJilu;
import org.apache.ibatis.annotations.Param;

public interface GenjinJiluMapper {

	int deleteByPrimaryKey(int id);

    int insertSelective(GenjinJilu record);

    GenjinJilu selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(GenjinJilu record);

    List<GenjinJilu> queryList(GenjinJilu record);
    
    int updateByIds(@Param("status") Integer status, @Param("ids") String[] ids);
    
    int deleteByIds(@Param("ids") String[] ids);
    
    int getCount(GenjinJilu record);
    
    int updateByPrimaryKey(GenjinJilu record);
}