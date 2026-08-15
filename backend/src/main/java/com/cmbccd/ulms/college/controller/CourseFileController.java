package com.cmbccd.ulms.college.controller;

import com.cmbccd.ulms.college.domain.CourseFile;
import com.cmbccd.ulms.college.domain.CourseProgress;
import com.cmbccd.ulms.college.domain.CourseProgressExample;
import com.cmbccd.ulms.college.service.CourseFileService;
import com.cmbccd.ulms.college.service.CourseProgressService;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Msg;
import com.github.pagehelper.PageHelper;

import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("college/course/file")
public class CourseFileController {

    @Resource
    private CourseFileService courseFileService;
    @Resource
    private CourseProgressService courseProgressService;

    /**
     * 插入课程附件
     * @param courseFile
     * @return
     */
    @PostMapping("")
    public Msg insert(@RequestBody CourseFile courseFile) {
        int count = courseFileService.insert(courseFile);
        if(count < 1) return Msg.error();
        return Msg.success();
    }

    /**
     * 更新课程附件
     * @param courseFiles
     * @return
     */
    @PutMapping("")
    public Msg update(@RequestBody List<CourseFile> courseFiles) {
        int count = 0;
        for (CourseFile courseFile : courseFiles) {
            count = courseFileService.update(courseFile);
            if (count < 1) {
                courseFileService.insert(courseFile);
            }
        }
        return Msg.success();
    }

    /**
     * 获取我的学习进度
     * @param courseId
     * @return
     */
    @GetMapping("/progress/my/{courseId}")
    public Msg listMyProgress (@PathVariable("courseId") String courseId) {
        String userId = Util.userIdByShiro();
        CourseProgressExample example = new CourseProgressExample();
        CourseProgressExample.Criteria criteria = example.createCriteria();
        criteria.andCourseIdEqualTo(courseId);
        criteria.andPloNumEqualTo(userId);
        List<CourseProgress> list = courseProgressService.list(example);
        return Msg.success(list);
    }

    /**
     * 更新我的学习进度
     * @param courseProgress
     * @return
     */
    @PutMapping("/progress/my")
    public Msg listMyProgress (@RequestBody CourseProgress courseProgress) {
        if (Util.isNullorEmpty(courseProgress.getJourno())){
            courseProgress = courseProgressService.insert(courseProgress);
        } else {
            courseProgress = courseProgressService.updateTime(courseProgress);
        }
        return Msg.success(courseProgress);
    }

    /**
     * 我的学习附件时长日志
     * @param params
     * @return
     */
    @GetMapping("/progress/log")
    public Msg listMyStudyHoursLog (@RequestParam Map<String, String> params) {
        String userId = Util.userIdByShiro();
        Map<String, Integer> pageParams = Util.innitTablePages(params);
        // String queryType = params.get("queryType");
        // String query = params.get("query");
        CourseProgressExample example = new CourseProgressExample();
        CourseProgressExample.Criteria criteria = example.createCriteria();
        criteria.andPloNumEqualTo(userId);
        if (!Util.isNullorEmpty(params.get("order"))) {
            example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
        }
        PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
        List<CourseProgress> list = courseProgressService.listMyLearnLog(example);
        return Msg.success(new DataPage<CourseProgress>(list));
    }

}
