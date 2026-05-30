package com.cmbccd.ulms.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 
* <p>Title: UlmsConfig.java</p>  
* <p>Description: 用户文件管理配置</p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
@Component
@ConfigurationProperties(prefix="ulms")
public class UlmsConfig {

	private String uploadPath;

	private String username;

	private String password;
	
	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
