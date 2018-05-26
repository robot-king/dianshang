package com.interceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.pojo.Employee;
import com.pojo.SeoTag;
import com.pojo.User;
import com.service.SeoTagService;
import com.service.UserService;
import com.util.Constant;
import com.util.JsonUtils;
import com.util.SessionUtils;
import com.util.ToolsUtils;

public class LoginInterceptor implements HandlerInterceptor {

	/** 日志对象 */
	private static final Logger logger = Logger.getLogger(LoginInterceptor.class);

	@Autowired
	UserService userService;
	@Autowired
	SeoTagService seoTagService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());

		Boolean flag = true;

		logger.info("记录seo");
		List<String> seo_list = new ArrayList<>();
		List<SeoTag> list = seoTagService.list(null);
		if (!CollectionUtils.isEmpty(list)) {
			for (SeoTag seoTag : list) {
				if (!seo_list.contains(seoTag.getTitle())) {
					seo_list.add(seoTag.getTitle());
				}
			}
			SessionUtils.setAttr(request, SessionUtils.SYSTEM_SEO,
					StringUtils.strip(seo_list.toString(), "[]").replaceAll(" ", ""), SessionUtils.SYSTEM_ONE_HOUR);
		}

		logger.info("访问:" + url);
		// 前台 .action
		if (url.endsWith(".action")) {
			User user = (User) SessionUtils.getAttr(request, SessionUtils.SYSTEM_USER);
			if (user == null) {
				logger.info("拦截:" + url);
				// 判断如果用ajax方式提交
				if (request.getHeader("x-requested-with") != null
						&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/html; charset=utf-8");
					PrintWriter out = null;
					try {
						out = response.getWriter();
						String msg = JsonUtils.getJsonString4JavaPOJO(
								ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "no_login", "未登录"));
						out.append(msg);
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (out != null) {
							out.close();
						}
					}
				} else {
					response.sendRedirect(contextPath + "/userAuth/loginPage.action");
				}
				flag = false;
			} else {
				flag = true;
			}
		}
		// 后台 .do
		if (url.endsWith(".do")) {
			Employee fyUser = (Employee) SessionUtils.getAttr(request, SessionUtils.SYSTEM_ADMIN);
			if (fyUser == null) {
				logger.info("拦截:" + url);
				response.sendRedirect(contextPath + "/employee/loginPage.do");
				flag = false;
			} else {
				flag = true;
			}
		}

		return flag;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

}