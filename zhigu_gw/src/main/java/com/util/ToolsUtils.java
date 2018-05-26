package com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class ToolsUtils {

	/**
	 * 时间戳转化
	 * 
	 * @return
	 */
	public static String datetimeStr() {
		String datetimeStr = "";
		Date date = new Date();
		datetimeStr = date.getTime() + "";
		return datetimeStr;
	}

	/**
	 * 接口返回数据
	 * 
	 * @Description:
	 * @param statusCode
	 *            状态码
	 * @param result
	 *            正常则传结果对象 否则 传提示语
	 * @return map
	 */
	public static Map<String, Object> returnResult(int statusCode, Object msg) {
		Map<String, Object> map = new HashMap<>();
		map.put("success", statusCode == Constant.DataStatus.SUCCESS_CODE ? true : false);
		map.put("status_code", statusCode);
		map.put("msg", msg);
		return map;
	}

	public static Map<String, Object> returnResult(int statusCode, Object result, Object msg) {
		Map<String, Object> map = new HashMap<>();
		map.put("success", statusCode == Constant.DataStatus.SUCCESS_CODE ? true : false);
		map.put("status_code", statusCode);
		map.put("data", result);
		map.put("msg", msg);
		return map;
	}

	/**
	 * 判断是否为空
	 * 
	 * @Description:
	 * @param str
	 * @return
	 */
	public static boolean isParamEmpty(String str) {
		return StringUtils.isEmpty(str);
	}

	/**
	 * 判断是否为空
	 * 
	 * @Description:
	 * @param str
	 *            0也为空
	 * @return
	 */
	public static boolean isParamEmpty(Integer str) {
		if (null == str || 0 == str) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isParamEmpty(Date str) {
		if (null == str || str.toString().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否为空 对0特殊判断
	 * 
	 * @Description:
	 * @param str
	 * 
	 * @param zeroIsEmpty
	 *            是否为空(认为0为空就传true否则false)
	 * @return
	 */
	public static boolean isParamEmpty(Integer str, boolean zeroIsEmpty) {
		if (null == str) {
			return true;
		} else {
			if (zeroIsEmpty && 0 == str) {
				return true;
			} else {
				return false;
			}
		}
	}

	/**
	 * 判断是否为 null 或 空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String isEmpty(Object str) {

		if (str == null) {
			return "";
		}

		if (StringUtils.isEmpty(str.toString())) {
			return "";
		}
		return str.toString();
	}

	/**
	 * 判断 str 是否可以转换为 数字 false 不可以; true 可以
	 */
	public static boolean isNumeric(String str) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}

		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 判断 str 是否可以转换为double
	 * 
	 * @param str
	 * @return true可以 ; false不可以
	 */
	public static boolean isDoubleStr(String str) {
		boolean ret = true;
		try {
			double d = Double.parseDouble(str);
			ret = true;
		} catch (Exception ex) {
			ret = false;
		}
		return ret;
	}

	// 生成类名
	public static String formatColumName(String columName) {
		String modelName = "";
		if (columName.indexOf("_") > 0) {
			modelName = columName.substring(0, columName.indexOf("_"));
			columName = columName.substring(columName.indexOf("_") + 1, columName.length());
			columName = capitalized(columName);
			if (columName.indexOf("_") > 0) {
				modelName += formatColumName(columName);
			} else {
				modelName += columName;
			}
		} else {
			modelName = columName;
		}
		return modelName;
	}

	// 首字母大写
	public static String capitalized(String s) {
		String one = s.substring(0, 1);
		return one.toUpperCase() + s.substring(1, s.length());
	}

	/**
	 * 系统生成编号
	 * 
	 * @param preStr
	 *            前缀
	 * @param number
	 *            当前数字编号
	 * @return
	 */
	public static String generateNumber(String preStr, int number) {
		StringBuilder result = new StringBuilder();
		result.append(preStr + "-");
		int seq = BigInteger.valueOf(Long.valueOf(TimeUtils.convertDate2Str(new Date(), "yyyyMMddHHmmss"))).intValue()
				+ number;
		String temp = seq + "";
		for (int i = 0; i < 10 - temp.length(); i++) {
			result.append("0");
		}
		return result.append(temp).toString();
	}

	/**
	 * 
	 * @Description:
	 * @param preStr
	 *            前缀
	 * @param length
	 *            数字长度总长度
	 * @param number
	 * @return
	 */
	public static String serviceRecordNumber(String preStr, int length, int number) {
		StringBuilder result = new StringBuilder();
		result.append(preStr + "-");
		String temp = number + "";
		for (int i = 0; i < length - temp.length(); i++) {
			result.append("0");
		}
		return result.append(temp).toString();
	}

	/**
	 * @param urlAll
	 *            :请求接口
	 * @param httpArg
	 *            :参数
	 * @return 返回结果:工作日对应结果为 0, 休息日对应结果为 1, 节假日对应的结果为 2
	 */
	public static String isHoliDay(Date date) {
		String dateStr = TimeUtils.convertDate2Str(date, TimeUtils.YYYYMMDD_PATTERN);
		String httpUrl = "http://apis.baidu.com/xiaogg/holiday/holiday";
		String httpArg = "d=" + dateStr;
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" + httpArg;

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey", "a84b751bde299b0bcf0f71d715539d3b");
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("");
			}
			reader.close();
			result = sbf.toString().trim();
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
			result = "100";
		}
		return result;
	}

	/**
	 * 自动补充
	 * 
	 * @param digit
	 *            自动补充的位数
	 * @param num
	 *            待补充的数字
	 * @return
	 */
	public static String automaticZero(int digit, int num) {

		NumberFormat format = NumberFormat.getInstance();
		format.setMinimumIntegerDigits(digit);
		String result = format.format(num).replace(",", "");
		return result;

	}

	/**
	 * 根据指定日期获取账单日
	 * 
	 * @param date
	 * @return
	 */
	public static Date getBillDate(Date date) {
		Date firstDayOfMonth = TimeUtils.getFirstDayOfMonth(date);// 当月所在的第一天
		int month = TimeUtils.getMonthByDate(date);// 获取当月值
		if (month == 2) { // 2月
			/*
			 * int year = TimeUtils.getYearByDate(date); if
			 * (TimeUtils.isLeapYear(year)) {// 如果是闰年 if
			 * (TimeUtils.getDayByDate(date) == 29) {// 如果是29号 return date;//
			 * 把当前时间返回处理 } return TimeUtils.dateAdd(firstDayOfMonth, 28); } else
			 * { if (TimeUtils.getDayByDate(date) == 28) { return date;//
			 * 把当前时间返回处理 } return TimeUtils.dateAdd(firstDayOfMonth, 27); }
			 */
			return TimeUtils.dateAdd(firstDayOfMonth, 27);
		}
		if (TimeUtils.getDayByDate(date) == 31) {// 如果是31号
			Date nextMonth = TimeUtils.monthAdd(date, 1);//
			Date nextMonthfirstDay = TimeUtils.getFirstDayOfMonth(nextMonth);// 当月所在的第一天
			return TimeUtils.dateAdd(nextMonthfirstDay, 29);
		}

		return TimeUtils.dateAdd(firstDayOfMonth, 29);
	}

	/**
	 * 根据指定日期判断上月最后一天是否29号或31号
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isPlusOneDay(Date date) {

		boolean isPlus = false;
		Date lastMonth = TimeUtils.monthAdd(date, -1);
		Date lastDay = TimeUtils.getLastDayOfMonth(lastMonth);
		int check = TimeUtils.getDayByDate(lastDay);
		if (check == 29 || check == 31) {
			isPlus = true;
		}
		return isPlus;
	}

	public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
			"o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8",
			"9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
			"U", "V", "W", "X", "Y", "Z" };

	/**
	 * 生成8位唯一编号
	 * 
	 * @return
	 */
	public static String generateShortUUID() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x3E]);
		}
		return shortBuffer.toString();

	}

	private static Date date = new Date();
	private static StringBuilder buf = new StringBuilder();
	private static int seq = 0;
	private static final int ROTATION = 99999;

	/**
	 * 根据时间生成唯一编号
	 * 
	 * @return
	 */
	public static synchronized long next() {
		if (seq > ROTATION)
			seq = 0;
		buf.delete(0, buf.length());
		date.setTime(System.currentTimeMillis());
		String str = String.format("%1$tY%1$tm%1$td%1$tk%1$tM%1$tS%2$05d", date, seq++);
		return Long.parseLong(str);
	}

	/**
	 * 去除中括号,替换所有空格
	 * 
	 * @param str
	 * @return
	 */
	public static String filterBracket(String str) {
		return str.toString().replaceAll("[\\[\\]]", "").replaceAll(" ", "");
	}

	/**
	 * 计算计划投资日期(计息日) 排除节假日和工作日以及账单日
	 * 
	 * @param planInvestmentTime
	 * @return
	 */
	public static Date calculatePlanInvestmentTime(Date planInvestmentTime) {
		String isHoliDay = ToolsUtils.isHoliDay(planInvestmentTime);
		// 计算计划投资日期（计划投资日期只能为工作日）
		if (!"0".equals(isHoliDay) && !"100".equals(isHoliDay)) {
			// 只让循环8次
			for (int i = 1; i < 8; i++) {
				planInvestmentTime = TimeUtils.dateAdd(planInvestmentTime, 1);
				isHoliDay = ToolsUtils.isHoliDay(planInvestmentTime);
				// 100表示出错了
				if ("100".equals(isHoliDay)) {
					break;
				} else if ("0".equals(isHoliDay)) {
					break;
				}
			}
		}
		if ((TimeUtils.getMonthByDate(planInvestmentTime) == 2 && TimeUtils.getDayByDate(planInvestmentTime) == 28)
				|| TimeUtils.getDayByDate(planInvestmentTime) == 30) {
			planInvestmentTime = calculatePlanInvestmentTime(TimeUtils.dateAdd(planInvestmentTime, 1));
		}
		return planInvestmentTime;
	}

	/**
	 * 四舍五入
	 * 
	 * @param money
	 * @return
	 */
	public static String round(double money) {
		// 每月收益金额四舍五入
		double perNetEarning = Double.valueOf(money);
		DecimalFormat df = new DecimalFormat("#.00");
		return String.valueOf(df.format(perNetEarning));
	}

	public static void delDir(String path) {
		File dir = new File(path);
		if (dir.exists()) {
			File[] tmp = dir.listFiles();
			for (int i = 0; i < tmp.length; i++) {
				if (tmp[i].isDirectory()) {
					delDir(path + "/" + tmp[i].getName());
				} else {
					System.out.println(dir.getName());
					tmp[i].delete();
				}
			}

			System.out.println(dir.getName());
			dir.delete();
		}
	}

	public static Boolean sendValidCode(String mobile, String templateCode, String number) throws ClientException {

		// 更换绑定手机 SMS_123799486
		// 修改密码验证码 SMS_123669564
		// 注册时的短信验证码 SMS_123739325
		// 短信快捷登陆 SMS_123799484

		Boolean sendFlag = false;
		// 设置超时时间-可自行调整
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		// 初始化ascClient需要的几个参数
		final String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
		final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）
		// 替换成你的AK
		final String accessKeyId = "LTAI7pcobHz7D7cC";// 你的accessKeyId,参考本文档步骤2
		final String accessKeySecret = "YSVuz1W571qcxY0EPOLWJzA4JJK08N";// 你的accessKeySecret，参考本文档步骤2
		// 初始化ascClient,暂时不支持多region
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);
		// 组装请求对象
		SendSmsRequest request = new SendSmsRequest();
		// 使用post提交
		request.setMethod(MethodType.POST);
		// 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
		request.setPhoneNumbers(mobile);
		// 必填:短信签名-可在短信控制台中找到
		request.setSignName("智谷科技工作室");
		// 必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(templateCode);
		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		// 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
		// ${number}
		request.setTemplateParam("{\"code\":\"" + number + "\"}");
		// 可选-上行短信扩展码(无特殊需求用户请忽略此字段)
		// request.setSmsUpExtendCode("90997");
		// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		request.setOutId("yourOutId");
		// 请求失败这里会抛ClientException异常
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
			// 请求成功
			sendFlag = true;
		}
		System.out.println(sendSmsResponse.getMessage());
		return sendFlag;
	}

	public static String getGongWangIP() {
		String ipAddress = "";
		try {
			String url = "http://www.net.cn/static/customercare/yourip.asp";
			// String reqData = HttpClientUtil.httpGetRequest(url);
			// System.out.println(reqData);
			Document doc = Jsoup.connect(url).timeout(10000).get();
			String center = doc.select("CENTER").text();
			System.out.println("===============================");
			System.out.println(center);
			ipAddress = center.substring(center.indexOf("： ") + 1, center.length()).trim();
			System.out.println(ipAddress);
			System.out.println("===============================");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ipAddress;
	}

	public static void main(String[] args) {
		System.out.println(datetimeStr());
	}

}
