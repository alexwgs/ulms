package com.cmbccd.ulms.edu.service.Impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.dao.BookConfigMapper;
import com.cmbccd.ulms.edu.domain.BookConfig;
import com.cmbccd.ulms.edu.domain.BookConfigExample;
import com.cmbccd.ulms.edu.service.BookConfigService;
import com.cmbccd.ulms.sys.dao.PublicMapper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class BookConfigServiceImpl implements BookConfigService {

	@Resource
	private BookConfigMapper bookConfigMapper;
	
	@Resource
	private PublicMapper publicMapper;
	@Override
	public List<BookConfig> listBookConfig(BookConfigExample example) {
		
		return bookConfigMapper.selectByExample(example);
	}

	@Override
	public int create(BookConfig record) {
		String userId = Util.userIdByShiro();
		if(Util.isNullorEmpty(userId)) return 0;
		record.setHandlePlo(userId);
		record.setHandleDate(Util.currentDateTime());
		record.setBookCode(publicMapper.selectNewJourno());
		return bookConfigMapper.insertSelective(record);
	}

	@Override
	public int update(BookConfig record) {
		String userId = Util.userIdByShiro();
		if(Util.isNullorEmpty(userId)) return 0;
		record.setHandlePlo(userId);
		record.setHandleDate(Util.currentDateTime());
		return bookConfigMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(String bookCode) {
		
		return bookConfigMapper.deleteByPrimaryKey(bookCode);
	}
}
