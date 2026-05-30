package com.cmbccd.ulms.edu.service.Impl;

import com.cmbccd.ulms.edu.dao.BookInfoMapper;
import com.cmbccd.ulms.edu.domain.BookInfo;
import com.cmbccd.ulms.edu.domain.BookInfoExample;
import com.cmbccd.ulms.edu.service.BookInfoService;
import com.cmbccd.ulms.sys.dao.PublicMapper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class BookInfoServiceImpl implements BookInfoService {

	@Resource
	private BookInfoMapper bookInfoMapper;
	
	@Resource
	private PublicMapper publicMapper;
	
	@Override
	public List<BookInfo> listBookInfo(BookInfoExample example) {
		return bookInfoMapper.selectByExample(example);
	}

	@Override
	public int create(BookInfo record) {
		record.setInfoCode(publicMapper.selectNewJourno());
		return bookInfoMapper.insert(record);
	}

	@Override
	public int update(BookInfo record) {
		return bookInfoMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(String infoCode) {
		return bookInfoMapper.deleteByPrimaryKey(infoCode);
	}

	@Override
	public BookInfo get(String infoCode) {
		
		return bookInfoMapper.selectByPrimaryKey(infoCode);
	}
}
