package com.dao;

import java.util.List;
import com.pojo.WebSite;
import org.apache.ibatis.annotations.Param;

public interface WebSiteMapper {

	int deleteByPrimaryKey(int id);

    int insertSelective(WebSite record);

    WebSite selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(WebSite record);

    List<WebSite> queryList(WebSite record);
    
    int updateByIds(@Param("status") Integer status, @Param("ids") String[] ids);
    
    int deleteByIds(@Param("ids") String[] ids);
    
    int getCount(WebSite record);
    
    int updateByPrimaryKey(WebSite record);
}