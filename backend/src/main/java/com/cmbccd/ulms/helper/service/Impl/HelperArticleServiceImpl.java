package com.cmbccd.ulms.helper.service.impl;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.helper.dao.HelperArticleMapper;
import com.cmbccd.ulms.helper.domain.HelperArticle;
import com.cmbccd.ulms.helper.domain.HelperArticleExample;
import com.cmbccd.ulms.helper.domain.HelperArticleExample.Criteria;
import com.cmbccd.ulms.helper.service.HelperArticleService;
import com.cmbccd.ulms.sys.service.PublicService;

import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class HelperArticleServiceImpl implements HelperArticleService {

    @Resource
    private HelperArticleMapper helperArticleMapper;

    @Resource
    private PublicService publicService;

    @Override
    public List<HelperArticle> listNoBlob(HelperArticleExample example) {
        return helperArticleMapper.selectByExample(example);
    }

    @Override
    public DataPage<HelperArticle> listArticleByQuery(Map<String, String> params, boolean selfOnly) {
        String status = params.get("status");
        String routeId = params.get("routeId");
        String query = params.get("query");
        String queryType = params.get("queryType");
        Map<String, Integer> pageParams = Util.innitTablePages(params);
        HelperArticleExample example = new HelperArticleExample();
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
        List<HelperArticle> list = helperArticleMapper.selectByExample(example);
        return new DataPage<HelperArticle>(list);
    }

    @Override
    public HelperArticleExample listForFont(Map<String, String> params) {
        String routeId = params.get("routeId");
        String query = params.get("query");
        String queryType = params.get("queryType");
        Map<String, Integer> pageParams = Util.innitTablePages(params);
        HelperArticleExample example = new HelperArticleExample();
        HelperArticleExample.Criteria criteria = example.createCriteria();
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
    public HelperArticle get(String journo) {
        return helperArticleMapper.selectByPrimaryKey(journo);
    }

    @Override
    public int create(HelperArticle record) {
        record.setJourno(publicService.getJourno());
        record.setInsertDate(Util.currentDateTime());
        record.setInsertPlo(Util.userIdByShiro());
        return helperArticleMapper.insertSelective(record);
    }

    @Override
    public int delete(String journo) {
        return helperArticleMapper.deleteByPrimaryKey(journo);
    }

    @Override
    public int update(HelperArticle record) {
        record.setUpdateDate(Util.currentDateTime());
        record.setUpdatePlo(Util.userIdByShiro());
        return helperArticleMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    @Override
    public int increaseReadCount(String journo) {
        return helperArticleMapper.updateArticleReadCount(journo);
    }
}
