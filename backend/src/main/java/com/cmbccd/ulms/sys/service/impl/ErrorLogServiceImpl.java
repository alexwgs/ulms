package com.cmbccd.ulms.sys.service.impl;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.dao.ErrorLogMapper;
import com.cmbccd.ulms.sys.domain.ErrorLog;
import com.cmbccd.ulms.sys.domain.ErrorLogExample;
import com.cmbccd.ulms.sys.domain.ErrorLogExample.Criteria;
import com.cmbccd.ulms.sys.service.ErrorLogService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

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
	public DataPage<ErrorLog> getErrorLogListByQuery(Map<String, String> params) {
		ErrorLogExample example = new ErrorLogExample();
		Criteria criteria = example.createCriteria();
		if (!Util.isNullorEmpty(params.get("priority"))) {
			criteria.andPriorityEqualTo(params.get("priority"));
		}
		if (!Util.isNullorEmpty(params.get("dataType"))) {
			criteria.andDataTypeEqualTo(params.get("dataType"));
		}
		if (!(Util.isNullorEmpty(params.get("begDate")) && Util.isNullorEmpty(params.get("endDate")))) {
			criteria.andDataTimeBetween(params.get("begDate") + " 00:00:00", params.get("endDate") + " 23:59:59");
		}
		if (!Util.isNullorEmpty(params.get("order"))) {
			example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
		}
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		List<ErrorLog> list = errorLogMapper.selectByExample(example);
		return new DataPage<ErrorLog>(list);
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
