package com.cmbccd.ulms.edu.service.Impl;

import com.cmbccd.ulms.edu.dao.AreaListMapper;
import com.cmbccd.ulms.edu.domain.AreaList;
import com.cmbccd.ulms.edu.domain.AreaListExample;
import com.cmbccd.ulms.edu.domain.AreaListExample.Criteria;
import com.cmbccd.ulms.edu.service.AreaListService;
import com.cmbccd.ulms.sys.dao.PublicMapper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class AreaListServiceImpl implements AreaListService {

	
	@Resource
	private AreaListMapper areaListMapper;
	
	@Resource
	private PublicMapper publicMapper;
	
	@Override
	public List<AreaList> listAreaList(AreaListExample example) {
		return areaListMapper.selectByExample(example);
	}

	@Override
	public int create(AreaList record) {
		
		record.setJourno(publicMapper.selectNewJourno());
		return areaListMapper.insertSelective(record);
	}

	@Override
	public int update(AreaList record) {
		
		return areaListMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int delete(String journo) {
		
		return areaListMapper.deleteByPrimaryKey(journo);
	}
	
	public boolean checkArea(String areaCode, String ip) {
		AreaListExample example = new AreaListExample();
		Criteria criteria = example.createCriteria();
		criteria.andAreaCodeEqualTo(areaCode);
		criteria.andSeatIpEqualTo(ip);
		List<AreaList> list = this.listAreaList(example);
		if(list.size()<1) return false;
		return true;
	}
}
