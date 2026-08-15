package com.cmbccd.ulms.hr.service.impl;

import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.hr.dao.PerfListMapper;
import com.cmbccd.ulms.hr.domain.HrLabel;
import com.cmbccd.ulms.hr.domain.PerfList;
import com.cmbccd.ulms.hr.domain.PerfListExample;
import com.cmbccd.ulms.hr.service.HrLabelService;
import com.cmbccd.ulms.hr.service.PerfListService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class PerfListServiceImpl implements PerfListService {

    private static final Set<String> ALLOWED_ORDER_COLUMNS = new HashSet<>(Arrays.asList(
        "deptNum", "deptGroup", "jobLevel", "perfMonth", "perfUnit", "ploNum", "perfTarget"
    ));

    @Resource
    private PerfListMapper perfListMapper;

    @Resource
    private HrLabelService hrLabelService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertInit(String perfMonth) {
        this.delete(perfMonth);
        int count = 0;
        List<PerfList> list = perfListMapper.initList(perfMonth);
        for (PerfList item: list
             ) {
            item.setId(Util.getUUID());
            count += perfListMapper.insert(item);
        }
        // 根据申请的表单更新考核单元以及补贴单元和个人人力
        this.updatePerfUnit(perfMonth);
        // 同步更新标签
        hrLabelService.gennerLabel(perfMonth);
        // 根据更新好的标签更新各个口径的人力
        List<HrLabel> listHumFlags = hrLabelService.listLabelHumanFlag(perfMonth);
        for (HrLabel listHumFlag : listHumFlags) {
            perfListMapper.updateLabelHumanFlag(perfMonth, listHumFlag.getPloNum(), listHumFlag.getHumFlag(), listHumFlag.getGroupFlag(),listHumFlag.getDeptFlag());
        }
        return count;
    }

    @Override
    public List<PerfList> list(Map<String, String> params) {
        String perfMonth = Util.isNullorEmpty(params.get("perfMonth"))?Util.getMonth():params.get("perfMonth");
        String perfUnit = params.get("perfUnit");
        String deptNum = params.get("deptNum");
        Map<String, Integer> pageParams = Util.innitTablePages(params);
        PerfListExample example = new PerfListExample();
        PerfListExample.Criteria criteria = example.createCriteria();
        criteria.andPerfMonthEqualTo(perfMonth);
        if(!Util.isNullorEmpty(perfUnit)){
            criteria.andPerfUnitEqualTo(perfUnit);
        }
        if(!Util.isNullorEmpty(deptNum)){
            List<String> deptNums = Arrays.asList(deptNum.split(","));
            criteria.andDeptGroupIn(deptNums);
        }
        if (Util.isNullorEmpty(params.get("order"))) {
            example.setOrderByClause(" dept_num,dept_group,job_level ");
        }else if (ALLOWED_ORDER_COLUMNS.contains(params.get("order"))) {
            example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
        }
        PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
        List<PerfList> list = perfListMapper.selectByExample(example);
        for (PerfList perfList : list) {
            perfList.setDeptNum(DataCache.DEPARTMENT.get(perfList.getDeptNum()).getDeptName());
            if (!Util.isNullorEmpty(perfList.getDeptGroup())) {
                perfList.setDeptGroup(DataCache.DEPARTMENT.get(perfList.getDeptGroup()).getDeptName());
            } else {
                perfList.setDeptGroup("");
            }
            if (Util.isNullorEmpty(DataCache.JOBINFO.get(perfList.getJobLevel()))) {
                perfList.setJobLevel("");
            } else {
                perfList.setJobLevel(DataCache.JOBINFO.get(perfList.getJobLevel()).getJobName());
            }

        }
        for (PerfList  item : list) {
            item.setLabelList(hrLabelService.list(perfMonth, item.getPloNum()));
        }
        return list;
    }

    public List<PerfList> report(Map<String, String> params) {
        String perfMonth = Util.isNullorEmpty(params.get("perfMonth"))?Util.getMonth():params.get("perfMonth");
        String perfUnit = params.get("perfUnit");
        String deptNum = params.get("deptNum");
        PerfListExample example = new PerfListExample();
        PerfListExample.Criteria criteria = example.createCriteria();
        criteria.andPerfMonthEqualTo(perfMonth);
        if(!Util.isNullorEmpty(perfUnit)){
            criteria.andPerfUnitEqualTo(perfUnit);
        }
        if(!Util.isNullorEmpty(deptNum)){
            List<String> deptNums = Arrays.asList(deptNum.split(","));
            criteria.andDeptGroupIn(deptNums);
        }
        if (Util.isNullorEmpty(params.get("order"))) {
            example.setOrderByClause(" dept_num,dept_group,job_level ");
        }else if (ALLOWED_ORDER_COLUMNS.contains(params.get("order"))) {
            example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
        }
        List<PerfList> list = perfListMapper.selectByExample(example);
        for (PerfList perfList : list) {
            perfList.setDeptNum(DataCache.DEPARTMENT.get(perfList.getDeptNum()).getDeptName());
            if (!Util.isNullorEmpty(perfList.getDeptGroup())) {
                perfList.setDeptGroup(DataCache.DEPARTMENT.get(perfList.getDeptGroup()).getDeptName());
            } else {
                perfList.setDeptGroup("");
            }
            if (Util.isNullorEmpty(DataCache.JOBINFO.get(perfList.getJobLevel()))) {
                perfList.setJobLevel("");
            } else {
                perfList.setJobLevel(DataCache.JOBINFO.get(perfList.getJobLevel()).getJobName());
            }

        }
        for (PerfList  item : list) {
            List<HrLabel> labelList = hrLabelService.list(perfMonth, item.getPloNum());
            StringBuilder labels = new StringBuilder();
            for (HrLabel e : labelList) {
                labels.append("[").append(e.getLabelName()).append(";").append("个人/小组/科室：").append(e.getHumFlag()).append("/").append(e.getGroupFlag()).append("/").append(e.getDeptFlag()).append("备注：").append(e.getMemo()).append("]");
            }
            item.setLabels(labels.toString());
        }
        return list;
    }

    @Override
    public PerfList getByUserId(String userId, String perfMonth) {
        PerfListExample example = new PerfListExample();
        PerfListExample.Criteria criteria = example.createCriteria();
        criteria.andPloNumEqualTo(userId);
        criteria.andPerfMonthEqualTo(perfMonth);
        List<PerfList> list = perfListMapper.selectByExample(example);
        if(list.size()<1) return null;
        return list.get(0);
    }

    @Override
    public int updatePerfUnit(String perfMonth) {
        int count = 0;
        List<PerfList> list = perfListMapper.selectFlowPerfUnit(perfMonth);
        PerfListExample example = new PerfListExample();
        PerfListExample.Criteria criteria = example.createCriteria();
        criteria.andPerfMonthEqualTo(perfMonth);
        for (PerfList perfList : list) {
            criteria.andPloNumEqualTo(perfList.getPloNum());
            count += perfListMapper.updateByExampleSelective(perfList,example);
        }
        return count;
    }

    @Override
    public int delete(String perfMonth) {
        PerfListExample example = new PerfListExample();
        PerfListExample.Criteria criteria = example.createCriteria();
        criteria.andPerfMonthEqualTo(perfMonth);
        return perfListMapper.deleteByExample(example);
    }

    /**
     * 首先要确定当前查询人的团队及组织架构
     * 1.当前是否有特殊团队考核申请
     * 2.当前岗位
     * 3.当前组织架构
     * @return
     */
    @Override
    public List<PerfList> listMyTeam(String perfMonth) {
        String userId = Util.userIdByShiro();
        PerfList perfList = this.getByUserId(userId, perfMonth);
        PerfListExample example = new PerfListExample();
        PerfListExample.Criteria criteria = example.createCriteria();
        // 无考核
        // List<String> units = Arrays.asList("金普卡团队","融媒体团队","网服团队");

        criteria.andPerfMonthEqualTo(perfMonth);
        if(Util.isNullorEmpty(perfList.getPerfUnit())) {
            // 金普卡、融媒体、网络排除
            criteria.andPerfUnitIsNull();
        } else if(perfList.getPerfUnit().equals(perfList.getOriPerfUnit())) {
            // 判断当前是否有特殊申请团队
            criteria.andPerfUnitEqualTo(perfList.getPerfUnit());
            if (Util.isNullorEmpty(perfList.getDeptGroup())) {
                criteria.andDeptNumEqualTo(perfList.getDeptNum());
            }else {
                criteria.andDeptGroupEqualTo(perfList.getDeptGroup());
            }
        } else {
            criteria.andPerfUnitEqualTo(perfList.getPerfUnit());
        }
        List<PerfList> list = perfListMapper.selectByExample(example);
        return list;
    }
}
