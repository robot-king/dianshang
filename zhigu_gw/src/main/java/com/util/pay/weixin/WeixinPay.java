package com.util.pay.weixin;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.util.StringUtil;
import com.util.pay.weixin.tixian.GetRSA;
import com.util.pay.weixin.tixian.HttpClientCustomSSL;
import com.util.pay.weixin.tixian.SignUtils;
import com.util.pay.weixin.tixian.XMLUtils;

public class WeixinPay {

	private static final Logger logger = Logger.getLogger(WeixinPay.class);

	/**
	 * 统一下单
	 * 
	 * @param order_price/价格注意：价格的单位是分
	 * @param body/商品名称
	 * @param out_trade_no/订单号
	 * @param trade_type/JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付、MWEB--H5支付
	 * @throws Exception
	 */
	public static Map WeixinPay(String order_price, String body, String out_trade_no, String trade_type, String openid,
			String gongWangIp) throws Exception {
		// 账号信息
		String appid = PayConfigUtil.APP_ID; // appid
		// String appsecret = PayConfigUtil.APP_SECRET; // appsecret
		String mch_id = PayConfigUtil.MCH_ID; // 商业号
		String key = PayConfigUtil.API_KEY; // key

		String currTime = PayCommonUtil.getCurrTime();
		String strTime = currTime.substring(8, currTime.length());
		String strRandom = PayCommonUtil.buildRandom(4) + "";
		String nonce_str = strTime + strRandom;

		// 获取发起电脑 ip
		String spbill_create_ip = PayConfigUtil.CREATE_IP;
		if (trade_type.equals("MWEB")) {
			spbill_create_ip = gongWangIp;
			logger.info(">>>>>>>>>>>>>>>>>>>>H5支付-获取用户ip:" + spbill_create_ip);
		}
		logger.info(">>>>>>>>>>>>>>>>>>>>获取用户ip:" + spbill_create_ip);
		// 回调接口
		String notify_url = PayConfigUtil.NOTIFY_URL;
		// String trade_type = "NATIVE";

		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("body", body);
		packageParams.put("out_trade_no", out_trade_no);
		packageParams.put("total_fee", order_price);
		packageParams.put("spbill_create_ip", spbill_create_ip);
		packageParams.put("notify_url", notify_url);
		packageParams.put("trade_type", trade_type);
		if (trade_type.equals("JSAPI")) {
			packageParams.put("openid", openid);
		}

		String sign = PayCommonUtil.createSign("UTF-8", packageParams, key);
		packageParams.put("sign", sign);

		String requestXML = PayCommonUtil.getRequestXml(packageParams);
		System.out.println(requestXML);

		String resXml = HttpUtil.postData(PayConfigUtil.UFDODER_URL, requestXML);
		System.out.println(resXml);

		Map map = XMLUtil.doXMLParse(resXml);
		// String return_code = (String) map.get("return_code");
		// String prepay_id = (String) map.get("prepay_id");
		// String urlCode = (String) map.get("code_url");

		return map;
	}

	// 支付回调
	public void weixin_notify(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// 读取参数
		InputStream inputStream;
		StringBuffer sb = new StringBuffer();
		inputStream = request.getInputStream();
		String s;
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		while ((s = in.readLine()) != null) {
			sb.append(s);
		}
		in.close();
		inputStream.close();

		// 解析xml成map
		Map<String, String> m = new HashMap<String, String>();
		m = XMLUtil.doXMLParse(sb.toString());

		// 过滤空 设置 TreeMap
		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
		Iterator it = m.keySet().iterator();
		while (it.hasNext()) {
			String parameter = (String) it.next();
			String parameterValue = m.get(parameter);

			String v = "";
			if (null != parameterValue) {
				v = parameterValue.trim();
			}
			packageParams.put(parameter, v);
		}

		// 账号信息
		String key = PayConfigUtil.API_KEY; // key

		logger.info(packageParams);
		// 判断签名是否正确
		if (PayCommonUtil.isTenpaySign("UTF-8", packageParams, key)) {
			// ------------------------------
			// 处理业务开始
			// ------------------------------
			String resXml = "";
			if ("SUCCESS".equals((String) packageParams.get("result_code"))) {
				// 这里是支付成功
				////////// 执行自己的业务逻辑////////////////
				String mch_id = (String) packageParams.get("mch_id");
				String openid = (String) packageParams.get("openid");
				String is_subscribe = (String) packageParams.get("is_subscribe");
				String out_trade_no = (String) packageParams.get("out_trade_no");

				String total_fee = (String) packageParams.get("total_fee");

				logger.info("mch_id:" + mch_id);
				logger.info("openid:" + openid);
				logger.info("is_subscribe:" + is_subscribe);
				logger.info("out_trade_no:" + out_trade_no);
				logger.info("total_fee:" + total_fee);

				////////// 执行自己的业务逻辑////////////////

				logger.info("支付成功");
				// 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
				resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
						+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

			} else {
				logger.info("支付失败,错误信息：" + packageParams.get("err_code"));
				resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
						+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
			}
			// ------------------------------
			// 处理业务完毕
			// ------------------------------
			BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
			out.write(resXml.getBytes());
			out.flush();
			out.close();
		} else {
			logger.info("通知签名验证失败");
		}
	}

	// 生成时间戳
	public static String createTimestamp() {
		return Long.toString(System.currentTimeMillis() / 1000);
	}

	/**
	 * 提现到银行卡
	 * 
	 * @param partner_trade_no/商户企业付款单号
	 * @param enc_bank_no/收款方银行卡号
	 * @param enc_true_name/收款方用户名
	 * @param bank_code/收款方开户行
	 * @param amount/付款金额
	 * @param desc/付款说明
	 * @return
	 * @throws Exception
	 */
	public static Map tixian(String partner_trade_no, String enc_bank_no, String enc_true_name, String bank_code,
			String amount, String desc) throws Exception {

		String mch_id = PayConfigUtil.MCH_ID;
		String key = PayConfigUtil.API_KEY;
		// enc_true_name = getUTF8XMLString(enc_true_name);
		// 注意：持卡人姓名微信付款到银行卡接口只接收UTF-8格式，编辑器与编码有关的设置都要设置成UTF-8，否则无法完成付款
		System.out.println("=========================================");
		System.out.println("编码格式:" + StringUtil.getStringEncode(enc_true_name));
		System.out.println("=========================================");
		System.out.println(enc_true_name);

		enc_true_name = GetRSA.getRSA(enc_true_name, PayConfigUtil.PUBLIC_KEY_PKCS8);
		enc_bank_no = GetRSA.getRSA(enc_bank_no, PayConfigUtil.PUBLIC_KEY_PKCS8);
		String nonce_str = StringUtil.getStrRandom(28);

		// 获取签名
		SortedMap<Object, Object> parameters1 = new TreeMap<Object, Object>();
		parameters1.put("mch_id", mch_id);
		parameters1.put("partner_trade_no", partner_trade_no);
		parameters1.put("nonce_str", nonce_str);
		parameters1.put("enc_bank_no", enc_bank_no);
		parameters1.put("enc_true_name", enc_true_name);
		parameters1.put("bank_code", bank_code);
		parameters1.put("amount", amount);
		parameters1.put("desc", desc);

		String sign = SignUtils.createSign(PayConfigUtil.CHARSET, parameters1);
		parameters1.put("sign", sign);
		String requestXML = XMLUtils.getRequestXml(parameters1);
		System.out.println(requestXML);

		// 请求付款
		String resXml = HttpClientCustomSSL.httpClientResultPANK(requestXML);
		System.out.println(resXml);

		Map map = XMLUtil.doXMLParse(resXml);

		return map;
	}

	/***
	 * 提现到零钱
	 * 
	 * @param partner_trade_no/商户单号
	 * @param amount/金额
	 * @param desc/描述
	 * @param openid/用户微信标识
	 * @return
	 * @throws Exception
	 */
	public static Map tixianToWeiXinLingQian(String partner_trade_no, String amount, String desc, String openid)
			throws Exception {

		String mch_id = PayConfigUtil.MCH_ID;
		String app_id = PayConfigUtil.APP_ID;
		System.out.println("=========================================");
		System.out.println("=========================================");

		String nonce_str = StringUtil.getStrRandom(28);

		// 获取签名
		SortedMap<Object, Object> parameters1 = new TreeMap<Object, Object>();
		parameters1.put("mch_appid", app_id);
		parameters1.put("mchid", mch_id);
		parameters1.put("nonce_str", nonce_str);
		parameters1.put("partner_trade_no", partner_trade_no);
		parameters1.put("openid", openid);
		parameters1.put("check_name", "NO_CHECK");
		parameters1.put("re_user_name", "");
		parameters1.put("amount", amount);
		parameters1.put("desc", desc);
		parameters1.put("spbill_create_ip", PayConfigUtil.CREATE_IP);

		String sign = SignUtils.createSign(PayConfigUtil.CHARSET, parameters1);
		parameters1.put("sign", sign);
		String requestXML = XMLUtils.getRequestXml(parameters1);
		System.out.println(requestXML);

		// 请求付款
		String resXml = HttpClientCustomSSL.httpClientResultWeiXinLingQian(requestXML);
		System.out.println(resXml);

		Map map = XMLUtil.doXMLParse(resXml);

		return map;
	}

	public static String gb2312ToUtf8(String str) {
		String urlEncode = "";
		try {
			urlEncode = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return urlEncode;
	}

	public static void main(String[] args) {
		// System.out.println(PayCommonUtil.createSign("UTF-8", packageParams,
		// key));
		// String currTime = PayCommonUtil.getCurrTime();
		// String strTime = currTime.substring(8, currTime.length());
		// String strRandom = PayCommonUtil.buildRandom(4) + "";
		// String nonce_str = strTime + strRandom;
		// System.out.println(nonce_str);

		String tempStr = "中文";// 准备转换的字符
		try {
			System.out.println(tempStr + ":" + StringUtil.getStringEncode(tempStr));
			String result = new String(tempStr.getBytes(), "UTF-8");
			System.out.println(result + ":" + StringUtil.getStringEncode(result));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 转换后的结果
	}
}
