/**
 * 
 */
package com.cmbccd.ulms.oht.service;

import com.cmbccd.ulms.oht.domain.QuickMemo;
import com.cmbccd.ulms.oht.domain.QuickMemoExample;

import java.util.List;

/** 
* @Author WeiGenSheng
* @Time 2020年5月14日 下午3:33:40 
* @Version 1.0
* @Description:
*/
/** 
* @Description: （对类进行功能描述）
* @date: datedate{time} （日期）
*/
public interface QuickMemoService {
	
	List<QuickMemo> getQuickMemoList (QuickMemoExample example);
	//前端使用
	List<QuickMemo> getQuickMemoByRoleType(String roleType);
	
	int addQuickMemo(QuickMemo record);
	
	int updateQuickMemoByJourno(QuickMemo record);
	
	int deleteQuickMemoByJourno(String journo);
	
	String selectNewJourno();
	

}
