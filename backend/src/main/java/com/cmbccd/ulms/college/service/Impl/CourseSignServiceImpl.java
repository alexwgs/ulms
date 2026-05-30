package com.cmbccd.ulms.college.service.Impl;

import com.cmbccd.ulms.college.dao.CourseSignMapper;
import com.cmbccd.ulms.college.domain.CourseSign;
import com.cmbccd.ulms.college.domain.CourseSignExample;
import com.cmbccd.ulms.college.service.CourseSignService;
import com.cmbccd.ulms.common.util.Util;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class CourseSignServiceImpl implements CourseSignService {
    @Resource
    private CourseSignMapper courseSignMapper;

    @Override
    public int insert(CourseSign record) {
        record.setHandleDate(Util.currentDateTime());
        return courseSignMapper.insertSelective(record);
    }

    @Override
    public int delete(CourseSign record) {
        CourseSignExample example = new CourseSignExample();
        CourseSignExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(record.getCourseId());
        criteria.andPloNumEqualTo(record.getPloNum());
        return courseSignMapper.deleteByExample(example);
    }

    @Override
    public List<CourseSign> list(CourseSignExample example) {
        return courseSignMapper.selectByExample(example);
    }

    @Override
    public CourseSign get(String courseId, String ploNum) {
        CourseSignExample example = new CourseSignExample();
        CourseSignExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        criteria.andPloNumEqualTo(ploNum);
        List<CourseSign> list = courseSignMapper.selectByExample(example);
        if (list.size()<1) return null;
        return list.get(0);
    }

    @Override
    public int updateCourseStudyNum(String CourseId) {
        return courseSignMapper.updateCourseStudyNum(CourseId);
    }
}
