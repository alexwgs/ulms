package com.cmbccd.ulms.college.service.Impl;

import com.cmbccd.ulms.college.dao.CourseExamInfoMapper;
import com.cmbccd.ulms.college.dao.CourseExamMapper;
import com.cmbccd.ulms.college.domain.CourseExamExample;
import com.cmbccd.ulms.college.domain.CourseExam;
import com.cmbccd.ulms.college.service.CourseExamService;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.service.PublicService;

import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
@Service
public class CourseExamServiceImpl implements CourseExamService {
    @Resource
    private CourseExamMapper courseExamMapper;

    @Resource
    private CourseExamInfoMapper courseExamInfoMapper;

    @Resource
    private PublicService publicService;

    @Override
    public CourseExam get(String examCode) {
        return courseExamMapper.selectByPrimaryKey(examCode);
    }

    @Override
    public List<CourseExam> listMyExam(String courseId) {
        String userId = Util.userIdByShiro();
        CourseExamExample example = new CourseExamExample();
        CourseExamExample.Criteria criteria = example.createCriteria();
        criteria.andPloNumEqualTo(userId);
        criteria.andCourseIdEqualTo(courseId);
        example.setOrderByClause(" journo desc");
        return courseExamMapper.selectByExample(example);
    }

    @Override
    public int update(CourseExam record) {
        return courseExamMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public CourseExam insert(CourseExam record) {
        String userId = Util.userIdByShiro();
        record.setPloNum(userId);
        record.setBegDate(Util.currentDateTime());
        record.setJourno(publicService.getJourno());
        if(courseExamMapper.insertSelective(record) == 1) return record;
        return null;
    }

    @Override
    public CourseExam insertNewExam (CourseExam record,String libCode) {
        record = this.insert(record);
        courseExamInfoMapper.insertNewExam(record.getJourno(), libCode, record.getPloNum(),record.getQuesNum());
        return record;
    }

    @Override
    public int updateExamScore(String examCode) {
        return courseExamMapper.updateExamScore(examCode);
    }
}
