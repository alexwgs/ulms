package com.cmbccd.ulms.sys.service.impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.dao.JobInfoMapper;
import com.cmbccd.ulms.sys.domain.JobInfo;
import com.cmbccd.ulms.sys.domain.JobInfoExample;
import com.cmbccd.ulms.sys.service.JobInfoService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class JobInfoServiceImpl implements JobInfoService {

    @Resource
    private JobInfoMapper jobInfoMapper;


    @Override
    public List<JobInfo> list(Map<String, String> params) {
        if (Util.isNullorEmpty(params)) return jobInfoMapper.selectByExample(null);
        JobInfoExample example = new JobInfoExample();
        JobInfoExample.Criteria criteria = example.createCriteria();

        if (!Util.isNullorEmpty(params.get("jobStatus"))) {
            criteria.andJobStatusEqualTo(params.get("jobStatus"));
        }
        if (!Util.isNullorEmpty(params.get("order"))) {
            example.setOrderByClause(Util.camel4underline(params.get("order")) + " " + params.get("orderType"));
        }

        Map<String, Integer> pageParams = Util.innitTablePages(params);
        PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
        List<JobInfo> list  =  jobInfoMapper.selectByExample(example);
        return list;
    }

    @Override
    public int update(JobInfo record) {
        return jobInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int insert(JobInfo record) {
        return jobInfoMapper.insert(record);
    }

    @Override
    public int delete(String id) {
        return jobInfoMapper.deleteByPrimaryKey(id);
    }
}
