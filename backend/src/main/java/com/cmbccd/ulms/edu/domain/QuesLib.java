package com.cmbccd.ulms.edu.domain;

import java.util.List;

public class QuesLib {
    private String libCode;

    private String libName;

    private Integer libLevel;

    private String upCode;

    private String libDiff;

    private Integer quesNum;

    private Integer libStat;

    private List<QuesLib> children;
    
    public String getLibCode() {
        return libCode;
    }

    public void setLibCode(String libCode) {
        this.libCode = libCode == null ? null : libCode.trim();
    }

    public String getLibName() {
        return libName;
    }

    public void setLibName(String libName) {
        this.libName = libName == null ? null : libName.trim();
    }

    public Integer getLibLevel() {
        return libLevel;
    }

    public void setLibLevel(Integer libLevel) {
        this.libLevel = libLevel;
    }

    public String getUpCode() {
        return upCode;
    }

    public void setUpCode(String upCode) {
        this.upCode = upCode == null ? null : upCode.trim();
    }

    public String getLibDiff() {
        return libDiff;
    }

    public void setLibDiff(String libDiff) {
        this.libDiff = libDiff == null ? null : libDiff.trim();
    }

    public Integer getQuesNum() {
        return quesNum;
    }

    public void setQuesNum(Integer quesNum) {
        this.quesNum = quesNum;
    }

    public Integer getLibStat() {
        return libStat;
    }

    public void setLibStat(Integer libStat) {
        this.libStat = libStat;
    }

	public List<QuesLib> getChildren() {
		return children;
	}

	public void setChildren(List<QuesLib> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "QuesLib [libCode=" + libCode + ", libName=" + libName + ", libLevel=" + libLevel + ", upCode=" + upCode
				+ ", libDiff=" + libDiff + ", quesNum=" + quesNum + ", libStat=" + libStat + ", children=" + children
				+ "]";
	}
    
    
}