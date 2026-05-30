package com.cmbccd.ulms.sys.controller;

import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.*;
import com.cmbccd.ulms.sys.service.DepartmentService;
import com.cmbccd.ulms.sys.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("rpa")
public class RpaController {
    @Resource
    private EmployeeService employeeService;

    @Resource
    private DepartmentService departmentService;
    @GetMapping("/employee")
    public Msg getRpaEmployee(@RequestParam Map<String, String> params){
        List<EmployeeForRPA> list = new ArrayList<>();
        String dept = params.get("dept_num");
        String group = params.get("dept_group");
        String jobLevel = params.get("job_level");
        String status = params.get("status");
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause(" dept_num asc, dept_group asc, job_level desc , plo_num asc ");
        if(!Util.isNullorEmpty(dept)){
            List<String> deptArry = Arrays.asList(dept.split(","));
            criteria.andDeptNumIn(deptArry);
        }
        if(!Util.isNullorEmpty(group)){
            List<String> grouptArry = Arrays.asList(group.split(","));
            criteria.andDeptGroupIn(grouptArry);
        }
        if(!Util.isNullorEmpty(jobLevel)){
            List<String> jobLevelArry = Arrays.asList(jobLevel.split(","));
            criteria.andJobLevelIn(jobLevelArry);
        }
        if(Util.isNullorEmpty(status)){
            criteria.andPloStatusEqualTo("00");
            List<EmployeeForRPA> list1 = employeeService.getEmployeeForRpa(example);
            criteria.andPloStatusEqualTo("01");
            criteria.andOutDateIsNotNull();
            criteria.andOutDateGreaterThanOrEqualTo(Util.getPastDate(30));
            List<EmployeeForRPA> list2 = employeeService.getEmployeeForRpa(example);
            list.addAll(list1);
            list.addAll(list2);
        }else{
            criteria.andPloStatusEqualTo(status);
            list = employeeService.getEmployeeForRpa(example);
        }
        for (EmployeeForRPA emp:
                list) {
            emp.setDeptName(Util.isNullorEmpty(emp.getDeptNum()) ?"": DataCache.DEPARTMENT.get(emp.getDeptNum()).getDeptName());
            emp.setGroupName(Util.isNullorEmpty(emp.getDeptGroup()) ?"":DataCache.DEPARTMENT.get(emp.getDeptGroup()).getDeptName());
        }
        return Msg.success(list);
    }

    @GetMapping("/department")
    public Msg getRpaDepartment(@RequestParam Map<String, String> params){
        List<Department> list = new ArrayList<>();
        String dept = params.get("dept_num");
        String group = params.get("dept_group");
        String organ = params.get("organ");
        DepartmentExample example = new DepartmentExample();
        DepartmentExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause(" dept_num asc ");
        if(!Util.isNullorEmpty(dept)){
            List<String> deptArry = Arrays.asList(dept.split(","));
            criteria.andDeptNumIn(deptArry);
        }
        if(!Util.isNullorEmpty(group)){
            List<String> grouptArry = Arrays.asList(group.split(","));
            criteria.andUpDeptIn(grouptArry);
        }
        if(!Util.isNullorEmpty(organ)){
            criteria.andOrganIsNotNull();
        }
        list = departmentService.listDepartment(example);
        return Msg.success(list);
    }
}
