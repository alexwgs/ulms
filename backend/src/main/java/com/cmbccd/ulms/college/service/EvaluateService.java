package com.cmbccd.ulms.college.service;

import com.cmbccd.ulms.college.domain.Evaluate;
import com.cmbccd.ulms.college.domain.EvaluateExample;

import java.util.List;

public interface EvaluateService {

    List<Evaluate> list(EvaluateExample example);

    Evaluate get(String journo);

    int insert(Evaluate evaluate);

    int update(Evaluate evaluate);

    int delete(String journo);

    List<Evaluate> listByCourseId(String courseId);
}
