package com.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pojo.Message;

public interface MessageService {

	// 添加
	public int add(Message record);

	// 修改
	public int update(Message record);

	// 所有数据
	public List<Message> list(Message record);

	// 删除
	public int deleteByPrimaryKey(int id);

	// 分页查询
	public List<Message> listPage(int pageNum, int pageSize, Message record);

	// 查询单条数据
	public Message queryById(int id);

	// 批量修改数据
	int updateByIds(@Param("status") Integer status, @Param("ids") String[] ids);

	// 批量删除数据
	int deleteByIds(@Param("ids") String[] ids);

	List<Message> queryListByDialog(int i, int j, Message record);
}