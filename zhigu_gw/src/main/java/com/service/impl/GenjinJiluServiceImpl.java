package com.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.pojo.GenjinJilu;
import com.dao.GenjinJiluMapper;
import com.service.GenjinJiluService;
import com.util.Constant;
import com.util.ToolsUtils;

@Service("genjinJiluService")
public class GenjinJiluServiceImpl implements GenjinJiluService {

	@Autowired
	private GenjinJiluMapper genjinJiluMapper;
	
	@Transactional
	@Override
	public Map add(GenjinJilu record) {
		genjinJiluMapper.insertSelective(record);
		return ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);
	}
	
	@Transactional
	@Override
	public Map update(GenjinJilu record) {
		genjinJiluMapper.updateByPrimaryKeySelective(record);
		return ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);
	}
	
	@Override
	public List<GenjinJilu> list(GenjinJilu record) {
		return genjinJiluMapper.queryList(record);
	}
	
	@Override
	public int deleteByPrimaryKey(int id) {
		return genjinJiluMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public List<GenjinJilu> listPage(int pageNum, int pageSize,GenjinJilu record) {
		PageHelper.startPage(pageNum, pageSize);
		return genjinJiluMapper.queryList(record);
	}
	
	@Override
	public GenjinJilu queryById(int id) {
		return genjinJiluMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int updateByIds(Integer status,String[] ids) {
		return genjinJiluMapper.updateByIds(status,ids);
	}
	
	@Override
	public int deleteByIds(String[] ids) {
		return genjinJiluMapper.deleteByIds(ids);
	}
	
	@Override
	public int getCount(GenjinJilu record) {
		return genjinJiluMapper.getCount(record);
	}
}
