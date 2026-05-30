package com.cmbccd.ulms.sys.dao;

import com.cmbccd.ulms.sys.domain.Station;
import com.cmbccd.ulms.sys.domain.StationExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StationMapper {
    long countByExample(StationExample example);

    int deleteByExample(StationExample example);

    int insert(Station record);

    int insertSelective(Station record);

    List<Station> selectByExample(StationExample example);

    int updateByExampleSelective(@Param("record") Station record, @Param("example") StationExample example);

    int updateByExample(@Param("record") Station record, @Param("example") StationExample example);
    
    Station selectByIp(String pcIp);
    
    Station selectByIpNoMemo(String pcIp);
    
    int updateMemoByIp(String pcIp, String memo, String userId);
}