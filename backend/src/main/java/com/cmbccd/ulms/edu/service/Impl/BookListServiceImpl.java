package com.cmbccd.ulms.edu.service.impl;

import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.dao.BookListMapper;
import com.cmbccd.ulms.edu.domain.BookList;
import com.cmbccd.ulms.edu.domain.BookListExample;
import com.cmbccd.ulms.edu.domain.BookListExample.Criteria;
import com.cmbccd.ulms.edu.service.BookListService;
import com.cmbccd.ulms.sys.domain.Employee;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookListServiceImpl implements BookListService {

	@Resource
	private BookListMapper bookListMapper;
	
	@Override
	public List<BookList> list(BookListExample example) {
		List<BookList> list = bookListMapper.selectByExampleWithDateTime(example);
		for(BookList item : list) {
			item.setUser(DataCache.EMPLOYEE.get(item.getPloNum()));
		}
		return list;
	}
	
	@Override
	public List<Employee> listBookedEmployee(String examCode, String infoCode, String timeCode,Integer listStat) {
		BookListExample example = new BookListExample();
		Criteria criteria = example.createCriteria();
		criteria.andExamCodeEqualTo(examCode);
		criteria.andInfoCodeEqualTo(infoCode);
		criteria.andTimeCodeEqualTo(timeCode);
		criteria.andListStatEqualTo(listStat);
		example.setOrderByClause(" BOOK_DATE DESC");
		List<BookList> list = bookListMapper.selectByExample(example);
		List<Employee> emps = new ArrayList<Employee>();
		for(BookList item : list) {
			emps.add(DataCache.EMPLOYEE.get(item.getPloNum()));
		}
		return emps;
	}

	@Override
	public int create(BookList record) {
		record.setBookDate(Util.currentDateTime());
		record.setListStat(1);
		return bookListMapper.insert(record);
	}

	@Override
	public int delete(BookList record) {
		BookListExample example = new BookListExample();
		Criteria criteria = example.createCriteria();
		criteria.andExamCodeEqualTo(record.getExamCode());
		criteria.andInfoCodeEqualTo(record.getInfoCode());
		criteria.andTimeCodeEqualTo(record.getTimeCode());
		criteria.andBookDateEqualTo(record.getBookDate());
		return bookListMapper.deleteByExample(example);
	}

	@Override
	public int setInvalid(BookList record) {
		BookListExample example = new BookListExample();
		Criteria criteria = example.createCriteria();
		criteria.andExamCodeEqualTo(record.getExamCode());
		criteria.andPloNumEqualTo(record.getPloNum());
		criteria.andListStatEqualTo(1);
		BookList bookList = new BookList();
		bookList.setListStat(0);
		return bookListMapper.updateByExampleSelective(bookList, example);
	}

	@Override
	public List<BookList> listMyBookRecord(BookList record) {
		BookListExample example = new BookListExample();
		Criteria criteria = example.createCriteria();
		criteria.andExamCodeEqualTo(record.getExamCode());
		criteria.andPloNumEqualTo(record.getPloNum());
		if (!Util.isNullorEmpty(record.getListStat())) criteria.andListStatEqualTo(record.getListStat());
		return bookListMapper.selectByExample(example);
	}

	@Override
	public String getBookTime(String examCode) {
		String userId = Util.userIdByShiro();
		List<String> list = bookListMapper.selectBookTime(examCode, userId);
		if(list.size()<1) return "";
		return list.get(0);
	}


}
