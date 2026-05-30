package com.cmbccd.ulms.college.service;

import com.cmbccd.ulms.college.domain.CourseExamTemplate;

public interface CourseExamTemplateService {

    CourseExamTemplate get(String courseId);

    int update(CourseExamTemplate record);

    int insert(CourseExamTemplate record);

    int delete(String courseId);
}
