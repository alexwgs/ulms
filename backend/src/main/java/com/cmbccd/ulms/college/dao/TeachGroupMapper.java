package com.cmbccd.ulms.college.dao;

import com.cmbccd.ulms.college.domain.TeachGroup;
import com.cmbccd.ulms.college.domain.TeachGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TeachGroupMapper {
    long countByExample(TeachGroupExample example);

    int deleteByExample(TeachGroupExample example);

    int insert(TeachGroup record);

    int insertSelective(TeachGroup record);

    List<TeachGroup> selectByExample(TeachGroupExample example);

    int updateByExampleSelective(@Param("record") TeachGroup record, @Param("example") TeachGroupExample example);

    int updateByExample(@Param("record") TeachGroup record, @Param("example") TeachGroupExample example);

    List<TeachGroup> listGroupName();

    List<String> listGroupNameByPloNum(String ploNum);

    int batchInsert(List<TeachGroup> list);

    void deleteAll();
}