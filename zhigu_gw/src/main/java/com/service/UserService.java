package com.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.pojo.User;

public interface UserService {

	// 添加
	public int add(User record);

	// 修改
	public int update(User record);

	// 所有数据
	public List<User> list(User record);

	// 删除
	public int deleteByPrimaryKey(int id);

	// 分页查询
	public List<User> listPage(int pageNum, int pageSize, User record);

	// 查询单条数据
	public User queryById(int id);

	// 修改昵称
	public Map updateNickName(User record, MultipartFile iconUrlFile, String fileUploadPath);

	// 导出
	public byte[] exportExcel();

	// 非好友用户列表
	List<User> queryNotFriendList(int pageNum, int pageSize, User record);

	public void updateUserLoginStatus(User uu);
}