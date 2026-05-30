package com.cmbccd.ulms.college.service;

import com.cmbccd.ulms.college.domain.CourseExam;

import java.util.List;


public interface CourseExamService {

    CourseExam get(String examCode);

    List<CourseExam> listMyExam(String courseId);

    int update(CourseExam record);

    CourseExam insert(CourseExam record);

    CourseExam insertNewExam(CourseExam record,String libCode);

    int updateExamScore(String examCode);

}
