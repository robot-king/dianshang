package com.util.pay.weixin.tixian;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import com.util.pay.weixin.PayConfigUtil;

/**
 * 签名工具类
 */
public class SignUtils {

	/**
	 * @param characterEncoding
	 *            编码格式 utf-8
	 */
	public static String createSign(String characterEncoding, SortedMap<Object, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + PayConfigUtil.API_KEY);
		String sign = MD5Utils.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
		System.out.println(sign);
		return sign;
	}
}
