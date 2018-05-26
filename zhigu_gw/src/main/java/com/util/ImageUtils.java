package com.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtils {

	/**
	 * 裁剪并压缩图片默认尺寸图片
	 * 
	 * @throws IOException
	 */
	public static void compress(String filePath) throws IOException {
		File file = new File(filePath);
		// 计算图片宽度，如果宽度超过，按600*600压缩尺寸
		BufferedImage bufferedImage = ImageIO.read(file);
		int width = bufferedImage.getWidth();
		// System.out.println("width:" + width);
		if (width > 600) {
			Thumbnails.of(file).size(600, 600).toFile(file);
		}
		// 计算图片质量大小，如果质量大于100kb,则进行不改变尺寸，只压缩质量大小
		// System.out.println("size:" +
		// String.format("%.1f",file.length()/1024.0));// 源图大小
		if (file.length() / 1024.0 > 100) {
			Thumbnails.of(file).scale(0.25f).toFile(file);
			compress(filePath);
		}
	}

	/**
	 * 裁剪并压缩图片指定尺寸图片
	 * 
	 * @throws IOException
	 */
	public static void cutAndcompress(String filePath, int width, int height) throws IOException {
		File file = new File(filePath);
		if (width == 0 || height == 0) {
			width = 80;
			height = 80;
		}
		// 先将指定的图片宽高按两倍的尺寸缩小
		Thumbnails.of(file).size(2 * width, 2 * height).toFile(file);
		// 再将新图片从中心裁剪至指定尺寸图片
		Thumbnails.of(file).sourceRegion(Positions.CENTER, width, height).size(width, height).toFile(file);
	}

	/**
	 * 裁剪若干个等高等宽的图片
	 * 
	 * @param rows
	 *            目标切片行数。默认2，必须是范围 [1, 20] 之内
	 * @param cols
	 *            目标切片列数。默认2，必须是范围 [1, 20] 之内
	 * @throws IOException
	 */
	public static String[] cut(String filePath, int rows, int cols) throws IOException {

		String suffix = filePath.substring(filePath.lastIndexOf("."));
		String newFilePath = filePath.substring(0, filePath.lastIndexOf("."));
		if (rows <= 0 || rows > 20)
			rows = 2; // 切片行数
		if (cols <= 0 || cols > 20)
			cols = 2; // 切片列数

		String[] arr = new String[rows * cols];

		File file = new File(filePath);
		// 计算图片宽度，如果宽度超过，按600*600压缩尺寸
		BufferedImage bufferedImage = ImageIO.read(file);
		int width = bufferedImage.getWidth();
		int height = bufferedImage.getHeight();
		System.out.println("width:" + width);
		System.out.println("height:" + height);

		// 切片文件编号
		int count = 1;
		// 横纵坐标
		int x = 0, y = 0;
		// 每个切片的宽度，高度
		int uw = 0, uh = 0;
		if (width % rows == 0) {
			uw = width / rows;
		} else {
			uw = (int) Math.floor(width / rows) + 1;
		}
		if (height % cols == 0) {
			uh = height / cols;
		} else {
			uh = (int) Math.floor(height / cols) + 1;
		}
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				x = i * uw;
				y = j * uh;
				System.out.println("x:" + x + ",y:" + y + ",width:" + width + ",height:" + height);
				String generateFilePath = newFilePath + "_" + count + suffix;
				Thumbnails.of(filePath).sourceRegion(x, y, uw, uh).size(uw, uh).keepAspectRatio(false)
						.toFile(generateFilePath);
				int fileIndex = count - 1;
				arr[fileIndex] = generateFilePath;
				count++;
			}
		}
		return arr;
	}

	public static void main(String[] args) {
		try {
			String[] arr = cut("F:/1.png", 5, 5);
			for (String s : arr) {
				System.out.println(s);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}