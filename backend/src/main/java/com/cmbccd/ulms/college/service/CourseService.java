package com.cmbccd.ulms.college.service;

import com.cmbccd.ulms.college.domain.Course;
import com.cmbccd.ulms.college.domain.CourseExample;
import com.cmbccd.ulms.common.util.DataPage;

import java.util.List;
import java.util.Map;

public interface CourseService {

    List<Course> list(CourseExample example);

    DataPage<Course> listCourseByUser(Map<String, String> params, String userId);

    DataPage<Course> listCourseByAdmin(Map<String, String> params);

    List<Course> listWithClob(CourseExample example);

    Course get(String courseId, Integer fileStatus);

    Course getWithClob(String courseId, Integer fileStatus);

    int insert(Course record);

    int update(Course record);


}
