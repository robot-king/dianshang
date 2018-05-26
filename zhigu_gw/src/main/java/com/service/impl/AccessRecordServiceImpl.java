package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.pojo.AccessRecord;
import com.dao.AccessRecordMapper;
import com.service.AccessRecordService;

@Service("accessRecordService")
public class AccessRecordServiceImpl implements AccessRecordService {

	@Autowired
	private AccessRecordMapper accessRecordMapper;
	
	@Override
	public int add(AccessRecord record) {
		return accessRecordMapper.insertSelective(record);
	}
	@Override
	public int update(AccessRecord record) {
		return accessRecordMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public List<AccessRecord> list(AccessRecord record) {
		return accessRecordMapper.queryList(record);
	}
	@Override
	public int deleteByPrimaryKey(int id) {
		return accessRecordMapper.deleteByPrimaryKey(id);
	}
	@Override
	public List<AccessRecord> listPage(int pageNum, int pageSize,AccessRecord record) {
		PageHelper.startPage(pageNum, pageSize);
		return accessRecordMapper.queryList(record);
	}
	@Override
	public AccessRecord queryById(int id) {
		return accessRecordMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int updateByIds(Integer status,String[] ids) {
		return accessRecordMapper.updateByIds(status,ids);
	}
	
	@Override
	public int deleteByIds(String[] ids) {
		return accessRecordMapper.deleteByIds(ids);
	}
}
