package com.cmbccd.ulms.college.controller;

import com.cmbccd.ulms.college.domain.CourseExamTemplate;
import com.cmbccd.ulms.college.service.CourseExamTemplateService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("/college/course/exam")
public class CourseExamTemplateController {

    @Resource
    private CourseExamTemplateService courseExamTemplateService;

    /**
     * 获取课程考试模板
     * @param courseId
     * @return
     */
    @GetMapping("/template/{courseId}")
    public Msg getCourseExamTemplate (@PathVariable("courseId") String courseId) {
        CourseExamTemplate courseExamTemplate = courseExamTemplateService.get(courseId);
        return Msg.success(courseExamTemplate);
    }

    /**
     * 更新课程考试模板
     * @param record
     * @return
     */
    @PutMapping("/template")
    public Msg updateTemplate (@RequestBody CourseExamTemplate record) {
        int count = courseExamTemplateService.update(record);
        if(count<1) return Msg.error();
        return Msg.success();
    }

    /**
     * 插入课程考试模板
     * @param record
     * @return
     */
    @PostMapping("/template")
    public Msg insertTemplate(@RequestBody CourseExamTemplate record) {
        int count = courseExamTemplateService.insert(record);
        if(count<1) return Msg.error();
        return Msg.success();
    }

    /**
     * 删除课程考试模板
     * @param courseId
     * @return
     */
    @DeleteMapping("/template/{courseId}")
    public Msg deleteCourseExamTemplate (@PathVariable("courseId") String courseId) {
        courseExamTemplateService.delete(courseId);
        return Msg.success();
    }

}
