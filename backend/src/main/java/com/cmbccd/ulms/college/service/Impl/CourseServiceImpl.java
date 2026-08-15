package com.cmbccd.ulms.college.service.impl;

import com.cmbccd.ulms.college.dao.CourseMapper;
import com.cmbccd.ulms.college.domain.Course;
import com.cmbccd.ulms.college.domain.CourseExample;
import com.cmbccd.ulms.college.domain.CourseFile;
import com.cmbccd.ulms.college.domain.Teacher;
import com.cmbccd.ulms.college.service.CourseFileService;
import com.cmbccd.ulms.college.service.CourseService;
import com.cmbccd.ulms.college.service.CourseTeacherService;
import com.cmbccd.ulms.college.service.CourseTypeService;
import com.cmbccd.ulms.college.service.TeachGroupService;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.service.PublicService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
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
    private PublicService publicService;

    @Resource
    private TeachGroupService teachGroupService;

    @Resource
    private CourseTypeService courseTypeService;

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
    public DataPage<Course> listCourseByUser(Map<String, String> params, String userId) {
        Map<String, Integer> pageParams = Util.innitTablePages(params);
        String courseType = params.get("courseType");
        String queryType = params.get("queryType");
        String query = params.get("query");
        CourseExample example = new CourseExample();
        CourseExample.Criteria criteria = example.createCriteria();
        if (!Util.isNullorEmpty(params.get("order"))) {
            example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
        }
        if (!Util.isNullorEmpty(query)) {
            if ("courseName".equals(queryType)) {
                criteria.andCourseNameLike("%" + query + "%");
            } else if ("lecturer".equals(queryType)) {
                criteria.andLecturerEqualTo(query);
            }
        }
        String today = Util.getDateToday();
        criteria.andStatusEqualTo((short) 1);
        criteria.andBegDateLessThanOrEqualTo(today);
        criteria.andEndDateGreaterThanOrEqualTo(today);
        // 获取授课对象
        criteria.andTeachObjectIn(teachGroupService.listGroupNameByPloNum(userId));
        if (!Util.isNullorEmpty(courseType)) {
            List<Integer> ids = courseTypeService.getChiledIds(Integer.parseInt(courseType));
            criteria.andCourseTypeIn(ids.stream().map(e -> e.shortValue()).collect(Collectors.toList()));
        }
        PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
        List<Course> list = list(example);
        return new DataPage<Course>(list);
    }

    @Override
    public DataPage<Course> listCourseByAdmin(Map<String, String> params) {
        Map<String, Integer> pageParams = Util.innitTablePages(params);
        String query = params.get("query");
        String status = params.get("status");
        CourseExample example = new CourseExample();
        CourseExample.Criteria criteria = example.createCriteria();
        if (!Util.isNullorEmpty(params.get("order"))) {
            example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
        }
        if (!Util.isNullorEmpty(query)) {
            if (query.length() == 13) {
                criteria.andCourseIdEqualTo(query);
            } else {
                criteria.andCourseNameLike("%" + query + "%");
            }
        }
        if (!Util.isNullorEmpty(status)) {
            criteria.andStatusEqualTo(Short.parseShort(status));
        }
        PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
        List<Course> list = listWithClob(example);
        return new DataPage<Course>(list);
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
        record.setCourseId(publicService.getJourno());
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
