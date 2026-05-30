package com.cmbccd.ulms.edu.dao;

import com.cmbccd.ulms.edu.domain.DisputeQuestion;
import com.cmbccd.ulms.edu.domain.QuesDispute;
import com.cmbccd.ulms.edu.domain.QuesDisputeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuesDisputeMapper {
    long countByExample(QuesDisputeExample example);

    int deleteByExample(QuesDisputeExample example);

    int deleteByPrimaryKey(String journo);

    int insert(QuesDispute record);

    int insertSelective(QuesDispute record);

    List<QuesDispute> selectByExample(QuesDisputeExample example);

    QuesDispute selectByPrimaryKey(String journo);

    int updateByExampleSelective(@Param("record") QuesDispute record, @Param("example") QuesDisputeExample example);

    int updateByExample(@Param("record") QuesDispute record, @Param("example") QuesDisputeExample example);

    int updateByPrimaryKeySelective(QuesDispute record);

    int updateByPrimaryKey(QuesDispute record);
    
    List<DisputeQuestion> selectDisputeQuesionByExample(QuesDisputeExample example);
}