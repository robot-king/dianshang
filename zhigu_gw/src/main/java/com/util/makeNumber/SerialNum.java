package com.util.makeNumber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

public class SerialNum {
	private static String count = "000";
	private static String dateValue = "20131115";

	public synchronized static String getMoveOrderNo(String code, String number) {
		long No = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		String nowdate = sdf.format(new Date());
		No = Long.parseLong(nowdate);
		if (!(String.valueOf(No)).equals(dateValue)) {
			count = "000";
			dateValue = String.valueOf(No);

		}
		String num = String.valueOf(No);
		num += getNo(count, number);
		if (code.equals("")) {
			code = "system";
		}
		num = code + num;
		return num;

	}

	public static String getNo(String s, String number) {
		String rs = s;
		if (!StringUtils.isEmpty(number)) {
			rs = number;
		}
		int i = Integer.parseInt(rs);
		i += 1;
		rs = "" + i;
		for (int j = rs.length(); j < 5; j++) {
			rs = "0" + rs;
		}
		return rs;

	}

	/**
	 * 有下划线的
	 * 
	 * @param str
	 * @param number
	 * @return
	 */
	public static String makeNo(String str, String number) {
		str = Cn2Spell.converterToFirstSpell(str);
		String code = str.toUpperCase() + "_";
		return getMoveOrderNo(code, number);
	}

	/**
	 * 没有下划线的
	 * 
	 * @param str
	 * @param number
	 * @return
	 */
	public static String makeNo2(String str, String number) {
		str = Cn2Spell.converterToFirstSpell(str);
		String code = str.toUpperCase();
		return getMoveOrderNo(code, number);
	}

	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 生成随机字母
	 * 
	 * @param str
	 * @param number
	 * @return
	 */
	public static String makeAlphabet(int number) {

		List<String> list = new ArrayList<>();

		String al = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		int index = new Random().nextInt(al.length());
		list.add(String.valueOf(al.charAt(index)));
		number--;
		if (number != 0) {
			list.add(makeAlphabet(number));
		}

		return StringUtils.strip(list.toString(), "[]").replaceAll(",", "").replaceAll(" ", "");
	}

	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		// System.out.println(SerialNum.makeNo("柚才网",
		// RandomUtils.getRandomNumber(4)));
		// for (int i = 0; i < 100; i++) {
		// System.out.println(makeNo("计算机", i + ""));
		// // System.out.println(getNo("", i+""));
		// }
		// SerialNum.makeNo("柚才网", getRandomString(8));
		System.out.println(getRandomString(6));
	}
}
