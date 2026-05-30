package com.cmbccd.ulms.oht.dao;

import com.cmbccd.ulms.oht.domain.StatusJour;
import com.cmbccd.ulms.oht.domain.StatusJourExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StatusJourMapper {
    Integer countByExample(StatusJourExample example);

    int deleteByExample(StatusJourExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StatusJour record);

    int insertSelective(StatusJour record);

    List<StatusJour> selectByExample(StatusJourExample example);

    StatusJour selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StatusJour record, @Param("example") StatusJourExample example);

    int updateByExample(@Param("record") StatusJour record, @Param("example") StatusJourExample example);

    int updateByPrimaryKeySelective(StatusJour record);

    int updateByPrimaryKey(StatusJour record);
    
    int selectNewId();
    
    int updateLastStatusJour(String userId, String endTime);
    
    List<StatusJour> getUserStatusTotal(String userId,String begDate,String endDate);
    
    int getUserStatusIdTotal(@Param("userId") String userId,@Param("begDate") String begDate,@Param("endDate") String endDate,@Param("statusId") Integer statusId);

    List<Map<String, String>> getStatusDetailByUserId(String userId, String dataDate);
    
    List<Map<String, String>> getStatusDetailByUserIds(StatusJourExample example);
    
    Integer getStatusDurationByOhtFlag(@Param("ohtFlag") Integer ohtFlag,@Param("userId") String userId,@Param("begDate") String begDate,@Param("endDate") String endDate);
    Integer getOnCallDurationByOhtFlag(@Param("userId") String userId,@Param("begDate") String begDate,@Param("endDate") String endDate);

    Integer getAvailableStatusTimeByUserId(String userId, String begDate, String endDate);
}