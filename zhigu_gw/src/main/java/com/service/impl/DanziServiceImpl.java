package com.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.pojo.Danzi;
import com.dao.DanziMapper;
import com.service.DanziService;
import com.util.Constant;
import com.util.ToolsUtils;

@Service("danziService")
public class DanziServiceImpl implements DanziService {

	@Autowired
	private DanziMapper danziMapper;
	
	@Transactional
	@Override
	public Map add(Danzi record) {
		danziMapper.insertSelective(record);
		return ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);
	}
	
	@Transactional
	@Override
	public Map update(Danzi record) {
		danziMapper.updateByPrimaryKeySelective(record);
		return ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);
	}
	
	@Override
	public List<Danzi> list(Danzi record) {
		return danziMapper.queryList(record);
	}
	
	@Override
	public int deleteByPrimaryKey(int id) {
		return danziMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public List<Danzi> listPage(int pageNum, int pageSize,Danzi record) {
		PageHelper.startPage(pageNum, pageSize);
		return danziMapper.queryList(record);
	}
	
	@Override
	public Danzi queryById(int id) {
		return danziMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int updateByIds(Integer status,String[] ids) {
		return danziMapper.updateByIds(status,ids);
	}
	
	@Override
	public int deleteByIds(String[] ids) {
		return danziMapper.deleteByIds(ids);
	}
	
	@Override
	public int getCount(Danzi record) {
		return danziMapper.getCount(record);
	}
}
