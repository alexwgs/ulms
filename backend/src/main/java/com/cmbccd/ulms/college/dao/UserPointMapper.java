package com.cmbccd.ulms.college.dao;


import com.cmbccd.ulms.college.domain.UserPoint;

import java.util.List;

public interface UserPointMapper {
    List<UserPoint> selectUserStudyPointRank(int allFlag);

    UserPoint selectMyStudyPoint(String ploNum);
}
