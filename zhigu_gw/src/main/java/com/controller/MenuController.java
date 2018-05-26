package com.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.pojo.Menu;
import com.service.MenuService;
import com.util.Constant;
import com.util.JsonUtils;
import com.util.PageInfoJson;
import com.util.SessionUtils;
import com.util.ToolsUtils;
import com.util.TreeNode;

@Controller
@RequestMapping("menu")
public class MenuController {

	/** 日志对象 */
	private static final Logger logger = Logger.getLogger(MenuController.class);

	@Autowired
	private MenuService menuService;

	// 根据主键获取对象
	@RequestMapping("/queryById.do")
	public String getById(Integer id, HttpServletResponse response) {
		Map map = new HashMap();
		Menu menu = menuService.queryById(id);
		if (null != menu) {
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);
			map.put("message", menu);
		} else {
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		return JSON.toJSONString(map, true);
	}

	// 新增
	@RequestMapping(value = "/save.do", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String save(Menu menu, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			Menu record = new Menu();
			record.setCode(menu.getCode());
			List<Menu> list = menuService.list(record);
			// 名称可以重复,code不可以重复
			if (!CollectionUtils.isEmpty(list)) {
				map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", "编码已存在");
				return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
			}
			menu.setStatus(Constant.ON);
			menu.setCreateTime(new Date());
			int status = menuService.add(menu);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

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
	public String update(Menu menu, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			Menu record = new Menu();
			record.setCode(menu.getCode());
			List<Menu> list = menuService.list(record);
			// 名称可以重复,code不可以重复
			if (!CollectionUtils.isEmpty(list)) {
				record = list.get(0);
				if (menu.getId() != record.getId()) {
					map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", "编码已存在");
					return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
				}
			}
			int status = menuService.update(menu);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		return JSON.toJSONString(map, true);
	}

	// 删除
	@RequestMapping(value = "/delete.do", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String delete(String ids, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			String idArr[] = ids.split(",");
			int status = menuService.updateByIds(Constant.DELETE, idArr);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		return JSON.toJSONString(map, true);
	}

	@RequestMapping("/menu_list.do")
	public String menu_list(Model model) throws Exception {
		return "admin/menu_list";
	}

	// 分页查询列表
	@RequestMapping("/queryList.do")
	@ResponseBody
	public PageInfoJson<Menu> queryList(Integer page, Integer rows, Menu menu, HttpServletRequest request) {

		Map map = new HashMap();
		List<Menu> list = null;
		try {
			if (null == menu.getStatus()) {
				menu.setStatus(Constant.ON);
			}
			list = menuService.listPage(page == null ? 1 : page, rows == null ? 10 : rows, menu);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		PageInfoJson<Menu> pageInfo = new PageInfoJson(list);
		return pageInfo;
	}

	// 分页查询列表
	@RequestMapping(value = "/treeList.do", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String treeList(Menu menu, HttpServletRequest request) {

		Map map = new HashMap();
		List<Menu> list = null;
		try {
			if (null == menu.getStatus()) {
				menu.setStatus(Constant.ON);
			}
			list = menuService.list(menu);
			List<TreeNode> targetList = new ArrayList<>();
			for (Menu record : list) {
				TreeNode treeNode = new TreeNode();
				treeNode.setId(record.getId().toString());
				treeNode.setParendId(null == record.getParentId() ? "#" : record.getParentId().toString());
				treeNode.setText(record.getName());
				treeNode.setUrl(record.getUrl());
				net.sf.json.JSONObject ja = new net.sf.json.JSONObject();
				ja.put("code", record.getCode());
				ja.put("parentName", record.getParentName());
				treeNode.setAttributes(ja);
				treeNode.setSortNum(record.getSortNum());
				targetList.add(treeNode);
			}
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE,
					TreeNode.formatTreeFromListNoRootNode2(targetList), Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		return JSON.toJSONString(map, true);
	}

	// 分页查询列表
	@RequestMapping("/queryListByEmployeeId.do")
	@ResponseBody
	public PageInfoJson<Menu> queryListByEmployeeId(Integer page, Integer rows, Integer employeeId,
			HttpServletRequest request) {

		Map map = new HashMap();
		List<Menu> list = null;
		try {
			list = menuService.queryListByEmployeeId(page == null ? 1 : page, rows == null ? 10 : rows, employeeId);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		PageInfoJson<Menu> pageInfo = new PageInfoJson(list);
		return pageInfo;
	}

	// 左侧菜单
	@RequestMapping(value = "/menuData.do", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String menuData(HttpServletRequest request) {

		Map map = new HashMap();
		List<Menu> list = menuService.menuData(SessionUtils.getEmployeeIdByKey(request, SessionUtils.SYSTEM_ADMIN));
		List<TreeNode> targetList = new ArrayList<>();
		for (Menu menu : list) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(menu.getId().toString());
			treeNode.setParendId(null == menu.getParentId() ? "#" : menu.getParentId().toString());
			treeNode.setText(menu.getName());
			treeNode.setUrl(menu.getUrl());
			treeNode.setSortNum(menu.getSortNum());
			targetList.add(treeNode);
		}
		map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE,
				TreeNode.formatTreeFromListNoRootNode2(targetList), Constant.DataStatus.SUCCESS_MSG);
		return JSON.toJSONString(map, true);
	}

	// 链接设置
	@RequestMapping("/href_list.do")
	public String href_list(Model model) throws Exception {
		return "admin/href_list";
	}

}
