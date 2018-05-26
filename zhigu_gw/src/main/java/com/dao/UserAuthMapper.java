package com.dao;

import java.util.List;

import com.pojo.UserAuth;

public interface UserAuthMapper {

	int deleteByPrimaryKey(int id);

    int insert(UserAuth record);

    int insertSelective(UserAuth record);

    UserAuth selectByPrimaryKey(int id);

    int updateByPrimaryKeySelective(UserAuth record);

    int updateByPrimaryKey(UserAuth record);
    
    List<UserAuth> queryList(UserAuth record);
}