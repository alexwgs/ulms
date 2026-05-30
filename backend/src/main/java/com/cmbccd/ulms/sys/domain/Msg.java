package com.cmbccd.ulms.sys.domain;

import com.cmbccd.ulms.common.util.Util;

import java.util.HashMap;

/** 
* @Author WeiGenSheng
* @Time 2020年4月18日 下午7:31:57 
* @Version 1.0
* Description:
*/
public class Msg extends HashMap<String, Object>{
//	private Integer code;
//	private String msg;
//	private Map<String, Object> data = new HashMap<String, Object>();

	/** 状态码 */
	public static final String CODE_TAG = "code";

	/** 返回内容 */
	public static final String MSG_TAG = "msg";

	/** 数据对象 */
	public static final String DATA_TAG = "data";
	/**
	 * 状态类型
	 */
	public enum Type
	{
		/** 成功 */
		SUCCESS(200),
		/** 警告 */
		WARN(301),
		/** 错误 */
		ERROR(500);
		private final int value;

		Type(int value)
		{
			this.value = value;
		}

		public int value()
		{
			return this.value;
		}
	}

	public Msg()
	{
	}
	/**
	 * 初始化一个新创建的 Msg 对象
	 *
	 * @param type 状态类型
	 * @param msg 返回内容
	 */
	public Msg(Type type, String msg)
	{
		super.put(CODE_TAG, type.value);
		super.put(MSG_TAG, msg);
	}

	public Msg(Integer code, String msg)
	{
		super.put(CODE_TAG, code);
		super.put(MSG_TAG, msg);
	}
	/**
	 * 初始化一个新创建的 Msg 对象
	 *
	 * @param type 状态类型
	 * @param msg 返回内容
	 * @param data 数据对象
	 */
	public Msg(Type type, String msg, Object data)
	{
		super.put(CODE_TAG, type.value);
		super.put(MSG_TAG, msg);
		if (!Util.isNullorEmpty(data))
		{
			super.put(DATA_TAG, data);
		}
	}
	/**
	 * 方便链式调用
	 *
	 * @param key 键
	 * @param value 值
	 * @return 数据对象
	 */
	@Override
	public Msg put(String key, Object value)
	{
		super.put(key, value);
		return this;
	}

	/**
	 * 返回成功消息
	 *
	 * @return 成功消息
	 */
	public static Msg success()
	{
		return Msg.success("操作成功");
	}
	/**
	 * 返回成功数据
	 *
	 * @return 成功消息
	 */
	public static Msg success(Object data)
	{
		return Msg.success("操作成功", data);
	}

	/**
	 * 返回成功消息
	 *
	 * @param msg 返回内容
	 * @return 成功消息
	 */
	public static Msg success(String msg)
	{
		return Msg.success(msg, null);
	}

	/**
	 * 返回成功消息
	 *
	 * @param msg 返回内容
	 * @param data 数据对象
	 * @return 成功消息
	 */
	public static Msg success(String msg, Object data)
	{
		return new Msg(Type.SUCCESS, msg, data);
	}

	/**
	 * 返回警告消息
	 *
	 * @param msg 返回内容
	 * @return 警告消息
	 */
	public static Msg warn(String msg)
	{
		return Msg.warn(msg, null);
	}

	/**
	 * 返回警告消息
	 *
	 * @param msg 返回内容
	 * @param data 数据对象
	 * @return 警告消息
	 */
	public static Msg warn(String msg, Object data)
	{
		return new Msg(Type.WARN, msg, data);
	}

	/**
	 * 返回错误消息
	 *
	 * @return
	 */
	public static Msg error()
	{
		return Msg.error("操作失败");
	}

	/**
	 * 返回错误消息
	 *
	 * @param msg 返回内容
	 * @return 警告消息
	 */
	public static Msg error(String msg)
	{
		return Msg.error(msg, null);
	}

	/**
	 * 返回错误消息
	 *
	 * @param msg 返回内容
	 * @param data 数据对象
	 * @return 警告消息
	 */
	public static Msg error(String msg, Object data)
	{
		return new Msg(Type.ERROR, msg, data);
	}





//
//
//	public static Msg success(Object  data) {
//		Msg result = new Msg();
//		result.setCode(200);
//		result.setMsg("操作成功！");
//		return result;
//	}
//
//	public static Msg success(String msg) {
//		Msg result=new Msg();
//		result.setCode(200);
//		result.setMsg(msg);
//		return result;
//	}
//
//	public static Msg error() {
//		Msg result=new Msg();
//		result.setCode(100);
//		result.setMsg("操作失败！");
//		return result;
//	}
//
//	public static Msg error(String msg) {
//		Msg result=new Msg();
//		result.setCode(100);
//		result.setMsg(msg);
//		return result;
//	}
//
//	public static Msg error(Integer code,String msg) {
//		Msg result=new Msg();
//		result.setCode(code);
//		result.setMsg(msg);
//		return result;
//	}
//
//	public Msg add(String key , Object value) {
//		this.getData().put(key,value);
//		return this;
//	}
//
//	public Integer getCode() {
//		return code;
//	}
//	public void setCode(Integer code) {
//		this.code = code;
//	}
//	public String getMsg() {
//		return msg;
//	}
//	public void setMsg(String msg) {
//		this.msg = msg;
//	}
//	public Map<String, Object> getData() {
//		return data;
//	}
//	public void setData(Map<String, Object> data) {
//		this.data = data;
//	}
//
	
}
