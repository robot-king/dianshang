package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.EmployeeMenuMapper;
import com.github.pagehelper.PageHelper;
import com.pojo.EmployeeMenu;
import com.service.EmployeeMenuService;

@Service("employeeMenuService")
public class EmployeeMenuServiceImpl implements EmployeeMenuService {

	@Autowired
	private EmployeeMenuMapper employeeMenuMapper;
	
	@Override
	public int add(EmployeeMenu record) {
		return employeeMenuMapper.insertSelective(record);
	}
	@Override
	public int update(EmployeeMenu record) {
		return employeeMenuMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public List<EmployeeMenu> list(EmployeeMenu record) {
		return employeeMenuMapper.queryList(record);
	}
	@Override
	public int deleteByPrimaryKey(int id) {
		return employeeMenuMapper.deleteByPrimaryKey(id);
	}
	@Override
	public List<EmployeeMenu> listPage(int pageNum, int pageSize,EmployeeMenu record) {
		PageHelper.startPage(pageNum, pageSize);
		return employeeMenuMapper.queryList(record);
	}
	@Override
	public EmployeeMenu queryById(int id) {
		return employeeMenuMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int updateByIds(Integer status,String[] ids) {
		return employeeMenuMapper.updateByIds(status,ids);
	}
	
	@Override
	public int deleteByIds(String[] ids) {
		return employeeMenuMapper.deleteByIds(ids);
	}
}
