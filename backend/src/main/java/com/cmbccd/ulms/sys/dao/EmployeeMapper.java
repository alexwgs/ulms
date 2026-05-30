package com.cmbccd.ulms.sys.dao;

import com.cmbccd.ulms.sys.domain.Employee;
import com.cmbccd.ulms.sys.domain.EmployeeExample;
import com.cmbccd.ulms.sys.domain.EmployeeForRPA;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
@Mapper
public interface EmployeeMapper {
    long countByExample(EmployeeExample example);

    int deleteByExample(EmployeeExample example);

    int deleteByPrimaryKey(String ploNum);

    int insert(Employee record);

    int insertSelective(Employee record);

    List<Employee> selectByExample(EmployeeExample example);

    Employee selectByPrimaryKey(String ploNum);

    int updateByExampleSelective(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByExample(@Param("record") Employee record, @Param("example") EmployeeExample example);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);
    
    //初始化缓存到内存中
    List<Employee> selectEmployeeInit();
   
    //注意此处使用到表私CUS_HR_INFO
    List<HashMap<String , Object>> getEmployeeBirthday(String month);

    List<EmployeeForRPA> selectRpaByExample(EmployeeExample example);
}