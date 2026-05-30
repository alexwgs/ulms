package com.cmbccd.ulms.websocket.domain;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;

public class MsgTemplate {

	 @JSONField(name = "model")
	String model; //模块
	 @JSONField(name = "type")
	String type;  //类型
	 @JSONField(name = "code")
	Integer code; //状态
	 @JSONField(name = "msg")
	String msg;   //状态说明
	 @JSONField(name = "data")
	private Map<String,Object> data = new HashMap<String,Object>();	//附加主要信息
	
	public MsgTemplate add(String key , Object value) {
		this.getData().put(key,value);
		return this;
	}
	
	public static MsgTemplate success(String model,String type,String message) {
		MsgTemplate result=new MsgTemplate();
		result.setModel(model);
		result.setType(type);
		result.setCode(200);
		result.setMsg(message);
		return result;
	}
	public static MsgTemplate success(String model,String type) {
		MsgTemplate result=new MsgTemplate();
		result.setModel(model);
		result.setType(type);
		result.setCode(200);
		result.setMsg("成功！");
		return result;
	}
	
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "MsgTemplate [model=" + model + ", type=" + type + ", code=" + code + ", msg=" + msg + ", data=" + data
				+ "]";
	}
	
	
	
}
