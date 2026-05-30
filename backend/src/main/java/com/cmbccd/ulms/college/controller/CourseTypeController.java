package com.cmbccd.ulms.college.controller;

import com.cmbccd.ulms.college.domain.CourseType;
import com.cmbccd.ulms.college.service.CourseTypeService;
import com.cmbccd.ulms.sys.domain.Msg;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

@RequestMapping("/college/course/type")
@RestController
public class CourseTypeController {
    @Resource
    private CourseTypeService courseTypeService;


    /**
     * 获取课程类型树形结构数据
     * @return 返回Msg对象，包含课程类型树形结构数据
     */
    @GetMapping("/tree")
    public Msg getCourseTypeTree() {
    // 调用courseTypeService的courseTypeTree方法获取状态为1的课程类型树形结构数据
        List<CourseType> list = courseTypeService.courseTypeTree((short)1);
    // 返回成功状态及课程类型树形结构数据
        return Msg.success(list);
    }

    /**
     * 更新课程类型的接口方法
     * 使用 @PutMapping 注解标记为 PUT 请求处理
     * 使用 @SaCheckPermission 注解进行权限检查，需要 "college:coursetype:update" 权限
     *
     * @param courseType 接收前端传来的课程类型信息，通过 @RequestBody 注解自动绑定
     * @return 返回操作结果，成功返回 Msg.success()，失败返回 Msg.error()
     */
    @PutMapping("")
    @SaCheckPermission("college:coursetype:update")
    public Msg update(@RequestBody CourseType courseType){
        int count =courseTypeService.update(courseType);
        if (count<1) return Msg.error();
        return Msg.success();
    }

    /**
     * 插入课程类型的请求处理方法
     * 使用@PostMapping注解映射HTTP POST请求
     * 使用@SaCheckPermission注解进行权限检查，需要"college:coursetype:insert"权限
     *
     * @param courseType 请求体中的课程类型数据，通过@RequestBody注解自动绑定
     * @return 返回Msg对象，包含操作结果信息
     */
    @PostMapping("")
    @SaCheckPermission("college:coursetype:insert")
    public Msg insert(@RequestBody CourseType courseType){
        int count =courseTypeService.insert(courseType);
        if (count<1) return Msg.error();
        return Msg.success();
    }

    /**
     * 删除课程类型的接口方法
     * 使用DELETE方式请求，并通过id路径变量指定要删除的课程类型
     *
     * @param id 要删除的课程类型的ID
     * @return 返回操作结果，成功返回Msg.success()，失败返回Msg.error()
     */
    @DeleteMapping("/{id}")
    @SaCheckPermission("college:coursetype:delete")
    public Msg delete(@PathVariable int id){
        int count =courseTypeService.delete(id);
        if (count<1) return Msg.error();
        return Msg.success();
    }
}
