package com.cmbccd.ulms.college.service;

import com.cmbccd.ulms.college.domain.CourseExamInfo;

import java.util.List;


public interface CourseExamInfoService {

    List<CourseExamInfo> listTestByJourno(String examCode);

    int submitAnswer(CourseExamInfo courseExamInfo);
}
