package com.cmbccd.ulms.edu.dao;

import com.cmbccd.ulms.edu.domain.QuesScore;
import com.cmbccd.ulms.edu.domain.QuesScoreExample;
import com.cmbccd.ulms.edu.domain.report.QuesScoreHum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuesScoreMapper {
    long countByExample(QuesScoreExample example);

    int deleteByExample(QuesScoreExample example);

    int deleteByPrimaryKey(String journo);

    int insert(QuesScore record);

    int insertSelective(QuesScore record);

    List<QuesScore> selectByExample(QuesScoreExample example);

    QuesScore selectByPrimaryKey(String journo);

    int updateByExampleSelective(@Param("record") QuesScore record, @Param("example") QuesScoreExample example);

    int updateByExample(@Param("record") QuesScore record, @Param("example") QuesScoreExample example);

    int updateByPrimaryKeySelective(QuesScore record);

    int updateByPrimaryKey(QuesScore record);
    // 更新考试全部得分
    int updateUserScore(String examCode);
    
    // 更新考的得分状态
    int completeScoreStatus(String examCode);
    
    List<QuesScoreHum>reportQuesScoreHum(String examCode);
    
    int deleteByExamCode(String examCode);
}