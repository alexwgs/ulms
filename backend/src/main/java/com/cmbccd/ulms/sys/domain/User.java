package com.cmbccd.ulms.sys.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

public class User implements Serializable {
    private String code;

    private String xzjgCode;

    private String xzjgJghm;

    private String czyCode;

    private String name;

    private String lxdh;

    private String password;

    private String zjm;

    private String areaCode;

    private BigDecimal myts;

    private Date cjsj;

    private String roleid;

    private Date zhdl;

    private String sfzx;

    private String ip;

    private String sfYxjl;

    private String xzczyCode;

    private String xzczyName;

    private Date xzrq;

    private String xgczyCode;

    private String xgczyName;

    private Date xgrq;

    private Date factrq;
    
    private boolean remeberMe;
    
    private Set<Integer> menuList;

    

	public Set<Integer> getMenuList() {
		return menuList;
	}

	public void setMenuList(Set<Integer> menuList) {
		this.menuList = menuList;
	}

	public boolean isRemeberMe() {
		return remeberMe;
	}

	public void setRemeberMe(boolean remeberMe) {
		this.remeberMe = remeberMe;
	}

	public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getXzjgCode() {
        return xzjgCode;
    }

    public void setXzjgCode(String xzjgCode) {
        this.xzjgCode = xzjgCode == null ? null : xzjgCode.trim();
    }

    public String getXzjgJghm() {
        return xzjgJghm;
    }

    public void setXzjgJghm(String xzjgJghm) {
        this.xzjgJghm = xzjgJghm == null ? null : xzjgJghm.trim();
    }

    public String getCzyCode() {
        return czyCode;
    }

    public void setCzyCode(String czyCode) {
        this.czyCode = czyCode == null ? null : czyCode.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh == null ? null : lxdh.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getZjm() {
        return zjm;
    }

    public void setZjm(String zjm) {
        this.zjm = zjm == null ? null : zjm.trim();
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public BigDecimal getMyts() {
        return myts;
    }

    public void setMyts(BigDecimal myts) {
        this.myts = myts;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid == null ? null : roleid.trim();
    }

    public Date getZhdl() {
        return zhdl;
    }

    public void setZhdl(Date zhdl) {
        this.zhdl = zhdl;
    }

    public String getSfzx() {
        return sfzx;
    }

    public void setSfzx(String sfzx) {
        this.sfzx = sfzx == null ? null : sfzx.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getSfYxjl() {
        return sfYxjl;
    }

    public void setSfYxjl(String sfYxjl) {
        this.sfYxjl = sfYxjl == null ? null : sfYxjl.trim();
    }

    public String getXzczyCode() {
        return xzczyCode;
    }

    public void setXzczyCode(String xzczyCode) {
        this.xzczyCode = xzczyCode == null ? null : xzczyCode.trim();
    }

    public String getXzczyName() {
        return xzczyName;
    }

    public void setXzczyName(String xzczyName) {
        this.xzczyName = xzczyName == null ? null : xzczyName.trim();
    }

    public Date getXzrq() {
        return xzrq;
    }

    public void setXzrq(Date xzrq) {
        this.xzrq = xzrq;
    }

    public String getXgczyCode() {
        return xgczyCode;
    }

    public void setXgczyCode(String xgczyCode) {
        this.xgczyCode = xgczyCode == null ? null : xgczyCode.trim();
    }

    public String getXgczyName() {
        return xgczyName;
    }

    public void setXgczyName(String xgczyName) {
        this.xgczyName = xgczyName == null ? null : xgczyName.trim();
    }

    public Date getXgrq() {
        return xgrq;
    }

    public void setXgrq(Date xgrq) {
        this.xgrq = xgrq;
    }

    public Date getFactrq() {
        return factrq;
    }

    public void setFactrq(Date factrq) {
        this.factrq = factrq;
    }

    public User() {
        this.code = "010";
        this.xzjgCode = "1";
        this.xzjgJghm = "43000100";
        this.czyCode = "0";
        this.name = "匿名";
        this.areaCode = "03";
        this.roleid = "0";
    }
}