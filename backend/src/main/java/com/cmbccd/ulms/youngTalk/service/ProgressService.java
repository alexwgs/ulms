package com.cmbccd.ulms.youngTalk.service;

import com.cmbccd.ulms.youngTalk.domain.Progress;

import java.util.List;

public interface ProgressService {
	public int newId();
	
	public List<Progress> getProgressByArticleId(int articleId);
	
	public int insertNewProgress(Progress record);
	
	public int updateProgressById(Progress record);
}
