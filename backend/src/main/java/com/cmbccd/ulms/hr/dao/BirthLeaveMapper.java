package com.cmbccd.ulms.hr.dao;

import com.cmbccd.ulms.hr.domain.BirthLeave;
import com.cmbccd.ulms.hr.domain.BirthLeaveExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BirthLeaveMapper {
    long countByExample(BirthLeaveExample example);

    int deleteByExample(BirthLeaveExample example);

    int deleteByPrimaryKey(String id);

    int insert(BirthLeave record);

    int insertSelective(BirthLeave record);

    List<BirthLeave> selectByExample(BirthLeaveExample example);

    BirthLeave selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") BirthLeave record, @Param("example") BirthLeaveExample example);

    int updateByExample(@Param("record") BirthLeave record, @Param("example") BirthLeaveExample example);

    int updateByPrimaryKeySelective(BirthLeave record);

    int updateByPrimaryKey(BirthLeave record);

    List<BirthLeave> listBirthLeave(String ploNum);
}