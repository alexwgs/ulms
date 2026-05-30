package com.cmbccd.ulms.sys.dao;

import com.cmbccd.ulms.sys.domain.Menu;
import com.cmbccd.ulms.sys.domain.MenuExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {
    long countByExample(MenuExample example);

    int deleteByExample(MenuExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    List<Menu> selectByExample(MenuExample example);

    Menu selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Menu record, @Param("example") MenuExample example);

    int updateByExample(@Param("record") Menu record, @Param("example") MenuExample example);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    int creatNewMenuId();

    List<String> selectFontPermissionByIds(String [] authMenuList);

    List<Menu> selectByRoleId(String[] roleIds);
}