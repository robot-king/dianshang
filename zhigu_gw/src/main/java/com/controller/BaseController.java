package com.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.service.UserService;

@Controller
public class BaseController {

	/** 日志对象 */
	private static final Logger logger = Logger.getLogger(BaseController.class);

	@Autowired
	private UserService userService;

	@RequestMapping("/jspUrl/**")
	public ModelAndView baseUrl(HttpServletRequest request) {
		String url = request.getServletPath();
		url = url.substring(url.indexOf("jspUrl") + 7, url.length());
		ModelAndView modelAndView = new ModelAndView(url);// 默认为forward模式
		return modelAndView;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// 对于需要转换为Date类型的属性，使用DateEditor进行处理
		binder.registerCustomEditor(Date.class,
				new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}

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

}
