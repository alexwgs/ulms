package com.cmbccd.ulms.college.service.Impl;

import com.cmbccd.ulms.college.dao.CourseMapper;
import com.cmbccd.ulms.college.domain.Course;
import com.cmbccd.ulms.college.domain.CourseExample;
import com.cmbccd.ulms.college.domain.CourseFile;
import com.cmbccd.ulms.college.domain.Teacher;
import com.cmbccd.ulms.college.service.CourseFileService;
import com.cmbccd.ulms.college.service.CourseService;
import com.cmbccd.ulms.college.service.CourseTeacherService;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.dao.PublicMapper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    @Resource
    private CourseMapper courseMapper;

    @Resource
    private CourseTeacherService courseTeacherService;

    @Resource
    private CourseFileService courseFileService;

    @Resource
    private PublicMapper publicMapper;

    @Override
    public List<Course> list(CourseExample example) {
        List<Course> courses = courseMapper.selectByExample(example);
        List<Teacher> teachers = courseTeacherService.list(null);
        for(Course item : courses) {
            List<Teacher> teacher = teachers.stream().filter(e -> e.getPloNum().equals(item.getLecturer())).collect(Collectors.toList());
            if(teacher.size()<1) continue;
            item.setLecturer(teacher.get(0).getPloName());
        }
        return courses;
    }

    @Override
    public List<Course> listWithClob(CourseExample example) {
        List<Course> courses = courseMapper.selectByExampleWithBLOBs(example);
        for(Course item : courses) {
            item.setCourseFile(courseFileService.list(item.getCourseId(), null));
        }
        return courses;
    }

    @Override
    public Course get(String courseId, Integer fileStatus) {
        List<CourseFile> courseFiles = courseFileService.list(courseId,fileStatus);
        CourseExample example = new CourseExample();
        CourseExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        List<Course> courses = courseMapper.selectByExample(example);
        if(Util.isNullorEmpty(courses)) return null;
        Course course = courses.get(0);
        course.setCourseFile(courseFiles);
        return course;
    }

    @Override
    public Course getWithClob(String courseId, Integer fileStatus) {
        List<CourseFile> courseFiles = courseFileService.list(courseId,fileStatus);
        Course course = courseMapper.selectByPrimaryKey(courseId);
        course.setCourseFile(courseFiles);
        return course;
    }

    @Override
    public int insert(Course record) {
        String userId = Util.userIdByShiro();
        record.setHandleDate(Util.currentDateTime());
        record.setHandlePlo(userId);
        record.setCourseId(publicMapper.selectNewJourno());
        return courseMapper.insertSelective(record);
    }

    @Override
    public int update(Course record) {
//        String userId = Util.userIdByShiro();
//        record.setHandleDate(Util.currentDateTime());
//        record.setHandlePlo(userId);
        return courseMapper.updateByPrimaryKeySelective(record);
    }
}
