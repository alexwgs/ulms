package com.cmbccd.ulms.edu.dao;

import com.cmbccd.ulms.edu.domain.QuesTemp;
import com.cmbccd.ulms.edu.domain.QuesTempExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuesTempMapper {
    long countByExample(QuesTempExample example);

    int deleteByExample(QuesTempExample example);

    int insert(QuesTemp record);

    int insertSelective(QuesTemp record);

    List<QuesTemp> selectByExample(QuesTempExample example);

    int updateByExampleSelective(@Param("record") QuesTemp record, @Param("example") QuesTempExample example);

    int updateByExample(@Param("record") QuesTemp record, @Param("example") QuesTempExample example);
}