package com.cmbccd.ulms.sys.service.impl;

import com.cmbccd.ulms.sys.dao.DepartmentMapper;
import com.cmbccd.ulms.sys.domain.Department;
import com.cmbccd.ulms.sys.domain.DepartmentExample;
import com.cmbccd.ulms.sys.domain.DepartmentExample.Criteria;
import com.cmbccd.ulms.sys.service.DepartmentService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** 
* @Author WeiGenSheng
* @Time 2020年4月28日 下午5:12:36 
* @Version 1.0
* @Description:
*/
@Service
public class DepartmentServiceImpl implements DepartmentService {
	
	@Resource
	private DepartmentMapper departmentMapper;
	
	@Override
	public Department getDepartmentNameByDeptNum(String deptNum) {
		
		return departmentMapper.selectByDeptNum(deptNum);
	}
	@Override
	public List<Department> getDepartmentTree() {
		DepartmentExample example = new DepartmentExample();
		example.setOrderByClause(" dept_num ");
		List<Department> departments = departmentMapper.selectByExample(example);
		List<Department> departmentsTree =  departments.stream().filter(e -> e.getUpDept()==null || e.getUpDept().equals("") ).collect(Collectors.toList());
		//查找下一级菜单
		findDepartmentChild(departments , departmentsTree);
		return departmentsTree;
	}
	public void findDepartmentChild(List<Department> departments,List<Department> departmentsTree) {
		for(Department rootDepartment : departmentsTree) {
			List<Department> childDepartment = new ArrayList<>();
			for(Department department : departments) {
				if(department.getUpDept() != null && department.getUpDept().equals(rootDepartment.getDeptNum())) {
					childDepartment.add(department);
				}
				rootDepartment.setChildren(childDepartment);
			}
		}
	}

	@Override
	public int updateDepartment(Department record ,DepartmentExample example) {
		return departmentMapper.updateByExampleSelective(record, example);
	}

	@Override
	public int deleteDepartment(DepartmentExample example) {
		return departmentMapper.deleteByExample(example);
	}

	@Override
	public int insertDepartment(Department record) {
		return departmentMapper.insert(record);
	}

	@Override
	public Map<String ,Department> getDepartmentMap() {		
		return departmentMapper.getDepartmentMap();
	}

	@Override
	public List<String> getDepartmentByName(String deptName) {
		DepartmentExample example = new DepartmentExample();
		Criteria criteria = example.createCriteria();
		criteria.andDeptNameLike(deptName);
		List<Department> departments = departmentMapper.selectByExample(example);
		List<String> deptNums = departments.stream().map(Department::getDeptNum).collect(Collectors.toList());
		return deptNums;
	}

	@Override
	public List<Department> listDepartment(DepartmentExample example) {
		return departmentMapper.selectByExample(example);
	}
	
}
