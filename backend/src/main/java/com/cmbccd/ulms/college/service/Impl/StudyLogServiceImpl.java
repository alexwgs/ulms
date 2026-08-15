package com.cmbccd.ulms.college.service.impl;

import com.cmbccd.ulms.college.dao.CourseMapper;
import com.cmbccd.ulms.college.dao.StudyLogMapper;
import com.cmbccd.ulms.college.domain.Course;
import com.cmbccd.ulms.college.domain.StudyLog;
import com.cmbccd.ulms.college.domain.StudyLogExample;
import com.cmbccd.ulms.college.service.StudyLogService;
import com.cmbccd.ulms.common.util.Util;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class StudyLogServiceImpl implements StudyLogService {

    @Resource
    private StudyLogMapper studyLogMapper;

    @Resource
    private CourseMapper courseMapper;

    @Override
    public int insert(Course course, String ploNum) {
        StudyLog record = new StudyLog();
        record.setCourseId(course.getCourseId());
        record.setPloNum(ploNum);
        record.setLecturer(course.getLecturer());
        record.setCoin(course.getCoin());
        record.setHours(course.getHours());
        record.setStudyComp((short) 0);
        record.setExamComp((short)2);
        record.setEvalComp((short)2);
        record.setTeachMethod(course.getTeachMethod());
        record.setIfEval(course.getIfEval());
        record.setTempId(course.getTempId());
        record.setIfExam(course.getIfExam());
        record.setExamId(course.getExamId());
        record.setStudyDate(Util.currentDateTime());
        return studyLogMapper.insertSelective(record);
    }

    /**
     * 更新学习进度，同时同步人员学分
     * @param record
     * @return
     */
    @Override
    public int update(StudyLog record) {
        StudyLogExample example = new StudyLogExample();
        StudyLogExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(record.getCourseId());
        criteria.andPloNumEqualTo(record.getPloNum());
        return studyLogMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int delete(String courseId, String ploNum) {
        StudyLogExample example = new StudyLogExample();
        StudyLogExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        criteria.andPloNumEqualTo(ploNum);
        return studyLogMapper.deleteByExample(example);
    }

    @Override
    public List<StudyLog> list(StudyLogExample example) {
        return studyLogMapper.selectByExample(example);
    }

    @Override
    public List<StudyLog> listWithCourse(StudyLogExample example) {
        List<StudyLog> list = studyLogMapper.selectByExample(example);
        for(StudyLog item: list) {
            item.setCourse(courseMapper.selectByPrimaryKey(item.getCourseId()));
        }
        return list;
    }

    @Override
    public StudyLog get(String courseId, String ploNum) {
        StudyLogExample example = new StudyLogExample();
        StudyLogExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        criteria.andPloNumEqualTo(ploNum);
        List<StudyLog> list = studyLogMapper.selectByExample(example);
        if (list.size()<1) return null;
        return list.get(0);
    }

    public boolean chekCourseComplete(StudyLog studyLog) {
        short ifExam = studyLog.getIfExam();
        short ifEval = studyLog.getIfEval();
        short examComp = studyLog.getExamComp();
        short evalComp = studyLog.getEvalComp();
        if (ifExam == 0 && ifEval ==0) {
            return true;
        } else if(ifEval == 0 && ifExam ==1 ) {
            return examComp==1?true:false;
        } else if(ifExam == 0 &&  ifEval==1) {
            return evalComp==1?true:false;
        } else {
            return (examComp==1&& evalComp==1)?true:false;
        }
    }
}
