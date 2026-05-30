package com.cmbccd.ulms.edu.dao;

import com.cmbccd.ulms.edu.domain.QuesLib;
import com.cmbccd.ulms.edu.domain.QuesLibExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuesLibMapper {
    long countByExample(QuesLibExample example);

    int deleteByExample(QuesLibExample example);

    int deleteByPrimaryKey(String libCode);

    int insert(QuesLib record);

    int insertSelective(QuesLib record);

    List<QuesLib> selectByExample(QuesLibExample example);

    QuesLib selectByPrimaryKey(String libCode);

    int updateByExampleSelective(@Param("record") QuesLib record, @Param("example") QuesLibExample example);

    int updateByExample(@Param("record") QuesLib record, @Param("example") QuesLibExample example);

    int updateByPrimaryKeySelective(QuesLib record);

    int updateByPrimaryKey(QuesLib record);

    int updateLibQuesionInvalid(@Param("libCode") String libCode,@Param("handlePlo") String handlePlo);
}