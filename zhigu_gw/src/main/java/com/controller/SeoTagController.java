package com.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.pojo.SeoTag;
import com.service.SeoTagService;
import com.util.Constant;
import com.util.PageInfoJson;
import com.util.ToolsUtils;

@Controller
@RequestMapping("seoTag")
public class SeoTagController {

	/** 日志对象 */
	private static final Logger logger = Logger.getLogger(SeoTagController.class);

	@Autowired
	private SeoTagService recordService;

	// 根据主键获取对象
	@RequestMapping("/queryById.do")
	public String queryById(Integer id,HttpServletResponse response) {
		Map map = new HashMap();
		SeoTag record = recordService.queryById(id);
		if(null != record) {
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, record, Constant.DataStatus.SUCCESS_MSG);
		} else {
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);
		}
		return JSON.toJSONString(map, true);
	}
	
	// 新增
	@RequestMapping(value = "/save.do", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String save(SeoTag record, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			record.setCreateTime(new Date());
			map = recordService.add(record);
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
	public String update(SeoTag record, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			map = recordService.update(record);
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
			int status = recordService.deleteByIds(idArr);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);
		}
		return JSON.toJSONString(map, true);
	}
	
	/** // 批量启用,禁用,删除
	@RequestMapping(value = "/delete.do", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String delete(String ids, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			String idArr[] = ids.split(",");
			int status = recordService.updateByIds(Constant.DELETE, idArr);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);
		}
		return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
	}**/
	
	@RequestMapping("/seo_tag_list.do")
	public String seo_tag_list(Model model) throws Exception {
		return "admin/seo_tag_list";
	}

	// 分页查询列表
	@RequestMapping("/queryList.do")
	@ResponseBody
	public PageInfoJson<SeoTag> queryList(Integer page, Integer rows, SeoTag record,
			HttpServletRequest request) {

		Map map = new HashMap();
		List<SeoTag> list = null;
		try {
			list = recordService.listPage(page == null ? 1 : page, rows == null ? 10 : rows, record);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);
		}
		PageInfoJson<SeoTag> pageInfo = new PageInfoJson(list);
		return pageInfo;
	}
	
	// 前台列表页面
	@RequestMapping("/seo_tag_list.action")
	public String seo_tag_list_home(Model model) throws Exception {
		return "home/seo_tag_list";
	}

	// 前台分页查询列表
	@RequestMapping("/queryList.action")
	@ResponseBody
	public PageInfoJson<SeoTag> queryListByHome(Integer page, Integer rows, SeoTag record,
			HttpServletRequest request) {

		Map map = new HashMap();
		List<SeoTag> list = null;
		try {
			list = recordService.listPage(page == null ? 1 : page, rows == null ? 10 : rows, record);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);
		}
		PageInfoJson<SeoTag> pageInfo = new PageInfoJson(list);
		return pageInfo;
	}
	
}
