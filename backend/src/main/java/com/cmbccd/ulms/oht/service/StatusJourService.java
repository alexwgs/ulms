package com.cmbccd.ulms.oht.service;

import com.cmbccd.ulms.oht.domain.StatusJour;

import java.util.List;
import java.util.Map;
/**
 * 
* <p>Title: StatusJourService.java</p>  
* <p>Description: </p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
public interface StatusJourService {
	
    int selectNewId();
    
	public StatusJour getUserLatestStatusJour (String date,String userId);
	/**
	 * 插入一条新的工作状态切换，同时结算上一状态（或有）
	 * @param record
	 * @return
	 */
	public int insertNewStatusJour(StatusJour record);
	/**
	 * 更新最后一次业务主任工作状态
	 * @param userId
	 * @param endTime
	 * @return
	 */
	public int updateLastStatusJour(String userId, String endTime);
	// 获取某日业务主任工作状态
	public List<StatusJour> selectStatusJourList(String userId, String dataDate);
	// 获取时间段内总工作时长
    public List<StatusJour> getUserStatusTotal(String userId,String begDate,String endDate);
    // 获取时间段内某一工作状态ID的总时长
    public int getUserStatusIdTotal(String userId,String begDate,String endDate, Integer statusId);

    public List<Map<String, String>> getStatusDetailByUserId(String userId, String dataDate);
    
    List<Map<String, String>> getStatusDetailByUserIds(String [] userId, String dataDate);

    //获取用户在指定时间段内的可接单时长
    int getAvailableStatusTimeByUserId(String userId, String begDate, String endDate);
}
