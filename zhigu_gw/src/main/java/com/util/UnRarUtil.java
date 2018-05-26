package com.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.log4j.Logger;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;
import com.util.file.FileUpload;
import com.util.file.FileUploadUtil;
import com.util.makeNumber.Cn2Spell;

public class UnRarUtil {

	/** 日志对象 */
	private static final Logger logger = Logger.getLogger(UnRarUtil.class);

	/** 使用GBK编码可以避免压缩中文文件名乱码 */
	private static final String CHINESE_CHARSET = "GBK";
	/** 文件读取缓冲区大小 */
	private static final int CACHE_SIZE = 1024;

	/**
	 * 解压压缩包
	 * 
	 * @param zipFilePath
	 *            压缩文件路径
	 * @param destDir
	 *            解压目录
	 */
	public static String unZip(String zipFilePath, String destDir) {

		// 返回首页文件路径
		String indexPath = "";
		ZipFile zipFile = null;
		try {
			BufferedInputStream bis = null;
			FileOutputStream fos = null;
			BufferedOutputStream bos = null;

			if (zipFilePath.toLowerCase().endsWith(".rar")) {
				System.out.println("非rar文件！");
			}

			zipFile = new ZipFile(zipFilePath, Charset.forName("gbk"));
			Enumeration<ZipEntry> zipEntries = (Enumeration<ZipEntry>) zipFile.entries();
			File file, parentFile;
			ZipEntry entry;
			byte[] cache = new byte[CACHE_SIZE];
			while (zipEntries.hasMoreElements()) {
				entry = (ZipEntry) zipEntries.nextElement();
				if (entry.isDirectory()) {
					new File(destDir + File.separator + Cn2Spell.converterToSpell(entry.getName())).mkdirs();
					continue;
				}
				bis = new BufferedInputStream(zipFile.getInputStream(entry));
				file = new File(destDir + File.separator + Cn2Spell.converterToSpell(entry.getName()));

				//
				System.out.println(file.getAbsolutePath());
				if (file.getAbsolutePath().indexOf("index.html") > 0) {
					indexPath = file.getAbsolutePath();
					indexPath = "site" + File.separator
							+ indexPath.substring(indexPath.indexOf("template"), indexPath.length());
				}

				parentFile = file.getParentFile();
				if (parentFile != null && (!parentFile.exists())) {
					parentFile.mkdirs();
				}
				fos = new FileOutputStream(file);
				bos = new BufferedOutputStream(fos, CACHE_SIZE);
				int readIndex = 0;
				while ((readIndex = bis.read(cache, 0, CACHE_SIZE)) != -1) {
					fos.write(cache, 0, readIndex);
				}
				bos.flush();
				bos.close();
				fos.close();
				bis.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				zipFile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("============================");
		System.out.println("首页路径:" + indexPath);
		System.out.println("============================");
		return indexPath;
	}

	public static String renameFile(String rarPath, String zipPath) {
		// Old file
		File oldFile = new File(rarPath);
		// new file
		File newFile = new File(zipPath);

		boolean flag = oldFile.renameTo(newFile);
		if (flag) {
			System.out.println("文件重命名成功");
		} else {
			System.out.println("文件重命名失败");
		}
		return null;
	}

	public static String unrar(String srcRarPath, String dstDirectoryPath) throws Exception {

		// 返回首页文件路径
		String indexPath = "";

		System.out.println("===================" + srcRarPath + "===================");
		System.out.println("🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛");
		System.out.println("🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛");
		System.out.println("🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛🔛");

		File dstDiretory = new File(dstDirectoryPath);

		if (!dstDiretory.exists()) {// 目标目录不存在时，创建该文件夹
			dstDiretory.mkdirs();

		}

		File fol = null, out = null;
		Archive a = null;
		try {
			a = new Archive(new File(srcRarPath));
			if (a != null) {
				a.getMainHeader().print(); // 打印文件信息.
				FileHeader fh = a.nextFileHeader();

				while (fh != null) {

					if (fh.isDirectory()) { // 文件夹
						// 如果是中文路径，调用getFileNameW()方法，否则调用getFileNameString()方法，还可以使用if(fh.isUnicode())
						if (existZH(fh.getFileNameW())) {
							fol = new File(dstDirectoryPath + File.separator
									+ Cn2Spell.converterToFirstSpell(fh.getFileNameW().replace(" ", "")));
						} else {
							fol = new File(dstDirectoryPath + File.separator + fh.getFileNameString());
						}
						fol.mkdirs();
					} else { // 文件
						if (existZH(fh.getFileNameW())) {
							out = new File(dstDirectoryPath + File.separator
									+ Cn2Spell.converterToFirstSpell(fh.getFileNameW().trim().replace(" ", "")));
						} else {
							out = new File(dstDirectoryPath + File.separator + fh.getFileNameString().trim());

						}

						//
						System.out.println(out.getAbsolutePath());
						if (out.getAbsolutePath().indexOf("index.html") > 0) {
							indexPath = out.getAbsolutePath();
							indexPath = "site" + File.separator
									+ indexPath.substring(indexPath.indexOf("template"), indexPath.length());
						}

						System.out.println(out.getAbsolutePath());
						try {// 之所以这么写try，是因为万一这里面有了异常，不影响继续解压.
							if (!out.exists()) {
								if (!out.getParentFile().exists()) {// 相对路径可能多级，可能需要创建父目录.
									out.getParentFile().mkdirs();
								}
								out.createNewFile();
							}

							FileOutputStream os = new FileOutputStream(out);
							a.extractFile(fh, os);
							os.close();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					fh = a.nextFileHeader();
				}

				a.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return indexPath;
	}

	/*
	 * 
	 * 判断是否是中文
	 * 
	 */
	public static boolean existZH(String str) {

		String regEx = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		while (m.find()) {
			return true;
		}

		return false;

	}

	public static void bianLiCCFile() {
		File file = new File("F:\\HTML模板解压文件");
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
					unRarSmallFile(f);
					System.out.println("========================");
				}
			}
		}
	}

	public static void unRarSmallFile(File file) {
		File[] files = file.listFiles();
		if (files == null) {// 如果目录为空，直接退出
			return;
		}
		System.out.println("一共：" + files.length + "个文件");
		int ll = files.length;
		// 遍历，目录下的所有文件
		for (File f : files) {
			// 判断文件格式
			try {
				if (f.getAbsolutePath().endsWith(".rar")) {
					unrar(f.getAbsolutePath(), "F:\\topContentImage\\template\\");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ll--;
			System.out.println("第" + ll + "个");
		}
	}

	/**
	 * 复制整个文件夹内容
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf/ff
	 * @return boolean
	 */
	public static void copyFolder(String oldPath, String newPath) {

		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("复制整个文件夹内容操作出错");
			e.printStackTrace();

		}

	}

	public static String moveTotherFolders(String startPath, String endPath) {
		// 重命名文件
		String suffix = startPath.substring(startPath.indexOf("."), startPath.length());
		String setName = File.separator + FileUploadUtil.getRandomFileName() + suffix;
		endPath = endPath + setName;
		try {
			File startFile = new File(startPath);
			System.out.println(endPath);
			if (startFile.renameTo(new File(endPath))) {
				logger.info("文件移动成功");
			} else {
				logger.info("文件移动失败");
			}
		} catch (Exception e) {
			logger.info("文件移动异常");

		}
		endPath = "site" + setName;
		return endPath;
	}

	public static void main(String[] args) throws Exception {

		// unZip("D:\\121.42.144.11\\pigShop\\订单\\11022724-盛世集团\\盛世集团-18-1-14.zip",
		// "D:\\121.42.144.11\\pigShop\\订单\\11022724-盛世集团\\template");

		// bianLiCCFile();
		// unrar("F:\\topContentImage\\template\\1890220180413.rar",
		// "F:\\topContentImage\\template\\");

		moveTotherFolders("F:\\1126320180413404错误页html5模板下载_错误页 404 bootstrap.jpg", "F:\\HTML模板过滤文件");
	}

}