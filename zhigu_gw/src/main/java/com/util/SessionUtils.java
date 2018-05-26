package com.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.pojo.Employee;
import com.pojo.User;

/**
 * 
 * session 工具类
 */
public final class SessionUtils {

	protected static final Logger logger = Logger.getLogger(SessionUtils.class);

	public static final String SYSTEM_ADMIN = "admin_3306";// 管理员

	public static final String SYSTEM_USER = "user_2988";// 用户

	public static final String SYSTEM_WEIXIN = "wei_xin";// 微信标识

	public static final String SYSTEM_WEI_XIN_MOBILE = "wei_xin_mobile";// 微信与电话绑定标识

	public static final String SYSTEM_VALIDATE_NUM = "validate_num";// 验证码

	public static final String SYSTEM_SEO = "seo_tag";// seo

	public static final int SYSTEM_ONE_MINUTES = 1 * 60;// 验证时间60秒,1分钟

	public static final int SYSTEM_ONE_HOUR = 1 * 60 * 60;// 验证时间3600秒,1小时

	public static final int SYSTEM_TWO_HOUR = 2 * 60 * 60;// 验证时间7200秒，2小时

	public static final int SYSTEM_14_HOUR = 14 * 60 * 60;// 验证时间3600秒,1小时

	/**
	 * 设置session的值
	 * 
	 * @param request
	 * @param key
	 * @param value
	 * @param time
	 *            设置session有效期
	 */
	public static void setAttr(HttpServletRequest request, String key, Object value, int time) {
		HttpSession session = request.getSession();
		session.setAttribute(key, value);
		if (time != 0) {
			session.setMaxInactiveInterval(time);
		} else {
			session.setMaxInactiveInterval(30 * 60);
		}
	}

	/**
	 * 获取session的值
	 * 
	 * @param request
	 * @param key
	 * @param value
	 */
	public static Object getAttr(HttpServletRequest request, String key) {
		return request.getSession().getAttribute(key);
	}

	/**
	 * 删除Session值
	 * 
	 * @param request
	 * @param key
	 */
	public static void removeAttr(HttpServletRequest request, String key) {
		request.getSession().removeAttribute(key);
	}

	/**
	 * 从session中获取验证码
	 * 
	 * @param request
	 * @return SysUser
	 */
	public static String getValidateCode(HttpServletRequest request, String key) {
		return (String) request.getSession().getAttribute(key);
	}

	/**
	 * 删除session验证码
	 * 
	 * @param request
	 * @return SysUser
	 */
	public static void removeValidateCode(HttpServletRequest request, String key) {
		removeAttr(request, key);
	}

	// 后台管理员
	public static Integer getEmployeeIdByKey(HttpServletRequest request, String key) {
		Employee employee = (Employee) request.getSession().getAttribute(key);
		return null == employee ? null : employee.getId();
	}

	// 前台用户
	public static Integer getUserIdByKey(HttpServletRequest request, String key) {
		User user = (User) request.getSession().getAttribute(key);
		return null == user ? null : user.getId();
	}

}