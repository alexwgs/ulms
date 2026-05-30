package com.cmbccd.ulms.college.service;

import com.cmbccd.ulms.college.domain.Course;
import com.cmbccd.ulms.college.domain.CourseExample;

import java.util.List;

public interface CourseService {

    List<Course> list(CourseExample example);

    List<Course> listWithClob(CourseExample example);

    Course get(String courseId, Integer fileStatus);

    Course getWithClob(String courseId, Integer fileStatus);

    int insert(Course record);

    int update(Course record);


}
