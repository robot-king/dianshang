package com.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.pojo.UserAuth;

public interface UserAuthService {

	// 添加
	public int add(UserAuth record);

	// 修改
	public int update(UserAuth record);

	// 所有数据
	public List<UserAuth> list(UserAuth record);

	// 删除
	public int deleteByPrimaryKey(int id);

	// 分页查询
	public List<UserAuth> listPage(int pageNum, int pageSize, UserAuth record);

	// 查询单条数据
	public UserAuth queryById(int id);

	// 添加
	public Map login(UserAuth record, HttpServletRequest request);

	// 设置邮箱
	public Map setEmail(UserAuth record);

	// 绑定微信
	public Map setWeChat(UserAuth record);

	// 绑定微博
	public Map setWeBo(UserAuth record);

	// 绑定QQ
	public Map setQQ(UserAuth record);

	// 修改密码
	public Map updatePwd(Integer userId, String oldPwd, String newPwd);

	// 重置密码
	public Map resetPwd(UserAuth record);

	// 通过手机验证码登录
	Map loginByMessageCode(String mobile, String validCode, HttpServletRequest request);
}