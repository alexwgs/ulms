package com.cmbccd.ulms.edu.service;

import com.cmbccd.ulms.edu.domain.BookInfo;
import com.cmbccd.ulms.edu.domain.BookInfoExample;

import java.util.List;

public interface BookInfoService {
	public List<BookInfo> listBookInfo(BookInfoExample example);

	public int create(BookInfo record);
	
	public int update(BookInfo record);
	
	public int delete(String infoCode);
	
	public BookInfo get(String infoCode);
}
