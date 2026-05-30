package com.cmbccd.ulms.college.controller;

import com.cmbccd.ulms.college.domain.Evaluate;
import com.cmbccd.ulms.college.domain.EvaluateExample;
import com.cmbccd.ulms.college.domain.StudyLog;
import com.cmbccd.ulms.college.service.EvaluateService;
import com.cmbccd.ulms.college.service.StudyLogService;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Msg;
import com.github.pagehelper.PageHelper;

import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("college/evaluate")
public class EvaluateController {

    @Resource
    private EvaluateService evaluateService;
    @Resource
    private StudyLogService studyLogService;
    /**
     * 评价列表
     * @param params
     * @return
     */
    @GetMapping("/list")
    public Msg listEvaluate(@RequestParam Map<String, String> params) {
        Map<String, Integer> pageParams = Util.innitTablePages(params);
        String courseId = params.get("courseId");
        EvaluateExample example = new EvaluateExample();
        EvaluateExample.Criteria criteria = example.createCriteria();
        if (!Util.isNullorEmpty(params.get("order"))) {
            example.setOrderByClause(Util.camel4underline(params.get("order")) + " " + params.get("orderType"));
        }
        if(!Util.isNullorEmpty(courseId)) criteria.andCourseIdEqualTo(courseId);
        PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
        List<Evaluate> list = evaluateService.list(example);
        return Msg.success(new DataPage<Evaluate>(list));
    }

    /**
     * 新增评价
     * @param evaluate
     * @return
     */
    @PostMapping("")
    public Msg insert(@RequestBody Evaluate evaluate) {
        String userId = Util.userIdByShiro();
        // 更新评价信息
        StudyLog studyLog = studyLogService.get(evaluate.getCourseId(), userId);
        if(studyLog.getEvalComp() == 1) return Msg.error("您已经评价过这个课程了！");
        int count = evaluateService.insert(evaluate);
        studyLog.setEvalDate(Util.currentDateTime());
        studyLog.setEvalComp((short)1);
        if(studyLogService.chekCourseComplete(studyLog)) {
            studyLog.setCoursePass((short)1);
            studyLog.setCompDate(Util.currentDateTime());
        }
        studyLogService.update(studyLog);
        if (count < 1) return Msg.error();
        return Msg.success();
    }
}
