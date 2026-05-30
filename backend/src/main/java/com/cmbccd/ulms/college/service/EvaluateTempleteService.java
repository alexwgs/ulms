package com.cmbccd.ulms.college.service;

import com.cmbccd.ulms.college.domain.EvaluateTemplete;

import java.util.List;

public interface EvaluateTempleteService {

    List<EvaluateTemplete> group(Integer tempStat);

    List<EvaluateTemplete> select(String tempId);

    int update(EvaluateTemplete record);

    int insert(EvaluateTemplete record);
}
