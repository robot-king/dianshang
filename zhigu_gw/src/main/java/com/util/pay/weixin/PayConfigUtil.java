package com.util.pay.weixin;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class PayConfigUtil {

	public static HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
			.getRequest();

	public static final String CHARSET = "UTF-8";// 编码格式
	public static final String APP_ID = "wx0202a5aaee854784";
	public static final String MCH_ID = "1485806332";
	public static final String API_KEY = "C4CA4238A0B923820DCC509A6F75849B";
	public static final String CREATE_IP = getClientIpAddress(request);
	public static final String H5_CREATE_IP = getRealIp(request);
	// public static final String CREATE_IP = request.getRemoteAddr();
	public static final String NOTIFY_URL = "http://www.mozji.com/payOrder/notifyAfterUserPay.action";
	public static final String UFDODER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	// 宝箱回调地址
	public static final String BOX_NOTIFY_URL = "http://www.mozji.com/userBox/notifyAfterUserPay.action";
	// 邮费回调地址
	public static final String POSTAGE_NOTIFY_URL = "http://www.mozji.com/inventoryAddress/notifyAfterUserPay.action";

	// 付款用的公钥
	public static final String PUBLIC_KEY_PKCS8 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoMT97TmX6yzRSJMRb/nl"
			+ "jgUC0uq67vT5K6NMLYReU29pCwzyue3ufjR3Oj/grO3vZVn5+OwnSXfeogkFkcqe"
			+ "tl+OQIfkRaGwwMT2M7iAoArQj5kT6c3G2u5twijUSpVTyxWO8dC3wPaIcHRuQc0R"
			+ "JcPtTstXIjP9id/WT/c1tHQFtfHreGNjcQxDOO8XuT9tosA+n75O7p27cNji/Ptr"
			+ "YMM9bOboRWZIBsgivUKgSFoe85knP66cJ18gUbxIah3DWEc4h6hyo8JH4VsATLLy"
			+ "tzSsTRffiiT85YbC/CVCxWgq6ueYenPx7bmkYwN2uscsn4ElOK8xoFn69WRwNzKi" + "pQIDAQAB";

	public static final String CA_LICENSE = getCaLicense();

	public static String getCaLicense() {
		String path = "";
		try {
			// 判断Windows系统还是Linux系统
			String osName = System.getProperties().getProperty("os.name");
			if (osName.toLowerCase().startsWith("win")) {
				path = java.net.URLDecoder.decode(
						PayConfigUtil.class.getResource("/com/util/pay/weixin/tixian/apiclient_cert.p12").getFile(),
						"utf-8").substring(1);
			} else {
				path = java.net.URLDecoder.decode(
						PayConfigUtil.class.getResource("/com/util/pay/weixin/tixian/apiclient_cert.p12").getFile(),
						"utf-8");

			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;
	}

	public static void main(String[] args) {
		System.out.println(getCaLicense());
	}

	/***
	 * 获取客户端ip地址(可以穿透代理)
	 * 
	 * @param request
	 * @return
	 */
	public static String getClientIpAddress(HttpServletRequest request) {
		String[] HEADERS_TO_TRY = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_X_FORWARDED_FOR",
				"HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP", "HTTP_FORWARDED_FOR",
				"HTTP_FORWA" + "RDED", "HTTP_VIA", "REMOTE_ADDR", "X-Real-IP" };
		for (String header : HEADERS_TO_TRY) {
			String ip = request.getHeader(header);
			if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
				return ip;
			}
		}
		return request.getRemoteAddr();
	}

	public static String getRealIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
