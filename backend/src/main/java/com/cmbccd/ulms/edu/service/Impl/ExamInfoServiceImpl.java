package com.cmbccd.ulms.edu.service.impl;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.dao.ExamInfoMapper;
import com.cmbccd.ulms.edu.domain.AreaConfig;
import com.cmbccd.ulms.edu.domain.ExamInfo;
import com.cmbccd.ulms.edu.domain.ExamInfoExample;
import com.cmbccd.ulms.edu.domain.ExamInfoExample.Criteria;
import com.cmbccd.ulms.edu.domain.QuesTemp;
import com.cmbccd.ulms.edu.service.AreaConfigService;
import com.cmbccd.ulms.edu.service.ExamInfoService;
import com.cmbccd.ulms.edu.service.QuesTempService;
import com.cmbccd.ulms.sys.service.PublicService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

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
	public DataPage<ExamInfo> listExamInfoQuery(Map<String, String> params, boolean selfOnly) {
		String examStat = params.get("status");
		String query = params.get("query");
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		ExamInfoExample example = new ExamInfoExample();
		Criteria criteria = example.createCriteria();
		if (!Util.isNullorEmpty(params.get("order"))) {
			example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
		}
		if (!Util.isNullorEmpty(examStat)) {
			criteria.andExamStatEqualTo(Integer.parseInt(examStat));
		}
		if (!Util.isNullorEmpty(query)) {
			criteria.andExamNameLike("%" + query + "%");
		}
		if (selfOnly) {
			criteria.andHandlePloEqualTo(Util.userIdByShiro());
		}
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		List<ExamInfo> list = examInfoMapper.selectByExample(example);
		return new DataPage<ExamInfo>(list);
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

	@Resource
	private QuesTempService quesTempService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int createExamTemp(String examCode, List<QuesTemp> list) {
		quesTempService.delete(examCode);
		int quesNum = quesTempService.create(list);
		// 更新试题数量
		ExamInfo examInfo = new ExamInfo();
		examInfo.setExamCode(examCode);
		examInfo.setQuesNum(quesNum);
		this.update(examInfo);
		return quesNum;
	}

	
}
