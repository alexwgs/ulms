package com.cmbccd.ulms.college.domain;

import com.cmbccd.ulms.sys.domain.Employee;

import java.math.BigDecimal;

public class UserPoint {

    private String ploNum;

    private Integer signNum;

    private Integer studyNum;

    private BigDecimal hours;

    private BigDecimal coin;

    private BigDecimal point;

    private Employee user;

    public String getPloNum() {
        return ploNum;
    }

    public Integer getSignNum() {
        return signNum;
    }

    public Integer getStudyNum() {
        return studyNum;
    }

    public BigDecimal getHours() {
        return hours;
    }

    public BigDecimal getCoin() {
        return coin;
    }

    public BigDecimal getPoint() {
        return point;
    }

    public Employee getUser() {
        return user;
    }

    public void setPloNum(String ploNum) {
        this.ploNum = ploNum;
    }

    public void setSignNum(Integer signNum) {
        this.signNum = signNum;
    }

    public void setStudyNum(Integer studyNum) {
        this.studyNum = studyNum;
    }

    public void setHours(BigDecimal hours) {
        this.hours = hours;
    }

    public void setCoin(BigDecimal coin) {
        this.coin = coin;
    }

    public void setPoint(BigDecimal point) {
        this.point = point;
    }

    public void setUser(Employee user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserPoints{" +
                "ploNum='" + ploNum + '\'' +
                ", signNum=" + signNum +
                ", studyNum=" + studyNum +
                ", hours=" + hours +
                ", coin=" + coin +
                ", point=" + point +
                ", user=" + user +
                '}';
    }
}
