package com.cmbccd.ulms.hr.dao;

import com.cmbccd.ulms.hr.domain.HrLabel;
import com.cmbccd.ulms.hr.domain.HrLabelExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HrLabelMapper {
    long countByExample(HrLabelExample example);

    int deleteByExample(HrLabelExample example);

    int deleteByPrimaryKey(String id);

    int insert(HrLabel record);

    int insertSelective(HrLabel record);

    List<HrLabel> selectByExample(HrLabelExample example);

    HrLabel selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") HrLabel record, @Param("example") HrLabelExample example);

    int updateByExample(@Param("record") HrLabel record, @Param("example") HrLabelExample example);

    int updateByPrimaryKeySelective(HrLabel record);

    int updateByPrimaryKey(HrLabel record);

    List<HrLabel> selectResignedLabels(@Param("perfMonth") String perfMonth);

    List<HrLabel> selectBirthLabels(@Param("perfMonth") String perfMonth);

    List<HrLabel> selectLabelHumanFlag(String perfMonth);
}