package com.cmbccd.ulms.oht.dao;

import com.cmbccd.ulms.oht.domain.OhtRole;
import com.cmbccd.ulms.oht.domain.OhtRoleExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface OhtRoleMapper {
    long countByExample(OhtRoleExample example);

    int deleteByExample(OhtRoleExample example);

    int insert(OhtRole record);

    int insertSelective(OhtRole record);

    List<OhtRole> selectByExample(OhtRoleExample example);

    int updateByExampleSelective(@Param("record") OhtRole record, @Param("example") OhtRoleExample example);

    int updateByExample(@Param("record") OhtRole record, @Param("example") OhtRoleExample example);
    
    int deleteByPrimaryKey(Integer roleCode);
    
    int updateByPrimaryKey(@Param("record") OhtRole record);
    
    OhtRole selectByPrimaryKey(Integer roleCode);
    
    int selectNewId();

}