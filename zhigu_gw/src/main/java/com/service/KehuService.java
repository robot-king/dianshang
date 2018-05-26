package com.service;

import java.util.List;
import com.pojo.Kehu;
import org.apache.ibatis.annotations.Param;
import java.util.Map;

public interface KehuService {
	
	// 添加
	public Map add(Kehu record);

	// 修改
	public Map update(Kehu record);

	// 所有数据
	public List<Kehu> list(Kehu record);

	// 删除
	public int deleteByPrimaryKey(int id);

	// 分页查询
	public List<Kehu> listPage(int pageNum, int pageSize,Kehu record);
	
	// 查询单条数据
	public Kehu queryById(int id);
	
	// 批量修改数据
	int updateByIds(@Param("status") Integer status,@Param("ids") String[] ids);
    
    // 批量删除数据
    int deleteByIds(@Param("ids") String[] ids);
    
    // 总条数
    int getCount(Kehu record);
}