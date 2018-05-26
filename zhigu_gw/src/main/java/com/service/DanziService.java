package com.service;

import java.util.List;
import com.pojo.Danzi;
import org.apache.ibatis.annotations.Param;
import java.util.Map;

public interface DanziService {
	
	// 添加
	public Map add(Danzi record);

	// 修改
	public Map update(Danzi record);

	// 所有数据
	public List<Danzi> list(Danzi record);

	// 删除
	public int deleteByPrimaryKey(int id);

	// 分页查询
	public List<Danzi> listPage(int pageNum, int pageSize,Danzi record);
	
	// 查询单条数据
	public Danzi queryById(int id);
	
	// 批量修改数据
	int updateByIds(@Param("status") Integer status,@Param("ids") String[] ids);
    
    // 批量删除数据
    int deleteByIds(@Param("ids") String[] ids);
    
    // 总条数
    int getCount(Danzi record);
}