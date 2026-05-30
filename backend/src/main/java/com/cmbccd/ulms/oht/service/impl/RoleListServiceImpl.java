package com.cmbccd.ulms.oht.service.impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.oht.dao.RoleListMapper;
import com.cmbccd.ulms.oht.domain.RoleList;
import com.cmbccd.ulms.oht.domain.RoleListExample;
import com.cmbccd.ulms.oht.domain.RoleListExample.Criteria;
import com.cmbccd.ulms.oht.service.RoleListService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * 
* <p>Title: RoleListServiceImpl.java</p>  
* <p>Description: </p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
@Service
public class RoleListServiceImpl implements RoleListService {

	@Resource
	private RoleListMapper roleListMapper;

	@Override
	public List<RoleList> getRoleListByExample(RoleListExample example) {
		return roleListMapper.selectByExample(example);
	}

	@Override
	public List<RoleList> getUserRoleListByUserId(String userId, Integer status) {
		RoleListExample example = new RoleListExample();
		Criteria criteria = example.createCriteria();
		criteria.andPloNumEqualTo(userId);
		criteria.andRoleStatEqualTo(status);
		return roleListMapper.selectByExample(example);
	}

	@Override
	public List<RoleList> getUserRoleListByRoleType(Integer roleType, Integer status) {
		RoleListExample example = new RoleListExample();
		Criteria criteria = example.createCriteria();
		criteria.andRoleTypeEqualTo(roleType);
		criteria.andRoleStatEqualTo(status);
		return roleListMapper.selectByExample(example);
	}

	@Override
	public int deleteUserRole(String ploNum) {
		if (Util.isNullorEmpty(ploNum)) {
			return 0;
		}
		RoleListExample example = new RoleListExample();
		Criteria criteria = example.createCriteria();
		criteria.andPloNumEqualTo(ploNum);
		return roleListMapper.deleteByExample(example);
	}

	@Override
	public int updateUserRole(RoleList record) {
		if (Util.isNullorEmpty(record.getPloNum()) || Util.isNullorEmpty(record.getRoleStat())) {
			return 0;
		}
		RoleListExample example = new RoleListExample();
		Criteria criteria = example.createCriteria();
		criteria.andPloNumEqualTo(record.getPloNum());
		return roleListMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int insertUserRole(RoleList record) {
		if (Util.isNullorEmpty(record.getPloNum()) || Util.isNullorEmpty(record.getRoleStat())) {
			return 0;
		}
		return roleListMapper.insertSelective(record);
	}
}
