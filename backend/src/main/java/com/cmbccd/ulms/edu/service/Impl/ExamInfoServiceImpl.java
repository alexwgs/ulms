package com.cmbccd.ulms.edu.service.impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.dao.ExamInfoMapper;
import com.cmbccd.ulms.edu.domain.AreaConfig;
import com.cmbccd.ulms.edu.domain.ExamInfo;
import com.cmbccd.ulms.edu.domain.ExamInfoExample;
import com.cmbccd.ulms.edu.service.AreaConfigService;
import com.cmbccd.ulms.edu.service.ExamInfoService;
import com.cmbccd.ulms.sys.service.PublicService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class ExamInfoServiceImpl implements ExamInfoService {

	@Resource
	private ExamInfoMapper examInfoMapper;
	
	@Resource
	private AreaConfigService areaConfigService;
	
	@Resource
	private PublicService publicService;
	
	
	@Override
	public int create(ExamInfo record) {
		String userId = Util.userIdByShiro();
		if (Util.isNullorEmpty(userId)) {
			return 0;
		}
		record.setHandlePlo(userId);
		record.setHandleDate(Util.currentDateTime());
		record.setExamCode(publicService.getJourno());
		return examInfoMapper.insertSelective(record);
	}

	@Override
	public int update(ExamInfo record) {
		String userId = Util.userIdByShiro();
		if (Util.isNullorEmpty(userId)) {
			return 0;
		}
		record.setHandlePlo(userId);
		record.setHandleDate(Util.currentDateTime());
		return examInfoMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(String examCode) {
		
		return examInfoMapper.deleteByPrimaryKey(examCode);
	}

	@Override
	public List<ExamInfo> list(ExamInfoExample example) {
		
		return examInfoMapper.selectByExample(example);
	}

	@Override
	public ExamInfo getRecord(String examCode) {
		ExamInfo record = examInfoMapper.selectByPrimaryKey(examCode);
		AreaConfig areaConfig = areaConfigService.get(record.getAreaCode());
		if(!Util.isNullorEmpty(areaConfig)) record.setAreaInfo(areaConfig.getAreaName() + "["+areaConfig.getAreaDesc()+"]");
		return record;
	}

	@Override
	public List<ExamInfo> listCurrentExam() {
		String userId = Util.userIdByShiro();
		if (Util.isNullorEmpty(userId)) {
			return null;
		}
		List<ExamInfo> list = examInfoMapper.selectCurrentExam(userId);
		for (ExamInfo record : list) {
			AreaConfig areaConfig = areaConfigService.get(record.getAreaCode());
			if(!Util.isNullorEmpty(areaConfig)) record.setAreaInfo(areaConfig.getAreaName() + "["+areaConfig.getAreaDesc()+"]");
		}
		return list;
	}

	
	
	
}
