package com.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author
 *
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 1L;

	// id
	private Integer id;

	// 标题
	private String title;

	// 内容
	private String content;

	// 对话编号
	private String dialogNo;

	// 类型（1：促销，2：公告，3：留言，4：私信，5：邀请，6：交易报价,7:系统通知）
	private Integer type;
	public static final int TYPE_CUXIAO = 1;
	public static final int TYPE_GONGGAO = 2;
	public static final int TYPE_LIUYAN = 3;
	public static final int TYPE_SIXIN = 4;
	public static final int TYPE_YAOQING = 5;
	public static final int TYPE_BAOJIA = 6;
	public static final int TYPE_SYS_TONGZHI = 7;

	// 发送人ID
	private Integer sendId;

	// 接收人ID
	private Integer receivedId;

	// 状态（1：未读，2：已读）
	private Integer status;
	public static final int STATUS_NO_READ = 1;
	public static final int STATUS_YES_READ = 2;

	// 创建时间
	private Date createTime;

	// 发送消息用户
	private User user;

	// 接收消息用户
	private User receivedUser;

	// 是否是系统通知
	private String isHaoyouMessage;
	public static final String HAO_YOU_YES = "1";
	public static final String HAO_YOU_NO = "2";

	public String getDialogNo() {
		return dialogNo;
	}

	public void setDialogNo(String dialogNo) {
		this.dialogNo = dialogNo;
	}

	public String getIsHaoyouMessage() {
		return isHaoyouMessage;
	}

	public void setIsHaoyouMessage(String isHaoyouMessage) {
		this.isHaoyouMessage = isHaoyouMessage;
	}

	public User getReceivedUser() {
		return receivedUser;
	}

	public void setReceivedUser(User receivedUser) {
		this.receivedUser = receivedUser;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSendId() {
		return sendId;
	}

	public void setSendId(Integer sendId) {
		this.sendId = sendId;
	}

	public Integer getReceivedId() {
		return receivedId;
	}

	public void setReceivedId(Integer receivedId) {
		this.receivedId = receivedId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}