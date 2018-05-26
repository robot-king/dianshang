package com.service;

import java.util.List;
import com.pojo.GenjinJilu;
import org.apache.ibatis.annotations.Param;
import java.util.Map;

public interface GenjinJiluService {
	
	// 添加
	public Map add(GenjinJilu record);

	// 修改
	public Map update(GenjinJilu record);

	// 所有数据
	public List<GenjinJilu> list(GenjinJilu record);

	// 删除
	public int deleteByPrimaryKey(int id);

	// 分页查询
	public List<GenjinJilu> listPage(int pageNum, int pageSize,GenjinJilu record);
	
	// 查询单条数据
	public GenjinJilu queryById(int id);
	
	// 批量修改数据
	int updateByIds(@Param("status") Integer status,@Param("ids") String[] ids);
    
    // 批量删除数据
    int deleteByIds(@Param("ids") String[] ids);
    
    // 总条数
    int getCount(GenjinJilu record);
}