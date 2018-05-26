package com.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dao.UserAuthMapper;
import com.dao.UserMapper;
import com.github.pagehelper.PageHelper;
import com.pojo.User;
import com.pojo.UserAuth;
import com.service.UserAuthService;
import com.service.UserService;
import com.util.Constant;
import com.util.Md5EncryptUtil;
import com.util.SessionUtils;
import com.util.StringUtil;
import com.util.ToolsUtils;

@Service("userAuthService")
public class UserAuthServiceImpl implements UserAuthService {

	@Autowired
	private UserAuthMapper userAuthMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private UserService userService;

	@Override
	public int add(UserAuth record) {
		return userAuthMapper.insertSelective(record);
	}

	@Override
	public int update(UserAuth record) {
		return userAuthMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<UserAuth> list(UserAuth record) {
		return userAuthMapper.queryList(record);
	}

	@Override
	public int deleteByPrimaryKey(int id) {
		return userAuthMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<UserAuth> listPage(int pageNum, int pageSize, UserAuth record) {
		PageHelper.startPage(pageNum, pageSize);
		return userAuthMapper.queryList(record);
	}

	@Override
	public UserAuth queryById(int id) {
		return userAuthMapper.selectByPrimaryKey(id);
	}

	@Override
	public Map login(UserAuth userAuth, HttpServletRequest request) {

		Map map = new HashMap();

		try {
			if (StringUtil.isEmpty(userAuth.getIdentifier())) {
				return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入用户名");
			}
			if (StringUtil.isEmpty(userAuth.getCredential())) {
				return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入密码");
			}
			UserAuth ua = new UserAuth();
			ua.setIdentityType(Constant.IdentityType.MOBILE);
			ua.setIdentifier(userAuth.getIdentifier());
			List<UserAuth> list = userAuthMapper.queryList(ua);
			if (!CollectionUtils.isEmpty(list)) {
				ua = list.get(0);
				User user = userService.queryById(ua.getUserId());
				if (null != user) {
					if (user.getLoginStatus().intValue() == User.LONGIN_STATUS_FENGJIN) {
						return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "您已经被禁止登录");
					}
				}
				if (ua.getCredential().equals(Md5EncryptUtil.MD5(userAuth.getCredential()))) {
					Map ccMap = new HashMap();

					ccMap.put("userId", user.getId());
					ccMap.put("iconUrl", user.getIconUrl());
					ccMap.put("money", user.getMoney());
					ccMap.put("name", user.getName());
					ccMap.put("nickName", user.getNickName());
					ccMap.put("signature", user.getSignature());
					ccMap.put("vipType", user.getVipType());

					user.setLoginTime(new Date());
					user.setLoginStatus(User.LONGIN_STATUS_ZAIXAIN);
					userService.update(user);

					if (null != SessionUtils.getAttr(request, SessionUtils.SYSTEM_WEIXIN)) {
						SessionUtils.setAttr(request, SessionUtils.SYSTEM_WEI_XIN_MOBILE, ua.getIdentifier(),
								SessionUtils.SYSTEM_ONE_HOUR);
					}

					map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, ccMap, "登录成功");
				} else {
					map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "密码错误");
				}
			} else {
				map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "手机号不存在");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}

		return map;
	}

	@Override
	public Map loginByMessageCode(String mobile, String validCode, HttpServletRequest request) {

		Map map = new HashMap();

		try {
			if (StringUtil.isEmpty(mobile)) {
				return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入手机号");
			}
			if (StringUtil.isEmpty(validCode)) {
				return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入验证码");
			} else {
				// 判断是否存在并有效
				if (StringUtil.isEmpty(SessionUtils.getValidateCode(request, SessionUtils.SYSTEM_VALIDATE_NUM))) {
					return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "验证码已失效");
				}
				// 判断是否存在并有效
				if (!validCode.equals(SessionUtils.getValidateCode(request, SessionUtils.SYSTEM_VALIDATE_NUM))) {
					return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "验证码错误");
				}
			}
			UserAuth ua = new UserAuth();
			ua.setIdentityType(Constant.IdentityType.MOBILE);
			ua.setIdentifier(mobile);
			List<UserAuth> list = userAuthMapper.queryList(ua);
			if (!CollectionUtils.isEmpty(list)) {
				ua = list.get(0);
				User user = userService.queryById(ua.getUserId());
				if (null != user) {
					if (user.getLoginStatus().intValue() == User.LONGIN_STATUS_FENGJIN) {
						return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "您已经被禁止登录");
					}
				}
				Map ccMap = new HashMap();

				ccMap.put("userId", user.getId());
				ccMap.put("iconUrl", user.getIconUrl());
				ccMap.put("money", user.getMoney());
				ccMap.put("name", user.getName());
				ccMap.put("nickName", user.getNickName());
				ccMap.put("signature", user.getSignature());
				ccMap.put("vipType", user.getVipType());

				user.setLoginTime(new Date());
				user.setLoginStatus(User.LONGIN_STATUS_ZAIXAIN);
				userService.update(user);

				map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, ccMap, "登录成功");
			} else {
				map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "手机号不存在");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}

		return map;
	}

	@Override
	public Map setEmail(UserAuth userAuth) {
		Map map = new HashMap();

		try {
			if (StringUtil.isEmpty(userAuth.getIdentifier())) {
				return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入邮箱 Identifier");
			}
			if (null == userAuth.getUserId()) {
				return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入用户ID  UserId");
			}
			UserAuth ua = new UserAuth();
			ua.setIdentityType(Constant.IdentityType.EMAIL);
			ua.setUserId(userAuth.getUserId());
			List<UserAuth> list = userAuthMapper.queryList(ua);
			if (!CollectionUtils.isEmpty(list)) {
				ua = list.get(0);
				ua.setIdentifier(userAuth.getIdentifier());
				userAuthMapper.updateByPrimaryKeySelective(ua);
			} else {
				ua.setIdentifier(userAuth.getIdentifier());
				ua.setCreateTime(new Date());
				userAuthMapper.insertSelective(ua);
			}
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, ua.getId(),
					Constant.DataStatus.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, Constant.DataStatus.FAIL_MSG);

		}

		return map;
	}

	@Override
	public Map setWeChat(UserAuth userAuth) {
		Map map = new HashMap();

		try {
			if (StringUtil.isEmpty(userAuth.getIdentifier())) {
				return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入微信唯一标识 Identifier");
			}
			if (null == userAuth.getUserId()) {
				return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入用户ID  UserId");
			}
			UserAuth ua = new UserAuth();
			ua.setIdentityType(Constant.IdentityType.WE_CHAT);
			ua.setUserId(userAuth.getUserId());
			List<UserAuth> list = userAuthMapper.queryList(ua);
			if (!CollectionUtils.isEmpty(list)) {
				ua = list.get(0);
				ua.setIdentifier(userAuth.getIdentifier());
				userAuthMapper.updateByPrimaryKeySelective(ua);
			} else {
				ua.setIdentifier(userAuth.getIdentifier());
				ua.setCreateTime(new Date());
				userAuthMapper.insertSelective(ua);
			}
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, ua.getId(),
					Constant.DataStatus.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, Constant.DataStatus.FAIL_MSG);

		}

		return map;
	}

	@Override
	public Map setWeBo(UserAuth userAuth) {
		Map map = new HashMap();

		try {
			if (StringUtil.isEmpty(userAuth.getIdentifier())) {
				return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入微博唯一标识 Identifier");
			}
			if (null == userAuth.getUserId()) {
				return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入用户ID  UserId");
			}
			UserAuth ua = new UserAuth();
			ua.setIdentityType(Constant.IdentityType.WEI_BO);
			ua.setUserId(userAuth.getUserId());
			List<UserAuth> list = userAuthMapper.queryList(ua);
			if (!CollectionUtils.isEmpty(list)) {
				ua = list.get(0);
				ua.setIdentifier(userAuth.getIdentifier());
				userAuthMapper.updateByPrimaryKeySelective(ua);
			} else {
				ua.setIdentifier(userAuth.getIdentifier());
				ua.setCreateTime(new Date());
				userAuthMapper.insertSelective(ua);
			}
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, ua.getId(),
					Constant.DataStatus.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, Constant.DataStatus.FAIL_MSG);

		}

		return map;
	}

	@Override
	public Map setQQ(UserAuth userAuth) {
		Map map = new HashMap();

		try {
			if (StringUtil.isEmpty(userAuth.getIdentifier())) {
				return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入QQ唯一标识 Identifier");
			}
			if (null == userAuth.getUserId()) {
				return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入用户ID  UserId");
			}
			UserAuth ua = new UserAuth();
			ua.setIdentityType(Constant.IdentityType.QQ);
			ua.setUserId(userAuth.getUserId());
			List<UserAuth> list = userAuthMapper.queryList(ua);
			if (!CollectionUtils.isEmpty(list)) {
				ua = list.get(0);
				ua.setIdentifier(userAuth.getIdentifier());
				userAuthMapper.updateByPrimaryKeySelective(ua);
			} else {
				ua.setIdentifier(userAuth.getIdentifier());
				ua.setCreateTime(new Date());
				userAuthMapper.insertSelective(ua);
			}
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, ua.getId(),
					Constant.DataStatus.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, Constant.DataStatus.FAIL_MSG);

		}

		return map;
	}

	@Override
	public Map updatePwd(Integer userId, String oldPwd, String newPwd) {

		Map map = new HashMap();

		if (null == userId) {
			return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入用户ID  userId");
		}
		if (StringUtil.isEmpty(oldPwd)) {
			return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入旧密码 oldPwd");
		}
		if (StringUtil.isEmpty(newPwd)) {
			return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入新密码 newPwd");
		}

		UserAuth ua = new UserAuth();
		ua.setUserId(userId);
		List<UserAuth> list = userAuthMapper.queryList(ua);
		if (!CollectionUtils.isEmpty(list)) {
			ua = list.get(0);
			if (ua.getCredential().equals(Md5EncryptUtil.MD5(oldPwd))) {
				for (UserAuth userAuth : list) {
					userAuth.setCredential(Md5EncryptUtil.MD5(newPwd));
					userAuthMapper.updateByPrimaryKeySelective(userAuth);
				}
				map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, ua.getId(),
						Constant.DataStatus.SUCCESS_MSG);
			} else {
				return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "原密码错误");
			}
		}

		return map;
	}

	@Override
	public Map resetPwd(UserAuth record) {

		Map map = new HashMap();

		if (StringUtil.isEmpty(record.getCredential())) {
			return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入密码 Credential");
		}
		if (null == record.getUserId()) {
			return ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入用户ID  UserId");
		}

		UserAuth ua = new UserAuth();
		ua.setUserId(record.getUserId());
		List<UserAuth> list = userAuthMapper.queryList(ua);
		if (!CollectionUtils.isEmpty(list)) {
			for (UserAuth userAuth : list) {
				userAuth.setCredential(Md5EncryptUtil.MD5(record.getCredential()));
				userAuthMapper.updateByPrimaryKeySelective(userAuth);
			}
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, ua.getId(),
					Constant.DataStatus.SUCCESS_MSG);
		}

		return map;
	}

}
