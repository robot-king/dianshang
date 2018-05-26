package com.service.impl;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.LunBoTuMapper;
import com.github.pagehelper.PageHelper;
import com.pojo.LunBoTu;
import com.service.LunBoTuService;
import com.util.Base64Image;
import com.util.Constant;
import com.util.StringUtil;
import com.util.ToolsUtils;
import com.util.date.DateUtil;
import com.util.file.FileUploadUtil;

@Service("lunBoTuService")
public class LunBoTuServiceImpl implements LunBoTuService {

	/** 日志对象 */
	private static final Logger logger = Logger.getLogger(LunBoTuServiceImpl.class);

	@Autowired
	private LunBoTuMapper lunBoTuMapper;

	@Transactional
	@Override
	public Map add(LunBoTu record, String bannerBase64, String fileUploadPath) {

		Map map = new HashMap();
		map = savePic(record, bannerBase64, fileUploadPath);
		lunBoTuMapper.insertSelective(record);
		return ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);
	}

	@Transactional
	@Override
	public Map update(LunBoTu record, String bannerBase64, String fileUploadPath) {
		Map map = new HashMap();
		map = savePic(record, bannerBase64, fileUploadPath);
		lunBoTuMapper.updateByPrimaryKeySelective(record);
		return ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);
	}

	public Map savePic(LunBoTu record, String bannerBase64, String fileUploadPath) {
		String dictory = DateUtil.formatDate(new Date(), "yyyyMM");
		Map map = new HashMap();
		String bannerUrlPath = "";
		if (!StringUtil.isEmpty(bannerBase64)) {
			try {
				String setFileName = FileUploadUtil.getRandomFileName();
				String suffix = ".png";

				// 创建目录
				String path = fileUploadPath + "/" + dictory;
				File folder = new File(path);
				if (!folder.exists()) {
					logger.info("创建目录：" + path);
					folder.mkdirs();
				}

				// 创建文件
				path = path + "/" + setFileName + suffix;
				File file = new File(path);
				if (!file.exists()) {
					logger.info("创建文件：" + path);
					file.createNewFile();
				}

				boolean flag = Base64Image.generateImage(bannerBase64, path);
				if (flag) {
					bannerUrlPath = "/site" + "/" + dictory + "/" + setFileName + suffix;
					record.setBannerUrl(bannerUrlPath);
				} else {
					map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", "存储图片异常");
					return map;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			map = ToolsUtils.returnResult(Constant.DataStatus.FAIL_CODE, "", "请选择轮播图");
			return map;
		}
		return map;
	}

	@Override
	public List<LunBoTu> list(LunBoTu record) {
		return lunBoTuMapper.queryList(record);
	}

	@Override
	public int deleteByPrimaryKey(int id) {
		return lunBoTuMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<LunBoTu> listPage(int pageNum, int pageSize, LunBoTu record) {
		PageHelper.startPage(pageNum, pageSize);
		return lunBoTuMapper.queryList(record);
	}

	@Override
	public LunBoTu queryById(int id) {
		return lunBoTuMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByIds(Integer status, String[] ids) {
		return lunBoTuMapper.updateByIds(status, ids);
	}

	@Override
	public int deleteByIds(String[] ids) {
		return lunBoTuMapper.deleteByIds(ids);
	}

	@Override
	public int getCount(LunBoTu record) {
		return lunBoTuMapper.getCount(record);
	}
}
