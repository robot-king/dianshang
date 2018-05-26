package com.service;

import java.util.List;
import com.pojo.AccessRecord;
import org.apache.ibatis.annotations.Param;

public interface AccessRecordService {
	
	// 添加
	public int add(AccessRecord record);

	// 修改
	public int update(AccessRecord record);

	// 所有数据
	public List<AccessRecord> list(AccessRecord record);

	// 删除
	public int deleteByPrimaryKey(int id);

	// 分页查询
	public List<AccessRecord> listPage(int pageNum, int pageSize,AccessRecord record);
	
	// 查询单条数据
	public AccessRecord queryById(int id);
	
	// 批量修改数据
	int updateByIds(@Param("status") Integer status,@Param("ids") String[] ids);
    
    // 批量删除数据
    int deleteByIds(@Param("ids") String[] ids);
}