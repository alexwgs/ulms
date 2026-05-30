package com.cmbccd.ulms.edu.service;

import com.cmbccd.ulms.edu.domain.DisputeQuestion;
import com.cmbccd.ulms.edu.domain.QuesDispute;
import com.cmbccd.ulms.edu.domain.QuesDisputeExample;

import java.util.List;

public interface QuesDisputeService {

	int create(QuesDispute record);
	
	int update(QuesDispute record);
	
	List<QuesDispute> list(QuesDisputeExample example);
	
	List<QuesDispute> list(String examCode, String userId);
	
	QuesDispute get(String quesCode, String userId);
	
	List<DisputeQuestion> listDisputeQuestion(QuesDisputeExample example);
	
	int update(Short disputeResult,QuesDisputeExample example);
}
