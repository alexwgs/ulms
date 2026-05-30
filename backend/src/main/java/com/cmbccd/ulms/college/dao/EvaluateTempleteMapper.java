package com.cmbccd.ulms.college.dao;

import com.cmbccd.ulms.college.domain.EvaluateTemplete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EvaluateTempleteMapper {

    int insert(EvaluateTemplete record);

    int update(EvaluateTemplete record);

    List<EvaluateTemplete> select(String tempId);

    List<EvaluateTemplete> group(Integer quesType);

    List<EvaluateTemplete> selectByJourno(String tempId, String journo);

    int insertJourno(@Param("journo") String journo, @Param("tempNo") Integer tempNo, @Param("quesType") Integer quesType, @Param("cont") String cont);

    int updateCourseEvaluate(@Param("courseId") String courseId);
}
