package com.cmbccd.ulms.hr.service.Impl;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.hr.dao.HrLabelMapper;
import com.cmbccd.ulms.hr.domain.HrLabel;
import com.cmbccd.ulms.hr.domain.HrLabelExample;
import com.cmbccd.ulms.hr.service.HrLabelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class HrLabelServiceImpl implements HrLabelService {

    @Resource
    private HrLabelMapper hrLabelMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<HrLabel> gennerLabel(String perfMonth) {
        List<HrLabel> outList = hrLabelMapper.selectResignedLabels(perfMonth);
        List<HrLabel> birthList = hrLabelMapper.selectBirthLabels(perfMonth);

        List<HrLabel> allList = Stream.of(outList, birthList).flatMap(Collection::stream).collect(Collectors.toList());
        this.delete(perfMonth);
        for (HrLabel hrLabel : allList) {
            hrLabel.setId(Util.getUUID());
            hrLabelMapper.insert(hrLabel);
        }
        return allList;
    }
    @Override
    public List<HrLabel> list(String perfMonth, String ploNum) {
        HrLabelExample example = new HrLabelExample();
        HrLabelExample.Criteria criteria = example.createCriteria();
        criteria.andPerfMonthEqualTo(perfMonth);
        criteria.andPloNumEqualTo(ploNum);
        return hrLabelMapper.selectByExample(example);
    }

    @Override
    public List<HrLabel> listLabelHumanFlag(String perfMonth) {
        return hrLabelMapper.selectLabelHumanFlag(perfMonth);
    }

    @Override
    public List<HrLabel> list(String perfMonth) {
        HrLabelExample example = new HrLabelExample();
        HrLabelExample.Criteria criteria = example.createCriteria();
        criteria.andPerfMonthEqualTo(perfMonth);
        return hrLabelMapper.selectByExample(example);
    }

    public int delete(String perfMonth) {
        HrLabelExample example = new HrLabelExample();
        HrLabelExample.Criteria criteria = example.createCriteria();
        criteria.andPerfMonthEqualTo(perfMonth);
        return hrLabelMapper.deleteByExample(example);
    }
}
