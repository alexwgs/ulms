package com.cmbccd.ulms.college.service;

import com.cmbccd.ulms.college.domain.CourseFile;

import java.util.List;

public interface CourseFileService {

    List<CourseFile> list(String courseId, Integer Status);

    /**
     * N+1 优化：按课程 ID 列表批量查询附件（用于列表页一次加载）
     */
    List<CourseFile> listByCourseIds(List<String> courseIds);

    int update(CourseFile courseFile);

    int delete(String courseId,String fileId);

    int insert(CourseFile courseFile);

}
