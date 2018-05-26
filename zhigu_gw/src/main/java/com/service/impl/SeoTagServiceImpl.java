package com.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.pojo.SeoTag;
import com.dao.SeoTagMapper;
import com.service.SeoTagService;
import com.util.Constant;
import com.util.ToolsUtils;

@Service("seoTagService")
public class SeoTagServiceImpl implements SeoTagService {

	@Autowired
	private SeoTagMapper seoTagMapper;
	
	@Transactional
	@Override
	public Map add(SeoTag record) {
		seoTagMapper.insertSelective(record);
		return ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);
	}
	
	@Transactional
	@Override
	public Map update(SeoTag record) {
		seoTagMapper.updateByPrimaryKeySelective(record);
		return ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);
	}
	
	@Override
	public List<SeoTag> list(SeoTag record) {
		return seoTagMapper.queryList(record);
	}
	
	@Override
	public int deleteByPrimaryKey(int id) {
		return seoTagMapper.deleteByPrimaryKey(id);
	}
	
	@Override
	public List<SeoTag> listPage(int pageNum, int pageSize,SeoTag record) {
		PageHelper.startPage(pageNum, pageSize);
		return seoTagMapper.queryList(record);
	}
	
	@Override
	public SeoTag queryById(int id) {
		return seoTagMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int updateByIds(Integer status,String[] ids) {
		return seoTagMapper.updateByIds(status,ids);
	}
	
	@Override
	public int deleteByIds(String[] ids) {
		return seoTagMapper.deleteByIds(ids);
	}
	
	@Override
	public int getCount(SeoTag record) {
		return seoTagMapper.getCount(record);
	}
}
