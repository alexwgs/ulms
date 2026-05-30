package com.cmbccd.ulms.sys.service;

import com.cmbccd.ulms.sys.domain.JobInfo;

import java.util.List;
import java.util.Map;

public interface JobInfoService {

    List<JobInfo> list(Map<String, String> params);

    int update(JobInfo record);

    int insert(JobInfo record);

    int delete(String id);

}
