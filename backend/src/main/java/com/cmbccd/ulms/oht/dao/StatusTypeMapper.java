package com.cmbccd.ulms.oht.dao;

import com.cmbccd.ulms.oht.domain.StatusType;
import com.cmbccd.ulms.oht.domain.StatusTypeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StatusTypeMapper {
    long countByExample(StatusTypeExample example);

    int deleteByExample(StatusTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StatusType record);

    int insertSelective(StatusType record);

    List<StatusType> selectByExample(StatusTypeExample example);

    StatusType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StatusType record, @Param("example") StatusTypeExample example);

    int updateByExample(@Param("record") StatusType record, @Param("example") StatusTypeExample example);

    int updateByPrimaryKeySelective(StatusType record);

    int updateByPrimaryKey(StatusType record);
    
    int selectNewId();
    
    int identityNameOhtFlag(String[] statusNames);
    
    List<Map<String,String>> getUserStatusAndOhtFlag(Integer id);
    
    List<StatusType> getAllStatusType();
}