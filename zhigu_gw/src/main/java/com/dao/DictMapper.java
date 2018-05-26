package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pojo.Dict;

public interface DictMapper {

	int deleteByPrimaryKey(int id);

	int insert(Dict record);

	int insertSelective(Dict record);

	Dict selectByPrimaryKey(int id);

	int updateByPrimaryKeySelective(Dict record);

	int updateByPrimaryKey(Dict record);

	List<Dict> queryList(Dict record);

	List<Dict> queryListByIdAndNameAndIconUrl(Dict record);

	int updateByIds(@Param("status") Integer status, @Param("ids") String[] ids);

	int deleteByIds(@Param("ids") String[] ids);
	
	List<Dict> queryListJoinDict(Dict record);
}