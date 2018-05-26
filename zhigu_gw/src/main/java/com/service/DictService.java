package com.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pojo.Dict;

public interface DictService {

	// 添加
	public int add(Dict record);

	// 修改
	public int update(Dict record);

	// 所有数据
	public List<Dict> list(Dict record);

	// 删除
	public int deleteByPrimaryKey(int id);

	// 分页查询
	public List<Dict> listPage(int pageNum, int pageSize, Dict record);

	// 查询单条数据
	public Dict queryById(int id);

	int updateByIds(@Param("status") Integer status, @Param("ids") String[] ids);

	int deleteByIds(@Param("ids") String[] ids);
}