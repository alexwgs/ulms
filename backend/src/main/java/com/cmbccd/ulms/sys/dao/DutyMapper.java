package com.cmbccd.ulms.sys.dao;

import com.cmbccd.ulms.sys.domain.Duty;
import com.cmbccd.ulms.sys.domain.DutyExample;
import com.cmbccd.ulms.sys.domain.DutyKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DutyMapper {
    long countByExample(DutyExample example);

    int deleteByExample(DutyExample example);

    int deleteByPrimaryKey(DutyKey key);

    int insert(Duty record);

    int insertSelective(Duty record);

    List<Duty> selectByExample(DutyExample example);

    Duty selectByPrimaryKey(DutyKey key);

    int updateByExampleSelective(@Param("record") Duty record, @Param("example") DutyExample example);

    int updateByExample(@Param("record") Duty record, @Param("example") DutyExample example);

    int updateByPrimaryKeySelective(Duty record);

    int updateByPrimaryKey(Duty record);
}