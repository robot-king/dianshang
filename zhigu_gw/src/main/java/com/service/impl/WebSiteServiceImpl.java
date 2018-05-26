package com.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dao.DictMapper;
import com.dao.WebSiteMapper;
import com.github.pagehelper.PageHelper;
import com.pojo.Dict;
import com.pojo.WebSite;
import com.service.WebSiteService;
import com.util.Constant;
import com.util.DeleteFileUtil;
import com.util.StringUtil;
import com.util.ToolsUtils;
import com.util.UnRarUtil;
import com.util.date.DateUtil;
import com.util.file.FileUploadUtil;
import com.util.makeNumber.Cn2Spell;

@Service("webSiteService")
public class WebSiteServiceImpl implements WebSiteService {

	/** 日志对象 */
	private static final Logger logger = Logger.getLogger(WebSiteServiceImpl.class);

	@Autowired
	private WebSiteMapper webSiteMapper;
	@Autowired
	private DictMapper dictMapper;

	@Transactional
	@Override
	public Map add(WebSite record, MultipartFile coverUrlFile, MultipartFile templateFile, String fileUploadPath) {

		chuLiLuoJi(record, coverUrlFile, templateFile, fileUploadPath);
		webSiteMapper.insertSelective(record);
		return ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);
	}

	@Transactional
	@Override
	public Map update(WebSite record, MultipartFile coverUrlFile, MultipartFile templateFile, String fileUploadPath) {

		chuLiLuoJi(record, coverUrlFile, templateFile, fileUploadPath);
		webSiteMapper.updateByPrimaryKeySelective(record);
		return ToolsUtils.returnResult(Constant.DataStatus.SUCCESS_CODE, "", Constant.DataStatus.SUCCESS_MSG);
	}

	public void chuLiLuoJi(WebSite record, MultipartFile coverUrlFile, MultipartFile templateFile, String fileUploadPath) {
		String dictory = DateUtil.formatDate(new Date(), "yyyyMM");
		// 封面地址
		String coverUrlPath = "";
		if (null != coverUrlFile && !coverUrlFile.isEmpty()) {
			try {
				coverUrlPath = FileUploadUtil.uploadImg(coverUrlFile, fileUploadPath, dictory);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			record.setCoverUrl(coverUrlPath);
		}

		// 模板地址
		String templatePath = "";
		if (null != templateFile && !templateFile.isEmpty()) {
			try {
				// 存储压缩文件
				String srcRarPath = FileUploadUtil.uploadRar(templateFile, fileUploadPath, "template");
				// 解压文件，获取首页访问地址
				templatePath = UnRarUtil.unZip(srcRarPath, fileUploadPath + File.separator + "template");
				// 删除压缩文件
				DeleteFileUtil.deleteFile(srcRarPath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			record.setAccessUrl(templatePath);
		}
		// 判断其它分类是否为空
		if (!StringUtil.isEmpty(record.getWebType())) {
			String code = Cn2Spell.converterToSpell(record.getWebType());
			Dict dict = new Dict();
			dict.setCode(code);
			List<Dict> list = dictMapper.queryList(dict);
			// 为空则添加其它分类子类
			if (CollectionUtils.isEmpty(list)) {
				dict.setParentCode("webType");
				dict.setName(record.getWebType());
				dict.setCreateTime(new Date());
				dict.setStatus(Constant.ON);
				dictMapper.insertSelective(dict);
			}
			record.setWebType(code);
		}
	}

	@Override
	public List<WebSite> list(WebSite record) {
		return webSiteMapper.queryList(record);
	}

	@Override
	public int deleteByPrimaryKey(int id) {
		return webSiteMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<WebSite> listPage(int pageNum, int pageSize, WebSite record) {
		PageHelper.startPage(pageNum, pageSize);
		return webSiteMapper.queryList(record);
	}

	@Override
	public WebSite queryById(int id) {
		return webSiteMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByIds(Integer status, String[] ids) {
		return webSiteMapper.updateByIds(status, ids);
	}

	@Override
	public int deleteByIds(String[] ids) {
		return webSiteMapper.deleteByIds(ids);
	}

	@Override
	public int getCount(WebSite record) {
		return webSiteMapper.getCount(record);
	}

	public void bianLiCCFile(String fileUploadPath) {
		File file = new File(fileUploadPath + File.separator + "muban");
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (files == null) {// 如果目录为空，直接退出
				return;
			}
			// 遍历，目录下的所有文件
			for (File f : files) {
				if (f.isDirectory()) {
					System.out.println(f.getAbsolutePath());
					long length = f.length();
					long rat = length / 1024;
					System.out.println("%%%%%%%%%%%等待" + rat + "秒%%%%%%%%%%%");
					unRarSmallFile(f, fileUploadPath);
					System.out.println("========================");
				}
			}
		}
	}

	public void unRarSmallFile(File file, String fileUploadPath) {
		File[] files = file.listFiles();
		if (files == null) {// 如果目录为空，直接退出
			return;
		}
		System.out.println("一共：" + files.length + "个文件");
		int ll = files.length;
		// 遍历，目录下的所有文件
		for (File f : files) {

			String path = f.getAbsolutePath();
			// 图片地址
			String picPath = path.substring(0, path.lastIndexOf("."));
			// 网站类型
			String webType = path.substring(path.indexOf("muban") + 6, path.length());
			webType = webType.substring(0, webType.indexOf(File.separator));
			path = path.substring(path.lastIndexOf(File.separator) + 1, path.indexOf(".")).replace("。", "");
			WebSite webSite = new WebSite();
			webSite.setTitle(path);
			webSite.setCreateTime(new Date());
			webSite.setType(WebSite.TYPE_MORE);
			String accessUrl = "";
			String coverUrl = "";
			try {
				// 解压文件
				if (f.getAbsolutePath().endsWith(".rar")) {
					accessUrl = UnRarUtil.unrar(f.getAbsolutePath(), fileUploadPath + File.separator + "template");
					webSite.setAccessUrl(File.separator + accessUrl);

					// 将图片移动到site虚拟目录
					picPath = picPath + ".jpg";
					coverUrl = UnRarUtil.moveTotherFolders(picPath, fileUploadPath);
					webSite.setCoverUrl(File.separator + coverUrl);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 判断其它分类是否为空
			if (!StringUtil.isEmpty(webType)) {
				String code = Cn2Spell.converterToSpell(webType);
				Dict dict = new Dict();
				dict.setCode(code);
				List<Dict> list = dictMapper.queryList(dict);
				// 为空则添加其它分类子类
				if (CollectionUtils.isEmpty(list)) {
					dict.setParentCode("webType");
					dict.setName(webType);
					dict.setCreateTime(new Date());
					dict.setStatus(Constant.OFF);
					dictMapper.insertSelective(dict);
				}
				webSite.setWebType(code);
			}
			ll--;
			System.out.println("第" + ll + "个");
			if (StringUtil.isEmpty(webSite.getCoverUrl()) || StringUtil.isEmpty(webSite.getAccessUrl())) {
				// 删除图片
				if (!StringUtil.isEmpty(webSite.getCoverUrl())) {
					String coverPath = webSite.getCoverUrl();
					coverPath = coverPath.replaceAll("site", fileUploadPath);
					DeleteFileUtil.deleteFile(coverPath);
				}
				// 删除解压文件
				if (!StringUtil.isEmpty(webSite.getAccessUrl())) {
					String accPath = webSite.getAccessUrl();
					accPath = accPath.replaceAll("site", fileUploadPath);
					String a_suffix = accPath.substring(0, accPath.indexOf("template") + 9);
					// System.out.println(a_suffix);
					String b_suffix = accPath.substring(accPath.indexOf("template") + 9, accPath.length());
					// System.out.println(b_suffix);
					b_suffix = b_suffix.substring(0, b_suffix.indexOf(File.separator));
					// System.out.println(b_suffix);
					accPath = a_suffix + b_suffix;
					logger.info("删除该目录下所有文件" + accPath);
					DeleteFileUtil.deleteDirectory(accPath);
				}
				continue;
			}
			webSiteMapper.insertSelective(webSite);
		}

	}

	@Override
	public void unRarFile(String fileUploadPath) {
		// TODO Auto-generated method stub
		bianLiCCFile(fileUploadPath);
	}

	// 循环遍历所有文件
	public static void bianLiTemplateFile(String fileUploadPath) {
		File file = new File(fileUploadPath);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (files == null) {// 如果目录为空，直接退出
				return;
			}
			// 遍历，目录下的所有文件
			for (File f : files) {
				if (f.isDirectory()) {
					System.out.println(f.getAbsolutePath());
					bianLiTemplateFile(f.getAbsolutePath());
				} else {
					pointHtml(f.getAbsolutePath());
				}
			}
		} else {
			pointHtml(file.getAbsolutePath());
		}
	}

	// 判断出.html结尾的文件
	public static void pointHtml(String path) {
		System.out.println(path);
		if (path.indexOf(".html") > 0) {
			try {
				//////// 开始挨个的读取文件 ////////
				BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));// 数据流读取文件
				StringBuffer strBuffer = new StringBuffer();
				for (String temp = null; (temp = bufReader.readLine()) != null; temp = null) {
					if (temp.indexOf("www.moobnn.com") > 0) {
						temp = temp.replace("www.moobnn.com", "");// 替换为你想要的东东
						System.out.print("替换：www.moobnn.com");
					}
					if (temp.indexOf("模板在线") > 0) {
						temp = temp.replace("模板在线", "");// 替换为你想要的东东
						System.out.print("替换：模板在线");
					}
					if (temp.indexOf("guantaow.taobao.com") > 0) {
						temp = temp.replace("guantaow.taobao.com", "");// 替换为你想要的东东
						System.out.print("替换：guantaow.taobao.com");
					}
					if (temp.indexOf("厚朴网络") > 0) {
						temp = temp.replace("厚朴网络", "");// 替换为你想要的东东
						System.out.print("替换：厚朴网络");
					}
					if (temp.indexOf("淘宝店") > 0) {
						temp = temp.replace("淘宝店", "");// 替换为你想要的东东
						System.out.print("替换：淘宝店");
					}
					if (temp.indexOf("网站模板") > 0) {
						temp = temp.replace("网站模板", "");// 替换为你想要的东东
						System.out.print("替换：网站模板");
					}
					if (temp.indexOf("moobnn.com") > 0) {
						temp = temp.replace("moobnn.com", "");// 替换为你想要的东东
						System.out.print("替换：moobnn.com");
					}
					if (temp.indexOf("网页模板") > 0) {
						temp = temp.replace("网页模板", "");// 替换为你想要的东东
						System.out.print("替换：网页模板");
					}
					strBuffer.append(temp);
					strBuffer.append(System.getProperty("line.separator"));// 行与行之间的分割
				}
				bufReader.close();
				PrintWriter printWriter = new PrintWriter(path);// 替换后输出的文件位置（切记这里的E:/ttt 在你的本地必须有这个文件夹）
				printWriter.write(strBuffer.toString().toCharArray());
				printWriter.flush();
				printWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void unReplace(String fileUploadPath) {
		// TODO Auto-generated method stub
		bianLiTemplateFile(fileUploadPath);
	}

	public static void main(String[] args) {
		System.out.println("==去掉广告==");
		bianLiTemplateFile("F:\\topContentImage\\template");
	}
}
