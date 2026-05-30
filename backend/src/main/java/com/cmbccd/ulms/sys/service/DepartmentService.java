package com.cmbccd.ulms.sys.service;

import com.cmbccd.ulms.sys.domain.Department;
import com.cmbccd.ulms.sys.domain.DepartmentExample;

import java.util.List;
import java.util.Map;

/** 
* @Author WeiGenSheng
* @Time 2020年4月28日 下午5:12:11 
* @Version 1.0
* @Description:
*/
public interface DepartmentService {

	public Department getDepartmentNameByDeptNum(String deptNum);
	
	public List<Department> getDepartmentTree();
	
	public Map<String ,Department> getDepartmentMap();
	
	public int updateDepartment(Department record, DepartmentExample example);
	
	public int deleteDepartment(DepartmentExample example);
	
	public int insertDepartment(Department record);
	
	public List<String> getDepartmentByName(String deptName);
	
	public List<Department> listDepartment(DepartmentExample example);

}
