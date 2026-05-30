package com.cmbccd.ulms.college.service;

import com.cmbccd.ulms.college.domain.UserPoint;

import java.util.List;

public interface UserPointService {

    List<UserPoint> listUserPointRank(int allFlag);

    UserPoint getMyPoint();

}
