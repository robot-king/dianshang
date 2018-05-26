package com.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

public class RealAddressUtil {

	public static String getIpAddr(HttpServletRequest request) {
		String ipAddress = null;
		// ipAddress = request.getRemoteAddr();
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}

		}

		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}

	public static JSONObject getCity(String ipAddress) {
		JSONObject json = null;
		try {
			// String strIP = "210.75.225.254";

			URL url = new URL("http://ip.taobao.com/service/getIpInfo.php");
			String content = "ip=" + ipAddress;

			// URL url = new URL("http://ip.qq.com/cgi-bin/searchip?searchip1="
			// + strIP);
			URLConnection conn = url.openConnection();
			HttpURLConnection connection = null;
			connection = (HttpURLConnection) url.openConnection();// 新建连接实例
			connection.setConnectTimeout(2000);// 设置连接超时时间，单位毫秒
			connection.setReadTimeout(2000);// 设置读取数据超时时间，单位毫秒
			connection.setDoOutput(true);// 是否打开输出流 true|false
			connection.setDoInput(true);// 是否打开输入流true|false
			connection.setRequestMethod("POST");// 提交方法POST|GET
			connection.setUseCaches(false);// 是否缓存true|false
			connection.connect();// 打开连接端口
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());// 打开输出流往对端服务器写数据
			out.writeBytes(content);// 写数据,也就是提交你的表单 name=xxx&pwd=xxx
			out.flush();// 刷新
			out.close();// 关闭输出流
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));// 往对端写完数据对端服务器返回数据
			// ,以BufferedReader流来读取
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			reader.close();
			System.out.println(result);
			json = JSONObject.parseObject(result.toString()).getJSONObject("data");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

	// 获取域名ip地址
	public static String getIpByDomain(String domain) {
		InetAddress ip = null;
		try {
			ip = InetAddress.getByName("www.mozji.com");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ip.getHostAddress();
	}

	public static void main(String[] args) throws UnknownHostException {
		InetAddress ip = InetAddress.getByName("www.mozji.com");
		System.out.println(ip.toString());
		System.out.println("Address:" + ip.getHostAddress());
		System.out.println("Name" + ip.getHostName());
	}
}
