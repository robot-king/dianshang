package com.dao;

import java.util.List;
import com.pojo.AccessRecord;
import org.apache.ibatis.annotations.Param;

public interface AccessRecordMapper {

	int deleteByPrimaryKey(int id);

    int insert(AccessRecord record);

    int insertSelective(AccessRecord record);

    AccessRecord selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(AccessRecord record);

    int updateByPrimaryKey(AccessRecord record);
    
    List<AccessRecord> queryList(AccessRecord record);
    
    int updateByIds(@Param("status") Integer status, @Param("ids") String[] ids);
    
    int deleteByIds(@Param("ids") String[] ids);
}