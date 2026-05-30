package com.cmbccd.ulms.college.controller;

import com.cmbccd.ulms.college.domain.CourseExam;
import com.cmbccd.ulms.college.domain.CourseExamInfo;
import com.cmbccd.ulms.college.domain.CourseExamTemplate;
import com.cmbccd.ulms.college.domain.StudyLog;
import com.cmbccd.ulms.college.service.CourseExamInfoService;
import com.cmbccd.ulms.college.service.CourseExamService;
import com.cmbccd.ulms.college.service.CourseExamTemplateService;
import com.cmbccd.ulms.college.service.StudyLogService;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Msg;

import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("college/course/exam")
public class CourseExamController {
    @Resource
    private CourseExamTemplateService courseExamTemplateService;

    @Resource
    private CourseExamInfoService courseExamInfoService;

    @Resource
    private CourseExamService courseExamService;

    @Resource
    private StudyLogService studyLogService;
    /**
     * 前端在课程详情列表点击考试的初始化数据
     * @param courseId
     * @return
     */
    @GetMapping("/init/{courseId}")
    public Msg initMyExam(@PathVariable("courseId") String courseId) {
        CourseExamTemplate courseExamTemplate = courseExamTemplateService.get(courseId);
        List<CourseExam> list = courseExamService.listMyExam(courseId);
        return Msg.success(list).put("setting", courseExamTemplate);
    }

    /**
     * 获取考试信息
     * @param courseId
     * @return
     */
    @GetMapping("/start/{courseId}")
    public Msg getExamInfo(@PathVariable("courseId") String courseId) {
        CourseExamTemplate courseExamTemplate = courseExamTemplateService.get(courseId);
        String libCode = courseExamTemplate.getLibCode();
        int quesNum = courseExamTemplate.getQuesNum();
        CourseExam courseExam = new CourseExam();
        courseExam.setCourseId(courseId);
        courseExam.setQuesNum((short) quesNum);
        courseExam.setPassNum(courseExamTemplate.getPassNum());
        courseExam.setPassFlag((short)2);
        // 根据传参判断是新生成一套试卷还是继续原有的考试。
        courseExam = courseExamService.insertNewExam(courseExam, libCode);
        // 获取新生成的试卷
        List<CourseExamInfo> list = courseExamInfoService.listTestByJourno(courseExam.getJourno());
        return Msg.success(list);
    }

    /**
     * 提交答案
     * @param courseExamInfo
     * @return
     */
    @PostMapping("/check")
    public Msg submitQuestion (@RequestBody CourseExamInfo courseExamInfo) {
        int count = courseExamInfoService.submitAnswer(courseExamInfo);

        if(count < 0) return Msg.error();
        return Msg.success();
    }

    /**
     * 提交考试
     * @param examCode
     * @return
     */
    @PutMapping("/submit/{examCode}")
    public Msg submitExam(@PathVariable String examCode){
        // 同时更新总得分
        courseExamService.updateExamScore(examCode);
        CourseExam courseExam = courseExamService.get(examCode);
        String userId = Util.userIdByShiro();
        StudyLog studyLog = studyLogService.get(courseExam.getCourseId(), userId);
        studyLog.setExamComp((short)1);
        if(studyLogService.chekCourseComplete(studyLog)) {
            if(Util.isNullorEmpty(studyLog.getCoursePass()) || studyLog.getCoursePass() != 1){
                studyLog.setCoursePass(courseExam.getPassFlag());
                studyLog.setCompDate(Util.currentDateTime());
            } else {
                return Msg.success("课程已经完成学习！").put("examResult", courseExam);
            }
        }
        studyLogService.update(studyLog);
        if(courseExam.getPassFlag() == 0) return Msg.error("本次考试没有通过！").put("examResult", courseExam);
        return Msg.success("恭喜你！成功通过了考试！").put("examResult", courseExam);
    }

}
