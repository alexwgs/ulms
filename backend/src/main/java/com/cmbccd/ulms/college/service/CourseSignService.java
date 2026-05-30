package com.cmbccd.ulms.college.service;

import com.cmbccd.ulms.college.domain.CourseSign;
import com.cmbccd.ulms.college.domain.CourseSignExample;

import java.util.List;

public interface CourseSignService {

    int insert(CourseSign record);

//    int update(CourseSign record);

    int delete(CourseSign record);

    List<CourseSign> list(CourseSignExample example);

    CourseSign get(String courseId, String ploNum);

    int updateCourseStudyNum(String CourseId);
}
