package com.cmbccd.ulms.college.service.impl;

import com.cmbccd.ulms.college.dao.CourseExamInfoMapper;
import com.cmbccd.ulms.college.domain.CourseExamInfo;
import com.cmbccd.ulms.college.domain.CourseExamInfoExample;
import com.cmbccd.ulms.college.service.CourseExamInfoService;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.service.QuesBankService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
@Service
public class CourseExamInfoServiceImpl implements CourseExamInfoService {
    @Resource
    private QuesBankService quesBankService;

    @Resource
    private CourseExamInfoMapper courseExamInfoMapper;

    @Override
    public List<CourseExamInfo> listTestByJourno(String examCode) {
        CourseExamInfoExample example = new CourseExamInfoExample();
        CourseExamInfoExample.Criteria criteria = example.createCriteria();
        criteria.andExamCodeEqualTo(examCode);
        example.setOrderByClause(" ques_order ");
        List<CourseExamInfo> list = courseExamInfoMapper.selectByExample(example);
        for(CourseExamInfo item: list) {
            item.setQuestion(quesBankService.get(item.getQuesCode()));
        }
        return list;
    }

    @Override
    public int submitAnswer(CourseExamInfo courseExamInfo) {
        int passFlag = quesBankService.checkUserAnswer(courseExamInfo.getQuestion(), courseExamInfo.getUserAnswer());
        courseExamInfo.setHandleDate(Util.currentDateTime());
        courseExamInfo.setIfCorrect((short)passFlag);
        courseExamInfoMapper.updateByPrimaryKey(courseExamInfo);
        return 0;
    }
}
