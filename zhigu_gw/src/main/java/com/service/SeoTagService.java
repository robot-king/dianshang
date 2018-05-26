package com.service;

import java.util.List;
import com.pojo.SeoTag;
import org.apache.ibatis.annotations.Param;
import java.util.Map;

public interface SeoTagService {
	
	// 添加
	public Map add(SeoTag record);

	// 修改
	public Map update(SeoTag record);

	// 所有数据
	public List<SeoTag> list(SeoTag record);

	// 删除
	public int deleteByPrimaryKey(int id);

	// 分页查询
	public List<SeoTag> listPage(int pageNum, int pageSize,SeoTag record);
	
	// 查询单条数据
	public SeoTag queryById(int id);
	
	// 批量修改数据
	int updateByIds(@Param("status") Integer status,@Param("ids") String[] ids);
    
    // 批量删除数据
    int deleteByIds(@Param("ids") String[] ids);
    
    // 总条数
    int getCount(SeoTag record);
}