package com.cmbccd.ulms.oht.domain;

import com.cmbccd.ulms.sys.domain.Employee;
import com.cmbccd.ulms.sys.domain.Station;

import jakarta.websocket.Session;
import java.util.HashMap;
import java.util.Map;

public class InitUser {

	Employee user;
	RoleList ohtRole;
	Station	station;
	Case unfinishCase;
	String ip;
	Session session;
	String userId;
	Integer userStatus;
	Integer ohtStatus;
	String identity;
	String statusTime;
	String statusName;
//	Integer orderStatus; // 0-空闲 1-求助等待中 2-处理中 3-待结案
	private Map<String,Object> data = new HashMap<String,Object>();

	public Employee getUser() {
		return user;
	}
	public void setUser(Employee user) {
		this.user = user;
	}
	public RoleList getOhtRole() {
		return ohtRole;
	}
	public void setOhtRole(RoleList ohtRole) {
		this.ohtRole = ohtRole;
	}
	public Station getStation() {
		return station;
	}
	public void setStation(Station station) {
		this.station = station;
	}
	public Case getUnfinishCase() {
		return unfinishCase;
	}
	public void setUnfinishCase(Case unfinishCase) {
		this.unfinishCase = unfinishCase;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Integer getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}
	public Integer getOhtStatus() {
		return ohtStatus;
	}
	public void setOhtStatus(Integer ohtStatus) {
		this.ohtStatus = ohtStatus;
	}
	
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	
	public String getStatusTime() {
		return statusTime;
	}
	public void setStatusTime(String statusTime) {
		this.statusTime = statusTime;
	}
	
	
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	

//	public Integer getOrderStatus() {
//		return orderStatus;
//	}
//	public void setOrderStatus(Integer orderStatus) {
//		this.orderStatus = orderStatus;
//	}
	
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	public InitUser add(String key , Object value) {
		this.getData().put(key,value);
		return this;
	}
	@Override
	public String toString() {
		return "InitUser [user=" + user + ", ohtRole=" + ohtRole + ", station=" + station + ", unfinishCase="
				+ unfinishCase + ", ip=" + ip + ", session=" + session + ", userId=" + userId + "]";
	}

	
	
}
