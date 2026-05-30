/**
 * 
 */
package com.cmbccd.ulms.oht.service;

import com.cmbccd.ulms.oht.domain.*;

import java.util.List;

/** 
* @Author WeiGenSheng
* @Time 2020年5月7日 下午4:31:52 
* @Version 1.0
* @Description:
*/
/** 
* @Description: （对类进行功能描述）
* @date: datedate{time} （日期）
*/
public interface CaseService {

	List<Case> getCaseList(CaseExample example);
	
	Case getCaseByCaseId(String caseId);
	
	Case getUnfinishCaseByHelper (String userId);
	
	Case getUnfinishCaseByListener (String userId);
	
	Case insertNewCase(String userId, Integer caseType, String extnNum);
	
	int updateCaseBySelective(Case record);
	
	List<Case> getHelpWaitCase();
	
	int updateDisconnectCaseStatus(String buildId);
	
	List<DirTakeCaseSumarry> getCaseCompleteCountByDir(String begTime, String endTime);
	
	List<DeptTakeCaseHour> getStartCaseCenterCountByDept(String begTime, String endTime);
	
	List<CaseReport> getCaseReport(CaseExample example);
	
}
