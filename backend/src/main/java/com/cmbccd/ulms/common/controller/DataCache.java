package com.cmbccd.ulms.common.controller;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Department;
import com.cmbccd.ulms.sys.domain.Dictionary;
import com.cmbccd.ulms.sys.domain.Employee;
import com.cmbccd.ulms.sys.domain.JobInfo;
import com.cmbccd.ulms.sys.service.DepartmentService;
import com.cmbccd.ulms.sys.service.DictionaryService;
import com.cmbccd.ulms.sys.service.EmployeeService;
import com.cmbccd.ulms.sys.service.JobInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
* <p>Title: DataCache.java</p>  
* <p>Description:初始化常用数据至内存中 </p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
@EnableScheduling
@Component
public class DataCache {
	private static final Logger log = LoggerFactory.getLogger(DataCache.class);

	public static Map<String, Employee> EMPLOYEE = new ConcurrentHashMap<>();
	public static Map<String, Department> DEPARTMENT = new ConcurrentHashMap<>();
	public static Map<String, JobInfo> JOBINFO = new ConcurrentHashMap<>();

    public static List<Dictionary> Dict = new ArrayList<>();
	public static final int BATCH_COUNT = 128;
	public static List<JobInfo> JOB = null;
	@Resource
	private EmployeeService employeeService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private DepartmentService departmentService;

	@Resource
	private JobInfoService jobInfoService;

//	cron [秒] [分] [小时] [日] [月] [周] [年]
//	每隔5秒执行一次：*/5 * * * * ?
//	每隔1分钟执行一次：0 */1 * * * ?
//	每天23点执行一次：0 0 23 * * ?
//	每天凌晨1点执行一次：0 0 1 * * ?
//	每月1号凌晨1点执行一次：0 0 1 1 * ?
//	每月最后一天23点执行一次：0 0 23 L * ?
//	每周星期六凌晨1点实行一次：0 0 1 ? * L
//	在26分、29分、33分执行一次：0 26,29,33 * * * ?
//	每天的0点、13点、18点、21点都执行一次：0 0 0,13,18,21 * * ?
	

	/**
	 * 初始化将所有人员信息缓存
	 * 
	 * @return void
	 */
	@PostConstruct
	@Scheduled(cron = "0 0 7,8,9,10,11,12,13,14,15,16,17,18,19,20,22 * * ?")
	public void init() {
		log.info("初始化人员信息开始");
		List<Employee> emps = employeeService.selectEmployeeInit();
		DEPARTMENT = departmentService.getDepartmentMap();
		EMPLOYEE.clear();
		JOBINFO.clear();
		Dict  = dictionaryService.getDictionaryList(null);
		JOB = jobInfoService.list(null);
		JOB.forEach(e -> JOBINFO.put(e.getJobLevel(), e));
		for (Employee emp : emps) {
			String userId = emp.getPloNum();
			if (Util.isNullorEmpty(emp.getAvatar())) {
				emp.setAvatar("upload/getFile/avatar/avatar.png");
			}
			
			String dept_name = Optional.ofNullable(DataCache.DEPARTMENT.get(emp.getDeptNum())).map(e -> e.getDeptName()).orElse("-");
			String group_name = Optional.ofNullable(DataCache.DEPARTMENT.get(emp.getDeptGroup())).map(e -> e.getDeptName()).orElse("-");
			emp.setDeptName(dept_name);
			emp.setGroupName(group_name);

			EMPLOYEE.put(userId, emp);
		}
		log.info("初始化人员信息结束");
	}


}