package com.cmbccd.ulms.college.controller;

import com.cmbccd.ulms.college.domain.Teacher;
import com.cmbccd.ulms.college.domain.TeacherExample;
import com.cmbccd.ulms.college.service.CourseTeacherService;
import com.cmbccd.ulms.sys.domain.Msg;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("college/teacher")
public class CourseTeacherController {
    @Resource
    private CourseTeacherService courseTeacherService;

    /**
     * 获取教师列表
     * @param params
     * @return
     */
    @GetMapping("/list")
    public Msg listTeacher(@RequestParam Map<String, String> params){
        return Msg.success(courseTeacherService.listTeacherByQuery(params));
    }
    /**
     * 获取所有教师列表
     * @return
     */
    @GetMapping("/all")
    public Msg listAllTeacher() {
        TeacherExample example = new TeacherExample();
//        TeacherExample.Criteria criteria = example.createCriteria();
//        criteria.andStatusEqualTo((short)1);
        return Msg.success(courseTeacherService.list(example));
    }

    /**
     * 获取推荐教师列表
     * @return
     */
    @GetMapping("/top")
    public Msg listTopTeacher() {
        return Msg.success(courseTeacherService.listTopTeacher());
    }

    /**
     * 更新教师信息
     * @param teacher
     * @return
     */
    @PutMapping("")
    @SaCheckPermission("college:teacher:update")
    public Msg update(@RequestBody Teacher teacher) {
        int count = courseTeacherService.update(teacher);
        if (count < 1) return Msg.error();
        return Msg.success();
    }

    /**
     * 新增教师信息
     * @param teacher
     * @return
     */
    @PostMapping("")
    @SaCheckPermission("college:teacher:insert")
    public Msg insert(@RequestBody Teacher teacher) {
        int count = courseTeacherService.insert(teacher);
        if(count < 1) return Msg.error();
        return Msg.success();
    }

    /**
     * 删除教师信息
     * @param ploNum
     * @return
     */
    @DeleteMapping("/{ploNum}")
    @SaCheckPermission("college:teacher:delete")
    public Msg delete(@PathVariable("ploNum") String ploNum) {
        int count = courseTeacherService.delete(ploNum);
        if(count < 1) return Msg.error();
        return Msg.success();
    }
}
