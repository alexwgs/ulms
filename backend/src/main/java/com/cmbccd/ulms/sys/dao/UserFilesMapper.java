package com.cmbccd.ulms.sys.dao;

import com.cmbccd.ulms.sys.domain.UserFiles;
import com.cmbccd.ulms.sys.domain.UserFilesExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserFilesMapper {
    long countByExample(UserFilesExample example);

    int deleteByExample(UserFilesExample example);

    int insert(UserFiles record);

    int insertSelective(UserFiles record);

    List<UserFiles> selectByExample(UserFilesExample example);

    int updateByExampleSelective(@Param("record") UserFiles record, @Param("example") UserFilesExample example);

    int updateByExample(@Param("record") UserFiles record, @Param("example") UserFilesExample example);
    
    int selectNewId();
    
    List<String> getFileSuffix();
}