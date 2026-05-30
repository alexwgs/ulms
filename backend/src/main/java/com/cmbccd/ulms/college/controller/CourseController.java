package com.cmbccd.ulms.college.controller;

import com.cmbccd.ulms.college.domain.Course;
import com.cmbccd.ulms.college.domain.CourseExample;
import com.cmbccd.ulms.college.domain.Teacher;
import com.cmbccd.ulms.college.service.CourseService;
import com.cmbccd.ulms.college.service.CourseTeacherService;
import com.cmbccd.ulms.college.service.CourseTypeService;
import com.cmbccd.ulms.college.service.TeachGroupService;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Msg;
import com.github.pagehelper.PageHelper;

import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/college/course")
public class CourseController {
    @Resource
    private CourseService courseService;
    @Resource
    private CourseTypeService courseTypeService;
    @Resource
    private CourseTeacherService courseTeacherService;
    @Resource
    private TeachGroupService teachGroupService;

    /**
     * 前端 - 课程列表
     * @param params
     * @return
     */
    @GetMapping("")
    public Msg ListCourse(@RequestParam Map<String, String> params) {
        String userId = Util.userIdByShiro();
        Map<String, Integer> pageParams = Util.innitTablePages(params);
        String courseType = params.get("courseType");
        String queryType = params.get("queryType");
        String query = params.get("query");
        CourseExample example = new CourseExample();
        CourseExample.Criteria criteria = example.createCriteria();
        if (!Util.isNullorEmpty(params.get("order"))) {
            example.setOrderByClause(Util.camel4underline(params.get("order")) + " " + params.get("orderType"));
        }
        if (!Util.isNullorEmpty(query)) {
            if("courseName".equals(queryType)) {
                criteria.andCourseNameLike("%"+query+"%");
            }else if ("lecturer".equals(queryType)){
                criteria.andLecturerEqualTo(query);
            }
        }
        String today = Util.getDateToday();
        criteria.andStatusEqualTo((short)1);
        criteria.andBegDateLessThanOrEqualTo(today);
        criteria.andEndDateGreaterThanOrEqualTo(today);
        // 获取授课对象
        criteria.andTeachObjectIn(teachGroupService.listGroupNameByPloNum(userId));
        if (!Util.isNullorEmpty(courseType)) {
            List<Integer> ids = courseTypeService.getChiledIds(Integer.parseInt(courseType));
            criteria.andCourseTypeIn(ids.stream().map(e -> e.shortValue()).collect(Collectors.toList()));
        }
        PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
        List<Course> list = courseService.list(example);
        return Msg.success(new DataPage<Course>(list));
    }
    /**
     * 管理 - 课程列表
     * @param params
     * @return
     */
    @GetMapping("/list")
    @SaCheckPermission("college:course:list")
    public Msg ListCourseByAdmin(@RequestParam Map<String, String> params) {
        Map<String, Integer> pageParams = Util.innitTablePages(params);
        // String courseType = params.get("courseType");
        // String queryType = params.get("queryType");
        String query = params.get("query");
        String status = params.get("status");
        CourseExample example = new CourseExample();
        CourseExample.Criteria criteria = example.createCriteria();
        if (!Util.isNullorEmpty(params.get("order"))) {
            example.setOrderByClause(Util.camel4underline(params.get("order")) + " " + params.get("orderType"));
        }
        if (!Util.isNullorEmpty(query)) {
            if(query.length() == 13) {
                criteria.andCourseIdEqualTo(query);
            }else{
                criteria.andCourseNameLike("%"+query+"%");
            }
        }
        if(!Util.isNullorEmpty(status)) {
            criteria.andStatusEqualTo(Short.parseShort(status));
        }
        PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
        List<Course> list = courseService.listWithClob(example);
        return Msg.success(new DataPage<Course>(list));
    }


    /**
     * 获取课程详情
     * @PathVariable("courseId") String courseId
     * @return
     */
    @GetMapping("/{courseId}")
    public Msg getCourse(@PathVariable("courseId") String courseId) {
        Integer fileStatus = 1;
        Course course = courseService.getWithClob(courseId, fileStatus);
        Teacher teacher = courseTeacherService.get(course.getLecturer());
        return Msg.success().put("course", course).put("teacher", teacher);
    }

    /**
     * 修改课程信息
     * @param course
     * @return
     */
    @PutMapping("")
    @SaCheckPermission("college:course:update")
    @MyLog(title = "[college-course]课程管理")
    public Msg update(@RequestBody Course course){
        int count = courseService.update(course);
        if(count < 1) return Msg.error();
        return Msg.success();
    }

    /**
     * 新增课程信息
     * @param course
     * @return
     */
    @PostMapping("")
    @SaCheckPermission("college:course:insert")
    @MyLog(title = "[college-course]课程管理")
    public Msg insert(@RequestBody Course course){
        int count = courseService.insert(course);
        if(count < 1) return Msg.error();
        return Msg.success();
    }

    /**
     * 获取首页滚动课程的列表
     * @param
     * @return
     */
    @GetMapping("/recommend")
    public Msg getIndex() {
        String userId = Util.userIdByShiro();
        CourseExample example = new CourseExample();
        CourseExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo((short)1);
        criteria.andTopFlagNotEqualTo((short)0);
        criteria.andTeachObjectIn(teachGroupService.listGroupNameByPloNum(userId));
        example.setOrderByClause(" top_flag desc");
        List<Course> courses = courseService.list(example);
        return Msg.success(courses);
    }
    /**
     * 获取首页推荐的课程列表
     * @param
     * @return
     */
    @GetMapping("/command")
    public Msg ListCommandCourse(){
        String userId = Util.userIdByShiro();
        CourseExample example = new CourseExample();
        CourseExample.Criteria criteria = example.createCriteria();
        criteria.andRecommendIsNotNull();
        criteria.andStatusEqualTo((short)1);
        criteria.andTeachObjectIn(teachGroupService.listGroupNameByPloNum(userId));
        example.setOrderByClause(" recommend_flag desc, course_score desc");
        List<Course> list = courseService.list(example);
        Map<String, List<Course>> command = list.stream().collect(Collectors.groupingBy(Course::getRecommend));
        return Msg.success(command);
    }

}
