package com.cmbccd.ulms.college.service.impl;

import com.cmbccd.ulms.college.dao.UserPointMapper;
import com.cmbccd.ulms.college.domain.UserPoint;
import com.cmbccd.ulms.college.service.UserPointService;
import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Employee;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.List;

@Service
public class UserPointServiceImpl implements UserPointService {

    @Resource
    private UserPointMapper userPointMapper;


    @Override
    public List<UserPoint> listUserPointRank(int allFlag) {
        List<UserPoint> list = userPointMapper.selectUserStudyPointRank(allFlag);
        for (UserPoint item: list) {
            Employee user = DataCache.EMPLOYEE.get(item.getPloNum());
            item.setUser(user);
        }
        return list;
    }

    @Override
    public UserPoint getMyPoint() {
        String userId = Util.userIdByShiro();
        Employee user = DataCache.EMPLOYEE.get(userId);
        UserPoint userPoint =  userPointMapper.selectMyStudyPoint(userId);
        userPoint.setUser(user);
        return userPoint;
    }
}
