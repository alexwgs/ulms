package com.cmbccd.ulms.college.controller;

import com.cmbccd.ulms.college.domain.Course;
import com.cmbccd.ulms.college.domain.CourseSign;
import com.cmbccd.ulms.college.domain.StudyLog;
import com.cmbccd.ulms.college.service.CourseSignService;
import com.cmbccd.ulms.college.service.StudyLogService;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Msg;

import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/college/study")
public class StudyLogController {

    @Resource
    private StudyLogService studyLogService;

    @Resource
    private CourseSignService courseSignService;

    /**
     * 获取学习进度
     * @param courseId
     * @return
     */
    @GetMapping("/{courseId}")
    public Msg getStudyLogByMyself(@PathVariable("courseId") String courseId){
        String userId = Util.userIdByShiro();
        CourseSign sign = courseSignService.get(courseId, userId);
        StudyLog studyLog = studyLogService.get(courseId, userId);
        return Msg.success().put("sign", sign).put("studyLog", studyLog);
    }

    /**
     * 报名,如报名默认直接新建学习进度
     * @param course
     * @return Msg
     */
    @PostMapping("/sign")
    public Msg signACourse(@RequestBody Course course) {
        String userId = Util.userIdByShiro();
        CourseSign courseSign = new CourseSign();
        courseSign.setCourseId(course.getCourseId());
        courseSign.setPloNum(userId);
        if(courseSignService.insert(courseSign) < 1) {
            courseSignService.delete(courseSign);
            return Msg.error();
        };
        if(studyLogService.insert(course, userId) < 1){
            courseSignService.delete(courseSign);
            return Msg.error();
        }
        //  报名后需更新课程学习人数
        courseSignService.updateCourseStudyNum(course.getCourseId());

        return Msg.success("报名成功！");
    }

    /**
     * 学习中心 - 学习记录
     * @param params
     * @return
     */
    @GetMapping("/my/record")
    public Msg listMyStudyRecord (@RequestParam Map<String, String> params) {
        return Msg.success(studyLogService.listMyLog("record", params, Util.userIdByShiro()));
    }

    /**
     * 点击完成在线学习，更新学习进度表
     * @param courseId
     * @return
     */
    @PutMapping("/file/complete/{courseId}")
    public Msg completeFileStudy (@PathVariable String courseId) {
        String userId = Util.userIdByShiro();
        StudyLog studyLog = studyLogService.get(courseId, userId);
        if(!Util.isNullorEmpty(studyLog.getStudyComp()) && studyLog.getStudyComp() == 1) return Msg.success("已经完成过课程学习了~");
        studyLog.setStudyDate(Util.currentDateTime());
        studyLog.setStudyComp((short)1);
        // 如无需考试、无需评价则直接完成考试
        if (studyLogService.chekCourseComplete(studyLog)) {
            studyLog.setCompDate(Util.currentDateTime());
            studyLog.setCoursePass((short)1);
        }
        int count = studyLogService.update(studyLog);
        if(count<1) return Msg.error();
        return Msg.success();
    }


    /**
     * 学习中心 - 学习积分记录
     * @param params
     * @return
     */
    @GetMapping("/my/point")
    public Msg listMyPointsLog (@RequestParam Map<String, String> params) {
        return Msg.success(studyLogService.listMyLog("point", params, Util.userIdByShiro()));
    }

    /**
     * 学习中心 - 学习时长记录
     * @param params
     * @return
     */
    @GetMapping("/my/hour")
    public Msg listMyHoursLog (@RequestParam Map<String, String> params) {
        return Msg.success(studyLogService.listMyLog("hour", params, Util.userIdByShiro()));
    }


}
