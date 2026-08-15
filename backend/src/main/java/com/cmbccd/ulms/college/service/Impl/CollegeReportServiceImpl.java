package com.cmbccd.ulms.college.service.impl;

import com.cmbccd.ulms.college.dao.CollegeReportMapper;
import com.cmbccd.ulms.college.domain.Evaluate;
import com.cmbccd.ulms.college.domain.EvaluateTemplete;
import com.cmbccd.ulms.college.domain.report.CourseExamDetail;
import com.cmbccd.ulms.college.domain.report.CourseLib;
import com.cmbccd.ulms.college.domain.report.CourseStudyDetail;
import com.cmbccd.ulms.college.service.CollegeReportService;
import com.cmbccd.ulms.college.service.EvaluateService;
import com.cmbccd.ulms.college.service.EvaluateTempleteService;
import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Employee;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CollegeReportServiceImpl implements CollegeReportService {

    @Resource
    private CollegeReportMapper collegeReportMapper;

    @Resource
    EvaluateService evaluateService;
    @Resource
    EvaluateTempleteService evaluateTempleteService;

    @Override
    public List<CourseStudyDetail> listCourseStudyDeatilHum(String courseId,String deptNum) {
        List<CourseStudyDetail> list = collegeReportMapper.selectCourseStudyDeatilHum(courseId, deptNum);
        for(CourseStudyDetail item: list) {
            item.setDeptNum(Optional.ofNullable(DataCache.getDepartments().get(item.getDeptNum())).map(e -> e.getDeptName()).orElse("-"));
            item.setDeptGroup(Optional.ofNullable(DataCache.getDepartments().get(item.getDeptGroup())).map(e -> e.getDeptName()).orElse("-"));
        }
        return list;
    }

    @Override
    public List<CourseLib> listCourseLib() {
        return collegeReportMapper.selectCourseLib();
    }

    @Override
    public List<CourseExamDetail> listCourseExamDetail(String courseId) {
        List<CourseExamDetail> list = collegeReportMapper.selectCourseExamDetail(courseId);
        for(CourseExamDetail item: list) {
            Employee user = DataCache.getEmployees().get(item.getPloNum());
            item.setPloName(user.getPloName());
            item.setDeptNum(user.getDeptName());
            item.setDeptGroup(user.getGroupName());
        }
        return list;
    }

    @Override
    public List<List<List<String>>> listEvalDetail(String courseId) {
        List<Evaluate> list =  evaluateService.listByCourseId(courseId);
        Map<String, List<Evaluate>> data = list.stream().collect(Collectors.groupingBy(item -> item.getTempId()));
        Set<String> keys = data.keySet();
        List<List<List<String>>> sheet = new ArrayList<List<List<String>>>();
        for(String tempId :keys){
            List<EvaluateTemplete> evaluateTemplete = evaluateTempleteService.select(tempId);
            List<List<String>> table = new ArrayList<List<String>>();
            // 表头加入
            List<String> header = new ArrayList<>();
            header.add("科室");
            header.add("组别");
            header.add("工号");
            header.add("姓名");
            header.add("匿名");
            header.add("评价时间");
            List<String> evalStrings = evaluateTemplete.stream().map(EvaluateTemplete::getQuesCont).collect(Collectors.toList());
            header.addAll(evalStrings);
            table.add(header);

            for(Evaluate item : list) {
                List<String> row = new ArrayList<>();
                Employee user = DataCache.getEmployees().get(item.getPloNum());
                row.add(user.getDeptName());
                row.add(Util.isNullorEmpty(user.getGroupName())?"-":user.getGroupName());
                row.add(item.getPloNum());
                row.add(user.getPloName());
                row.add(item.getIfAnon()==0?"实名":"匿名");
                row.add(item.getHandleDate());
                List<String> evalResult = collegeReportMapper.selectEvalByJourno(item.getJourno());
                for (String cont :evalResult) {
                    row.add(cont);
                }
                table.add(row);
            }
            sheet.add(table);
        }
        return sheet;
    }

    @Override
    public List<Map<String, String>> viewCourseStudyByDept(String courseId) {
        List<Map<String, String>> listMap = collegeReportMapper.viewCourseStudyByDept(courseId);
        for(Map<String, String> dataMap: listMap){
            dataMap.put("deptNum", Optional.ofNullable(DataCache.getDepartments().get(dataMap.get("deptNum"))).map(e -> e.getDeptName()).orElse("-"));
            dataMap.put("deptGroup", Optional.ofNullable(DataCache.getDepartments().get(dataMap.get("deptGroup"))).map(e -> e.getDeptName()).orElse("-"));
        }
        return listMap;
    }


}
