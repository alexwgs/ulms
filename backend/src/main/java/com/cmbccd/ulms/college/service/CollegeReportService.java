package com.cmbccd.ulms.college.service;

import com.cmbccd.ulms.college.domain.report.CourseExamDetail;
import com.cmbccd.ulms.college.domain.report.CourseLib;
import com.cmbccd.ulms.college.domain.report.CourseStudyDetail;

import java.util.List;
import java.util.Map;

public interface CollegeReportService {

    List<CourseStudyDetail> listCourseStudyDeatilHum(String courseId, String deptNum);

    List<CourseLib> listCourseLib();

    List<CourseExamDetail> listCourseExamDetail(String courseId);

    List<List<List<String>>> listEvalDetail(String courseId);

    List<Map<String, String>> viewCourseStudyByDept(String courseId);
}
