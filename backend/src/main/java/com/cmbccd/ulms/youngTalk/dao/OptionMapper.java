package com.cmbccd.ulms.youngTalk.dao;

import com.cmbccd.ulms.youngTalk.domain.Option;
import com.cmbccd.ulms.youngTalk.domain.OptionExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface OptionMapper {
    long countByExample(OptionExample example);

    int deleteByExample(OptionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Option record);

    int insertSelective(Option record);

    List<Option> selectByExample(OptionExample example);

    Option selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Option record, @Param("example") OptionExample example);

    int updateByExample(@Param("record") Option record, @Param("example") OptionExample example);

    int updateByPrimaryKeySelective(Option record);

    int updateByPrimaryKey(Option record);
    
    int creatNewId();
    
    List<Map<String,Object>> selectRadioNumGroupByQuestionId(@Param("articalId") int articalId,@Param("questionId") int questionId);
    
    int selectCheckboxNumGroupByOptionId(@Param("articalId") int articalId,@Param("questionId") int questionId,@Param("option") String option);
}