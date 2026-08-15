package com.cmbccd.ulms.hr.service.impl;

import com.cmbccd.ulms.hr.dao.PunchJourMapper;
import com.cmbccd.ulms.hr.domain.PunchJour;
import com.cmbccd.ulms.hr.domain.PunchJourExample;
import com.cmbccd.ulms.hr.service.PunchJourService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class PunchJourServiceImpl implements PunchJourService {

	@Resource
	private PunchJourMapper punchJourMapper;

	@Override
	public List<PunchJour> list(PunchJourExample example) {
		return punchJourMapper.selectByExample(example);
	}

	@Override
	public PunchJour get(String journo) {
		return punchJourMapper.selectByPrimaryKey(journo);
	}

	@Override
	public int create(PunchJour record) {
		
		return 0;
	}

	@Override
	public int update(PunchJour record) {
		
		return punchJourMapper.updateByPrimaryKey(record);
	}

	@Override
	public int delete(String journo) {
		
		return punchJourMapper.deleteByPrimaryKey(journo);
	}
	
	
}
