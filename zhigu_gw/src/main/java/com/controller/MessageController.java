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
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.pojo.Message;
import com.service.MessageService;
import com.util.Constant;
import com.util.PageInfoJson;
import com.util.SessionUtils;
import com.util.ToolsUtils;

@Controller
@RequestMapping("message")
public class MessageController {

	/** 日志对象 */
	private static final Logger logger = Logger.getLogger(MessageController.class);

	@Autowired
	private MessageService recordService;

	// 根据主键获取对象
	@RequestMapping("/queryById.do")
	public String queryById(Integer id, HttpServletResponse response) {
		Map map = new HashMap();
		Message record = recordService.queryById(id);
		if (null != record) {
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

		} else {
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		return JSON.toJSONString(map, true);
	}

	// 新增
	@RequestMapping(value = "/save.do", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String save(Message record, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			record.setStatus(Message.STATUS_NO_READ);
			record.setCreateTime(new Date());
			int status = recordService.add(record);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		return JSON.toJSONString(map, true);
	}

	// 新增
	@RequestMapping(value = "/save.action", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String save2(Message record, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			record.setStatus(Message.STATUS_NO_READ);
			record.setCreateTime(new Date());
			int status = recordService.add(record);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		return JSON.toJSONString(map, true);

	}

	// 修改
	@RequestMapping(value = "/update.action", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String update(Message record, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			if (null == record.getReceivedId()) {
				map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", "请输入接收消息人ID");
				return JSON.toJSONString(map);
			}
			if (null == record.getType()) {
				map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", "请输入消息类型");
				return JSON.toJSONString(map);
			}
			record.setStatus(Message.STATUS_NO_READ);
			List<Message> list = recordService.list(record);
			if (!CollectionUtils.isEmpty(list)) {
				for (Message message : list) {
					message.setStatus(Message.STATUS_YES_READ);
					int status = recordService.update(message);
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

	// 删除
	@RequestMapping(value = "/delete.action", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String delete2(Integer id, HttpServletRequest request) {
		Map map = new HashMap();

		try {
			int status = recordService.deleteByPrimaryKey(id);
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
	 * @RequestMapping(value = "/delete.do", produces =
	 *                       "text/html;charset=UTF-8", method =
	 *                       RequestMethod.POST)
	 * @ResponseBody public String delete(String ids, HttpServletRequest
	 *               request) { Map map = new HashMap();
	 * 
	 *               try { String idArr[] = ids.split(","); int status =
	 *               recordService.updateByIds(Constant.DELETE, idArr); map =
	 *               ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE,
	 *               "", Constant.DataStatus.SUCCESS_MSG);
	 * 
	 *               } catch (Exception e) { // TODO Auto-generated catch block
	 *               e.printStackTrace(); map =
	 *               ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "",
	 *               Constant.DataStatus.FAIL_MSG);
	 * 
	 *               } return JsonUtils.getJsonString4JavaPOJO(map,
	 *               JsonUtils.LONG_DATE_PATTERN); }
	 **/

	@RequestMapping("/message_list.do")
	public String message_list(Model model) throws Exception {
		return "admin/message_list";
	}

	// 分页查询列表
	@RequestMapping("/queryList.do")
	@ResponseBody
	public PageInfoJson<Message> queryList(Integer page, Integer rows, Message record, HttpServletRequest request) {

		Map map = new HashMap();
		List<Message> list = null;
		try {
			if (null == record.getStatus()) {
				record.setStatus(Constant.ON);
			}
			list = recordService.listPage(page == null ? 1 : page, rows == null ? 10 : rows, record);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		PageInfoJson<Message> pageInfo = new PageInfoJson(list);
		return pageInfo;
	}

	@RequestMapping("/message_list.action")
	public String message_list2(Model model, String code) throws Exception {
		model.addAttribute("code", code);
		return "home/message_list";
	}

	// 所有消息分页查询列表
	@RequestMapping("/queryListByHome.action")
	@ResponseBody
	public PageInfoJson<Message> queryListByHome(Integer page, Integer rows, Message record,
			HttpServletRequest request) {

		Map map = new HashMap();
		List<Message> list = null;
		try {
			list = recordService.listPage(page == null ? 1 : page, rows == null ? 10 : rows, record);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		PageInfoJson<Message> pageInfo = new PageInfoJson(list);
		return pageInfo;
	}

	// 发送消息
	@RequestMapping("/send_news.action")
	public String send_news(Integer jieshouUserId, Model model, HttpServletRequest request) throws Exception {

		// 用两个用户之间的唯一对话编号查询对话消息
		String no = "";
		Integer userId = SessionUtils.getUserIdByKey(request, SessionUtils.SYSTEM_USER);
		if (jieshouUserId < userId.intValue()) {
			no = jieshouUserId + "" + userId;
		} else {
			no = userId + "" + jieshouUserId;
		}
		// 将对话所有消息设置为已读
		Message record = new Message();
		record.setDialogNo(no);
		record.setType(Message.TYPE_SIXIN);
		List<Message> list = recordService.list(record);
		if (!CollectionUtils.isEmpty(list)) {
			for (Message message : list) {
				message.setStatus(Message.STATUS_YES_READ);
				recordService.update(message);
			}
		}

		model.addAttribute("jieshouUserId", jieshouUserId);
		return "home/send_news";
	}

	// 用户之间对话消息分页查询列表
	@RequestMapping("/queryListByDialog.action")
	@ResponseBody
	public PageInfoJson<Message> queryListByDialog(Integer page, Integer rows, Message record,
			HttpServletRequest request) {

		Map map = new HashMap();
		List<Message> list = null;
		try {
			// 用两个用户之间的唯一对话编号查询对话消息
			String no = "";
			if (record.getSendId().intValue() < record.getReceivedId().intValue()) {
				no = record.getSendId() + "" + record.getReceivedId();
			} else {
				no = record.getReceivedId() + "" + record.getSendId();
			}
			record.setDialogNo(no);
			record.setSendId(null);
			record.setReceivedId(null);
			list = recordService.queryListByDialog(page == null ? 1 : page, rows == null ? 10 : rows, record);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		PageInfoJson<Message> pageInfo = new PageInfoJson(list);
		return pageInfo;
	}
}
