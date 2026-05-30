package com.cmbccd.ulms.college.service.Impl;

import com.cmbccd.ulms.college.dao.CourseProgressMapper;
import com.cmbccd.ulms.college.domain.CourseProgress;
import com.cmbccd.ulms.college.domain.CourseProgressExample;
import com.cmbccd.ulms.college.service.CourseProgressService;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.service.PublicService;

import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class CourseProgressServiceImpl implements CourseProgressService {
    @Resource
    private CourseProgressMapper courseProgressMapper;

    @Resource
    private PublicService publicService;

    @Override
    public List<CourseProgress> list(CourseProgressExample example) {

        return courseProgressMapper.selectByExample(example);
    }

    @Override
    public CourseProgress get(String journo) {
        return courseProgressMapper.selectByPrimaryKey(journo);
    }

    @Override
    public int update(CourseProgress courseProgress) {
        return courseProgressMapper.updateByPrimaryKey(courseProgress);
    }

    @Override
    public CourseProgress updateTime(CourseProgress courseProgress) {
        String userId = Util.userIdByShiro();
        CourseProgressExample example = new CourseProgressExample();
        CourseProgressExample.Criteria criteria = example.createCriteria();
        criteria.andJournoEqualTo(courseProgress.getJourno());
        criteria.andCurrTimeLessThan(courseProgress.getCurrTime());
        courseProgress.setPloNum(userId);
        int count = courseProgressMapper.updateByExampleSelective(courseProgress, example);
        if (count<1) return null;
        return courseProgress;
    }

    @Override
    public CourseProgress insert(CourseProgress courseProgress) {
        String userId = Util.userIdByShiro();
        courseProgress.setStudyDate(Util.currentDateTime());
        courseProgress.setPloNum(userId);
        courseProgress.setJourno(publicService.getJourno());
        int count = courseProgressMapper.insertSelective(courseProgress);
        if (count<1) return null;
        return courseProgress;
    }

    @Override
    public int delete(String journo) {
        return courseProgressMapper.deleteByPrimaryKey(journo);
    }

    @Override
    public List<CourseProgress> listMyLearnLog(CourseProgressExample example) {
        return courseProgressMapper.selectMyLearnLog(example);
    }
}
