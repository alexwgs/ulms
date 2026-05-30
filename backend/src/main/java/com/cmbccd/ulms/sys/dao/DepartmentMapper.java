package com.cmbccd.ulms.sys.dao;

import com.cmbccd.ulms.sys.domain.Department;
import com.cmbccd.ulms.sys.domain.DepartmentExample;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface DepartmentMapper {
    long countByExample(DepartmentExample example);

    int deleteByExample(DepartmentExample example);

    int insert(Department record);

    int insertSelective(Department record);

    List<Department> selectByExample(DepartmentExample example);

    int updateByExampleSelective(@Param("record") Department record, @Param("example") DepartmentExample example);

    int updateByExample(@Param("record") Department record, @Param("example") DepartmentExample example);

	Department selectByDeptNum(String deptNum);
	
    @MapKey("deptNum")
    Map<String,Department> getDepartmentMap();
}