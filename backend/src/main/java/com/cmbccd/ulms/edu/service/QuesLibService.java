package com.cmbccd.ulms.edu.service;

import com.cmbccd.ulms.edu.domain.QuesLib;

import java.util.List;

public interface QuesLibService {
	List<QuesLib> quesTree(Integer status);

	int updateByLibCode(QuesLib record);

	int  deleteByLibCode(String libCode);

	int create(QuesLib record);

	int updateLibQuesionInvalid(String libCode, String handlePlo);
}
