package com.util;

import java.util.Random;

/**
 * 随机数方法
 */
public class RandomUtils {

	/**
	 * 生成"0001"..."0002"
	 * 
	 * @param length
	 *            生成code的长度
	 * @param initNum
	 *            初始数据
	 * @return length = 4, initNum=10 -> 0010
	 */
	public static String getSuffixCode(int length, int initNum) {
		String code = "";
		for (int i = length; i > ((initNum + "").length()); i--) {
			code += "0";
		}
		return (code + initNum);
	}

	/**
	 * 随机生成x位数字
	 * 
	 * @param 生成随机数的位数
	 * @return
	 */
	public static String getRandomNumber(int digital) {
		int[] array = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = array[index];
			array[index] = array[i - 1];
			array[i - 1] = tmp;
		}
		int result = 0;
		for (int i = 0; i < digital; i++) {
			result = result * 10 + array[i];
		}

		// 以上生成code如果以0开始就会生成少于6为的数字，采用getSuffixCode补全
		return getSuffixCode(digital, result);
	}

	public static void main(String[] args) {

		for (int i = 0; i < 10; i++) {
			System.out.println(getRandomNumber(3));
		}

	}
}
