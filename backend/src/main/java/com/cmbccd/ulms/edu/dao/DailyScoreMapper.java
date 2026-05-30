package com.cmbccd.ulms.edu.dao;

import com.cmbccd.ulms.edu.domain.DailyScore;
import com.cmbccd.ulms.edu.domain.DailyScoreExample;
import com.cmbccd.ulms.edu.domain.report.DailyScoreSummary;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DailyScoreMapper {
    long countByExample(DailyScoreExample example);

    int deleteByExample(DailyScoreExample example);

    int insert(DailyScore record);

    int insertSelective(DailyScore record);

    List<DailyScore> selectByExample(DailyScoreExample example);

    int updateByExampleSelective(@Param("record") DailyScore record, @Param("example") DailyScoreExample example);

    int updateByExample(@Param("record") DailyScore record, @Param("example") DailyScoreExample example);

    List<DailyScoreSummary> SelectDailyScoreSummay(@Param("example") DailyScoreExample example,@Param("groupBy") String groupBy);

    DailyScore getDailyScore(@Param("quesDate") String quesDate, @Param("userId") String userId);
}