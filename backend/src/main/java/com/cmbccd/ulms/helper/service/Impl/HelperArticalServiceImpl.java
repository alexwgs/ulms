package com.cmbccd.ulms.helper.service.impl;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.helper.dao.HelperArticalMapper;
import com.cmbccd.ulms.helper.domain.HelperArtical;
import com.cmbccd.ulms.helper.domain.HelperArticalExample;
import com.cmbccd.ulms.helper.domain.HelperArticalExample.Criteria;
import com.cmbccd.ulms.helper.service.HelperArticalService;
import com.cmbccd.ulms.sys.service.PublicService;

import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class HelperArticalServiceImpl implements HelperArticalService {

    @Resource
    private HelperArticalMapper helperArticalMapper;

    @Resource
    private PublicService publicService;

    @Override
    public List<HelperArtical> listNoBlob(HelperArticalExample example) {
        return helperArticalMapper.selectByExample(example);
    }

    @Override
    public DataPage<HelperArtical> listArticalByQuery(Map<String, String> params, boolean selfOnly) {
        String status = params.get("status");
        String routeId = params.get("routeId");
        String query = params.get("query");
        String queryType = params.get("queryType");
        Map<String, Integer> pageParams = Util.innitTablePages(params);
        HelperArticalExample example = new HelperArticalExample();
        Criteria criteria = example.createCriteria();
        Criteria criteria2 = example.createCriteria();
        if (selfOnly) {
            String userId = Util.userIdByShiro();
            criteria.andInsertPloEqualTo(userId);
            criteria2.andUpdatePloEqualTo(userId);
        }
        if (!Util.isNullorEmpty(params.get("order"))) {
            example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
        }
        if (!Util.isNullorEmpty(routeId)) {
            criteria.andRouteIdEqualTo(Integer.parseInt(routeId));
            criteria2.andRouteIdEqualTo(Integer.parseInt(routeId));
        }
        if (!Util.isNullorEmpty(status)) {
            criteria.andStatusEqualTo(Integer.parseInt(status));
            criteria2.andStatusEqualTo(Integer.parseInt(status));
        }
        if (!Util.isNullorEmpty(query)) {
            if (Util.isNullorEmpty(queryType) || queryType.equals("keyword")) {
                criteria2.andTitleLike("%" + query + "%");
                criteria.andKeyWordLike("%" + query + "%");
            } else if (queryType.equals("content")) {
                criteria2.andTitleLike("%" + query + "%");
                criteria.andContentLike("%" + query + "%");
            }
            example.or(criteria2);
        }
        PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
        List<HelperArtical> list = helperArticalMapper.selectByExample(example);
        return new DataPage<HelperArtical>(list);
    }

    @Override
    public HelperArticalExample listForFont(Map<String, String> params) {
        String routeId = params.get("routeId");
        String query = params.get("query");
        String queryType = params.get("queryType");
        Map<String, Integer> pageParams = Util.innitTablePages(params);
        HelperArticalExample example = new HelperArticalExample();
        HelperArticalExample.Criteria criteria = example.createCriteria();
        if (!Util.isNullorEmpty(params.get("order"))) {
            example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
        }
        example.setOrderByClause(" SORTING asc, CLICKS desc");
        if(!Util.isNullorEmpty(routeId)) {
            criteria.andRouteIdEqualTo(Integer.parseInt(routeId));
        }
        if(!Util.isNullorEmpty(query)){
            if("keyword".equals(queryType)){
                criteria.andKeyWordLike("%"+query+"%");
            }else if("content".equals(queryType)) {
                criteria.andContentLike("%"+query+"%");
            }else {
                // 默认title搜索
                criteria.andTitleLike("%"+query+"%");
            }
        }
        criteria.andStatusEqualTo(1);
        PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
        return example;
    }

    @Override
    public HelperArtical get(String journo) {
        return helperArticalMapper.selectByPrimaryKey(journo);
    }

    @Override
    public int create(HelperArtical record) {
        record.setJourno(publicService.getJourno());
        record.setInsertDate(Util.currentDateTime());
        record.setInsertPlo(Util.userIdByShiro());
        return helperArticalMapper.insertSelective(record);
    }

    @Override
    public int delete(String journo) {
        return helperArticalMapper.deleteByPrimaryKey(journo);
    }

    @Override
    public int update(HelperArtical record) {
        record.setUpdateDate(Util.currentDateTime());
        record.setUpdatePlo(Util.userIdByShiro());
        return helperArticalMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    @Override
    public int increaseReadCount(String journo) {
        return helperArticalMapper.updateArticalReadCount(journo);
    }
}
