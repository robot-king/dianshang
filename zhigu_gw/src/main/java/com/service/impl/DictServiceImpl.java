package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.DictMapper;
import com.github.pagehelper.PageHelper;
import com.pojo.Dict;
import com.service.DictService;

@Service("dictService")
public class DictServiceImpl implements DictService {

	@Autowired
	private DictMapper dictMapper;

	@Override
	public int add(Dict record) {
		return dictMapper.insertSelective(record);
	}

	@Override
	public int update(Dict record) {
		return dictMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<Dict> list(Dict record) {
		return dictMapper.queryList(record);
	}

	@Override
	public int deleteByPrimaryKey(int id) {
		return dictMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<Dict> listPage(int pageNum, int pageSize, Dict record) {
		PageHelper.startPage(pageNum, pageSize);
		return dictMapper.queryList(record);
	}

	@Override
	public Dict queryById(int id) {
		return dictMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByIds(Integer status,String[] ids) {
		return dictMapper.updateByIds(status,ids);
	}

	@Override
	public int deleteByIds(String[] ids) {
		return dictMapper.deleteByIds(ids);
	}
}
