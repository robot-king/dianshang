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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.pojo.Danzi;
import com.pojo.Dict;
import com.pojo.Kehu;
import com.service.DanziService;
import com.service.DictService;
import com.service.KehuService;
import com.util.Constant;
import com.util.PageInfoJson;
import com.util.StringUtil;
import com.util.ToolsUtils;
import com.util.makeNumber.Cn2Spell;

@Controller
@RequestMapping("danzi")
public class DanziController extends BaseController {

	/** 日志对象 */
	private static final Logger logger = Logger.getLogger(DanziController.class);

	@Autowired
	private DanziService recordService;
	@Autowired
	private DictService dictService;
	@Autowired
	private KehuService kehuService;

	// 根据主键获取对象
	@RequestMapping("/queryById.do")
	public String queryById(@RequestParam(value = "id", required = false) Integer id, HttpServletResponse response) {
		Map map = new HashMap();
		Danzi record = recordService.queryById(id);
		if (null != record) {
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, record, Constant.DataStatus.SUCCESS_MSG);
		} else {
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);
		}
		return JSON.toJSONString(map, true);
	}

	// 新增
	@RequestMapping(value = "/save.do", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String save(Danzi record, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			record.setCreateTime(new Date());
			laiyuanAndKehu(record);
			map = recordService.add(record);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);
		}
		return JSON.toJSONString(map, true);
	}

	public void laiyuanAndKehu(Danzi record) {
		// 判断客户是否存在
		if (!StringUtil.isEmpty(record.getKehuName())) {
			Kehu kehu = new Kehu();
			kehu.setXingming(record.getKehuName());
			List<Kehu> list = kehuService.list(kehu);
			// 为空则添加其分类子类
			if (CollectionUtils.isEmpty(list)) {
				kehu.setCreateTime(new Date());
				kehuService.add(kehu);
			}
			record.setKehuId(kehu.getId());
		}
		// 判断来源分类是否为空
		if (!StringUtil.isEmpty(record.getLaiyuan())) {
			String code = Cn2Spell.converterToSpell(record.getLaiyuan());
			Dict dict = new Dict();
			dict.setCode(code);
			List<Dict> list = dictService.list(dict);
			// 为空则添加其分类子类
			if (CollectionUtils.isEmpty(list)) {
				dict.setParentCode("laiyuan_type");
				dict.setName(record.getLaiyuan());
				dict.setCreateTime(new Date());
				dict.setStatus(Constant.ON);
				dictService.add(dict);
			}
			record.setLaiyuan(code);
		}
	}

	// 修改
	@RequestMapping(value = "/update.do", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String update(Danzi record, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			laiyuanAndKehu(record);
			if (record.getDanziStatus().equals(Danzi.DANZISTATUS_FUKUAN)) {
				record.setFukuanTime(new Date());
			}
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
	public String delete(@RequestParam(value = "ids", required = false) String ids, HttpServletRequest request) {
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

	/**
	 * // 批量启用,禁用,删除
	 * 
	 * @RequestMapping(value = "/delete.do", produces = "text/html;charset=UTF-8",
	 *                       method = RequestMethod.POST)
	 * @ResponseBody public String delete(@RequestParam(value = "ids", required =
	 *               false) String ids, HttpServletRequest request) { Map map = new
	 *               HashMap();
	 * 
	 *               try { String idArr[] = ids.split(","); int status =
	 *               recordService.updateByIds(Constant.DELETE, idArr); map =
	 *               ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "",
	 *               Constant.DataStatus.SUCCESS_MSG); } catch (Exception e) { //
	 *               TODO Auto-generated catch block e.printStackTrace(); map =
	 *               ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "",
	 *               Constant.DataStatus.FAIL_MSG); } return
	 *               JsonUtils.getJsonString4JavaPOJO(map,
	 *               JsonUtils.LONG_DATE_PATTERN); }
	 **/

	@RequestMapping("/danzi_list.do")
	public String danzi_list(String action, Integer kehu_id, Model model) throws Exception {
		model.addAttribute("action", action);
		model.addAttribute("kehu_id", kehu_id);
		return "admin/danzi_list";
	}

	// 分页查询列表
	@RequestMapping("/queryList.do")
	@ResponseBody
	public PageInfoJson<Danzi> queryList(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows, Danzi record,
			HttpServletRequest request) {

		Map map = new HashMap();
		List<Danzi> list = null;
		try {
			list = recordService.listPage(page == null ? 1 : page, rows == null ? 10 : rows, record);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);
		}
		PageInfoJson<Danzi> pageInfo = new PageInfoJson(list);
		return pageInfo;
	}

	// 前台列表页面
	@RequestMapping("/danzi_list.action")
	public String danzi_list_home(Model model) throws Exception {
		return "home/danzi_list";
	}

	// 前台分页查询列表
	@RequestMapping("/queryList.action")
	@ResponseBody
	public PageInfoJson<Danzi> queryListByHome(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "rows", required = false) Integer rows,
			Danzi record, HttpServletRequest request) {

		Map map = new HashMap();
		List<Danzi> list = null;
		try {
			list = recordService.listPage(page == null ? 1 : page, rows == null ? 10 : rows, record);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);
		}
		PageInfoJson<Danzi> pageInfo = new PageInfoJson(list);
		return pageInfo;
	}

}
