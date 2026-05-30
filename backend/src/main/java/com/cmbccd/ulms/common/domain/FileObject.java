package com.cmbccd.ulms.common.domain;

/**
 * 
* <p>Title: FileObject.java</p>  
* <p>Description: 文件对象</p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
public class FileObject {
	String name;
	String path;
	String fileId;
	String fileSuffix;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileSuffix() {
		return fileSuffix;
	}
	public void setFileSuffix(String fileSuffix) {
		this.fileSuffix = fileSuffix;
	}
	
	

}
