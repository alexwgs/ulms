package com.cmbccd.ulms.edu.dao;

import com.cmbccd.ulms.edu.domain.QuesBank;
import com.cmbccd.ulms.edu.domain.QuesBankExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface QuesBankMapper {
    long countByExample(QuesBankExample example);

    int deleteByExample(QuesBankExample example);

    int deleteByPrimaryKey(String quesCode);

    int insert(QuesBank record);

    int insertSelective(QuesBank record);

    List<QuesBank> selectByExample(QuesBankExample example);

    QuesBank selectByPrimaryKey(String quesCode);

    int updateByExampleSelective(@Param("record") QuesBank record, @Param("example") QuesBankExample example);

    int updateByExample(@Param("record") QuesBank record, @Param("example") QuesBankExample example);

    int updateByPrimaryKeySelective(QuesBank record);

    int updateByPrimaryKey(QuesBank record);

    List<String> selectQuestionByLibs(Map<String, Object> libsAndPloNum );

    List<QuesBank> selectNoSensitive(QuesBankExample example);
    
    // 批量更新题库题目数
    int updateQuesNumber();

    int invalidAllQuestionByQuesLib(@Param("handlePlo") String handlePlo,@Param("libCode") String libCode);
}