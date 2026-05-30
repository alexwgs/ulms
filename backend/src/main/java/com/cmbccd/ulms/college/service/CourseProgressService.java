package com.cmbccd.ulms.college.service;

import com.cmbccd.ulms.college.domain.CourseProgress;
import com.cmbccd.ulms.college.domain.CourseProgressExample;

import java.util.List;

public interface CourseProgressService {

    List<CourseProgress> list(CourseProgressExample example);

    CourseProgress get(String journo);

    int update(CourseProgress courseProgress);

    CourseProgress updateTime(CourseProgress courseProgress);

    CourseProgress insert(CourseProgress courseProgress);

    int delete(String journo);

    List<CourseProgress> listMyLearnLog(CourseProgressExample example);

}
