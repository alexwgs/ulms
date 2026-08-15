package com.cmbccd.ulms.rpa.service.impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.rpa.dao.ToolListMapper;
import com.cmbccd.ulms.rpa.domain.ToolList;
import com.cmbccd.ulms.rpa.domain.ToolListExample;
import com.cmbccd.ulms.rpa.service.ToolListService;
import com.cmbccd.ulms.sys.service.PublicService;
import com.cmbccd.ulms.sys.service.UserRoleService;
import com.cmbccd.ulms.sys.domain.UserRole;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ToolListServiceImpl implements ToolListService {

    private final ToolListMapper toolListMapper;

    private final PublicService publicService;

    private final UserRoleService userRoleService;

    public ToolListServiceImpl(ToolListMapper toolListMapper, PublicService publicService, UserRoleService userRoleService) {
        this.toolListMapper = toolListMapper;
        this.publicService = publicService;
        this.userRoleService = userRoleService;
    }

    @Override

    public List<ToolList> getAllToolList(@RequestParam Map<String, String> params) {
        Map<String, Integer> pageParams = Util.innitTablePages(params);
        ToolListExample example = new ToolListExample();
        ToolListExample.Criteria criteria = example.createCriteria();
        if (!Util.isNullorEmpty(params.get("status"))) {
            criteria.andStatusEqualTo(Short.parseShort(params.get("status")));
        }
        if(!Util.isNullorEmpty(params.get("query")) && !params.get("queryType").isEmpty()) {
            if ("id".equals(params.get("queryType"))) {
                criteria.andIdEqualTo(params.get("query"));
            } else if ("name".equals(params.get("queryType"))) {
                criteria.andNameLike("%" + params.get("query") + "%");
            }
        }
        if(!Util.isNullorEmpty(params.get("category"))){
            criteria.andCategoryEqualTo(params.get("category"));
        }

        if (!Util.isNullorEmpty(params.get("order"))) {
            example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
        }
        PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
        return toolListMapper.selectByExample(example);
    }

    @Override
    public int addToolList(ToolList toolList) {
        String id = publicService.getJourno();
        toolList.setId(id);
        toolList.setCreateTime(Util.currentDateTime());
        toolList.setCreateUser(Util.userIdByShiro());
        return toolListMapper.insertSelective(toolList);
    }

    @Override
    public int updateToolList(ToolList toolList) {
        return toolListMapper.updateByPrimaryKeySelective(toolList);
    }

    @Override
    public int deleteToolList(String id) {
        return toolListMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<ToolList> getUserToolList(Map<String, String> params) {
        ToolListExample example = new ToolListExample();
        ToolListExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo((short)1);
        if(!Util.isNullorEmpty(params.get("query"))){
            criteria.andNameLike("%"+params.get("query")+"%");
        }
        if (!Util.isNullorEmpty(params.get("order"))) {
            example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
        }
        List<ToolList> list = toolListMapper.selectByExample(example);
        UserRole userRole = userRoleService.getUserRolesByPrimaryKey(Util.userIdByShiro());
        List<String> userRoles = Arrays.asList(userRole.getRoles().split(","));
        // 判断两个string数组是否存在交集
        list.removeIf(toolList -> !Util.isNullorEmpty(toolList.getRoles()) &&
                Collections.disjoint(Arrays.asList(toolList.getRoles().split(",")), userRoles));
        return list;
    }

    @Override
    public List<String> searchToolListName(String name) {
        return toolListMapper.getToolListName(name);
    }
}
