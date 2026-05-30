package com.cmbccd.ulms.edu.dao;

import com.cmbccd.ulms.edu.domain.QuesCollect;
import com.cmbccd.ulms.edu.domain.QuesCollectExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuesCollectMapper {
    long countByExample(QuesCollectExample example);

    int deleteByExample(QuesCollectExample example);

    int deleteByPrimaryKey(String journo);

    int insert(QuesCollect record);

    int insertSelective(QuesCollect record);

    List<QuesCollect> selectByExample(QuesCollectExample example);

    QuesCollect selectByPrimaryKey(String journo);

    int updateByExampleSelective(@Param("record") QuesCollect record, @Param("example") QuesCollectExample example);

    int updateByExample(@Param("record") QuesCollect record, @Param("example") QuesCollectExample example);

    int updateByPrimaryKeySelective(QuesCollect record);

    int updateByPrimaryKey(QuesCollect record);
    
    int getCollectNumber(String ploNum, Integer collectDay);

    int  getHaveCollect(String ploNum, Integer collectDay, String quesCode);
}