package com.cmbccd.ulms.helper.dao;

import com.cmbccd.ulms.helper.domain.HelperArtical;
import com.cmbccd.ulms.helper.domain.HelperArticalExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HelperArticalMapper {
    long countByExample(HelperArticalExample example);

    int deleteByExample(HelperArticalExample example);

    int deleteByPrimaryKey(Object journo);

    int insert(HelperArtical record);

    int insertSelective(HelperArtical record);

    List<HelperArtical> selectByExampleWithBLOBs(HelperArticalExample example);

    List<HelperArtical> selectByExample(HelperArticalExample example);

    HelperArtical selectByPrimaryKey(String journo);

    int updateByExampleSelective(@Param("record") HelperArtical record, @Param("example") HelperArticalExample example);

    int updateByExampleWithBLOBs(@Param("record") HelperArtical record, @Param("example") HelperArticalExample example);

    int updateByExample(@Param("record") HelperArtical record, @Param("example") HelperArticalExample example);

    int updateByPrimaryKeySelective(HelperArtical record);

    int updateByPrimaryKeyWithBLOBs(HelperArtical record);

    int updateByPrimaryKey(HelperArtical record);

    int updateArticalReadCount(@Param("journo") String journo);
}