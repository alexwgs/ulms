package com.cmbccd.ulms.sys.domain;

import java.util.List;

public class Department {
    private String deptNum;

    private String deptName;

    private String empSum;

    private String upDept;

    private String organ;
    
    private List<Department> children;

    public String getDeptNum() {
        return deptNum;
    }

    public void setDeptNum(String deptNum) {
        this.deptNum = deptNum == null ? null : deptNum.trim();
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public String getEmpSum() {
        return empSum;
    }

    public void setEmpSum(String empSum) {
        this.empSum = empSum == null ? null : empSum.trim();
    }

    public String getUpDept() {
        return upDept;
    }

    public void setUpDept(String upDept) {
        this.upDept = upDept == null ? null : upDept.trim();
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ == null ? null : organ.trim();
    }

	public List<Department> getChildren() {
		return children;
	}

	public void setChildren(List<Department> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "Department [deptNum=" + deptNum + ", deptName=" + deptName + ", empSum=" + empSum + ", upDept=" + upDept
				+ ", organ=" + organ + ", children=" + children + "]";
	}
    
    
}