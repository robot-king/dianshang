package com.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dao.EmployeeMapper;
import com.github.pagehelper.PageHelper;
import com.pojo.Employee;
import com.pojo.UserAuth;
import com.service.EmployeeService;
import com.util.Constant;
import com.util.Md5EncryptUtil;
import com.util.StringUtil;
import com.util.ToolsUtils;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeMapper employeeMapper;

	@Override
	public int add(Employee record) {
		return employeeMapper.insertSelective(record);
	}

	@Override
	public int update(Employee record) {
		return employeeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<Employee> list(Employee record) {
		return employeeMapper.queryList(record);
	}

	@Override
	public int deleteByPrimaryKey(int id) {
		return employeeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<Employee> listPage(int pageNum, int pageSize, Employee record) {
		PageHelper.startPage(pageNum, pageSize);
		return employeeMapper.queryList(record);
	}

	@Override
	public Employee queryById(int id) {
		return employeeMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByIds(Integer status, String[] ids) {
		return employeeMapper.updateByIds(status, ids);
	}

	@Override
	public int deleteByIds(String[] ids) {
		return employeeMapper.deleteByIds(ids);
	}

	@Override
	public Map updatePwd(Integer id, String oldPwd, String newPwd) {
		Map map = new HashMap();

		if (null == id) {
			return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入用户ID  userId");
		}
		if (StringUtil.isEmpty(oldPwd)) {
			return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入旧密码 oldPwd");
		}
		if (StringUtil.isEmpty(newPwd)) {
			return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入新密码 newPwd");
		}

		Employee ua = employeeMapper.selectByPrimaryKey(id);
		if (ua.getPwd().equals(Md5EncryptUtil.MD5(oldPwd))) {
			ua.setPwd(Md5EncryptUtil.MD5(newPwd));
			employeeMapper.updateByPrimaryKeySelective(ua);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, Constant.DataStatus.SUCCESS_MSG);
		} else {
			return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "原密码错误");
		}

		return map;
	}
}
