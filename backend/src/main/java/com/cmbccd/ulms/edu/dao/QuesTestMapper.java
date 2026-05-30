package com.cmbccd.ulms.edu.dao;

import com.cmbccd.ulms.edu.domain.QuesTest;
import com.cmbccd.ulms.edu.domain.QuesTestExample;
import com.cmbccd.ulms.edu.domain.report.QuesTestDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuesTestMapper {
    long countByExample(QuesTestExample example);

    int deleteByExample(QuesTestExample example);

    int deleteByPrimaryKey(String journo);

    int insert(QuesTest record);

    int insertSelective(QuesTest record);

    List<QuesTest> selectByExample(QuesTestExample example);

    QuesTest selectByPrimaryKey(String journo);

    int updateByExampleSelective(@Param("record") QuesTest record, @Param("example") QuesTestExample example);

    int updateByExample(@Param("record") QuesTest record, @Param("example") QuesTestExample example);

    int updateByPrimaryKeySelective(QuesTest record);

    int updateByPrimaryKey(QuesTest record);
    
    Double getUserScore(String examCode, String ploNum);
    
    // 报表
    List<QuesTestDetail> reportQuesTestDetail(String examCode);
}