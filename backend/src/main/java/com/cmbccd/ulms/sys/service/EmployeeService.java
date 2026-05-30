package com.cmbccd.ulms.sys.service;

import com.cmbccd.ulms.sys.domain.Employee;
import com.cmbccd.ulms.sys.domain.EmployeeExample;
import com.cmbccd.ulms.sys.domain.EmployeeForRPA;

import java.util.HashMap;
import java.util.List;

/** 
* @Author WeiGenSheng
* @Time 2020年4月28日 下午4:30:13 
* @Version 1.0
* @Description:
*/
public interface EmployeeService {
	public List<Employee> getEmployeeByExample(EmployeeExample example);
	public Employee getEmployeeByPloNum(String ploNum);
	public int updateAvatarByPrimary(Employee record);
	public List<Employee> selectEmployeeInit();
	
    List<HashMap<String , Object>> getEmployeeBirthday(String month);
    
	
	public List<String> getPloNumByPloName(String ploName);

	public List<EmployeeForRPA> getEmployeeForRpa(EmployeeExample example);
}
