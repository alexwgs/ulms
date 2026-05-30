package com.cmbccd.ulms.college.dao;

import com.cmbccd.ulms.college.domain.report.CourseExamDetail;
import com.cmbccd.ulms.college.domain.report.CourseLib;
import com.cmbccd.ulms.college.domain.report.CourseStudyDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CollegeReportMapper {

    List<CourseStudyDetail> selectCourseStudyDeatilHum(@Param("courseId") String courseId, @Param("deptNum") String deptNum);

    List<CourseLib> selectCourseLib();

    List<CourseExamDetail> selectCourseExamDetail(String courseId);
//    @MapKey("key")
    List<String> selectEvalByJourno(String journo);

    List<Map<String, String>> viewCourseStudyByDept(String courseId);
}
