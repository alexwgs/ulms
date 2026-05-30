package com.cmbccd.ulms.college.service;

import java.util.List;

import com.cmbccd.ulms.college.domain.CourseType;

public interface CourseTypeService {

	List<CourseType> courseTypeTree(short status);

	List<Integer> getChiledIds(Integer id);

	int update(CourseType courseType);

	int delete(int id);

	int insert(CourseType courseType);

}
