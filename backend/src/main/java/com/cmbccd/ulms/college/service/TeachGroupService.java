package com.cmbccd.ulms.college.service;

import com.cmbccd.ulms.college.domain.TeachGroup;
import com.cmbccd.ulms.college.domain.TeachGroupExample;
import com.cmbccd.ulms.common.util.DataPage;

import java.util.List;
import java.util.Map;
public interface TeachGroupService {

    List<TeachGroup> listGroupName();

    List<TeachGroup> listUserByGroup(TeachGroupExample example);

    DataPage<TeachGroup> listUserByGroupQuery(Map<String, String> params);

    int delete(TeachGroup teachGroup);

    int add(TeachGroup teachGroup);

    List<String> listGroupNameByPloNum(String ploNum);

    void deleteAll();

    int batchInsert(List<TeachGroup> list);

}