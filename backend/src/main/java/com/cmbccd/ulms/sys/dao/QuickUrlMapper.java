package com.cmbccd.ulms.sys.dao;

import com.cmbccd.ulms.sys.domain.QuickUrl;
import com.cmbccd.ulms.sys.domain.QuickUrlExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QuickUrlMapper {
    long countByExample(QuickUrlExample example);

    int deleteByExample(QuickUrlExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(QuickUrl row);

    int insertSelective(QuickUrl row);

    List<QuickUrl> selectByExample(QuickUrlExample example);

    QuickUrl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") QuickUrl row, @Param("example") QuickUrlExample example);

    int updateByExample(@Param("row") QuickUrl row, @Param("example") QuickUrlExample example);

    int updateByPrimaryKeySelective(QuickUrl row);

    int updateByPrimaryKey(QuickUrl row);

    int getNewId();
}