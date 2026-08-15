package com.cmbccd.ulms.youngTalk.dao;

import com.cmbccd.ulms.youngTalk.domain.Question;
import com.cmbccd.ulms.youngTalk.domain.QuestionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QuestionMapper {
    long countByExample(QuestionExample example);

    int deleteByExample(QuestionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Question record);

    int insertSelective(Question record);

    List<Question> selectByExample(QuestionExample example);

    Question selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Question record, @Param("example") QuestionExample example);

    int updateByExample(@Param("record") Question record, @Param("example") QuestionExample example);

    int updateByPrimaryKeySelective(Question record);

    int updateByPrimaryKey(Question record);
    
    int creatNewId();
    
    List<Question> selectQuestionByArticleId(Integer articleId);
    // 通过
    List<Question> selectUserQuestionByArticleId(@Param("articleId") Integer articleId, @Param("userId") String userId);
}