package com.cmbccd.ulms.youngTalk.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cmbccd.ulms.youngTalk.domain.Answer;
import com.cmbccd.ulms.youngTalk.domain.AnswerExample;
/**
 * 
* <p>Title: AnswerMapper.java</p>  
* <p>Description: </p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
public interface AnswerMapper {
    long countByExample(AnswerExample example);

    int deleteByExample(AnswerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Answer record);

    int insertSelective(Answer record);

    List<Answer> selectByExample(AnswerExample example);

    Answer selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Answer record, @Param("example") AnswerExample example);

    int updateByExample(@Param("record") Answer record, @Param("example") AnswerExample example);

    int updateByPrimaryKeySelective(Answer record);

    int updateByPrimaryKey(Answer record);
    
    int creatNewId();
    
    List<Answer> selectSurveyUserIds(int articleId);
}