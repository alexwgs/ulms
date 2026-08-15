package com.cmbccd.ulms.college.service;

import com.cmbccd.ulms.college.domain.Course;
import com.cmbccd.ulms.college.domain.StudyLog;
import com.cmbccd.ulms.college.domain.StudyLogExample;
import com.cmbccd.ulms.common.util.DataPage;

import java.util.List;
import java.util.Map;

public interface StudyLogService {

//    int insert();
    int insert(Course course, String ploNum);

    int update(StudyLog record);

    int delete(String courseId, String ploNum);

    List<StudyLog> list(StudyLogExample example);

    List<StudyLog> listWithCourse(StudyLogExample example);

    DataPage<StudyLog> listMyLog(String type, Map<String, String> params, String userId);

    StudyLog get(String courseId, String ploNum);

    boolean chekCourseComplete(StudyLog record);

}
