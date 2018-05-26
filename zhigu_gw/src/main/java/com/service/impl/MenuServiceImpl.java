package com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.MenuMapper;
import com.github.pagehelper.PageHelper;
import com.pojo.Menu;
import com.service.MenuService;

@Service("menuService")
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuMapper menuMapper;
	
	@Override
	public int add(Menu record) {
		return menuMapper.insertSelective(record);
	}
	@Override
	public int update(Menu record) {
		return menuMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public List<Menu> list(Menu record) {
		return menuMapper.queryList(record);
	}
	@Override
	public int deleteByPrimaryKey(int id) {
		return menuMapper.deleteByPrimaryKey(id);
	}
	@Override
	public List<Menu> listPage(int pageNum, int pageSize,Menu record) {
		PageHelper.startPage(pageNum, pageSize);
		return menuMapper.queryList(record);
	}
	@Override
	public Menu queryById(int id) {
		return menuMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public int updateByIds(Integer status,String[] ids) {
		return menuMapper.updateByIds(status,ids);
	}
	
	@Override
	public int deleteByIds(String[] ids) {
		return menuMapper.deleteByIds(ids);
	}
	@Override
	public List<Menu> queryListByEmployeeId(int pageNum, int pageSize,Integer employeeId) {
		PageHelper.startPage(pageNum, pageSize);
		return menuMapper.queryListByEmployeeId(employeeId);
	}
	@Override
	public List<Menu> menuData(Integer employeeId) {
		return menuMapper.queryListByEmployeeId(employeeId);
	}
}
