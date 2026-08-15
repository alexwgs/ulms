package com.cmbccd.ulms.college.service.impl;

import com.cmbccd.ulms.college.dao.CourseFileMapper;
import com.cmbccd.ulms.college.domain.CourseFile;
import com.cmbccd.ulms.college.domain.CourseFileExample;
import com.cmbccd.ulms.college.service.CourseFileService;
import com.cmbccd.ulms.common.util.Util;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class CourseFileServiceImpl implements CourseFileService {
    @Resource
    private CourseFileMapper courseFileMapper;

    @Override
    public List<CourseFile> list(String courseId, Integer status) {
        CourseFileExample example = new CourseFileExample();
        CourseFileExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        if(!Util.isNullorEmpty(status)) criteria.andStatusEqualTo(status);
        example.setOrderByClause(" file_type, to_number(file_order)");
        return courseFileMapper.selectByExample(example);
    }

    @Override
    public int update(CourseFile courseFile) {
        CourseFileExample example = new CourseFileExample();
        CourseFileExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(courseFile.getCourseId());
        criteria.andFileIdEqualTo(courseFile.getFileId());
        return courseFileMapper.updateByExampleSelective(courseFile,example);
    }

    @Override
    public int delete(String courseId, String fileId) {
        CourseFileExample example = new CourseFileExample();
        CourseFileExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        criteria.andFileIdEqualTo(fileId);
        return courseFileMapper.deleteByExample(example);
    }

    @Override
    public int insert(CourseFile courseFile) {
        return courseFileMapper.insertSelective(courseFile);
    }
}
