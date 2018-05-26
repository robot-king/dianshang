package com.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dao.UserAuthMapper;
import com.dao.UserMapper;
import com.github.pagehelper.PageHelper;
import com.pojo.User;
import com.pojo.UserAuth;
import com.service.UserService;
import com.util.Constant;
import com.util.SessionUtils;
import com.util.StringUtil;
import com.util.ToolsUtils;
import com.util.date.DateUtil;
import com.util.excel.ExcelUtils;
import com.util.file.FileUploadUtil;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserAuthMapper userAuthMapper;
	@Autowired(required = false)
	private HttpServletRequest request;

	@Override
	public int add(User record) {
		return userMapper.insertSelective(record);
	}

	@Override
	public int update(User record) {
		int c = userMapper.updateByPrimaryKeySelective(record);
		User user = queryById(record.getId());
		SessionUtils.setAttr(request, SessionUtils.SYSTEM_USER, user, SessionUtils.SYSTEM_TWO_HOUR);
		return c;
	}

	@Override
	public List<User> list(User record) {
		return userMapper.queryList(record);
	}

	@Override
	public int deleteByPrimaryKey(int id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<User> listPage(int pageNum, int pageSize, User record) {
		PageHelper.startPage(pageNum, pageSize);
		return userMapper.queryList(record);
	}

	@Override
	public User queryById(int id) {
		return userMapper.selectByPrimaryKey(id);
	}

	public Map updateNickName(User record, MultipartFile iconUrlFile, String fileUploadPath) {
		Map map = new HashMap();
		try {
			if (null == record.getId()) {
				return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入用户ID id");
			}
			if (StringUtil.isEmpty(record.getNickName())) {
				return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入昵称 NickName");
			}

			// 判断自定义链接是否存在
			User user = userMapper.selectByPrimaryKey(record.getId());
			if (!StringUtil.isEmpty(record.getCustomeUrl())) {
				User uu = new User();
				uu.setCustomeUrl(record.getCustomeUrl());
				List<User> ulist = userMapper.queryList(uu);
				if (!CollectionUtils.isEmpty(ulist)) {
					uu = ulist.get(0);
					if (!uu.getId().equals(user.getId())) {
						return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "自定义URL已存在");
					}
				}
			}

			// 记录用户曾用名
			if (StringUtil.isEmpty(user.getPassedName())) {
				if (!StringUtil.isEmpty(user.getNickName()) && !record.getNickName().equals(user.getNickName())) {
					record.setPassedName(user.getNickName());
				}
			} else {
				String passedName = user.getPassedName();
				if (!StringUtil.isEmpty(user.getNickName()) && !passedName.contains(user.getNickName())
						&& !record.getNickName().equals(user.getNickName())) {
					passedName = passedName + "," + user.getNickName();
					record.setPassedName(passedName);
				}
			}

			String dictory = DateUtil.formatDate(new Date(), "yyyyMM");
			String iconUrlPath = "";
			if (null != iconUrlFile && !iconUrlFile.isEmpty()) {
				try {
					iconUrlPath = FileUploadUtil.uploadImgByCutSize(iconUrlFile, fileUploadPath, dictory, 140, 140);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				record.setIconUrl(iconUrlPath);
			}

			userMapper.updateByPrimaryKeySelective(record);

			// 更新登录授权昵称
			UserAuth userAuth = new UserAuth();
			userAuth.setIdentityType(Constant.IdentityType.NICK_NAME);
			userAuth.setUserId(record.getId());

			List<UserAuth> listAuths = userAuthMapper.queryList(userAuth);
			if (!CollectionUtils.isEmpty(listAuths)) {
				userAuth = listAuths.get(0);
				userAuth.setIdentifier(record.getNickName());
				userAuthMapper.updateByPrimaryKeySelective(userAuth);
			}

			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, user.getId(),
					Constant.DataStatus.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, Constant.DataStatus.FAIL_MSG);
		}

		return map;
	}

	@Override
	public byte[] exportExcel() {
		HSSFWorkbook workbook = new HSSFWorkbook();// 创建excel
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] result = null;
		try {
			DecimalFormat df = new DecimalFormat("0.00");

			// 设置统计概况表格头
			List<String> summaryHead = new ArrayList<String>();
			summaryHead.add("用户ID");
			summaryHead.add("注册名");
			summaryHead.add("手机号");
			summaryHead.add("昵称");
			summaryHead.add("关联微信号");
			summaryHead.add("关联QQ号");
			summaryHead.add("手机型号");
			summaryHead.add("手机类型");
			summaryHead.add("阅读总时间");
			summaryHead.add("阅读文章总数");
			summaryHead.add("关注分类数量");
			summaryHead.add("新增关注数量");
			summaryHead.add("取消关注数量");
			summaryHead.add("创建时间");

			String summarySheetName = "用户账号";
			List staticsList = new ArrayList<>();
			List<User> userList = userMapper.queryList(null);

			for (User user : userList) {
				List targetList = new ArrayList<>();

				targetList.add(DateUtil.DateToStr(user.getCreateTime(), DateUtil.LONG_DATE_PATTERN));
				staticsList.add(targetList);
			}

			ExcelUtils.createExcelData(workbook, summarySheetName, summaryHead, staticsList);

			workbook.write(out);
			result = out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public List<User> queryNotFriendList(int pageNum, int pageSize, User record) {
		PageHelper.startPage(pageNum, pageSize);
		return userMapper.queryNotFriendList(record);
	}

	@Override
	public void updateUserLoginStatus(User uu) {
		userMapper.updateByPrimaryKeySelective(uu);
	}
}
