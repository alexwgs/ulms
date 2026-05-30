package com.cmbccd.ulms.college.service;

import com.cmbccd.ulms.college.domain.CourseFile;

import java.util.List;

public interface CourseFileService {

    List<CourseFile> list(String courseId, Integer Status);

    int update(CourseFile courseFile);

    int delete(String courseId,String fileId);

    int insert(CourseFile courseFile);

}
