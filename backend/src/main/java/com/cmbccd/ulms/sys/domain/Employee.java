package com.cmbccd.ulms.sys.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

public class Employee {
    @ExcelProperty("员编")
    private String ploNum;
    @ExcelProperty("姓名")
    private String ploName;
    @ExcelProperty("科室")
    private String deptName;
    @ExcelProperty("组别")
    private String groupName;
    @ExcelIgnore
    private String deptNum;
    @ExcelIgnore
    private String deptGroup;
    @ExcelProperty("期数")
    private String batchGroup;
    @ExcelIgnore
    private String jobLevel;
    @ExcelProperty("状态")
    private String ploStatus;
    @ExcelProperty("入职日期")
    private String inDate;
    @ExcelProperty("离职日期")
    private String outDate;
    @ExcelIgnore
    private String ploDesc;
    @ExcelIgnore
    private String organ;
    @ExcelIgnore
    private String flag;
    @ExcelIgnore
    private String engName;
    @ExcelIgnore
    private String ploAddr;
    @ExcelIgnore
    private String ploMobil;
    @ExcelProperty("岗位")
    private String jobStatus;
    @ExcelIgnore
    private String officeCmb;
    @ExcelIgnore
    private String turnType;
    @ExcelIgnore
    private String pzjc;
    @ExcelIgnore
    private String avatar;
    @ExcelIgnore
    private String agentNum;
    @ExcelIgnore
    private String roles;

    
    
	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
	
    public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getAgentNum() {
		return agentNum;
	}

	public void setAgentNum(String agentNum) {
		this.agentNum = agentNum;
	}

	public String getPloNum() {
        return ploNum;
    }

    public void setPloNum(String ploNum) {
        this.ploNum = ploNum == null ? null : ploNum.trim();
    }

    public String getPloName() {
        return ploName;
    }

    public void setPloName(String ploName) {
        this.ploName = ploName == null ? null : ploName.trim();
    }

    public String getDeptNum() {
        return deptNum;
    }

    public void setDeptNum(String deptNum) {
        this.deptNum = deptNum == null ? null : deptNum.trim();
    }

    public String getDeptGroup() {
        return deptGroup;
    }

    public void setDeptGroup(String deptGroup) {
        this.deptGroup = deptGroup == null ? null : deptGroup.trim();
    }

    public String getBatchGroup() {
        return batchGroup;
    }

    public void setBatchGroup(String batchGroup) {
        this.batchGroup = batchGroup == null ? null : batchGroup.trim();
    }

    public String getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel == null ? null : jobLevel.trim();
    }

    public String getPloStatus() {
        return ploStatus;
    }

    public void setPloStatus(String ploStatus) {
        this.ploStatus = ploStatus == null ? null : ploStatus.trim();
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate == null ? null : inDate.trim();
    }

    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate == null ? null : outDate.trim();
    }

    public String getPloDesc() {
        return ploDesc;
    }

    public void setPloDesc(String ploDesc) {
        this.ploDesc = ploDesc == null ? null : ploDesc.trim();
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ == null ? null : organ.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName == null ? null : engName.trim();
    }

    public String getPloAddr() {
        return ploAddr;
    }

    public void setPloAddr(String ploAddr) {
        this.ploAddr = ploAddr == null ? null : ploAddr.trim();
    }

    public String getPloMobil() {
        return ploMobil;
    }

    public void setPloMobil(String ploMobil) {
        this.ploMobil = ploMobil == null ? null : ploMobil.trim();
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus == null ? null : jobStatus.trim();
    }

    public String getOfficeCmb() {
        return officeCmb;
    }

    public void setOfficeCmb(String officeCmb) {
        this.officeCmb = officeCmb == null ? null : officeCmb.trim();
    }

    public String getTurnType() {
        return turnType;
    }

    public void setTurnType(String turnType) {
        this.turnType = turnType == null ? null : turnType.trim();
    }

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	
	public String getPzjc() {
		return pzjc;
	}

	public void setPzjc(String pzjc) {
		this.pzjc = pzjc;
	}

	@Override
	public String toString() {
		return "Employee [ploNum=" + ploNum + ", ploName=" + ploName + ", deptNum=" + deptNum + ", deptGroup="
				+ deptGroup + ", batchGroup=" + batchGroup + ", jobLevel=" + jobLevel + ", ploStatus=" + ploStatus
				+ ", inDate=" + inDate + ", outDate=" + outDate + ", ploDesc=" + ploDesc + ", organ=" + organ
				+ ", flag=" + flag + ", engName=" + engName + ", ploAddr=" + ploAddr + ", ploMobil=" + ploMobil
				+ ", jobStatus=" + jobStatus + ", officeCmb=" + officeCmb + ", turnType=" + turnType + ", deptName="
				+ deptName + ", groupName=" + groupName + ", agentNum=" + agentNum + "]";
	}

}