package com.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.pojo.Kehu;
import com.dao.KehuMapper;
import com.service.KehuService;
import com.util.Constant;
import com.util.ToolsUtils;

@Service("kehuService")
public class KehuServiceImpl implements KehuService {

	@Autowired
	private KehuMapper kehuMapper;
	
	@Transactional
	@Override
	public Map add(Kehu record) {
		kehuMapper.insertSelective(record);
		return ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);
	}
	
	@Transactional
	@Override
	public Map update(Kehu record) {
		kehuMapper.updateByPrimaryKeySelective(record);
		return ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);
	}
	
	@Override
	public List<Kehu> list(Kehu record) {
		return kehuMapper.queryList(record);
	}
	
	@Override
	public int deleteByPrimaryKey(int id) {
		return kehuMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public List<Kehu> listPage(int pageNum, int pageSize,Kehu record) {
		PageHelper.startPage(pageNum, pageSize);
		return kehuMapper.queryList(record);
	}
	
	@Override
	public Kehu queryById(int id) {
		return kehuMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int updateByIds(Integer status,String[] ids) {
		return kehuMapper.updateByIds(status,ids);
	}
	
	@Override
	public int deleteByIds(String[] ids) {
		return kehuMapper.deleteByIds(ids);
	}
	
	@Override
	public int getCount(Kehu record) {
		return kehuMapper.getCount(record);
	}
}
