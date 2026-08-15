package com.cmbccd.ulms.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.domain.Tree;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.ExcelUtils;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.*;
import com.cmbccd.ulms.sys.domain.EmployeeExample.Criteria;
import com.cmbccd.ulms.sys.service.AgentService;
import com.cmbccd.ulms.sys.service.DepartmentService;
import com.cmbccd.ulms.sys.service.EmployeeService;
import com.cmbccd.ulms.sys.service.UserRoleService;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author WeiGenSheng
 * @Time 2020年4月28日 下午4:38:23
 * @Version 1.0
 * @Description:
 */
@RestController
@CrossOrigin
@RequestMapping("/employee/")
public class EmployeeController {

	@Resource
	private EmployeeService employeeService;
	@Resource
	private DepartmentService departmentService;
	@Resource
	private AgentService agentService;
	@Resource
	private UserRoleService userRoleService;


//	private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

	/**
	 * 获取人员名单，同时将科室、组别、角色、状态 字典匹配 返回后台人员管理页面
	 * 
	 * @return
	 */
	@GetMapping(value = "list/{ploStatus}")
	@SaCheckPermission("employee:list")
	public Msg getEmployeeList(@RequestParam Map<String, String> params, @PathVariable("ploStatus") String ploStatus) {
		EmployeeExample example = new EmployeeExample();
		Criteria criteria = example.createCriteria();
		// 获取分页、人员状态数据设置条件
		if (ploStatus != null && !ploStatus.equals("all")) {
			criteria.andPloStatusEqualTo(ploStatus);
		}

		String param = params.get("query");
		if (!Util.isNullorEmpty(param)) {
			String key = params.get("queryType");
			if (key.equals("ploNum")) {
				criteria.andPloNumLike("%" + param + "%");
			} else if (key.equals("ploName")) {
				criteria.andPloNameLike("%" + param + "%");
			} else if (key.equals("deptNum")) {
				criteria.andDeptNumIn(DataCache.getDepartments().values().stream().filter(department -> department.getDeptName().indexOf(param)>-1 ).map(Department::getDeptNum).collect(Collectors.toList()));
			} else if (key.equals("deptGroup")) {
				criteria.andDeptGroupIn(DataCache.getDepartments().values().stream().filter(department -> department.getDeptName().indexOf(param)>-1 ).map(Department::getDeptNum).collect(Collectors.toList()));
			} else if (key.equals("agentNum")) {
				criteria.andPloNumEqualTo(agentService.getPloNumByAgent(param));
			}
		}

		Map<String, Integer> pageParams = Util.innitTablePages(params);
		example.setOrderByClause(" dept_num asc, dept_group asc, job_level desc , plo_num asc ");

		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		List<Employee> emps = employeeService.getEmployeeByExample(example);
		// Department dept;
		for (Employee emp : emps) {
			String userId = emp.getPloNum();
			emp.setDeptName(Util.isNullorEmpty(emp.getDeptNum()) ?"":DataCache.getDepartments().get(emp.getDeptNum()).getDeptName());
			emp.setGroupName(Util.isNullorEmpty(emp.getDeptGroup()) ?"":DataCache.getDepartments().get(emp.getDeptGroup()).getDeptName());
			String agentNum = "";
			agentNum = agentService.getCurrentAgentNum(userId);
			agentNum = Util.stringNulltoEmpty(agentNum);
			emp.setAgentNum(agentNum);

			UserRole userRole = userRoleService.getUserRolesByPrimaryKey(userId);
			if (!Util.isNullorEmpty(userRole)) {
				emp.setRoles(userRole.getRoles());
			}
		}

		return Msg.success(new DataPage<Employee>(emps));
	}

	@PostMapping(value = "disppatch/role/{userId}")
	@SaCheckPermission("employee:role")
	@MyLog(title = "[sys-user]员工信息")
	public Msg dispatchRoles(@RequestBody Map<String, List<String>> roles, @PathVariable("userId") String userId) {
		UserRole userRole = userRoleService.getUserRolesByPrimaryKey(userId);
		UserRole userRoleRecord = new UserRole();
		userRoleRecord.setRoles(Util.listToString(roles.get("roles"), ','));
		userRoleRecord.setUserId(userId);
		if (Util.isNullorEmpty(userRole)) {
			Integer recordCount = userRoleService.insertNewUserRole(userRoleRecord);
			return Msg.success("新增 " + recordCount + " 条角色配置");
		} else {
			Integer recordCount = userRoleService.updateUserRoleByPrimaryKey(userRoleRecord);
			return Msg.success("修改" + recordCount + " 条角色配置");
		}
	}

	@PostMapping(value = "avatar")
	@MyLog(title = "[sys-user]员工信息")
	public Msg updateAvatar(@RequestBody Employee employee) {
		String userId = Util.userIdByShiro();
		if (Util.isNullorEmpty(userId)) {
			return Msg.error("没有获取账户信息！请重新登录在尝试");
		}
		employee.setPloNum(userId);
		int count = employeeService.updateAvatarByPrimary(employee);
		if (count == 0) {
			return Msg.error("头像更新失败！");
		}

		// 更新服务器缓存的数据
		DataCache.getEmployees().get(userId).setAvatar(employee.getAvatar());
		return Msg.success("头像更新成功！");
	}

	/**
	 * 按照月份获取生日信息
	 * @param month
	 * @return Msg
	 */
	@GetMapping("birthday/{month}")
	public Msg getEmployeeBirthdayByMonth(@PathVariable("month") String month) {
		if (Util.isNullorEmpty(month)) {
			return Msg.error("没有获取到正确到月份");
		}
		List<HashMap<String, Object>> brthdays = employeeService.getEmployeeBirthday(month);
		return Msg.success(brthdays);
	}
	/**
	 * 人力资源控件（模糊查询）
	 * @param
	 * @return
	 */
	@GetMapping("find")
	public Msg getEmployeeList(@RequestParam Map<String, String> params) {
		String value = params.get("value");
		String status = params.get("status");
		if(Util.isNullorEmpty(status)){
			List<Employee> emps = DataCache.getEmployees().values().stream().filter(e -> e.getPloName().indexOf(value)>-1 || e.getPloNum().indexOf(value)>-1  || e.getGroupName().indexOf(value)>-1 || e.getDeptName().indexOf(value)>-1).collect(Collectors.toList());
			return Msg.success(sanitizeForPicker(emps));
		}else {
			List<Employee> emps = DataCache.getEmployees().values().stream().filter(e -> e.getPloStatus().equals(status) && (e.getPloName().indexOf(value)>-1 || e.getPloNum().indexOf(value)>-1  || e.getGroupName().indexOf(value)>-1 || e.getDeptName().indexOf(value)>-1)).collect(Collectors.toList());
			return Msg.success(sanitizeForPicker(emps));
		}
	}

	@GetMapping("find/{jobLevels}")
	public Msg getEmployeeListByJobLevel(@PathVariable("jobLevels") String jobLevels) {
		if(Util.isNullorEmpty(jobLevels) || jobLevels.length()<3) return Msg.success(null);
		List<Employee> emps = DataCache.getEmployees().values().stream().filter(e -> jobLevels.indexOf(e.getJobLevel())>-1 && e.getPloStatus().equals("00")).collect(Collectors.toList());
		return Msg.success(sanitizeForPicker(emps));
	}

	/**
	 * 选人控件脱敏：仅复制选人所需的非敏感字段，清空手机号/住址等 PII，且不修改 DataCache 缓存对象。
	 */
	private List<Employee> sanitizeForPicker(List<Employee> emps) {
		if (emps == null) {
			return null;
		}
		List<Employee> result = new ArrayList<>();
		for (Employee src : emps) {
			Employee e = new Employee();
			e.setPloNum(src.getPloNum());
			e.setPloName(src.getPloName());
			e.setDeptName(src.getDeptName());
			e.setGroupName(src.getGroupName());
			e.setDeptNum(src.getDeptNum());
			e.setDeptGroup(src.getDeptGroup());
			e.setBatchGroup(src.getBatchGroup());
			e.setJobLevel(src.getJobLevel());
			e.setPloStatus(src.getPloStatus());
			e.setInDate(src.getInDate());
			e.setOutDate(src.getOutDate());
			e.setJobStatus(src.getJobStatus());
			e.setAvatar(src.getAvatar());
			result.add(e);
		}
		return result;
	}
	/**
	 * 人力资源控件TREE
	 * @param type
	 * @return
	 */
	@GetMapping("tree/{type}")
	public Msg getEmployeeTree(@PathVariable("type") String type) {

		List<Employee> emps = employeeService.selectEmployeeInit();
		List<Department> depts = departmentService.listDepartment(null);
		List<Tree> treeList = new ArrayList<Tree>();
		if ("all".equals(type)) {
			List<Department> deptList = depts.stream().filter(e -> Util.isNullorEmpty(e.getUpDept()))
					.sorted(Comparator.comparing(Department::getDeptNum)).collect(Collectors.toList());
			for (Department dept : deptList) {
				Tree tree = new Tree();
				tree.setId(dept.getDeptNum());
				tree.setLabel(dept.getDeptName());
				List<Department> groupList = depts.stream()
						.filter(e -> !Util.isNullorEmpty(e.getUpDept()) && e.getUpDept().equals(dept.getDeptNum()))
						.sorted(Comparator.comparing(Department::getDeptNum)).collect(Collectors.toList());
				List<Tree> treeList2 = new ArrayList<Tree>();
				for (Department group : groupList) {
					Tree tree2 = new Tree();
					tree2.setId(group.getDeptNum());
					tree2.setLabel(group.getDeptName());
					List<Employee> empList = emps.stream()
							.filter(e -> e.getPloStatus().equals("00") && !Util.isNullorEmpty(e.getDeptGroup())
									&& e.getDeptGroup().equals(group.getDeptNum()))
							.sorted(Comparator.comparing(Employee::getDeptGroup)).collect(Collectors.toList());
					List<Tree> treeList3 = new ArrayList<Tree>();
					for (Employee emp : empList) {
						Tree tree3 = new Tree();
						tree3.setId(emp.getPloNum());
						tree3.setLabel(emp.getPloName() + "/" + emp.getPloNum() + "[" + emp.getBatchGroup() + "]");
						treeList3.add(tree3);
					}
					tree2.setChildren(treeList3);
					treeList2.add(tree2);
				}
				// 无组别人员
				List<Employee> empNoGroupList = emps.stream()
						.filter(e -> e.getPloStatus().equals("00") && Util.isNullorEmpty(e.getDeptGroup())
								&& e.getDeptNum().equals(dept.getDeptNum()))
						.sorted(Comparator.comparing(Employee::getPloNum)).collect(Collectors.toList());
				for (Employee emp : empNoGroupList) {
					Tree tree2 = new Tree();
					tree2.setId(emp.getPloNum());
					tree2.setLabel(emp.getPloName() + "/" + emp.getPloNum() + "[" + emp.getBatchGroup() + "]");
					treeList2.add(tree2);
				}

				tree.setChildren(treeList2);
				treeList.add(tree);
			}
		} else if ("test".equals(type)) {
			List<Department> deptList = depts.stream().filter(e -> Util.isNullorEmpty(e.getUpDept()))
					.sorted(Comparator.comparing(Department::getDeptNum)).collect(Collectors.toList());
			return Msg.success().put("deptListSize", deptList.size()).put("empsSize", emps.size());
		}
//		else {
//			List<Department> deptList = depts.stream().filter(e -> Util.isNullorEmpty(e.getUpDept())).sorted(Comparator.comparing(Department::getDeptNum)).collect(Collectors.toList());
//			
//			for (Department dept : deptList) {
//				LOG.info("循环科室数据{}",dept.toString());
//				Tree tree = new Tree();
//				tree.setId(dept.getDeptNum());
//				tree.setLabel(dept.getDeptName());
//				List<Employee> empList = emps.stream().filter(e -> e.getJobLevel().equals(type) && e.getDeptGroup()!=null && e.getPloStatus().equals("00") && e.getDeptNum().equals(dept.getDeptNum()) ).sorted(Comparator.comparing(Employee::getDeptGroup)).collect(Collectors.toList());
//				List<Tree> treeList2 = new ArrayList<Tree>();
//				for (Employee emp : empList) {
//					Tree tree2 = new Tree();
//					tree2.setId(emp.getPloNum());
//					tree2.setLabel(emp.getPloName() + "/" +emp.getPloNum() + "[" +emp.getDeptGroup()+"]");
//					treeList2.add(tree2);
//				}
//				tree.setChildren(treeList2);
//				treeList.add(tree);
//			}
//		}
		else {
			List<Department> deptList = depts.stream().filter(e -> Util.isNullorEmpty(e.getUpDept()))
					.sorted(Comparator.comparing(Department::getDeptNum)).collect(Collectors.toList());
			for (Department dept : deptList) {
				Tree tree = new Tree();
				tree.setId(dept.getDeptNum());
				tree.setLabel(dept.getDeptName());
				List<Department> groupList = depts.stream()
						.filter(e -> !Util.isNullorEmpty(e.getUpDept()) && e.getUpDept().equals(dept.getDeptNum()))
						.sorted(Comparator.comparing(Department::getDeptNum)).collect(Collectors.toList());
				List<Tree> treeList2 = new ArrayList<Tree>();
				for (Department group : groupList) {
					Tree tree2 = new Tree();
					tree2.setId(group.getDeptNum());
					tree2.setLabel(group.getDeptName());
					List<Employee> empList = emps.stream()
							.filter(e -> e.getJobLevel().equals(type) && e.getDeptGroup() != null
									&& e.getPloStatus().equals("00") && !Util.isNullorEmpty(e.getDeptGroup())
									&& e.getDeptGroup().equals(group.getDeptNum()))
							.collect(Collectors.toList());
					List<Tree> treeList3 = new ArrayList<Tree>();
					for (Employee emp : empList) {
						Tree tree3 = new Tree();
						tree3.setId(emp.getPloNum());
						tree3.setLabel(emp.getPloName() + "/" + emp.getPloNum() + "[" + emp.getBatchGroup() + "]");
						treeList3.add(tree3);
					}
					tree2.setChildren(treeList3);
					treeList2.add(tree2);
				}
				// 无组别人员
				List<Employee> empNoGroupList = emps.stream()
						.filter(e -> e.getJobLevel().equals(type) && e.getPloStatus().equals("00")
								&& Util.isNullorEmpty(e.getDeptGroup()) && e.getDeptNum().equals(dept.getDeptNum()))
						.sorted(Comparator.comparing(Employee::getPloNum)).collect(Collectors.toList());
				for (Employee emp : empNoGroupList) {
					Tree tree2 = new Tree();
					tree2.setId(emp.getPloNum());
					tree2.setLabel(emp.getPloName() + "/" + emp.getPloNum() + "[" + emp.getBatchGroup() + "]");
					treeList2.add(tree2);
				}

				tree.setChildren(treeList2);
				treeList.add(tree);
			}
		}
		return Msg.success().put("tree", treeList);
	}
	
	//		报表模块
	@SaCheckPermission("employee:report")
	@GetMapping("/report")
	@MyLog(title = "[sys-user]员工信息", content = "导出报表")
	public void reportExamScoreHum(HttpServletResponse response,@RequestParam Map<String, String> params) throws IOException {
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");
		List<Employee> list = DataCache.getEmployees().values().stream().filter(e ->"00".equals(e.getPloStatus()) ).collect(Collectors.toList());
		String fileName = "在职人员名单";
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
		HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtils.simpleExcelTemplateStyle();
		// 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
		// 如果这里想使用03 则 传入excelType参数即可
		EasyExcel.write(response.getOutputStream(), Employee.class).registerWriteHandler(horizontalCellStyleStrategy)
				.sheet("在职人员名单").doWrite(list);
	}


}
