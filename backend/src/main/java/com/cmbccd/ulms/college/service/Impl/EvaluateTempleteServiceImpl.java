package com.cmbccd.ulms.college.service.Impl;

import com.cmbccd.ulms.college.dao.EvaluateTempleteMapper;
import com.cmbccd.ulms.college.domain.EvaluateTemplete;
import com.cmbccd.ulms.college.service.EvaluateTempleteService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class EvaluateTempleteServiceImpl implements EvaluateTempleteService {

    @Resource
    private EvaluateTempleteMapper evaluateTempleteMapper;


    @Override
    public List<EvaluateTemplete> group(Integer tempStat) {
        return evaluateTempleteMapper.group(tempStat);
    }

    @Override
    public List<EvaluateTemplete> select(String tempId) {
        return evaluateTempleteMapper.select(tempId);
    }

    @Override
    public int update(EvaluateTemplete record) {
        return evaluateTempleteMapper.update(record);
    }

    @Override
    public int insert(EvaluateTemplete record) {
        return evaluateTempleteMapper.insert(record);
    }
}
