package com.cmbccd.ulms.edu.service;

import com.cmbccd.ulms.edu.domain.DailyConfig;
import com.cmbccd.ulms.edu.domain.DailyScore;
import com.cmbccd.ulms.edu.domain.DailyScoreExample;
import com.cmbccd.ulms.edu.domain.report.DailyScoreSummary;

import java.util.List;

public interface DailyScoreService {

    /**
     *
     * @param quesDate
     * @param userId 可为空，空值返回当前用户工号
     * @return
     */
    DailyScore get(String quesDate, String userId);

    DailyScore init(DailyConfig dailyConfig);

    List<DailyScore> list(DailyScoreExample example);

    int getDailyTaskNum(String begDate,String endDate);
    List<DailyScoreSummary> listSumary(DailyScoreExample example, String groupBy, int total);

    int create(DailyScore record);

    int update(DailyScore record);

    int updateSelective(DailyScore record);

    int delete(String quesDate, String userId);

    DailyScore submitQuestion(DailyScore dailyScore,int resultFlag, String userAnswer);

    DailyScore submitMood(DailyScore dailyScore);

    DailyScore submitArticalStudy(DailyScore dailyScore);
}
