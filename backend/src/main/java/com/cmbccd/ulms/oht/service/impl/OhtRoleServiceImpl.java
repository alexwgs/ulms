/**
 * 
 */
package com.cmbccd.ulms.oht.service.impl;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.oht.dao.OhtRoleMapper;
import com.cmbccd.ulms.oht.domain.OhtRole;
import com.cmbccd.ulms.oht.domain.OhtRoleExample;
import com.cmbccd.ulms.oht.service.OhtRoleService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 
* <p>Title: OhtRoleServiceImpl.java</p>  
* <p>Description: </p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
@Service
public class OhtRoleServiceImpl implements OhtRoleService{

	@Resource
	private OhtRoleMapper ohtRoleMapper; 
	
	@Override
	public List<OhtRole> getOhtRoleList(OhtRoleExample example) {
		
		return ohtRoleMapper.selectByExample(example);
	}

	@Override
	public DataPage<OhtRole> getOhtRoleListByQuery(Map<String, String> params) {
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		OhtRoleExample example = new OhtRoleExample();
		if (!Util.isNullorEmpty(params.get("order"))) {
			example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
		}
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		List<OhtRole> ohtRoles = ohtRoleMapper.selectByExample(example);
		return new DataPage<OhtRole>(ohtRoles);
	}

	@Override
	public Integer addSelectiveOhtRole(OhtRole record) {
		int roleCode = ohtRoleMapper.selectNewId();
		record.setRoleCode(roleCode);
		return ohtRoleMapper.insertSelective(record);
	}

	@Override
	public Integer updateOhtRoleByPrimaryId(OhtRole record) {
		return ohtRoleMapper.updateByPrimaryKey(record);
	}

	@Override
	public Integer deleteOhtRoleByPrimaryId(Integer roleCode) {
		return ohtRoleMapper.deleteByPrimaryKey(roleCode);
	}

	@Override
	public OhtRole selectOhtRoleByPrimaryId(Integer roleCode) {
		
		return ohtRoleMapper.selectByPrimaryKey(roleCode);
	}
	

}
