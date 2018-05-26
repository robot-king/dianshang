package com.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.util.date.DateUtil;
import com.util.file.FileUploadUtil;

@Controller
@RequestMapping("uploadFile")
public class UploadFileController {

	/** 日志对象 */
	private static final Logger logger = Logger.getLogger(UploadFileController.class);

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

	// 上传指定文件
	@RequestMapping(value = "/upload.do", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String upload(MultipartFile file, MultipartHttpServletRequest request) {
		Map map = new HashMap();
		try {

			String fileName = file.getOriginalFilename();
			String dictory = DateUtil.formatDate(new Date(), "yyyyMM");
			String path = FileUploadUtil.uploadImg(file, fileUploadPath, dictory);

			map.put("errno", 0);
			map.put("data", 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("errno", 1);
		}
		return JSON.toJSONString(map, true);
	}

	// 上传多个文件
	@RequestMapping(value = "/uploads.do", produces = "text/html;charset=UTF-8", method = RequestMethod.POST)
	@ResponseBody
	public String uploads(@RequestParam MultipartFile[] files, MultipartHttpServletRequest request) {
		Map map = new HashMap();
		try {

			List<String> imgList = new ArrayList<>();
			for (int i = 0; i < files.length; i++) {
				MultipartFile file = files[i];
				String fileName = file.getOriginalFilename();
				String dictory = DateUtil.formatDate(new Date(), "yyyyMM");
				String path = FileUploadUtil.uploadImg(file, fileUploadPath, dictory);
				imgList.add(path);
			}
			map.put("errno", 0);
			map.put("data", imgList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("errno", 1);
		}
		return JSON.toJSONString(map, true);
	}

}
