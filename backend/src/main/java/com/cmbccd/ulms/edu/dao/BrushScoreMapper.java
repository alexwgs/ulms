package com.cmbccd.ulms.edu.dao;

import com.cmbccd.ulms.edu.domain.BrushScore;
import com.cmbccd.ulms.edu.domain.BrushScoreExample;
import com.cmbccd.ulms.edu.domain.report.BrushScoreDept;
import com.cmbccd.ulms.edu.domain.report.BrushScoreGroup;
import com.cmbccd.ulms.edu.domain.report.BrushScoreHum;
import com.cmbccd.ulms.edu.domain.report.CategoryInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BrushScoreMapper {
    long countByExample(BrushScoreExample example);

    int deleteByExample(BrushScoreExample example);

    int deleteByPrimaryKey(String journo);

    int insert(BrushScore record);

    int insertSelective(BrushScore record);

    List<BrushScore> selectByExample(BrushScoreExample example);

    BrushScore selectByPrimaryKey(String journo);

    int updateByExampleSelective(@Param("record") BrushScore record, @Param("example") BrushScoreExample example);

    int updateByExample(@Param("record") BrushScore record, @Param("example") BrushScoreExample example);

    int updateByPrimaryKeySelective(BrushScore record);

    int updateByPrimaryKey(BrushScore record);
    
    int dayBrushCount(@Param("dataDate")  String dataDate,@Param("ploNum")  String ploNum);
    
    int monthBrushCount(@Param("month")  String month,@Param("ploNum") String ploNum);
    
    List<BrushScore> selectWrongQuestion(@Param("ploNum") String ploNum,@Param("passDays") Short passDays, @Param("quesNums") Short quesNums);
    
    List<BrushScoreHum> reportBrushScoreHum(String begDate, String endDate, String orderBy);

    List<BrushScoreGroup> reportBrushScoreGroup(String begDate, String endDate, String orderBy);
    
    List<BrushScoreDept> reportBrushScoreDept(String begDate, String endDate, String orderBy);
    
    List<CategoryInfo> reportCategoryInfo(String begDate, String endDate);
    
    
    List<BrushScore> centerWrongMostQuestion();
}