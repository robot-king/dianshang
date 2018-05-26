package com.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.util.ImageUtils;
import com.util.SpringToolUtils;
import com.util.date.DateUtil;

public class FileUploadUtil {
	private final static Logger log = Logger.getLogger(FileUploadUtil.class);
	private final static String UPLOAD_FILE = "uploadFile";

	/**
	 * 多文件上传<br>
	 * 
	 * @author gnf
	 * @Since 2015-09-21
	 * @param multiPartFile
	 *            多文件集合
	 * @return List<String> 返回多文件路径
	 */
	public static List<String> multiUploadFile(List<MultipartFile> multiPartFile, MultipartHttpServletRequest request, String dictory) {

		List<String> filePath = null;
		if (!CollectionUtils.isEmpty(multiPartFile)) {
			for (MultipartFile mFile : multiPartFile) {
				if (!mFile.isEmpty()) {
					log.info("上传文件的源文件名称:" + mFile.getOriginalFilename());
					String sourcePath = mFile.getOriginalFilename();
					FileUpload fileUpload = (FileUpload) SpringToolUtils.getBean("uploadFile");
					String path = request.getSession().getServletContext().getRealPath("/upload/" + dictory);
					try {
						String suffix = sourcePath.substring(sourcePath.lastIndexOf("."));
						if (fileUpload.getFileSuffix().indexOf(suffix.toLowerCase()) != -1) {
							if (mFile.getSize() <= Long.parseLong(fileUpload.getMaxFileSize())) {
								String picPath = "/" + UUID.randomUUID() + sourcePath.substring(sourcePath.indexOf("."), sourcePath.length());
								String uploadFilePath = path + "/" + picPath;
								File file = new File(path);
								if (!file.exists()) {
									file.mkdirs();
								}
								FileOutputStream fileOS = null;
								try {
									fileOS = new FileOutputStream(uploadFilePath);
									fileOS.write(mFile.getBytes());
								} catch (Exception e) {
									log.error(e.getMessage());
								} finally {
									if (fileOS != null) {
										fileOS.close();
									}
								}
								if (CollectionUtils.isEmpty(filePath)) {
									filePath = new ArrayList<String>();
								}
								String urlPath = "/upload/" + dictory + picPath;
								filePath.add(urlPath);
								log.info("=======文件上传成功====");
							} else {
								throw new RuntimeException("上传文件的总大小:" + Long.parseLong(fileUpload.getMaxFileSize()) / (1024 * 1024) + "M");
							}
						} else {
							throw new RuntimeException("上传文件的拓展名称 :" + fileUpload.getFileSuffix());
						}
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				}
			}
		} else {
			throw new RuntimeException("multiPartFile is null");
		}

		return filePath;
	}

	/**
	 * tomcat虚拟目录指定图片存储路径
	 * 
	 * @param uploadFile/存储文件
	 * @param fileUploadPath/存储文件主目录
	 * @param setFileName/设置文件名称
	 * @param dictory/存储文件子目录
	 * @param imgurl、虚拟目录访问地址
	 * @return
	 * @throws Exception
	 */
	public static String uploadImg(MultipartFile uploadFile, String fileUploadPath, String dictory) throws Exception {
		log.info("文件上传开始");
		String uploadFilePath = null;// 实际存储路径，绝对路径，大图片（原始图）路径
		String uploadFilePathSmall = null;// 实际存储路径，绝对路径，小图片（缩略图）路径
		String accessShowPath = null;// 访问显示路径
		String fileName = uploadFile.getOriginalFilename();
		if (!uploadFile.isEmpty()) {
			// 文件对象
			FileUpload fileUpload = (FileUpload) SpringToolUtils.getBean(UPLOAD_FILE);
			String path = fileUploadPath + "/" + dictory;
			try {
				String suffix = fileName.substring(fileName.lastIndexOf("."));
				// 上传文件验证
				if (fileUpload.getFileSuffix().indexOf(suffix.toLowerCase()) != -1) {
					if (uploadFile.getSize() <= Long.parseLong(fileUpload.getMaxFileSize())) {
						// 文件存储完整路径
						String setFileName = getRandomFileName();
						// 大图片路径
						uploadFilePath = path + "/" + setFileName + suffix;
						// 小图片路径
						uploadFilePathSmall = path + "/" + setFileName + "_s" + suffix;
						File folder = new File(path);
						if (!folder.exists()) {
							folder.mkdirs();
						}

						// 生成大图片
						File file = new File(uploadFilePath);
						uploadFile.transferTo(file);
						file.createNewFile();

						// 生成小图片
						File file2 = new File(uploadFilePathSmall);
						copyFile(file, file2);

						accessShowPath = "/site" + "/" + dictory + "/" + setFileName + suffix;
						log.info("文件上传成功");
						log.info("文件上传结束");
						log.info("=======压缩图片====");
						ImageUtils.compress(uploadFilePathSmall);
					} else {
						log.info("文件上传超过最大值:" + Long.parseLong(fileUpload.getMaxFileSize()) / (1024 * 1024) + "M");
						throw new RuntimeException("文件上传超过最大值:" + Long.parseLong(fileUpload.getMaxFileSize()) / (1024 * 1024) + "M");
					}
				} else {
					log.info("文件格式不正确:" + fileUpload.getFileSuffix());
					throw new RuntimeException("文件格式不正确:" + fileUpload.getFileSuffix());
				}

			} catch (Exception e) {
				log.info("文件上传异常");
				throw new RuntimeException(e.getMessage());
			}
		} else {
			log.info("文件上传为空");
			throw new RuntimeException("uploadFile is null");
		}
		return accessShowPath;
	}

	/**
	 * tomcat虚拟目录指定图片存储路径
	 * 
	 * @param uploadFile/存储文件
	 * @param fileUploadPath/存储文件主目录
	 * @param setFileName/设置文件名称
	 * @param dictory/存储文件子目录
	 * @param imgurl/虚拟目录访问地址
	 * @return
	 * @throws Exception
	 */
	public static String uploadImgByCutSize(MultipartFile uploadFile, String fileUploadPath, String dictory, int width, int height) throws Exception {
		log.info("文件上传开始");
		String uploadFilePath = null;// 实际存储路径，绝对路径，大图片（原始图）路径
		String uploadFilePathSmall = null;// 实际存储路径，绝对路径，小图片（缩略图）路径
		String accessShowPath = null;// 访问显示路径
		String fileName = uploadFile.getOriginalFilename();
		if (!uploadFile.isEmpty()) {
			// 文件对象
			FileUpload fileUpload = (FileUpload) SpringToolUtils.getBean(UPLOAD_FILE);
			String path = fileUploadPath + "/" + dictory;
			try {
				String suffix = fileName.substring(fileName.lastIndexOf("."));
				// 上传文件验证
				if (fileUpload.getFileSuffix().indexOf(suffix.toLowerCase()) != -1) {
					if (uploadFile.getSize() <= Long.parseLong(fileUpload.getMaxFileSize())) {
						// 文件存储完整路径
						String setFileName = getRandomFileName();
						// 大图片路径
						uploadFilePath = path + "/" + setFileName + suffix;
						// 小图片路径
						uploadFilePathSmall = path + "/" + setFileName + "_s" + suffix;
						File folder = new File(path);
						if (!folder.exists()) {
							folder.mkdirs();
						}

						// 生成大图片
						File file = new File(uploadFilePath);
						uploadFile.transferTo(file);
						file.createNewFile();

						// 生成小图片
						File file2 = new File(uploadFilePathSmall);
						copyFile(file, file2);

						accessShowPath = "/site" + "/" + dictory + "/" + setFileName + suffix;
						log.info("文件上传成功");
						log.info("文件上传结束");
						log.info("=======压缩图片====");
						ImageUtils.cutAndcompress(uploadFilePathSmall, width, height);
					} else {
						log.info("文件上传超过最大值:" + Long.parseLong(fileUpload.getMaxFileSize()) / (1024 * 1024) + "M");
						throw new RuntimeException("文件上传超过最大值:" + Long.parseLong(fileUpload.getMaxFileSize()) / (1024 * 1024) + "M");
					}
				} else {
					log.info("文件格式不正确:" + fileUpload.getFileSuffix());
					throw new RuntimeException("文件格式不正确:" + fileUpload.getFileSuffix());
				}

			} catch (Exception e) {
				log.info("文件上传异常");
				throw new RuntimeException(e.getMessage());
			}
		} else {
			log.info("文件上传为空");
			throw new RuntimeException("uploadFile is null");
		}
		return accessShowPath;
	}

	/**
	 * tomcat项目中指定文件存储路径
	 * 
	 * @param uploadFile
	 * @param request
	 * @param dictory
	 * @return
	 * @throws Exception
	 */
	public static String uploadFileByAppointedDirectory(MultipartFile uploadFile, MultipartHttpServletRequest request, String dictory) throws Exception {
		log.info("文件上传开始");
		String uploadFilePath = null;
		String fileName = uploadFile.getOriginalFilename();
		if (!uploadFile.isEmpty()) {
			// 文件对象
			FileUpload fileUpload = (FileUpload) SpringToolUtils.getBean(UPLOAD_FILE);
			String path = request.getSession().getServletContext().getRealPath("/uploadFilePath/" + dictory);
			try {
				String suffix = fileName.substring(fileName.lastIndexOf("."));
				// 上传文件验证
				if (fileUpload.getFileSuffix().indexOf(suffix.toLowerCase()) != -1) {
					if (uploadFile.getSize() <= Long.parseLong(fileUpload.getMaxFileSize())) {
						// 文件存储完整路径
						uploadFilePath = path + "/" + fileName;
						File folder = new File(path);
						if (!folder.exists()) {
							folder.mkdirs();
						}

						File file = new File(uploadFilePath);
						uploadFile.transferTo(file);
						file.createNewFile();
						log.info("文件上传成功");
						log.info("文件上传结束");
					} else {
						log.info("文件上传超过最大值:" + Long.parseLong(fileUpload.getMaxFileSize()) / (1024 * 1024) + "M");
						throw new RuntimeException("文件上传超过最大值:" + Long.parseLong(fileUpload.getMaxFileSize()) / (1024 * 1024) + "M");
					}
				} else {
					log.info("文件格式不正确:" + fileUpload.getFileSuffix());
					throw new RuntimeException("文件格式不正确:" + fileUpload.getFileSuffix());
				}

			} catch (Exception e) {
				log.info("文件上传异常");
				throw new RuntimeException(e.getMessage());
			}
		} else {
			log.info("文件上传为空");
			throw new RuntimeException("uploadFile is null");
		}
		return "/uploadFilePath" + "/" + dictory + "/" + fileName;
	}

	/**
	 * tomcat虚拟目录指定压缩存储路径
	 * 
	 * @param uploadFile/存储文件
	 * @param fileUploadPath/存储文件主目录
	 * @param setFileName/设置文件名称
	 * @param dictory/存储文件子目录
	 * @return
	 * @throws Exception
	 */
	public static String uploadRar(MultipartFile uploadFile, String fileUploadPath, String dictory) throws Exception {
		log.info("文件上传开始");
		String uploadFilePath = null;// 实际存储路径，绝对路径，大图片（原始图）路径
		String fileName = uploadFile.getOriginalFilename();
		if (!uploadFile.isEmpty()) {
			// 文件对象
			FileUpload fileUpload = (FileUpload) SpringToolUtils.getBean(UPLOAD_FILE);
			String path = fileUploadPath + File.separator + dictory;
			try {
				String suffix = fileName.substring(fileName.lastIndexOf("."));
				// 上传文件验证
				if (fileUpload.getFileSuffix().indexOf(suffix.toLowerCase()) != -1) {
					if (uploadFile.getSize() <= Long.parseLong(fileUpload.getMaxFileSize())) {
						// 文件存储完整路径
						String setFileName = getRandomFileName();
						// 大图片路径
						uploadFilePath = path + File.separator + setFileName + suffix;
						File folder = new File(path);
						if (!folder.exists()) {
							folder.mkdirs();
						}

						// 生成文件
						File file = new File(uploadFilePath);
						uploadFile.transferTo(file);
						file.createNewFile();

						log.info("文件上传成功");
						log.info("文件上传结束");
					} else {
						log.info("文件上传超过最大值:" + Long.parseLong(fileUpload.getMaxFileSize()) / (1024 * 1024) + "M");
						throw new RuntimeException("文件上传超过最大值:" + Long.parseLong(fileUpload.getMaxFileSize()) / (1024 * 1024) + "M");
					}
				} else {
					log.info("文件格式不正确:" + fileUpload.getFileSuffix());
					throw new RuntimeException("文件格式不正确:" + fileUpload.getFileSuffix());
				}

			} catch (Exception e) {
				log.info("文件上传异常");
				throw new RuntimeException(e.getMessage());
			}
		} else {
			log.info("文件上传为空");
			throw new RuntimeException("uploadFile is null");
		}
		return uploadFilePath;
	}

	/**
	 * 复制文件
	 * 
	 * @param fromFile
	 * @param toFile
	 * @throws IOException
	 */
	public static void copyFile(File fromFile, File toFile) throws IOException {
		FileInputStream ins = new FileInputStream(fromFile);
		FileOutputStream out = new FileOutputStream(toFile);
		byte[] b = new byte[1024];
		int n = 0;
		while ((n = ins.read(b)) != -1) {
			out.write(b, 0, n);
		}

		ins.close();
		out.close();
	}

	public static String getRandomFileName() {

		SimpleDateFormat simpleDateFormat;
		simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		String str = simpleDateFormat.format(date);
		Random random = new Random();
		int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数

		return rannum + str;// 当前时间
	}

	// 下载图片，并设置大小图片
	public static String setPicByDownload(String urlString, String fileUploadPath) {
		log.info("准备下载：" + urlString + ",根目录路径" + fileUploadPath);
		String accessShowPath = null;// 访问显示路径
		try {
			String uploadFilePath = null;// 实际存储路径，绝对路径，大图片（原始图）路径
			String uploadFilePathSmall = null;// 实际存储路径，绝对路径，小图片（缩略图）路径
			String dictory = DateUtil.formatDate(new Date(), "yyyyMM");
			// 文件存储完整路径
			String path = fileUploadPath + "/" + dictory;

			String suffix = ".png";
			String setFileName = getRandomFileName();
			String fileName = setFileName + suffix;

			// 大图片路径
			uploadFilePath = path + "/" + setFileName + suffix;
			log.info("大图片路径:" + uploadFilePath);

			log.info("开始下载");
			// 构造URL
			URL url = new URL(urlString);
			// 打开连接
			URLConnection con = url.openConnection();
			// 设置请求超时为5s
			con.setConnectTimeout(5 * 1000);
			// 输入流
			InputStream is = con.getInputStream();

			log.info("下载中……");

			// 1K的数据缓冲
			byte[] bs = new byte[1024];
			// 读取到的数据长度
			int len;
			// 输出的文件流
			log.info("输出的文件流");
			File sf = new File(path);
			if (!sf.exists()) {
				sf.mkdirs();
			}
			OutputStream os = new FileOutputStream(sf.getPath() + System.getProperty("file.separator") + fileName);
			// 开始读取
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			// 完毕，关闭所有链接
			os.close();
			is.close();

			// 小图片路径
			log.info("小图片路径");
			uploadFilePathSmall = path + "/" + setFileName + "_s" + suffix;

			log.info("获取大图片");
			File file1 = new File(uploadFilePath);

			// 生成小图片
			log.info("生成小图片路径：" + uploadFilePathSmall);
			File file2 = new File(uploadFilePathSmall);
			copyFile(file1, file2);

			// 返回虚拟路径
			accessShowPath = "/site" + "/" + dictory + "/" + setFileName + suffix;
			log.info("返回虚拟路径：" + accessShowPath);
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.debug(e.getMessage());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.debug(e.getMessage());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.debug(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.debug(e.getMessage());
		}
		return accessShowPath;
	}
}
