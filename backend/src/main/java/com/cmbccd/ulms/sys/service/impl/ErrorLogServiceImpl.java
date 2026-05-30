package com.cmbccd.ulms.sys.service.impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.dao.ErrorLogMapper;
import com.cmbccd.ulms.sys.domain.ErrorLog;
import com.cmbccd.ulms.sys.domain.ErrorLogExample;
import com.cmbccd.ulms.sys.domain.ErrorLogExample.Criteria;
import com.cmbccd.ulms.sys.service.ErrorLogService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class ErrorLogServiceImpl implements ErrorLogService {

	@Resource
	private ErrorLogMapper errorLogMapper;
	

	@Override
	public int getNewId() {
		return errorLogMapper.getNewId();
	}


	@Override
	public List<ErrorLog> getErrorLogListByExample(ErrorLogExample example) {
		return errorLogMapper.selectByExample(example);
	}


	@Override
	public int deleteErrorLogById(int id) {
		ErrorLogExample example = new ErrorLogExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		return errorLogMapper.deleteByExample(example);
	}


	@Override
	public int insertNewErrorLog(ErrorLog record) {
		String userId = "";
		userId = Util.isNullorEmpty(Util.userIdByShiro()) ? "" :  Util.userIdByShiro();
		record.setId(errorLogMapper.getNewId());
		record.setDataTime(Util.currentDateTime());
		record.setUserId(userId);
		return errorLogMapper.insertSelective(record);
	}

}
