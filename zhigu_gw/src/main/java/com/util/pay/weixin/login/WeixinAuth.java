package com.util.pay.weixin.login;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.util.HttpClientUtil;
import com.util.JsonUtils;

/**
 * 公众号h5登录
 * 
 * @author zhangyonwei
 *
 */
public class WeixinAuth {
	// 与接口配置信息中的Token要一致
	private static String token = "weige";
	private static String APPID = "wx0202a5aaee854784";
	private static String APPSECRET = "47c4bc340752a9d82a2805960201ae35";
	private static String REDIRECT_URI = "http://www.mozji.com/userAuth/loginByWeiXin.action?";

	/**
	 * 验证签名
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signature, String timestamp, String nonce) {
		String[] arr = new String[] { token, timestamp, nonce };
		// 将token、timestamp、nonce三个参数进行字典序排序
		// Arrays.sort(arr);
		sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		MessageDigest md = null;
		String tmpStr = null;

		try {
			md = MessageDigest.getInstance("SHA-1");
			// 将三个参数字符串拼接成一个字符串进行sha1加密
			byte[] digest = md.digest(content.toString().getBytes());
			tmpStr = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		content = null;
		// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];
		String s = new String(tempArr);
		return s;
	}

	public static void sort(String a[]) {
		for (int i = 0; i < a.length - 1; i++) {
			for (int j = i + 1; j < a.length; j++) {
				if (a[j].compareTo(a[i]) < 0) {
					String temp = a[i];
					a[i] = a[j];
					a[j] = temp;
				}
			}
		}
	}

	/**
	 * 第一步：用户同意授权，获取code(引导关注者打开如下页面：) 获取 code、state
	 */
	public static String getStartURLToGetCode() {

		String takenUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
		takenUrl = takenUrl.replace("APPID", APPID);
		try {
			takenUrl = takenUrl.replace("REDIRECT_URI", URLEncoder.encode(REDIRECT_URI, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// FIXME ： snsapi_userinfo
		takenUrl = takenUrl.replace("SCOPE", "snsapi_userinfo");
		System.out.println(takenUrl);
		return takenUrl;
	}

	/**
	 * 获取access_token、openid 第二步：通过code获取access_token
	 * 
	 * @param code
	 *            url = "https://api.weixin.qq.com/sns/oauth2/access_token
	 *            ?appid=APPID &secret=SECRET &code=CODE
	 *            &grant_type=authorization_code"
	 */
	public static AccessToken getAccessToken(String code) {

		String authUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		authUrl = authUrl.replace("APPID", APPID);
		authUrl = authUrl.replace("SECRET", APPSECRET);
		authUrl = authUrl.replace("CODE", code);
		String jsonString = HttpClientUtil.httpPostRequest(authUrl);
		System.out.println("jsonString: " + jsonString);
		return (AccessToken) JsonUtils.getObject4JsonString(jsonString, AccessToken.class);
	}

	/**
	 * 第三步：刷新access_token（如果需要） 由于access_token拥有较短的有效期，当access_token超时后，
	 * 可以使用refresh_token进行刷新，refresh_token有效期为30天， 当refresh_token失效之后，需要用户重新授权。
	 * 
	 * @param code
	 *            url =
	 *            "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN
	 *            * "
	 */
	public static AccessToken getRefreshToken(String refreshToken) {

		String authUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
		authUrl = authUrl.replace("APPID", APPID);
		authUrl = authUrl.replace("REFRESH_TOKEN", refreshToken);
		String jsonString = HttpClientUtil.httpPostRequest(authUrl);
		System.out.println("jsonString: " + jsonString);
		return (AccessToken) JsonUtils.getObject4JsonString(jsonString, AccessToken.class);
	}

	/**
	 * 第四步：拉取用户信息(需scope为 snsapi_userinfo)
	 * 如果网页授权作用域为snsapi_userinfo，则此时开发者可以通过access_token和openid拉取用户信息了。
	 * 
	 * @param code
	 *            url =
	 *            "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN"
	 * @throws UnsupportedEncodingException
	 */
	public static SnsApiUserInfo getUserInfo(AccessToken accessToken) throws UnsupportedEncodingException {

		String authUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		authUrl = authUrl.replace("ACCESS_TOKEN", accessToken.getAccess_token());
		authUrl = authUrl.replace("OPENID", accessToken.getOpenid());
		String jsonString = HttpClientUtil.httpGetRequest(authUrl);
		jsonString = new String(jsonString.getBytes("ISO-8859-1"), "UTF-8");
		System.out.println("jsonString: " + jsonString);
		return (SnsApiUserInfo) JsonUtils.getObject4JsonString(jsonString, SnsApiUserInfo.class);
	}

	public static void main(String[] args) {
		getAccessToken(getStartURLToGetCode());
	}
}