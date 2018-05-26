package com.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author
 *
 */
public class Danzi implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private Integer id;

	// 需求描述
	private String des;

	// 客户ID
	private Integer kehuId;

	// 来源
	private String laiyuan;

	// 意向强弱{"1":"强","2":"一般","3":"弱"}
	private String yixiangStatus;

	public static String YIXIANGSTATUS_QIANG = "1";
	public static String YIXIANGSTATUS_YIBAN = "2";
	public static String YIXIANGSTATUS_RUO = "3";

	// 单子状态{"1":"验证客户","2":"需求确定","3":"方案报价","4":"谈判审核","5":"赢单","6":"输单","7":"托管","8":"正在开发","9":"开发完成","10":"改bug","11":"付款"}
	private String danziStatus;

	public static String DANZISTATUS_YANZHENGKEHU = "1";
	public static String DANZISTATUS_XUQIUQUEDING = "2";
	public static String DANZISTATUS_FANGANBAOJIA = "3";
	public static String DANZISTATUS_TANPANSHENHE = "4";
	public static String DANZISTATUS_YINGDAN = "5";
	public static String DANZISTATUS_SHUDAN = "6";
	public static String DANZISTATUS_TUOGUAN = "7";
	public static String DANZISTATUS_ZHENGZAIKAIFA = "8";
	public static String DANZISTATUS_KAIFAWANCHENG = "9";
	public static String DANZISTATUS_GAIBUG = "10";
	public static String DANZISTATUS_FUKUAN = "11";

	// 工作开始时间
	private Date startTime;

	// 工作结束时间
	private Date endTime;

	// 报价
	private String baojiaMoney;

	// 成交金额
	private String chengjiaoMoney;

	// 付款时间
	private Date fukuanTime;

	// 实际工作结束时间
	private Date shijiEndTime;

	// 创建时间
	private Date createTime;

	private String kehuName;

	public String getKehuName() {
		return kehuName;
	}

	public void setKehuName(String kehuName) {
		this.kehuName = kehuName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public Integer getKehuId() {
		return kehuId;
	}

	public void setKehuId(Integer kehuId) {
		this.kehuId = kehuId;
	}

	public String getLaiyuan() {
		return laiyuan;
	}

	public void setLaiyuan(String laiyuan) {
		this.laiyuan = laiyuan;
	}

	public String getYixiangStatus() {
		return yixiangStatus;
	}

	public void setYixiangStatus(String yixiangStatus) {
		this.yixiangStatus = yixiangStatus;
	}

	public String getDanziStatus() {
		return danziStatus;
	}

	public void setDanziStatus(String danziStatus) {
		this.danziStatus = danziStatus;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getBaojiaMoney() {
		return baojiaMoney;
	}

	public void setBaojiaMoney(String baojiaMoney) {
		this.baojiaMoney = baojiaMoney;
	}

	public String getChengjiaoMoney() {
		return chengjiaoMoney;
	}

	public void setChengjiaoMoney(String chengjiaoMoney) {
		this.chengjiaoMoney = chengjiaoMoney;
	}

	public Date getFukuanTime() {
		return fukuanTime;
	}

	public void setFukuanTime(Date fukuanTime) {
		this.fukuanTime = fukuanTime;
	}

	public Date getShijiEndTime() {
		return shijiEndTime;
	}

	public void setShijiEndTime(Date shijiEndTime) {
		this.shijiEndTime = shijiEndTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}