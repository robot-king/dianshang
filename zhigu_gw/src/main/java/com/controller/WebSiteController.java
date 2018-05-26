package com.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.pojo.WebSite;
import com.service.WebSiteService;
import com.util.Constant;
import com.util.DeleteFileUtil;
import com.util.PageInfoJson;
import com.util.StringUtil;
import com.util.ToolsUtils;

@Controller
@RequestMapping("webSite")
public class WebSiteController extends BaseController {

	/** 日志对象 */
	private static final Logger logger = Logger.getLogger(WebSiteController.class);

	@Autowired
	private WebSiteService recordService;

	// 根据主键获取对象
	@RequestMapping("/queryById.do")
	public String queryById(Integer id, HttpServletResponse response) {
		Map map = new HashMap();
		WebSite record = recordService.queryById(id);
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
	public String save(WebSite record, @Param("coverUrlFile") MultipartFile coverUrlFile, @Param("templateFile") MultipartFile templateFile, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			record.setCreateTime(new Date());
			map = recordService.add(record, coverUrlFile, templateFile, fileUploadPath);
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
	public String update(WebSite record, MultipartFile coverUrlFile, MultipartFile templateFile, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			map = recordService.update(record, coverUrlFile, templateFile, fileUploadPath);
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
			// 首先删除模板文件
			for (int i = 0; i < idArr.length; i++) {
				WebSite webSite = recordService.queryById(Integer.valueOf(idArr[i]));
				// 删除图片
				if (!StringUtil.isEmpty(webSite.getCoverUrl())) {
					String coverPath = webSite.getCoverUrl();
					coverPath = coverPath.replaceAll("site", fileUploadPath);
					DeleteFileUtil.deleteFile(coverPath);
				}
				// 删除解压文件
				if (!StringUtils.isEmpty(webSite.getAccessUrl())) {
					String path = webSite.getAccessUrl();
					path = path.replace("site", fileUploadPath);
					logger.info("删除模板文件路径：" + path);
					String a_suffix = path.substring(0, path.indexOf("template") + 9);
					// System.out.println(a_suffix);
					String b_suffix = path.substring(path.indexOf("template") + 9, path.length());
					// System.out.println(b_suffix);
					b_suffix = b_suffix.substring(0, b_suffix.indexOf(File.separator));
					// System.out.println(b_suffix);
					path = a_suffix + b_suffix;
					logger.info("删除该目录下所有文件" + path);
					DeleteFileUtil.deleteDirectory(path);
				}
			}
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
	 * @ResponseBody public String delete(String ids, HttpServletRequest request) {
	 *               Map map = new HashMap();
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

	@RequestMapping("/web_site_list.do")
	public String web_site_list(Model model) throws Exception {
		return "admin/web_site_list";
	}

	// 分页查询列表
	@RequestMapping("/queryList.do")
	@ResponseBody
	public PageInfoJson<WebSite> queryList(Integer page, Integer rows, WebSite record, HttpServletRequest request) {

		Map map = new HashMap();
		List<WebSite> list = null;
		try {
			list = recordService.listPage(page == null ? 1 : page, rows == null ? 10 : rows, record);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);
		}
		PageInfoJson<WebSite> pageInfo = new PageInfoJson(list);
		return pageInfo;
	}

	// 前台列表页面
	@RequestMapping("/web_site_list.action")
	public String web_site_list_home(Model model) throws Exception {
		return "home/web_site_list";
	}

	// 前台分页查询列表
	@RequestMapping("/queryList.action")
	@ResponseBody
	public PageInfoJson<WebSite> queryListByHome(Integer page, Integer rows, WebSite record, HttpServletRequest request) {

		Map map = new HashMap();
		List<WebSite> list = null;
		try {
			list = recordService.listPage(page == null ? 1 : page, rows == null ? 10 : rows, record);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);
		}
		PageInfoJson<WebSite> pageInfo = new PageInfoJson(list);
		return pageInfo;
	}

	// 解压模板文件
	@RequestMapping(value = "/unRarFile.do", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String unRarFile(String ids, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			recordService.unRarFile(fileUploadPath);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);
		}
		return JSON.toJSONString(map, true);
	}

	//
	@RequestMapping(value = "/unReplace.do", produces = "text/html;charset=UTF-8", method = RequestMethod.GET)
	@ResponseBody
	public String unReplace(String ids, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			recordService.unReplace(fileUploadPath + File.separator + "template");
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);
		}
		return JSON.toJSONString(map, true);
	}

	public static void main(String[] args) {
		String path = "F:\\topContentImage\\template\\Rectanglefflashjhplgrjlmb_lsxqxjhplgrgrjlwebjlbphwin8zpalzszmnxyssjbootstraphtml5\\tpmo_439_rectangle\\index.html";
		String a_suffix = path.substring(0, path.indexOf("template") + 9);
		System.out.println(a_suffix);
		String b_suffix = path.substring(path.indexOf("template") + 9, path.length());
		System.out.println(b_suffix);
		b_suffix = b_suffix.substring(0, b_suffix.indexOf(File.separator));
		System.out.println(b_suffix);
		path = a_suffix + b_suffix;
		System.out.println(path);
	}
}
