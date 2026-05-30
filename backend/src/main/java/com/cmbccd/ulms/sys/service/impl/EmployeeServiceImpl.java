package com.cmbccd.ulms.sys.service.impl;

import com.cmbccd.ulms.sys.dao.EmployeeMapper;
import com.cmbccd.ulms.sys.domain.Employee;
import com.cmbccd.ulms.sys.domain.EmployeeExample;
import com.cmbccd.ulms.sys.domain.EmployeeExample.Criteria;
import com.cmbccd.ulms.sys.domain.EmployeeForRPA;
import com.cmbccd.ulms.sys.service.EmployeeService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author WeiGenSheng
 * @Time 2020年4月28日 下午4:31:14
 * @Version 1.0
 * @Description:
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Resource
	private EmployeeMapper employeeMapper;

	@Override
	public List<Employee> getEmployeeByExample(EmployeeExample example) {

		return employeeMapper.selectByExample(example);
	}

	@Override
	public Employee getEmployeeByPloNum(String ploNum) {
		return employeeMapper.selectByPrimaryKey(ploNum);
	}

	@Override
	public int updateAvatarByPrimary(Employee record) {
		return employeeMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<Employee> selectEmployeeInit() {
		return employeeMapper.selectEmployeeInit();
	}

	@Override
	public List<HashMap<String, Object>> getEmployeeBirthday(String month) {
		return employeeMapper.getEmployeeBirthday(month);
	}

	@Override
	public List<String> getPloNumByPloName(String ploName) {
		EmployeeExample example = new EmployeeExample();
		Criteria criteria = example.createCriteria();
		criteria.andPloNameLike(ploName);
		List<Employee> emps = employeeMapper.selectByExample(example);
		List<String> ploNums = emps.stream().map(Employee::getPloNum).collect(Collectors.toList());
		return ploNums;
	}

	@Override
	public List<EmployeeForRPA> getEmployeeForRpa(EmployeeExample example) {
		return employeeMapper.selectRpaByExample(example);
	}
}
