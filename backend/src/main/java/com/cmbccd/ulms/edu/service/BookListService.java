package com.cmbccd.ulms.edu.service;

import com.cmbccd.ulms.edu.domain.BookList;
import com.cmbccd.ulms.edu.domain.BookListExample;
import com.cmbccd.ulms.sys.domain.Employee;

import java.util.List;

public interface BookListService {

	List<Employee> listBookedEmployee(String examCode, String infoCode, String timeCode,Integer listStat);
	
	List<BookList> list(BookListExample example);
	
	int create(BookList record);
	
	int delete (BookList record);
	
	int setInvalid(BookList record);
	
	List<BookList> listMyBookRecord(BookList record);
	
	String getBookTime(String examCode);
}
