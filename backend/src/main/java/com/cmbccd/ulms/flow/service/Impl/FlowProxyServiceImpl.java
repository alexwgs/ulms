package com.cmbccd.ulms.flow.service.impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.flow.dao.FlowProxyMapper;
import com.cmbccd.ulms.flow.domain.FlowProxy;
import com.cmbccd.ulms.flow.domain.FlowProxyExample;
import com.cmbccd.ulms.flow.service.FlowProxyService;
import com.cmbccd.ulms.sys.domain.Msg;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Service
public class FlowProxyServiceImpl implements FlowProxyService {
    @Resource
    private FlowProxyMapper flowProxyMapper;


    @Override
    public Msg insert(FlowProxy record) {
        // 判断是否有重叠代理的时间
        FlowProxyExample example = new FlowProxyExample();
        FlowProxyExample.Criteria criteria = example.createCriteria();
        criteria.andPloNumEqualTo(record.getPloNum());
        // 判断两个日期是否有重叠
        criteria.andBegDateLessThanOrEqualTo(record.getEndDate());
        criteria.andEndDateGreaterThanOrEqualTo(record.getBegDate());
        // 判断是否有重叠的代理人
        if (flowProxyMapper.countByExample(example) > 0) {
            return Msg.error("代理设置失败！被代理人在该时间段已有代理设置！");
        }
        record.setId(Util.getUUID());
        record.setHandleDate(Util.currentDateTime());
        record.setHandlePlo(Util.userIdByShiro());
        flowProxyMapper.insert(record);
        return Msg.success("设置代理成功！");
    }

    @Override
    public int delete(String id) {
        return flowProxyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int update(FlowProxy record) {
        record.setHandleDate(Util.currentDateTime());
        record.setHandlePlo(Util.userIdByShiro());
        return flowProxyMapper.updateByPrimaryKey(record);
    }

    @Override
    public FlowProxy get(String id) {
        return flowProxyMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<FlowProxy> list(Map<String, String> params) {
        String caseStatus = params.get("caseStatus");
        String ploNum = params.get("ploNum");

        FlowProxyExample example = new FlowProxyExample();
        FlowProxyExample.Criteria criteria = example.createCriteria();
        Map<String, Integer> pageParams = Util.innitTablePages(params);
        if(!Util.isNullorEmpty(caseStatus)) {
            if("1".equals(caseStatus)) {
                criteria.andEndDateGreaterThanOrEqualTo(Util.currentDateTime());
            } else if("2".equals(caseStatus)) {
                criteria.andEndDateLessThanOrEqualTo(Util.currentDateTime());
            }
        }
        if(!Util.isNullorEmpty(ploNum)) {
            criteria.andPloNumEqualTo(ploNum);
            example.or().andProxyNumEqualTo(ploNum);
        }
        if (Util.isNullorEmpty(params.get("order"))) {
            example.setOrderByClause(" handle_date desc ");
        }else {
            example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
        }
        PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
        return flowProxyMapper.selectByExample(example);
    }

    @Override
    public String selectByProxyId(String proxyId) {
        FlowProxyExample example = new FlowProxyExample();
        FlowProxyExample.Criteria criteria = example.createCriteria();
        criteria.andProxyNumEqualTo(proxyId);
        criteria.andBegDateGreaterThanOrEqualTo(Util.currentDateTime());
        criteria.andEndDateLessThanOrEqualTo(Util.currentDateTime());
        List<FlowProxy> flowProxies = flowProxyMapper.selectByExample(example);
        if(flowProxies.size() > 0){
            return flowProxies.get(0).getPloNum();
        }
        return null;
    }

    @Override
    public Msg stopProxy(String proxyId) throws ParseException {
        FlowProxy flowProxy = flowProxyMapper.selectByPrimaryKey(proxyId);
        long times = Util.diffDateTime(flowProxy.getEndDate(), Util.currentDateTime());
        if(times > 0 ) return Msg.warn("代理已经过期！");
        flowProxy.setEndDate(Util.currentDateTime());
        flowProxyMapper.updateByPrimaryKey(flowProxy);
        return Msg.success("提前终止代理成功！");
    }

}
