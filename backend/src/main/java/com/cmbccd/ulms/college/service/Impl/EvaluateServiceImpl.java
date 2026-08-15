package com.cmbccd.ulms.college.service.impl;

import com.cmbccd.ulms.college.dao.EvaluateMapper;
import com.cmbccd.ulms.college.dao.EvaluateTempleteMapper;
import com.cmbccd.ulms.college.domain.Evaluate;
import com.cmbccd.ulms.college.domain.EvaluateExample;
import com.cmbccd.ulms.college.domain.EvaluateExample.Criteria;
import com.cmbccd.ulms.college.domain.EvaluateTemplete;
import com.cmbccd.ulms.college.service.EvaluateService;
import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.service.PublicService;

import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class EvaluateServiceImpl implements EvaluateService {
    @Resource
    private EvaluateMapper evaluateMapper;

    @Resource
    private EvaluateTempleteMapper evaluateTempleteMapper;

    @Resource
    private PublicService publicService;

    @Override
    public List<Evaluate> list(EvaluateExample example) {
        List<Evaluate> list = evaluateMapper.selectByExample(example);
        for(Evaluate item : list) {
            if(item.getIfAnon() == 1) {
                item.setPloNum("匿名");
            } else {
                item.setPloNum(DataCache.getEmployees().get(item.getPloNum()).getPloName());
            }
            item.setEvaluate(evaluateTempleteMapper.selectByJourno(item.getTempId(),item.getJourno()));
        }
        return list;
    }

    @Override
    public DataPage<Evaluate> listEvaluateByQuery(Map<String, String> params) {
        Map<String, Integer> pageParams = Util.innitTablePages(params);
        String courseId = params.get("courseId");
        EvaluateExample example = new EvaluateExample();
        Criteria criteria = example.createCriteria();
        if (!Util.isNullorEmpty(params.get("order"))) {
            example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
        }
        if (!Util.isNullorEmpty(courseId)) criteria.andCourseIdEqualTo(courseId);
        PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
        List<Evaluate> list = list(example);
        return new DataPage<Evaluate>(list);
    }

    @Override
    public Evaluate get(String journo) {
        Evaluate evaluate = evaluateMapper.selectByPrimaryKey(journo);
        if(evaluate.getIfAnon() == 1) {
            evaluate.setPloNum("匿名");
        } else {
            evaluate.setPloNum(DataCache.getEmployees().get(evaluate.getPloNum()).getPloName());
        }
        evaluate.setEvaluate(evaluateTempleteMapper.selectByJourno(evaluate.getTempId(),evaluate.getJourno()));
        return evaluate;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(Evaluate record) {
        String journo = publicService.getJourno();
        String userId = Util.userIdByShiro();

        Evaluate evaluate = new Evaluate();
        evaluate.setCourseId(record.getCourseId());
        evaluate.setIfAnon(record.getIfAnon());
        evaluate.setTempId(record.getTempId());
        evaluate.setPloNum(userId);
        evaluate.setHandleDate(Util.currentDateTime());
        evaluate.setJourno(journo);
        int count = evaluateMapper.insert(evaluate);
        for(EvaluateTemplete item : record.getEvaluate()) {
            evaluateTempleteMapper.insertJourno(journo, item.getTempNo(), item.getQuesType(), item.getCont());
        }
        // 更新课程得分
        evaluateTempleteMapper.updateCourseEvaluate(record.getCourseId());
        return count;
    }

    @Override
    public int update(Evaluate evaluate) {
        return 0;
    }

    @Override
    public int delete(String journo) {
        return evaluateMapper.deleteByPrimaryKey(journo);
    }

    @Override
    public List<Evaluate> listByCourseId(String courseId) {
        EvaluateExample example = new EvaluateExample();
        EvaluateExample.Criteria criteria = example.createCriteria();
        example.setOrderByClause(" handle_date ");
        criteria.andCourseIdEqualTo(courseId);
        List<Evaluate> list = evaluateMapper.selectByExample(example);
        return list;
    }


}
