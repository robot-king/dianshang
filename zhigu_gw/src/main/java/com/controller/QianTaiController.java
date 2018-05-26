package com.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class QianTaiController {

	/** 日志对象 */
	private static final Logger logger = Logger.getLogger(QianTaiController.class);

	// 前台首页
	@RequestMapping("/index")
	public String index(Model model) throws Exception {
		return "home/index";
	}

	// 前台服务项目
	@RequestMapping("/service")
	public String service(Model model) throws Exception {
		return "home/service";
	}

	// 前台案例展示
	@RequestMapping("/case")
	public String caselist(Model model) throws Exception {
		return "home/case";
	}

	// 前台模板列表
	@RequestMapping("/list")
	public String list(Model model) throws Exception {
		return "home/list";
	}

	// 前台模板列表
	@RequestMapping("/list_more")
	public String list_more(Model model) throws Exception {
		return "home/list_more";
	}

	// 前台关于我们
	@RequestMapping("/about")
	public String about(Model model) throws Exception {
		return "home/about";
	}

	// 前台联系我们
	@RequestMapping("/link")
	public String link(Model model) throws Exception {
		return "home/link";
	}

}
