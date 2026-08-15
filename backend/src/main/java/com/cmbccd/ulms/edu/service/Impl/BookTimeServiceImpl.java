package com.cmbccd.ulms.edu.service.impl;

import com.cmbccd.ulms.edu.dao.BookTimeMapper;
import com.cmbccd.ulms.edu.domain.BookTime;
import com.cmbccd.ulms.edu.domain.BookTimeExample;
import com.cmbccd.ulms.edu.domain.BookTimeExample.Criteria;
import com.cmbccd.ulms.edu.service.BookTimeService;
import com.cmbccd.ulms.sys.service.PublicService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class BookTimeServiceImpl implements BookTimeService {

	@Resource
	private BookTimeMapper bookTimeMapper;
	
	@Resource
	private PublicService publicService;
	
	@Override
	public List<BookTime> listBookTime(BookTimeExample example) {
		return bookTimeMapper.selectByExample(example);
	}
	@Override
	public BookTime get(String journo) {
		
		return bookTimeMapper.selectByPrimaryKey(journo);
	}
	@Override
	public int create(BookTime record) {
		record.setJourno(publicService.getJourno());
		return bookTimeMapper.insertSelective(record);
	}

	@Override
	public int update(BookTime record) {
		
		return bookTimeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(String journo) {
		
		return bookTimeMapper.deleteByPrimaryKey(journo);
	}

	@Override
	public List<BookTime> listBookTime(String bookCode, Integer bookStat) {
		BookTimeExample example = new BookTimeExample();
		Criteria criteria = example.createCriteria();
		criteria.andBookCodeEqualTo(bookCode);
		criteria.andBookStatEqualTo(bookStat);
		example.setOrderByClause(" beg_time asc ");
		return bookTimeMapper.selectByExample(example);
	}


	
}
