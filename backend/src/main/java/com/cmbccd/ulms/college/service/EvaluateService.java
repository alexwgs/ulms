package com.cmbccd.ulms.college.service;

import com.cmbccd.ulms.college.domain.Evaluate;
import com.cmbccd.ulms.college.domain.EvaluateExample;
import com.cmbccd.ulms.common.util.DataPage;

import java.util.List;
import java.util.Map;

public interface EvaluateService {

    List<Evaluate> list(EvaluateExample example);

    DataPage<Evaluate> listEvaluateByQuery(Map<String, String> params);

    Evaluate get(String journo);

    int insert(Evaluate evaluate);

    int update(Evaluate evaluate);

    int delete(String journo);

    List<Evaluate> listByCourseId(String courseId);
}
