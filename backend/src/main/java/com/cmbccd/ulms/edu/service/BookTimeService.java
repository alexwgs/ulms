package com.cmbccd.ulms.edu.service;

import com.cmbccd.ulms.edu.domain.BookTime;
import com.cmbccd.ulms.edu.domain.BookTimeExample;

import java.util.List;

public interface BookTimeService {
	public List<BookTime> listBookTime(BookTimeExample example);
	
	public BookTime get(String journo);

	public int create(BookTime record);
	
	public int update(BookTime record);
	
	public int delete(String journo);
	
	public List<BookTime> listBookTime(String bookCode, Integer bookStat);
}
