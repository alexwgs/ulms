package com.cmbccd.ulms.hr.service.impl;

import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.hr.dao.BirthLeaveMapper;
import com.cmbccd.ulms.hr.domain.BirthLeave;
import com.cmbccd.ulms.hr.domain.BirthLeaveExample;
import com.cmbccd.ulms.hr.service.BirthLeaveService;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class BirthLeaveServiceImpl implements BirthLeaveService {
    @Resource
    private BirthLeaveMapper birthLeaveMapper;

    @Override
    public List<BirthLeave> list(Map<String, String> params) {
        String ploNum = Util.isNullorEmpty(params.get("ploNum"))?null:params.get("ploNum");
        Map<String, Integer> pageParams = Util.innitTablePages(params);
        PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
        List<BirthLeave> list = birthLeaveMapper.listBirthLeave(ploNum);
        for (BirthLeave item: list
             ) {
            item.setUser(DataCache.getEmployees().get(item.getPloNum()));
        }
        return list;
    }

    @Override
    public List<BirthLeave> getByPloNum(String ploNum) {
        BirthLeaveExample example = new BirthLeaveExample();
        BirthLeaveExample.Criteria criteria = example.createCriteria();
        criteria.andPloNumEqualTo(ploNum);
        example.setOrderByClause(" GROUP_ID, LABEL_ID");
        return birthLeaveMapper.selectByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int add(List<BirthLeave> records) {
        int count = 0;
        for (BirthLeave item : records ) {
            item.setId(Util.getUUID());
            item.setModifyId(Util.userIdByShiro());
            item.setModifyTime(Util.currentDateTime());
            count+=birthLeaveMapper.insert(item);
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int update(List<BirthLeave> records) {
        int count = 0;
        for (BirthLeave item : records ) {
            item.setModifyId(Util.userIdByShiro());
            item.setModifyTime(Util.currentDateTime());
            count+=birthLeaveMapper.updateByPrimaryKey(item);
        }
        return count;
    }
}
