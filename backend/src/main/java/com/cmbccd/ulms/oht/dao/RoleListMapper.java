package com.cmbccd.ulms.oht.dao;

import com.cmbccd.ulms.oht.domain.RoleList;
import com.cmbccd.ulms.oht.domain.RoleListExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface RoleListMapper {
    long countByExample(RoleListExample example);

    int deleteByExample(RoleListExample example);

    int insert(RoleList record);

    int insertSelective(RoleList record);

    List<RoleList> selectByExample(RoleListExample example);

    int updateByExampleSelective(@Param("record") RoleList record, @Param("example") RoleListExample example);

    int updateByExample(@Param("record") RoleList record, @Param("example") RoleListExample example);
}