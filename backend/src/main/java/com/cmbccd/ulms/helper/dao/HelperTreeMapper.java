package com.cmbccd.ulms.helper.dao;

import com.cmbccd.ulms.helper.domain.HelperTree;
import com.cmbccd.ulms.helper.domain.HelperTreeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HelperTreeMapper {
    long countByExample(HelperTreeExample example);

    int deleteByExample(HelperTreeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HelperTree record);

    int insertSelective(HelperTree record);

    List<HelperTree> selectByExample(HelperTreeExample example);

    HelperTree selectByPrimaryKey(Integer id);

    List<Integer> selectByPid(Integer pid);

    int updateByExampleSelective(@Param("record") HelperTree record, @Param("example") HelperTreeExample example);

    int updateByExample(@Param("record") HelperTree record, @Param("example") HelperTreeExample example);

    int updateByPrimaryKeySelective(HelperTree record);

    int updateByPrimaryKey(HelperTree record);

    int getNewId();
}