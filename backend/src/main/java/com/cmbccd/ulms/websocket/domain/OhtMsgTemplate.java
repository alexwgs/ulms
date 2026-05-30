package com.cmbccd.ulms.websocket.domain;

public class OhtMsgTemplate {
//	消息发送方向 1-对方 2-自己
	private Integer direction;
//	消息类型 1-文本 2-图片 预留3.4.5， 10-系统
	private Integer type;
	private String content;
	private String sendName;
	private String revName;
	private String sendId;
	private String revId;
	private String ctime;
	private String avatar;
	public Integer getDirection() {
		return direction;
	}
	public void setDirection(Integer direction) {
		this.direction = direction;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	public String getRevName() {
		return revName;
	}
	public void setRevName(String revName) {
		this.revName = revName;
	}
	public String getSendId() {
		return sendId;
	}
	public void setSendId(String sendId) {
		this.sendId = sendId;
	}
	public String getRevId() {
		return revId;
	}
	public void setRevId(String revId) {
		this.revId = revId;
	}
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	@Override
	public String toString() {
		return "OhtMsgTemplate [direction=" + direction + ", type=" + type + ", content=" + content + ", sendName="
				+ sendName + ", revName=" + revName + ", sendId=" + sendId + ", revId=" + revId + ", ctime=" + ctime
				+ ", avatar=" + avatar + "]";
	}

}
