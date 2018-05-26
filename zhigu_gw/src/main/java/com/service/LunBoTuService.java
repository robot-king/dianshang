package com.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.pojo.LunBoTu;

public interface LunBoTuService {

	// 添加
	public Map add(LunBoTu record, String bannerBase64, String fileUploadPath);

	// 修改
	public Map update(LunBoTu record, String bannerBase64, String fileUploadPath);

	// 所有数据
	public List<LunBoTu> list(LunBoTu record);

	// 删除
	public int deleteByPrimaryKey(int id);

	// 分页查询
	public List<LunBoTu> listPage(int pageNum, int pageSize, LunBoTu record);

	// 查询单条数据
	public LunBoTu queryById(int id);

	// 批量修改数据
	int updateByIds(@Param("status") Integer status, @Param("ids") String[] ids);

	// 批量删除数据
	int deleteByIds(@Param("ids") String[] ids);

	// 总条数
	int getCount(LunBoTu record);
}