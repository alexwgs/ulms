package com.cmbccd.ulms.college.service;

import com.cmbccd.ulms.college.domain.Teacher;
import com.cmbccd.ulms.college.domain.TeacherExample;
import com.cmbccd.ulms.common.util.DataPage;

import java.util.List;
import java.util.Map;

public interface CourseTeacherService {

    int insert(Teacher record);

    int update(Teacher record);

    int delete(String ploNum);

    List<Teacher> list(TeacherExample example);

    DataPage<Teacher> listTeacherByQuery(Map<String, String> params);

    Teacher get(String ploNum);

    List<Teacher> listTopTeacher();

}
