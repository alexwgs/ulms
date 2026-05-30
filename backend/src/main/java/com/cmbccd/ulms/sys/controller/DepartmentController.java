package com.cmbccd.ulms.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.domain.Tree;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Department;
import com.cmbccd.ulms.sys.domain.DepartmentExample;
import com.cmbccd.ulms.sys.domain.DepartmentExample.Criteria;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.sys.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author WeiGenSheng
 * @Time 2020年4月29日 上午9:36:01
 * @Version 1.0
 * @Description:
 */
@RestController
@CrossOrigin
@RequestMapping("/department")
public class DepartmentController {

	@Resource
	private DepartmentService departmentService;

	@GetMapping(value = "/tree")
	@SaCheckPermission("department:list")
	public Msg getDepartmentTree() {
		List<Department> departmentTree = departmentService.getDepartmentTree();
		return Msg.success(departmentTree);
	}

	@GetMapping("")
	public Msg listDepartment() {
		List<Department> depts = departmentService.listDepartment(null);
		return Msg.success(depts);
	}

	@DeleteMapping(value = "/{deptNum}")
	@SaCheckPermission("department:delete")
	@MyLog(title = "[sys-dept]组织架构")
	public Msg deleteDepartment(@PathVariable("deptNum") String deptNum) {
		if (deptNum == null || deptNum.length() != 4) {
			return Msg.error("没有获取到部门信息！");
		}
		DepartmentExample example = new DepartmentExample();
		Criteria criteria = example.createCriteria();
		criteria.andDeptNumEqualTo(deptNum);
		int delCount = departmentService.deleteDepartment(example);
		if (delCount == 0) {
			return Msg.error();
		}
		return Msg.success("成功删除 " + delCount + " 条记录！");
	}

	@PutMapping(value = "/{deptNum}")
	@SaCheckPermission("department:update")
	@MyLog(title = "[sys-dept]组织架构")
	public Msg updateDepartment(@RequestBody Department department, @PathVariable("deptNum") String deptNum) {
		if (deptNum == null || deptNum.length() != 4) {
			return Msg.error("没有获取到部门信息！");
		}
		DepartmentExample example = new DepartmentExample();
		Criteria criteria = example.createCriteria();
		criteria.andDeptNumEqualTo(deptNum);
		int updateCount = departmentService.updateDepartment(department, example);
		return Msg.success("成功修改 " + updateCount + " 条记录！");
	}

	@PostMapping(value = "")
	@SaCheckPermission("department:add")
	@MyLog(title = "[sys-dept]组织架构")
	public Msg addDepartment(@RequestBody Department department) {
		int addCount = departmentService.insertDepartment(department);
		return Msg.success("成功新增 " + addCount + " 条记录！");
	}
	
	@GetMapping("tree/{type}")
	public Msg getDepartmentTree(@PathVariable("type") String type) {
		List<Department> depts = departmentService.listDepartment(null);
		if(Util.isNullorEmpty(type))	 type="all";
		List<Tree> treeList = new ArrayList<Tree>();
		List<Department> deptList = new ArrayList<Department>();
		if ("all".equals(type)) {
			deptList = depts.stream().filter(e -> Util.isNullorEmpty(e.getUpDept()))
					.sorted(Comparator.comparing(Department::getDeptNum)).collect(Collectors.toList());
		}else if ("cust".equals(type)) {
			deptList = depts.stream().filter(e -> Util.isNullorEmpty(e.getUpDept()) && (Integer.parseInt(e.getDeptNum()) >= 3000 && Integer.parseInt(e.getDeptNum()) <= 4000))
					.sorted(Comparator.comparing(Department::getDeptNum)).collect(Collectors.toList());
		}else if ("dept".equals(type)) {
			deptList = depts.stream().filter(e -> Util.isNullorEmpty(e.getUpDept()) && (Integer.parseInt(e.getDeptNum()) >= 3000 && Integer.parseInt(e.getDeptNum()) <= 4000))
					.sorted(Comparator.comparing(Department::getDeptNum)).collect(Collectors.toList());
		}
		for (Department dept : deptList) {
			Tree tree = new Tree();
			tree.setId(dept.getDeptNum());
			tree.setLabel(dept.getDeptName());
			List<Department> groupList = new ArrayList<>();
			if (!"dept".equals(type)) {
				groupList = depts.stream()
					.filter(e -> !Util.isNullorEmpty(e.getUpDept()) && e.getUpDept().equals(dept.getDeptNum()))
					.sorted(Comparator.comparing(Department::getDeptNum)).collect(Collectors.toList());
			}
			List<Tree> treeList2 = new ArrayList<Tree>();
			for (Department group : groupList) {
				Tree tree2 = new Tree();
				tree2.setId(group.getDeptNum());
				tree2.setLabel(group.getDeptName());
				treeList2.add(tree2);
			}
			tree.setChildren(treeList2);
			treeList.add(tree);
		}
		return Msg.success(treeList);
	}
}
