package com.cmbccd.ulms.edu.domain;

import com.cmbccd.ulms.sys.domain.Employee;

import java.util.List;

public class BookTime {
    private String journo;

    private String bookCode;

    private Integer bookLimit;

    private String begTime;

    private String endTime;

    private Integer bookStat;
    
    private List<Employee> users; 

    public String getJourno() {
        return journo;
    }

    public void setJourno(String journo) {
        this.journo = journo == null ? null : journo.trim();
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode == null ? null : bookCode.trim();
    }

    public Integer getBookLimit() {
        return bookLimit;
    }

    public void setBookLimit(Integer bookLimit) {
        this.bookLimit = bookLimit;
    }

    public String getBegTime() {
        return begTime;
    }

    public void setBegTime(String begTime) {
        this.begTime = begTime == null ? null : begTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public Integer getBookStat() {
        return bookStat;
    }

    public void setBookStat(Integer bookStat) {
        this.bookStat = bookStat;
    }

	public List<Employee> getUsers() {
		return users;
	}

	public void setUsers(List<Employee> users) {
		this.users = users;
	}
    
    
}