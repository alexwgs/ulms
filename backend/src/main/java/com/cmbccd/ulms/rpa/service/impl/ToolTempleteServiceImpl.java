package com.cmbccd.ulms.rpa.service.impl;

import com.cmbccd.ulms.rpa.dao.ToolTempleteMapper;
import com.cmbccd.ulms.rpa.domain.ToolTemplete;
import com.cmbccd.ulms.rpa.domain.ToolTempleteExample;
import com.cmbccd.ulms.rpa.service.ToolTempleteService;
import com.cmbccd.ulms.sys.service.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolTempleteServiceImpl implements ToolTempleteService {
    @Autowired
    private ToolTempleteMapper toolTempleteMapper;

    @Autowired
    private PublicService publicService;

    @Override
    public List<ToolTemplete> getToolTemplete(String templeteId) {
        ToolTempleteExample example = new ToolTempleteExample();
        ToolTempleteExample.Criteria criteria = example.createCriteria();
        criteria.andTempleteIdEqualTo(templeteId);
        example.setOrderByClause("order_id");
        return toolTempleteMapper.selectByExample(example);
    }

    @Override
    public int updateToolTemplete(ToolTemplete toolTemplete) {
        return toolTempleteMapper.updateByPrimaryKeySelective(toolTemplete);
    }

    @Override
    public int addToolTemplete(ToolTemplete toolTemplete) {
        String id = publicService.getJourno();
        toolTemplete.setId(id);
        return toolTempleteMapper.insertSelective(toolTemplete);
    }

    @Override
    public int deleteToolTemplete(String id) {
        return toolTempleteMapper.deleteByPrimaryKey(id);
    }
}
