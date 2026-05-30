package com.cmbccd.ulms.college.service;

import com.cmbccd.ulms.college.domain.TeachGroup;
import com.cmbccd.ulms.college.domain.TeachGroupExample;
import java.util.List;
public interface TeachGroupService {

    List<TeachGroup> listGroupName();

    List<TeachGroup> listUserByGroup(TeachGroupExample example);

    int delete(TeachGroup teachGroup);

    int add(TeachGroup teachGroup);

    List<String> listGroupNameByPloNum(String ploNum);

    void deleteAll();

    int batchInsert(List<TeachGroup> list);

}