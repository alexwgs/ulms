package com.cmbccd.ulms.edu.service;

import com.cmbccd.ulms.edu.domain.BookConfig;
import com.cmbccd.ulms.edu.domain.BookConfigExample;

import java.util.List;

public interface BookConfigService {
	public List<BookConfig> listBookConfig(BookConfigExample example);

	public int create(BookConfig record);
	
	public int update(BookConfig record);
	
	public int delete(String bookCode);
}
