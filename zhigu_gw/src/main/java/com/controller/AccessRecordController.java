package com.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pojo.AccessRecord;
import com.service.AccessRecordService;
import com.util.Constant;
import com.util.JsonUtils;
import com.util.RealAddressUtil;
import com.util.ToolsUtils;
import com.util.task.DynamicSchedulingConfigurer;

@Controller
@RequestMapping("accessRecord")
public class AccessRecordController {

	/** 日志对象 */
	private static final Logger logger = Logger.getLogger(AccessRecordController.class);

	@Autowired
	private AccessRecordService recordService;
	@Autowired
	private DynamicSchedulingConfigurer dynamicSchedulingConfigurer;

	// 分页查询列表
	@RequestMapping(value = "/queryList.do", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String queryList(AccessRecord record, HttpServletRequest request) {

		Map map = new HashMap();
		List<AccessRecord> list = null;
		try {
			list = recordService.list(record);
			map = ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, list, Constant.DataStatus.SUCCESS_MSG);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", Constant.DataStatus.FAIL_MSG);

		}
		return JsonUtils.getJsonString4JavaPOJO(map, JsonUtils.LONG_DATE_PATTERN);
	}

	// 新增访问记录
	@RequestMapping(value = "/accessRecordAdd.action", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String accessRecordAdd(HttpServletRequest request) {
		Map map = new HashMap();
		// 访问记录
		AccessRecord record = new AccessRecord();
		String ipAddress = RealAddressUtil.getIpAddr(request);
		record.setIpAddress(ipAddress);
		try {
			JSONObject ja = RealAddressUtil.getCity(ipAddress);
			if (null != ja) {
				logger.info("新增访问记录：" + ja.toJSONString());
				record.setCountry(null == ja.get("country") ? "" : ja.get("country").toString());
				record.setArea(null == ja.get("area") ? "" : ja.get("area").toString());
				record.setProvince(null == ja.get("region") ? "" : ja.get("region").toString());
				record.setCity(null == ja.get("city") ? "" : ja.get("city").toString());
				record.setCounty(null == ja.get("county") ? "" : ja.get("county").toString());
				record.setCreateTime(new Date());
				recordService.add(record);
			}
			map.put("success", true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("success", false);
			map.put("message", e);
		}

		return JSON.toJSONString(map, true);
	}

	// 新增访问记录
	@RequestMapping(value = "/a", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String a(HttpServletRequest request) {
		Map map = new HashMap();

		Thread t1 = new Thread() {
			public void run() {

				try {
					// 等待任务调度初始化完成
					while (!dynamicSchedulingConfigurer.inited()) {
						Thread.sleep(100);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("任务调度初始化完成，添加任务");
				dynamicSchedulingConfigurer.addTriggerTask("task112", new TriggerTask(new Runnable() {

					@Override
					public void run() {
						System.out.println("添加：run job..." + Calendar.getInstance().get(Calendar.SECOND));

					}
				}, new CronTrigger("0 33 16 24 9 ?")));
			};
		};
		t1.start();
		try {
			// 加入join方法，t1线程的之后线程必须等待t1线程执行完才能执行
			t1.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		new Thread() {
			public void run() {

				try {
					Thread.sleep(30000);
				} catch (Exception e) {
				}
				System.out.println("重置任务............");
				dynamicSchedulingConfigurer.resetTriggerTask("task112", new TriggerTask(new Runnable() {

					@Override
					public void run() {
						System.out.println("重置：run job..." + Calendar.getInstance().get(Calendar.SECOND));

					}
				}, new CronTrigger("0 34 16 24 9 ?")));
			};
		}.start();

		return JSON.toJSONString(map, true);
	}
}
