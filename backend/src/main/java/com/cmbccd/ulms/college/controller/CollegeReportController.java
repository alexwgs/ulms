package com.cmbccd.ulms.college.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.cmbccd.ulms.college.domain.report.CourseExamDetail;
import com.cmbccd.ulms.college.domain.report.CourseLib;
import com.cmbccd.ulms.college.domain.report.CourseStudyDetail;
import com.cmbccd.ulms.college.service.CollegeReportService;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.ExcelUtils;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Msg;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/college/report")
public class CollegeReportController {

    @Resource
    private CollegeReportService collegeReportService;

    /**
     * 报表 - 人员课程学习情况BY人员
     * @param response
     * @param courseId
     * @throws IOException
     */
    @GetMapping("/hum/{courseId}")
    @SaCheckPermission("college:report:study:hum")
    @MyLog(title = "[college-report]报表管理", content = "报表下载")
    public void reportCourseStudyDeatilHum(HttpServletResponse response, @PathVariable("courseId") String courseId) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        List<CourseStudyDetail> list = collegeReportService.listCourseStudyDeatilHum(courseId, null);
        String fileName = "课程学习记录明细BY人员";
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtils.simpleExcelTemplateStyle();
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(response.getOutputStream(), CourseStudyDetail.class).registerWriteHandler(horizontalCellStyleStrategy)
                .sheet("课程学习记录明细BY人员").doWrite(list);
    }

    /**
     * 前端 - 报表 - 人员课程学习情况BY人员
     * @param params
     * @return Msg
     */
    @GetMapping("/course/infomation")
//    @SaCheckPermission("college:report:study:hum")
    @MyLog(title = "[college-report]报表管理", content = "报表下载")
    public Msg reportCourseStudyDeatilHum(@RequestParam Map<String, String> params) {
        String courseId = params.get("courseId");
        String deptNum = params.get("deptNum");
        if (Util.isNullorEmpty(courseId) || Util.isNullorEmpty(deptNum)) {
            return Msg.error("参数错误!请正确选择课程及科室或组别！");
        }
        List<CourseStudyDetail> list = collegeReportService.listCourseStudyDeatilHum(courseId, deptNum);
        return Msg.success(list);
    }
    /**
     * 报表 - 人员课程学习情况BY人员
     * @param response
     * @param params
     * @throws IOException
     */
    @GetMapping("/course/dowmload/infomation")
    @MyLog(title = "[college-report]报表管理", content = "报表下载")
    public void reportCourseStudyDeatilHumDowmload(HttpServletResponse response, @RequestParam Map<String, String> params) throws IOException {
        String courseId = params.get("courseId");
        String deptNum = params.get("deptNum");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        List<CourseStudyDetail> list = collegeReportService.listCourseStudyDeatilHum(courseId, deptNum);
        String fileName = "课程学习记录明细BY人员";
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtils.simpleExcelTemplateStyle();
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(response.getOutputStream(), CourseStudyDetail.class).registerWriteHandler(horizontalCellStyleStrategy)
                .sheet("课程学习记录明细BY人员").doWrite(list);
    }
    /**
     * 报表 - 课程列表报表
     * @param response
     * @throws IOException
     */
    @GetMapping("/course")
    @SaCheckPermission("college:report:course:list")
    @MyLog(title = "[college-report]报表管理", content = "报表下载")
    public void reportCourseLib(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        List<CourseLib> list = collegeReportService.listCourseLib();
        String fileName = "课程详情明细";
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtils.simpleExcelTemplateStyle();
        EasyExcel.write(response.getOutputStream(), CourseLib.class).registerWriteHandler(horizontalCellStyleStrategy)
                .sheet("课程详情明细").doWrite(list);
    }

    /**
     * 报表 - 课程考试记录BY人员
     * @param response
     * @throws IOException
     */
    @GetMapping("/exam/{courseId}")
    @SaCheckPermission("college:report:exam:list")
    @MyLog(title = "[college-report]报表管理", content = "报表下载")
    public void reportCourseExam(HttpServletResponse response, @PathVariable("courseId") String courseId) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        List<CourseExamDetail> list = collegeReportService.listCourseExamDetail(courseId);
        String fileName = "课程考试记录BY人员";
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtils.simpleExcelTemplateStyle();
        EasyExcel.write(response.getOutputStream(), CourseExamDetail.class).registerWriteHandler(horizontalCellStyleStrategy)
                .sheet("课程考试记录BY人员").doWrite(list);
    }


    /**
     * 报表 - 满意度评价明细By课程
     * @param response
     * @throws IOException
     */
    @GetMapping("/eval/{courseId}")
    @SaCheckPermission("college:report:eval:list")
    @MyLog(title = "[college-report]报表管理", content = "报表下载")
    public void reportCourseEval(HttpServletResponse response, @PathVariable("courseId") String courseId) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName = "满意度评价明细By课程";
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        List<List<List<String>>> list = collegeReportService.listEvalDetail(courseId);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtils.simpleExcelTemplateStyle();
        for(List<List<String>> data: list) {
            int i= 1;
            EasyExcel.write(response.getOutputStream()).registerWriteHandler(horizontalCellStyleStrategy)
                .sheet("满意度评价明细sheet"+i++).doWrite(data);
        }
    }

    /**
     * 图形 - 学习情况by科室组别
     * @param courseId
     * @throws IOException
     */
    @GetMapping("/view/{courseId}")
    public Msg reportCourseEval(@PathVariable("courseId") String courseId) {
        List<Map<String, String>> list = collegeReportService.viewCourseStudyByDept(courseId);
        return Msg.success(list);
    }
}
