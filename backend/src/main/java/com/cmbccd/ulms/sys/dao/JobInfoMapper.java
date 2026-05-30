package com.cmbccd.ulms.sys.dao;

import com.cmbccd.ulms.sys.domain.JobInfo;
import com.cmbccd.ulms.sys.domain.JobInfoExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JobInfoMapper {
    long countByExample(JobInfoExample example);

    int deleteByExample(JobInfoExample example);

    int deleteByPrimaryKey(String jobLevel);

    int insert(JobInfo record);

    int insertSelective(JobInfo record);

    List<JobInfo> selectByExample(JobInfoExample example);

    JobInfo selectByPrimaryKey(String jobLevel);

    int updateByExampleSelective(@Param("record") JobInfo record, @Param("example") JobInfoExample example);

    int updateByExample(@Param("record") JobInfo record, @Param("example") JobInfoExample example);

    int updateByPrimaryKeySelective(JobInfo record);

    int updateByPrimaryKey(JobInfo record);
}