package com.cmbccd.ulms.edu.service;

import com.cmbccd.ulms.edu.domain.BrushScore;
import com.cmbccd.ulms.edu.domain.BrushScoreExample;
import com.cmbccd.ulms.edu.domain.report.BrushScoreDept;
import com.cmbccd.ulms.edu.domain.report.BrushScoreGroup;
import com.cmbccd.ulms.edu.domain.report.BrushScoreHum;
import com.cmbccd.ulms.edu.domain.report.CategoryInfo;

import java.util.List;
import java.util.Map;

public interface BrushScoreService {
	List<BrushScore> list(BrushScoreExample example);
	
	int delete(String journo);
	
	int update(BrushScore record);
	
	int create(BrushScore record);
	
	BrushScore createBrushScore(BrushScore record);
	
	BrushScore get(String journo);
	
	int dayBrushCount(String dataDate, String ploNum);
	
	int monthBrushCount(String dataDate, String ploNum);
	
	BrushScore submitAnswer(BrushScore record);
	
	List<BrushScore> listWrongQuestion(String userId);
	
	List<BrushScore> centerWrongMostQuestion();
	// 报表
	List<BrushScoreHum> reportBrushScoreHum(Map<String, String> params);
	List<BrushScoreGroup> reportBrushScoreGroup(Map<String, String> params);
	List<BrushScoreDept> reportBrushScoreDept(Map<String, String> params);
	List<CategoryInfo> reportCategoryInfo(Map<String, String> params);
}
