package com.cmbccd.ulms.college.service;

import com.cmbccd.ulms.college.domain.Course;
import com.cmbccd.ulms.college.domain.StudyLog;
import com.cmbccd.ulms.college.domain.StudyLogExample;

import java.util.List;

public interface StudyLogService {

//    int insert();
    int insert(Course course, String ploNum);

    int update(StudyLog record);

    int delete(String courseId, String ploNum);

    List<StudyLog> list(StudyLogExample example);

    List<StudyLog> listWithCourse(StudyLogExample example);

    StudyLog get(String courseId, String ploNum);

    boolean chekCourseComplete(StudyLog record);

}
