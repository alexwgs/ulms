package com.cmbccd.ulms.college.service.Impl;

import com.cmbccd.ulms.college.dao.TeacherMapper;
import com.cmbccd.ulms.college.domain.Teacher;
import com.cmbccd.ulms.college.domain.TeacherExample;
import com.cmbccd.ulms.college.service.CourseTeacherService;
import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Employee;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class CourseTeacherServiceImpl implements CourseTeacherService {

    @Resource
    private TeacherMapper teacherMapper;

    @Override
    public int insert(Teacher record) {
        return teacherMapper.insertSelective(record);
    }

    @Override
    public int update(Teacher record) {
        return teacherMapper.updateByPrimaryKey(record);
    }

    @Override
    public int delete(String ploNum) {
        return teacherMapper.deleteByPrimaryKey(ploNum);
    }

    @Override
    public List<Teacher> list(TeacherExample example) {
        List<Teacher> list = teacherMapper.selectByExample(example);
        for (Teacher item : list ) {
            if (Util.isNullorEmpty(item.getPloNum())) continue;
            Employee employee = DataCache.EMPLOYEE.get(item.getPloNum());
            item.setUser(employee);
        }
        return list;
    }

    @Override
    public Teacher get(String ploNum) {
        Teacher teacher = teacherMapper.selectByPrimaryKey(ploNum);
        if (Util.isNullorEmpty(teacher)) {
            teacher = new Teacher();
        } else {
            Employee employee = Optional.ofNullable(DataCache.EMPLOYEE.get(teacher.getPloNum())).orElse(null);
            teacher.setUser(employee);
        }
        return teacher;
    }

    @Override
    public List<Teacher> listTopTeacher() {
        List<Teacher> list = teacherMapper.selectTopTeacher();
        for (Teacher item : list ) {
            if (Util.isNullorEmpty(item.getPloNum())) continue;
            Employee employee = DataCache.EMPLOYEE.get(item.getPloNum());
            item.setUser(employee);
        }
        return list;
    }
}
