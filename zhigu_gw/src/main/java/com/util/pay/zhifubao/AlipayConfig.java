package com.util.pay.zhifubao;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 */

public class AlipayConfig {

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String APP_ID = "2017102209455381";

	// 商户私钥，您的PKCS8格式RSA2私钥
	public static String MERCHANT_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCe1fdWFMv0Ty8SVJvu+A0l5wzv1fkX4ouW+GmLOwFLCRDovmAhhukZiEf7W4Wa01i+/IrX7ZWNL1UTIOwbkZ31hLfdllQcp3pJpixoBFphGD1nnjzzDG8XC9TEKlOR2VjxSndKMMZaemiSPFcCVKcnKu8PeMCIXh3O1lii/yGJaCIX/EpytQMQnyHaY+6CI9NtObBYL/fz2TmtqpZPdUmRQ9fjevH81MFic7afu8h6qsNz8WSZXOniVGFB/Zb8Wu3ByVpBGZ7Kgti8a/UwqcdnooWpwDz6etOyvdyiqUi4lcOZQYz93EFjXqrPsSaFFS6CMoJfmAo+3XyLb9SVgIDDAgMBAAECggEAKRDI0ciusouGcAbQCgi33YTPI3L+493+mRnAr+6hdvTxGjuePwzSV3eUpLgU4uKhnz9L5mfWspn6NOfeI4KShFe78d5cAMyMIV6FPJ9jfpb1yURwHaapUhwM9mq7EIpRJJFMJjJWFi5vut8+36tjTA0dobJmlNtAeamRYRL1aMDJ+Cb1/btrlicNuD6RX+HVoMaMChMj7m+UInJBElgDjzKF7DKyJqURTzm9wFf7vr6p0Vodb2IV1p5Nf9G9unDPAMtToCJwJTiZehf6zQ4SO7NZYRx5XEA7pvznSkALr7pqxqz04CMaiXqOC6nBCJORe5gwHFS9eF3gpUPLYIxzgQKBgQDsayMeEh6zJJ6wuatNx+2R6pioMM3FG/1ngwGEnIpWgVrYiHKb2N0H2O+JGe1LU2PD/lxshptqin/rcYsiXBdEPp9MUtftLdWm8ipbh6YHyijEDTYy5z8IbgUgZw/xIyTsKpQTJxcKzi0Jhz+Gjgr+tsMAsogUZujxNJAaf+eH7QKBgQCr/dD7ebTfP5pexDEcCXQOtLn6m24S6PgQvv2fxWTwDWafpZlnZ4uMRHRE0aAiZnSOkVKCcgH2Y17p4GnKQkAimT+AlTSiwBRvolj5jc5V1AYhB8g/2cv97JmTwStDChuXWvrmLmaIV3lP9xoqcgO/4gNF5goCswoqHQgWXMq1bwKBgCido1PYMxP9t5GO5EIhlcvarM1ATSmOzHMLC6i3qACCipPmpSHnGNnvR/+ax6yIL/yM513xpiOgladiQ0/s2EZuJ7naBaSGmkzrD6HX5QszjNtZlbUbCzYsiI7vhcqcigxUqEm+DnRZxVz8skowfu0RguDMyxoOL/i4WgVjcehlAoGAJ8ZG8iEvRwG6fXf5s3YsNMpuyBnx1sWxuI2ysaRAt5MhzIHLfDJh/0pFaTThR5NSGTZaUnhzI7vPU17mmDPCCT5PhTj8NzccgsSUtwGoWgjycekO0lVX650v1w+xJYvM3a/YnirU42r3TxriHjbHzzRBdYTBg+ZLz3lvSVykNFkCgYEAxCyxr88L6Ft/R/g5+js3XX4leU9WvUPsqsRZ5TQVMxYOzF3OpcufMdJ6Ji+/CL6obAfd+4TKmAVKrz2rjQzjdx6KlK/45VojMN+mExkCMLxcF3C+MPY28tip5tu4quTG/nxJrx7yzznCf3k4+jEy2SOec119NL8yyOe+5vTVyZk=";

	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm
	// 对应APPID下的支付宝公钥。
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5bIyxIx7LvQ567m+CyFfv6IrdCaJlIUo4frRz9FAbu/8LH5yREfJn411nhee6o0zwKcZSHLBqT97bECcJXwOIJgUKRUGYqw9ba2pM5PGlkRSsHRs9qigOMXxEbFXiBF8vzDDJAtJC8taWY37+WvPy6PpXrDEeB6JJjK5+4Et3CW7DVLy7wJQWZuISkjmBHjpKs1hMs1a2asUHWihazvfHRE3zjIHmZvDxTDAf9POc59q3zRE6qn0YC8JJevnsUWWNpB8B9He6rz3N8mG5KZf+S3+C9+KJoB3vfzy6Avs725cgcCsfK15RpBrBHirV4SxqHUsiZOxnvz3Y17vJQX+0wIDAQAB";

	// 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String NOTIFY_URL = "http://www.mozji.com/payOrder/notifyAfterUserAliPay.action";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String RETURN_URL = "http://www.mozji.com/payOrder/pay_alipay_result.action";

	// 签名方式
	public static String SIGN_TYPE = "RSA2";

	// 字符编码格式
	public static String CHARSET = "utf-8";

	// 字符串格式
	public static String FORMAT = "json";

	// 支付宝网关
	public static String GATEWAYURL = "https://openapi.alipay.com/gateway.do";

	// 支付宝网关
	public static String LOG_PATH = "D:\\logs";

	// 宝箱服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String BOX_NOTIFY_URL = "http://www.mozji.com/userBox/notifyAfterUserAliPay.action";

	// 宝箱页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String BOX_RETURN_URL = "http://www.mozji.com/userBox/pay_alipay_result.action";

	// 邮费服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String POSTAGE_NOTIFY_URL = "http://www.mozji.com/inventoryAddress/notifyAfterUserAliPay.action";

	// 邮费页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String POSTAGE_RETURN_URL = "http://www.mozji.com/inventoryAddress/pay_alipay_result.action";

	// ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	/**
	 * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
	 * 
	 * @param sWord
	 *            要写入日志里的文本内容
	 */
	public static void logResult(String sWord) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(LOG_PATH + "alipay_log_" + System.currentTimeMillis() + ".txt");
			writer.write(sWord);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
