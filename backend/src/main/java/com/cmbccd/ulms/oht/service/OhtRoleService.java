/**
 * 
 */
package com.cmbccd.ulms.oht.service;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.oht.domain.OhtRole;
import com.cmbccd.ulms.oht.domain.OhtRoleExample;

import java.util.List;
import java.util.Map;

/** 
* @Author WeiGenSheng
* @Time 2020年5月7日 下午6:49:20 
* @Version 1.0
* @Description:
*/
/** 
* @Description: （对类进行功能描述）
* @date: datedate{time} （日期）
*/
public interface OhtRoleService {

	//查看举手系统的角色列表
	public List<OhtRole> getOhtRoleList(OhtRoleExample example);

	//分页查看举手系统的角色列表（管理端查询）
	public DataPage<OhtRole> getOhtRoleListByQuery(Map<String, String> params);
	
	//新增角色
	public Integer addSelectiveOhtRole(OhtRole record);
	//修改角色
	public Integer updateOhtRoleByPrimaryId(OhtRole record);
	//删除角色
	public Integer deleteOhtRoleByPrimaryId(Integer roleCode);
	//通过roleCode获取role信息
	public OhtRole selectOhtRoleByPrimaryId(Integer roleCode);
}
