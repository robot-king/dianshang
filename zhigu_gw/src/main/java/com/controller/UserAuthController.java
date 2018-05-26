package com.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pojo.User;
import com.pojo.UserAuth;
import com.service.UserAuthService;
import com.service.UserService;
import com.util.Constant;
import com.util.JsonUtils;
import com.util.Md5EncryptUtil;
import com.util.SessionUtils;
import com.util.StringUtil;
import com.util.ToolsUtils;
import com.util.file.FileUploadUtil;
import com.util.pay.weixin.login.AccessToken;
import com.util.pay.weixin.login.SnsApiUserInfo;
import com.util.pay.weixin.login.WeixinAuth;

@Controller
@RequestMapping("userAuth")
public class UserAuthController extends BaseController {

	/** 日志对象 */
	private static final Logger logger = Logger.getLogger(UserAuthController.class);

	@Autowired
	private UserAuthService userAuthService;
	@Autowired
	private UserService userService;

	// 手机号登录
	@RequestMapping(value = "/login.action", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String login(UserAuth userAuth, HttpServletRequest request) {
		Map map = new HashMap();

		map = userAuthService.login(userAuth, request);

		return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
	}

	// 手机号短信验证码登录
	@RequestMapping(value = "/loginByMessageCode.action", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String loginByMessageCode(String mobile, String messageCode, HttpServletRequest request) {
		Map map = new HashMap();

		map = userAuthService.loginByMessageCode(mobile, messageCode, request);

		return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
	}

	// 设置邮箱
	@RequestMapping(value = "/setEmail.action", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String setEmail(UserAuth userAuth, HttpServletRequest request) {
		Map map = new HashMap();

		map = userAuthService.setEmail(userAuth);

		return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
	}

	// 绑定微信
	@RequestMapping(value = "/setWeChat.action", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String setWeChat(UserAuth userAuth, HttpServletRequest request) {
		Map map = new HashMap();

		map = userAuthService.setWeChat(userAuth);

		return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
	}

	// 绑定微博
	@RequestMapping(value = "/setWeBo.action", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String setWeBo(UserAuth userAuth, HttpServletRequest request) {
		Map map = new HashMap();

		map = userAuthService.setWeBo(userAuth);

		return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
	}

	// 绑定QQ
	@RequestMapping(value = "/setQQ.action", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String setQQ(UserAuth userAuth, HttpServletRequest request) {
		Map map = new HashMap();

		map = userAuthService.setQQ(userAuth);

		return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
	}

	// 修改密码
	@RequestMapping(value = "/updatePwd.action", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String updatePwd(Integer userId, String oldPwd, String newPwd, HttpServletRequest request) {
		Map map = new HashMap();

		map = userAuthService.updatePwd(userId, oldPwd, newPwd);

		return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
	}

	// 重置密码
	@RequestMapping(value = "/resetPwd.do", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String resetPwd(UserAuth userAuth, HttpServletRequest request) {
		Map map = new HashMap();

		map = userAuthService.resetPwd(userAuth);

		return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
	}

	// 登录页面.退出页面
	@RequestMapping("/loginPage.action")
	public String loginPage(Model model, HttpServletRequest request) {
		SessionUtils.removeAttr(request, SessionUtils.SYSTEM_USER);
		return "home/login";
	}

	// 修改密码页面
	@RequestMapping("/update_pwd_page.action")
	public String update_pwd_page() throws Exception {
		return "home/update_pwd_page";
	}

	/**
	 * 微信登录（微信引导页进入的方法）
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/loginByWeiXin.action")
	public String loginByWeiXin(HttpServletRequest request) throws UnsupportedEncodingException {
		// 微信接口自带 2 个参数
		String code = request.getParameter("code");
		String state = request.getParameter("state");
		System.out.println("code = " + code + ", state = " + state);

		if (code != null && !"".equals(code)) {
			// 授权成功, 微信获取用户openID
			logger.info("授权成功, 微信获取用户openID.....");
			AccessToken authInfo = WeixinAuth.getAccessToken(code);
			String openid = authInfo.getOpenid();
			String access_token = authInfo.getAccess_token();

			if (access_token == null) {
				logger.info("未检测到令牌.....");
				return "redirect:" + WeixinAuth.getStartURLToGetCode();
			}

			// 获取微信用户信息
			logger.info("获取微信用户信息.....");
			SnsApiUserInfo sns = WeixinAuth.getUserInfo(authInfo);

			// 数据库中查询微信号是否绑定平台账号
			UserAuth ua = new UserAuth();
			ua.setIdentifier(openid);
			ua.setIdentityType(Constant.IdentityType.WE_CHAT);
			List<UserAuth> list = userAuthService.list(ua);
			if (CollectionUtils.isEmpty(list)) {

				// 尚未绑定账号
				logger.info("尚未绑定账号.....");
				logger.info("正在绑定账号.....");

				if (null != sns) {
					User user = new User();
					user.setNickName(sns.getNickname());
					// 下载微信头像，并设置大小头像
					logger.info("下载微信头像，并设置大小头像");
					user.setIconUrl(FileUploadUtil.setPicByDownload(sns.getHeadimgurl(), fileUploadPath));
					user.setLoginTime(new Date());
					user.setCreateTime(new Date());
					userService.add(user);
					ua.setUserId(user.getId());
				}

				ua.setCreateTime(new Date());
				userAuthService.add(ua);
				logger.info("绑定账号成功.....");
			} else {
				logger.info("已绑定微信.....");
				ua = list.get(0);
				User uu = userService.queryById(ua.getUserId());
				if (StringUtil.isEmpty(uu.getNickName())) {
					uu.setNickName(sns.getNickname());
				}
				// 下载微信头像，并设置大小头像
				logger.info("下载微信头像，并设置大小头像");
				if (StringUtil.isEmpty(uu.getIconUrl())) {
					uu.setIconUrl(FileUploadUtil.setPicByDownload(sns.getHeadimgurl(), fileUploadPath));
				}
				uu.setLoginTime(new Date());
				userService.update(uu);
			}
			User user = userService.queryById(ua.getUserId());
			SessionUtils.setAttr(request, "openid", openid, SessionUtils.SYSTEM_TWO_HOUR);
			SessionUtils.setAttr(request, SessionUtils.SYSTEM_USER, user, SessionUtils.SYSTEM_TWO_HOUR);

			SessionUtils.setAttr(request, SessionUtils.SYSTEM_WEIXIN, openid, SessionUtils.SYSTEM_TWO_HOUR);

			logger.info("查询手机是否绑定手机号.....");

			UserAuth ua2 = new UserAuth();
			ua2.setUserId(ua.getUserId());
			ua2.setIdentityType(UserAuth.IDENTITYTYPE_SHOUJI);
			List<UserAuth> list2 = userAuthService.list(ua2);
			if (CollectionUtils.isEmpty(list2)) {
				logger.info("未绑定手机号，前往绑定手机页面");
				return "home/bind_mobile";
			}
			logger.info("已绑定手机号.....");
			logger.info("登录成功.....");
			ua2 = list2.get(0);
			SessionUtils.setAttr(request, SessionUtils.SYSTEM_WEI_XIN_MOBILE, ua2.getIdentifier(),
					SessionUtils.SYSTEM_ONE_HOUR);

			return "home/index";
		}
		logger.info("引导用户授权.....");
		return "redirect:" + WeixinAuth.getStartURLToGetCode();
	}

	// 验证手机号
	@RequestMapping(value = "/yanzhengShouji.action", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String yanzhengShouji(Integer userId, String mobile, String validCode, HttpServletRequest request) {
		Map map = new HashMap();
		if (StringUtil.isEmpty(mobile)) {
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入手机号");
			return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
		}
		if (StringUtil.isEmpty(validCode)) {
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入验证码");
			return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
		} else {
			// 判断是否存在并有效
			if (StringUtil.isEmpty(SessionUtils.getValidateCode(request, SessionUtils.SYSTEM_VALIDATE_NUM))) {
				map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "验证码已失效");
				return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
			}
			// 判断是否存在并有效
			if (!validCode.equals(SessionUtils.getValidateCode(request, SessionUtils.SYSTEM_VALIDATE_NUM))) {
				map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "验证码错误");
				return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
			}
		}
		map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "验证码正确");
		return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
	}

	// 绑定手机号,输入密码则设置密码（未在本网站注册的用户）
	@RequestMapping(value = "/setMobile.action", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String setMobile(Integer userId, String mobile, String pwd, String validCode, HttpServletRequest request) {
		Map map = new HashMap();
		if (StringUtil.isEmpty(mobile)) {
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入手机号");
			return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
		} else {
			UserAuth ua = new UserAuth();
			ua.setIdentityType(Constant.IdentityType.MOBILE);
			ua.setIdentifier(mobile);
			List<UserAuth> list = userAuthService.list(ua);
			if (!CollectionUtils.isEmpty(list)) {
				map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "手机号已存在");
				return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
			}
		}
		if (StringUtil.isEmpty(validCode)) {
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入验证码");
			return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
		} else {
			// 判断是否存在并有效
			if (StringUtil.isEmpty(SessionUtils.getValidateCode(request, SessionUtils.SYSTEM_VALIDATE_NUM))) {
				map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "验证码已失效");
				return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
			}
			// 判断是否存在并有效
			if (!validCode.equals(SessionUtils.getValidateCode(request, SessionUtils.SYSTEM_VALIDATE_NUM))) {
				map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "验证码错误");
				return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
			}
		}

		UserAuth userAuth = new UserAuth();
		userAuth.setUserId(userId);
		userAuth.setIdentityType(UserAuth.IDENTITYTYPE_SHOUJI);
		List<UserAuth> list = userAuthService.list(userAuth);
		if (!CollectionUtils.isEmpty(list)) {
			userAuth = list.get(0);
			userAuth.setIdentifier(mobile);
			if (!StringUtil.isEmpty(pwd)) {
				userAuth.setCredential(Md5EncryptUtil.MD5(pwd));
			}
			userAuthService.update(userAuth);
		} else {
			userAuth.setIdentifier(mobile);
			if (!StringUtil.isEmpty(pwd)) {
				userAuth.setCredential(Md5EncryptUtil.MD5(pwd));
			}
			userAuth.setCreateTime(new Date());
			userAuthService.add(userAuth);
		}

		User user = userService.queryById(userAuth.getUserId());
		String niMing = "用户" + mobile.substring(0, 3) + mobile.substring(8, 11);
		user.setNiMing(niMing);
		userService.update(user);

		SessionUtils.setAttr(request, SessionUtils.SYSTEM_WEI_XIN_MOBILE, userAuth.getIdentifier(),
				SessionUtils.SYSTEM_ONE_HOUR);

		map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "绑定手机成功");
		return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
	}

	// 判断手机号是否存在
	@RequestMapping(value = "/isCunZaiMobile.action", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String isCunZaiMobile(String mobile, HttpServletRequest request) {
		Map map = new HashMap();
		if (StringUtil.isEmpty(mobile)) {
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入手机号");
			return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
		}
		UserAuth ua = new UserAuth();
		ua.setIdentityType(Constant.IdentityType.MOBILE);
		ua.setIdentifier(mobile);
		List<UserAuth> list = userAuthService.list(ua);
		if (!CollectionUtils.isEmpty(list)) {
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "1", "请输入您用手机号注册的智谷科技工作室账号对应的密码。点击绑定按钮完成绑定。");
		} else {
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "2", "手机号未在本网站注册,请注册");
		}

		return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
	}

	/**
	 * 将微信绑定到以前手机号注册的账户上（已在本网站注册的用户）
	 * 
	 * @param mobile/手机号
	 * @param userId/微信号绑定的新用户ID2
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/setWeChatBindToMobile.action", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String setWeChatBindToMobile(String mobile, String pwd, Integer userId, HttpServletRequest request) {
		Map map = new HashMap();
		if (StringUtil.isEmpty(mobile)) {
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入手机号");
			return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
		}

		// 查询手机号绑定的旧用户ID1
		UserAuth ua = new UserAuth();
		ua.setIdentityType(Constant.IdentityType.MOBILE);
		ua.setIdentifier(mobile);
		List<UserAuth> list = userAuthService.list(ua);
		if (!CollectionUtils.isEmpty(list)) {
			ua = list.get(0);
			if (!ua.getCredential().equals(Md5EncryptUtil.MD5(pwd))) {
				map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "密码错误");
				return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
			}
		}

		UserAuth ut = new UserAuth();
		ut.setIdentityType(Constant.IdentityType.WE_CHAT);
		ut.setUserId(ua.getUserId());
		list = userAuthService.list(ut);
		if (!CollectionUtils.isEmpty(list)) {
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "该手机号已被其它微信绑定，请尝试其它手机号");
			return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
		}

		// 查询新用户ID2绑定的微信号2
		UserAuth ua2 = new UserAuth();
		ua2.setUserId(userId);
		ua2.setIdentityType(Constant.IdentityType.WE_CHAT);
		List<UserAuth> list2 = userAuthService.list(ua2);
		if (!CollectionUtils.isEmpty(list2)) {
			ua2 = list2.get(0);
			// 将旧用户的ID1绑定（赋值）到新的用户ID
			ua2.setUserId(ua.getUserId());
			userAuthService.update(ua2);
		}

		// 删除新用户ID2及绑定的微信号2
		userService.deleteByPrimaryKey(userId);

		User user = userService.queryById(ua.getUserId());
		String niMing = "用户" + mobile.substring(0, 3) + mobile.substring(8, 11);
		user.setNiMing(niMing);
		userService.update(user);

		SessionUtils.setAttr(request, SessionUtils.SYSTEM_WEI_XIN_MOBILE, ua.getIdentifier(),
				SessionUtils.SYSTEM_ONE_HOUR);

		map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "绑定手机成功");
		return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
	}

}
