package com.cmbccd.ulms.sys.service;

import com.cmbccd.ulms.sys.domain.User;

/** 
* @Author WeiGenSheng
* @Time 2020年4月25日 下午12:38:05 
* @Version 1.0
* Description:
*/
public interface UserService {
	User login(User user);
	User selectByCzyCode(String czyCode);
	int updatePasswordByCzyCode(User user);
	User getWithMenuList(String czyCode);
}
