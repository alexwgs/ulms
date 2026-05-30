package com.cmbccd.ulms.college.service.Impl;

import com.cmbccd.ulms.college.dao.CourseExamTemplateMapper;
import com.cmbccd.ulms.college.domain.Course;
import com.cmbccd.ulms.college.domain.CourseExamTemplate;
import com.cmbccd.ulms.college.service.CourseExamTemplateService;
import com.cmbccd.ulms.college.service.CourseService;
import com.cmbccd.ulms.common.util.Util;

import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

@Service
public class CourseExamTemplateServiceImpl implements CourseExamTemplateService {
    @Resource
    private CourseExamTemplateMapper courseExamTemplateMapper;

    @Resource
    private CourseService courseService;

    @Override
    public CourseExamTemplate get(String courseId) {
        return courseExamTemplateMapper.selectByPrimaryKey(courseId);
    }

    @Override
    public int update(CourseExamTemplate record) {
        String userId = Util.userIdByShiro();
        record.setHandlePlo(userId);
        record.setHandleDate(Util.currentDateTime());
        this.updateCourse(record.getCourseId(), (short)1, record.getLibCode());
        return courseExamTemplateMapper.updateByPrimaryKey(record);
    }

    @Override
    public int insert(CourseExamTemplate record) {
        String userId = Util.userIdByShiro();
        record.setHandlePlo(userId);
        record.setHandleDate(Util.currentDateTime());
        this.updateCourse(record.getCourseId(), (short)1, record.getLibCode());
        return courseExamTemplateMapper.insertSelective(record);
    }

    @Override
    public int delete(String courseId) {
        this.updateCourse(courseId, (short)0, "");
        return courseExamTemplateMapper.deleteByPrimaryKey(courseId);
    }

    public int updateCourse(String courseId, short ifExam, String examId){
        // 更新课程配置
        Course course = new Course();
        course.setCourseId(courseId);
        course.setIfExam(ifExam);
        course.setExamId(examId);
        return courseService.update(course);
    }
}
