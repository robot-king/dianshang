package com.util;

public class Constant {

	public class DataStatus {
		public static final int SUCCESS_CODE = 2000;
		public static final int FAIL_CODE = 2001;
		public static final String SUCCESS_MSG = "成功";
		public static final String FAIL_MSG = "失败";
	}

	// 登录类型（1：用户名，2：手机，3：邮箱，4：微博，5：QQ，6：微信）
	public class IdentityType {
		public static final int NICK_NAME = 1;
		public static final int MOBILE = 2;
		public static final int EMAIL = 3;
		public static final int WEI_BO = 4;
		public static final int QQ = 5;
		public static final int WE_CHAT = 6;
	}

	// 默认密码
	public static final String PWD = "123456";

	// 收藏或下载类型
	public class CollectType {
		public static final int COLLECT = 1;
		public static final int DOWNLOAD = 2;
		public static final int READ_YES = 1;
		public static final int READ_NO = 2;
	}

	// 验证码类型
	public class ValidCodeType {
		public static final int MOBILE = 1;
		public static final int EMAIL = 2;
		public static final int YES = 1;// 有效
		public static final int NO = 2;// 无效
	}

	// 文章级别（1：一般，2：好，3：很好，4：非常好）
	public class ArticleLevel {
		public static final int ONE = 1;
		public static final int TWO = 2;
		public static final int THREE = 3;
		public static final int FOUR = 4;
	}

	// 禁用,否
	public static final int OFF = 2;
	// 启用,是
	public static final int ON = 1;
	// 删除
	public static final int DELETE = 3;

	// 登录是否有效
	public class LoginStatus {
		// 无效
		public static final int OFF = 2;
		// 有效
		public static final int ON = 1;
	}

	// 是否关注
	public class CareStatus {
		// 否
		public static final int NO = 2;
		// 是
		public static final int YES = 1;
	}

	// 积分行为分数
	public class JiFenXingWei {
		public static final int QIANDAO = 10;
	}

	// public static boolean DEBUY_MONEY = true;// 为true时，支付金额按1分钱计算
	public static boolean DEBUY_MONEY = false;// 为false时，支付金额按实际金额计算

	public static String WEB_NAME = "智谷科技工作室";// 网站名称

}
