package com.cmbccd.ulms.sys.domain;

import com.cmbccd.ulms.common.util.Util;

public class ErrorLog {
	private Integer id;

	private String dataTime;

	private String userId;

	private String priority;

	private String dataType;

	private String info;

	private String content;

	public static ErrorLog info(String dataType, String info, String content) {
		ErrorLog result = new ErrorLog();
		result.setPriority("info");
		result.setDataType(dataType);
		result.setInfo(info);
		if (content.length() >= 1000) {
			content = content.substring(0, 1000);
		}
		result.setContent(content);
		return result;
	}

	public static ErrorLog error(String dataType, String info, String content) {
		ErrorLog result = new ErrorLog();
		result.setPriority("error");
		result.setDataType(dataType);
		result.setInfo(info);
		if (!Util.isNullorEmpty(content) && content.length() >= 1000) {
			content = content.substring(0, 1000);
		}
		result.setContent(content);
		return result;
	}

	public static ErrorLog warning(String dataType, String info, String content) {
		ErrorLog result = new ErrorLog();
		result.setPriority("warning");
		result.setDataType(dataType);
		result.setInfo(info);
		if (!Util.isNullorEmpty(content) && content.length() >= 1000) {
			content = content.substring(0, 1000);
		}
		result.setContent(content);
		return result;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDataTime() {
		return dataTime;
	}

	public void setDataTime(String dataTime) {
		this.dataTime = dataTime == null ? null : dataTime.trim();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority == null ? null : priority.trim();
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType == null ? null : dataType.trim();
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info == null ? null : info.trim();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}
}