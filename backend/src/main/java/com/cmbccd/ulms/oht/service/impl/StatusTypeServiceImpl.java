package com.cmbccd.ulms.oht.service.impl;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.oht.dao.StatusTypeMapper;
import com.cmbccd.ulms.oht.domain.StatusType;
import com.cmbccd.ulms.oht.domain.StatusTypeExample;
import com.cmbccd.ulms.oht.domain.StatusTypeExample.Criteria;
import com.cmbccd.ulms.oht.service.StatusTypeService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 
* <p>Title: StatusTypeServiceImpl.java</p>  
* <p>Description: </p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
@Service
public class StatusTypeServiceImpl implements StatusTypeService {

	@Resource
	private StatusTypeMapper statusTypeMapper;

	@Override
	public List<StatusType> getStatusByTree() {
		StatusTypeExample example = new StatusTypeExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(1);
		criteria.andDataTypeEqualTo("status");
		example.setOrderByClause(" sort desc,id");
		List<StatusType> total = statusTypeMapper.selectByExample(example);
		List<StatusType> statusTypeTree = total.stream()
				.filter(e -> e.getPid() == 0 && e.getStatus() != 0 && e.getLevelGrade() == 1)
				.collect(Collectors.toList());

		int i = 0;
		for (StatusType ele1 : statusTypeTree) {
			List<StatusType> level2 = total.stream().filter(e -> e.getPid() == ele1.getId() && e.getLevelGrade() == 2)
					.collect(Collectors.toList());
			statusTypeTree.get(i).setChildren(level2);
			int j = 0;
			for (StatusType ele2 : level2) {
				List<StatusType> level3 = total.stream()
						.filter(e -> e.getPid() == ele2.getId() && e.getLevelGrade() == 3).collect(Collectors.toList());
				if (!level3.isEmpty()) {
					statusTypeTree.get(i).getChildren().get(j).setChildren(level3);
				}
				j++;
			}
			i++;
		}
		return statusTypeTree;
	}

	@Override
	public List<StatusType> getIdentity() {
		StatusTypeExample example = new StatusTypeExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(1);
		criteria.andDataTypeEqualTo("identity");
		example.setOrderByClause(" sort desc,id");
		List<StatusType> identity = statusTypeMapper.selectByExample(example);
		return identity;
	}

	@Override
	public int selectNewId() {
		return statusTypeMapper.selectNewId();
	}

	@Override
	public List<StatusType> getStatusTypeList() {
		return statusTypeMapper.selectByExample(null);
	}

	@Override
	public int identityNameOhtFlag(String[] statusNames) {
		Integer flag = statusTypeMapper.identityNameOhtFlag(statusNames);
		return flag == null ? 0 : flag;
	}

	@Override
	public Map<String, String> getUserStatusAndOhtFlag(int id) {
		List<Map<String, String>> list = statusTypeMapper.getUserStatusAndOhtFlag(id);
		Map<String, String> map = new HashMap<String, String>();
		if (list.size() < 1) {
			map.put("level", "");
			map.put("leve2", "");
			map.put("leve3", "未定义");
			map.put("ohtFlag", "0");
		} else {
			map = list.get(0);
		}

		return map;
	}

	@Override
	public List<StatusType> getAllStatusType() {
		return statusTypeMapper.getAllStatusType();
	}

	@Override
	public List<StatusType> getStatusTypeByExample(StatusTypeExample example) {
		return statusTypeMapper.selectByExample(example);
	}

	@Override
	public DataPage<StatusType> getStatusTypeListByQuery(Map<String, String> params) {
		StatusTypeExample example = new StatusTypeExample();
		Criteria criteria = example.createCriteria();
		if (!Util.isNullorEmpty(params.get("dataType"))) {
			criteria.andDataTypeEqualTo(params.get("dataType"));
		}
		if (!Util.isNullorEmpty(params.get("levelGrade"))) {
			criteria.andLevelGradeEqualTo(Integer.parseInt(params.get("levelGrade")));
		}
		if (!Util.isNullorEmpty(params.get("status"))) {
			criteria.andStatusEqualTo(Integer.parseInt(params.get("status")));
		}
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		List<StatusType> statusTypeList = statusTypeMapper.selectByExample(example);
		return new DataPage<StatusType>(statusTypeList);
	}

	@Override
	public int updateStatusTypeByPrimaryId(StatusType record) {
		return statusTypeMapper.updateByPrimaryKey(record);
	}

	@Override
	public int insertStatusType(StatusType record) {
		record.setId(statusTypeMapper.selectNewId());
		return statusTypeMapper.insert(record);
	}

}
