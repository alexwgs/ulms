package com.cmbccd.ulms.edu.service;

import com.cmbccd.ulms.edu.domain.QuesBank;
import com.cmbccd.ulms.edu.domain.QuesBankExample;

import java.util.List;
import java.util.Map;

public interface QuesBankService {

	List<QuesBank> list(Map<String, String> params, Boolean isPageHelper);


	int update(QuesBank record);

	int updateBySelective(QuesBank record);

	int updateByQuesCodeWithNew(QuesBank record);

	int  deleteByQuesCode(String quesCode);

	int create(QuesBank record);

	QuesBank get(String quesCode);

	List<String> getQuestion(List<String> libCodes, String userId);

	QuesBank getNoSensitive(String quesCode);

//	List<QuesBank> listNoSensitive(String libCode, Integer quesStat);

	List<QuesBank> listNoSensitive(Map<String, String> params);

	int  checkUserAnswer(QuesBank question, String userAnswer);

	int updateQuesNumber();

	int invalidAllQuestionByQuesLib(String libCode);

	/**
	 * 批量转移题目到目标题库（事务在 Service 层，Controller 仅编排）
	 */
	int transferQuestions(String libCode, String[] questCodes);
}
