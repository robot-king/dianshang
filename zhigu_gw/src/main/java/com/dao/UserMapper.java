package com.dao;

import java.util.List;

import com.pojo.User;

public interface UserMapper {

	int deleteByPrimaryKey(int id);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(int id);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);

	List<User> queryList(User record);

	List<User> queryNotFriendList(User record);
}