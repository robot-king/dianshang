package com.controller;

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

import com.alibaba.fastjson.JSON;
import com.pojo.EmployeeMenu;
import com.service.EmployeeMenuService;
import com.util.Constant;
import com.util.PageInfoJson;
import com.util.ToolsUtils;

@Controller
@RequestMapping("employeeMenu")
public class EmployeeMenuController {

	/** 日志对象 */
	private static final Logger logger = Logger.getLogger(EmployeeMenuController.class);

	@Autowired
	private EmployeeMenuService employeeMenuService;

	// 批量绑定菜单
	@RequestMapping(value = "/bindMenu.do", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String bindMenu(String ids, Integer employeeId, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			String idArr[] = ids.split(",");

			for (int i = 0; i < idArr.length; i++) {
				EmployeeMenu em = new EmployeeMenu();
				em.setEmployeeId(employeeId);
				em.setMenuId(Integer.parseInt(idArr[i]));
				List<EmployeeMenu> list = employeeMenuService.list(em);
				if (!CollectionUtils.isEmpty(list)) {
					em = list.get(0);
					em.setStatus(Constant.ON);
					employeeMenuService.update(em);
				} else {
					em.setStatus(Constant.ON);
					em.setCreateTime(new Date());
					employeeMenuService.add(em);
				}
			}
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		return JSON.toJSONString(map, true);
	}

	// 批量绑定菜单
	@RequestMapping(value = "/unBindMenu.do", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String unBindMenu(String ids, Integer employeeId, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			String idArr[] = ids.split(",");

			for (int i = 0; i < idArr.length; i++) {
				EmployeeMenu em = new EmployeeMenu();
				em.setEmployeeId(employeeId);
				em.setMenuId(Integer.parseInt(idArr[i]));
				List<EmployeeMenu> list = employeeMenuService.list(em);
				if (!CollectionUtils.isEmpty(list)) {
					em = list.get(0);
					em.setStatus(Constant.DELETE);
					employeeMenuService.update(em);
				}
			}
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		return JSON.toJSONString(map, true);
	}

	@RequestMapping("/employee_menu_list.do")
	public String employee_menu_list(Model model) throws Exception {
		return "admin/employee_menu_list";
	}

	// 分页查询列表
	@RequestMapping("/queryList.do")
	@ResponseBody
	public PageInfoJson<EmployeeMenu> queryList(Integer page, Integer rows, EmployeeMenu employeeMenu,
			HttpServletRequest request) {

		Map map = new HashMap();
		List<EmployeeMenu> list = null;
		try {
			list = employeeMenuService.listPage(page == null ? 1 : page, rows == null ? 10 : rows, employeeMenu);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		PageInfoJson<EmployeeMenu> pageInfo = new PageInfoJson(list);
		return pageInfo;
	}

}
