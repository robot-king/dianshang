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
import com.pojo.Dict;
import com.service.DictService;
import com.util.Constant;
import com.util.JsonUtils;
import com.util.PageInfoJson;
import com.util.ToolsUtils;

@Controller
@RequestMapping("dict")
public class DictController {

	/** 日志对象 */
	private static final Logger logger = Logger.getLogger(DictController.class);

	@Autowired
	private DictService dictService;

	// 根据主键获取对象
	@RequestMapping("/queryById.action")
	public String getById(Integer id, HttpServletResponse response) {
		Map map = new HashMap();
		Dict dict = dictService.queryById(id);
		if (null != dict) {
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);
			map.put("message", dict);
		} else {
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
	}

	// 新增
	@RequestMapping(value = "/save.do", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String save(Dict dict, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			Dict record = new Dict();
			record.setCode(dict.getCode());
			List<Dict> list = dictService.list(record);
			// 名称可以重复,code不可以重复
			if (!CollectionUtils.isEmpty(list)) {
				map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", "编码已存在");
				return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
			}
			if (null == dict.getStatus()) {
				dict.setStatus(Constant.ON);
			}
			dict.setCreateTime(new Date());
			int status = dictService.add(dict);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);

	}

	// 修改
	@RequestMapping(value = "/update.do", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String update(Dict dict, HttpServletRequest request) {
		Map map = new HashMap();

		try {

			Dict record = new Dict();
			record.setCode(dict.getCode());
			List<Dict> list = dictService.list(record);
			if (!CollectionUtils.isEmpty(list)) {
				record = list.get(0);
				if (dict.getId() != record.getId()) {
					map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", "编码已存在");
					return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
				}
			}
			int status = dictService.update(dict);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
	}

	// 删除
	@RequestMapping(value = "/delete.do", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String delete(String ids, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			String idArr[] = ids.split(",");
			dictService.updateByIds(Constant.DELETE, idArr);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
	}

	@RequestMapping("/dict_list.do")
	public String dict_list(Model model) throws Exception {
		return "admin/dict_list";
	}

	// 分页查询列表
	@RequestMapping("/queryList.do")
	@ResponseBody
	public PageInfoJson<Dict> queryList(Integer page, Integer rows, Dict dict, HttpServletRequest request) {

		Map map = new HashMap();
		List<Dict> list = null;
		try {
			if (null == dict.getStatus()) {
				dict.setStatus(Constant.ON);
			}
			list = dictService.listPage(page == null ? 1 : page, rows == null ? 10 : rows, dict);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		PageInfoJson<Dict> pageInfo = new PageInfoJson(list);
		return pageInfo;
	}

	// 所有数据
	@RequestMapping(value = "/list.do", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String list(Dict dict, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			List<Dict> list = dictService.list(dict);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, list, Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
	}

	// 根据参数查询相应数据
	@RequestMapping(value = "/listByParentCode.do", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String listByParentCode(Dict dict, HttpServletRequest request) {
		Map map = new HashMap();
		List<String> targetList = new ArrayList<>();
		try {
			List<Dict> list = dictService.list(dict);

			if (!CollectionUtils.isEmpty(list)) {
				for (Dict dict2 : list) {
					targetList.add(dict2.getName());
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return JSON.toJSONString(targetList);
	}

	// 根据指定父级code新增子级
	@RequestMapping(value = "/saveByParentCode.do", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String saveByParentCode(Dict dict, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			Dict condition = new Dict();
			condition.setName(dict.getName());
			List<Dict> conditionList = dictService.list(condition);
			if (!CollectionUtils.isEmpty(conditionList)) {
				return JSON.toJSONString(map);
			}
			Dict record = new Dict();
			record.setParentCode(dict.getParentCode());
			List<Dict> list = dictService.list(record);
			int count = list.size();
			count++;
			dict.setCode(dict.getParentCode() + "_" + count);
			dict.setStatus(Constant.ON);
			dict.setCreateTime(new Date());
			int status = dictService.add(dict);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
	}

}
