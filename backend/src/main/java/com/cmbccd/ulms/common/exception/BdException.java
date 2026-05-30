package com.cmbccd.ulms.common.exception;

/**
 * 
* <p>Title: BDException.java</p>  
* <p>Description: 自定义异常处理</p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
public class BdException extends RuntimeException {
	private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;
    
    public BdException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public BdException(String msg, Throwable e) {
		super(msg, e);
		this.msg = msg;
	}
	
	public BdException(String msg, int code) {
		super(msg);
		this.msg = msg;
		this.code = code;
	}
	
	public BdException(String msg, int code, Throwable e) {
		super(msg, e);
		this.msg = msg;
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	
}
