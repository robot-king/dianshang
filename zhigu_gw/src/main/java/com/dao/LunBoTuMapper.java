package com.dao;

import java.util.List;
import com.pojo.LunBoTu;
import org.apache.ibatis.annotations.Param;

public interface LunBoTuMapper {

	int deleteByPrimaryKey(int id);

    int insertSelective(LunBoTu record);

    LunBoTu selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(LunBoTu record);

    List<LunBoTu> queryList(LunBoTu record);
    
    int updateByIds(@Param("status") Integer status, @Param("ids") String[] ids);
    
    int deleteByIds(@Param("ids") String[] ids);
    
    int getCount(LunBoTu record);
}