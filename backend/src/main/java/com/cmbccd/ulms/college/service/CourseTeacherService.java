package com.cmbccd.ulms.college.service;

import com.cmbccd.ulms.college.domain.Teacher;
import com.cmbccd.ulms.college.domain.TeacherExample;

import java.util.List;

public interface CourseTeacherService {

    int insert(Teacher record);

    int update(Teacher record);

    int delete(String ploNum);

    List<Teacher> list(TeacherExample example);

    Teacher get(String ploNum);

    List<Teacher> listTopTeacher();

}
