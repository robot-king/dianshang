package com.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.pojo.Employee;
import com.service.EmployeeService;
import com.service.MenuService;
import com.service.UserService;
import com.util.Constant;
import com.util.JsonUtils;
import com.util.Md5EncryptUtil;
import com.util.PageInfoJson;
import com.util.SessionUtils;
import com.util.StringUtil;
import com.util.ToolsUtils;
import com.util.date.DateUtil;
import com.util.file.FileUploadUtil;

@Controller
@RequestMapping("employee")
public class EmployeeController {

	/** 日志对象 */
	private static final Logger logger = Logger.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private MenuService menuService;
	@Autowired
	private UserService userService;

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

	// 根据主键获取对象
	@RequestMapping("/queryById.do")
	public String getById(Integer id, HttpServletResponse response) {
		Map map = new HashMap();
		Employee employee = employeeService.queryById(id);
		if (null != employee) {
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);
			map.put("message", employee);
		} else {
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		return JSON.toJSONString(map, true);
	}

	// 新增
	@RequestMapping(value = "/save.do", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String save(Employee employee, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			employee.setPwd(Md5EncryptUtil.MD5(employee.getPwd()));
			employee.setStatus(Constant.ON);
			employee.setCreateTime(new Date());
			int status = employeeService.add(employee);
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
	public String update(Employee employee, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			if (!StringUtil.isEmpty(employee.getPwd())) {
				employee.setPwd(Md5EncryptUtil.MD5(employee.getPwd()));
			}
			int status = employeeService.update(employee);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		return JSON.toJSONString(map, true);
	}

	// 修改
	@RequestMapping(value = "/updateIconUrl.do", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String updateIconUrl(Employee employee, @Param("iconUrlFile") MultipartFile iconUrlFile, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			String dictory = DateUtil.formatDate(new Date(), "yyyyMM");

			String coverUrlPath = "";
			if (!iconUrlFile.isEmpty()) {
				try {
					coverUrlPath = FileUploadUtil.uploadImg(iconUrlFile, fileUploadPath, dictory);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				employee.setIconUrl(coverUrlPath);
			}
			if (!StringUtil.isEmpty(employee.getPwd())) {
				employee.setPwd(Md5EncryptUtil.MD5(employee.getPwd()));
			}
			int status = employeeService.update(employee);
			if (null != employee.getId()) {
				employee = employeeService.queryById(employee.getId());
				SessionUtils.setAttr(request, SessionUtils.SYSTEM_ADMIN, employee, SessionUtils.SYSTEM_TWO_HOUR);
			}
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
	public String delete(Integer id, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			int status = employeeService.deleteByPrimaryKey(id);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		return JSON.toJSONString(map, true);
	}

	@RequestMapping("/employee_list.do")
	public String employee_list(Model model) throws Exception {
		return "admin/employee_list";
	}

	@RequestMapping("/index.do")
	public String index(Model model, HttpServletRequest request) throws Exception {
		return "admin/index";
	}

	// 分页查询列表
	@RequestMapping("/queryList.do")
	@ResponseBody
	public PageInfoJson<Employee> queryList(Integer page, Integer rows, Employee employee, HttpServletRequest request) {

		Map map = new HashMap();
		List<Employee> list = null;
		try {
			list = employeeService.listPage(page == null ? 1 : page, rows == null ? 10 : rows, employee);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		PageInfoJson<Employee> pageInfo = new PageInfoJson(list);
		return pageInfo;
	}

	// 登录
	@RequestMapping(value = "/login.do", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String login(Employee employee, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			if (StringUtil.isEmpty(employee.getNickName())) {
				map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", "用户名不能为空");
				return JSON.toJSONString(map, true);
			}
			if (StringUtil.isEmpty(employee.getPwd())) {
				map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", "密码不能为空");
				return JSON.toJSONString(map, true);
			}

			Employee record = new Employee();
			record.setNickName(employee.getNickName());
			List<Employee> list = employeeService.list(record);
			if (!CollectionUtils.isEmpty(list)) {
				record = list.get(0);
				if (record.getPwd().equals(Md5EncryptUtil.MD5(employee.getPwd()))) {
					SessionUtils.setAttr(request, SessionUtils.SYSTEM_ADMIN, record, SessionUtils.SYSTEM_14_HOUR);
					map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", "登录成功");

					// 更新登录时间
					record.setLoginTime(new Date());
					employeeService.update(record);
				} else {
					map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", "密码错误");
				}
			} else {
				map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", "用户名不存在");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);
		}
		return JSON.toJSONString(map, true);
	}

	@RequestMapping("/exit.do")
	public String exit(Model model, HttpServletRequest request) throws Exception {
		SessionUtils.removeAttr(request, SessionUtils.SYSTEM_ADMIN);
		return "admin/login";
	}

	@RequestMapping("/loginPage.do")
	public String loginPage(Model model, HttpServletRequest request) throws Exception {
		SessionUtils.removeAttr(request, SessionUtils.SYSTEM_ADMIN);
		return "admin/loginPage";
	}

	@RequestMapping("/update_pwd_page.do")
	public String icon_url_page(Model model, HttpServletRequest request) throws Exception {
		return "admin/update_pwd_page";
	}

	// 修改密码
	@RequestMapping(value = "/updatePwd.do", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String updatePwd(Integer id, String oldPwd, String newPwd, HttpServletRequest request) {
		Map map = new HashMap();

		map = employeeService.updatePwd(id, oldPwd, newPwd);

		return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
	}

	@RequestMapping("/statics.do")
	public String statics(Model model, HttpServletRequest request) throws Exception {
		return "admin/statics";
	}

}
