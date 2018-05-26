package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pojo.Message;

public interface MessageMapper {

	int deleteByPrimaryKey(int id);

	int insertSelective(Message record);

	Message selectByPrimaryKey(int id);

	int updateByPrimaryKeySelective(Message record);

	List<Message> queryList(Message record);

	int updateByIds(@Param("status") Integer status, @Param("ids") String[] ids);

	int deleteByIds(@Param("ids") String[] ids);

	List<Message> queryListByDialog(Message record);
}