package com.cmbccd.ulms.hr.dao;

import com.cmbccd.ulms.hr.domain.PerfList;
import com.cmbccd.ulms.hr.domain.PerfListExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PerfListMapper {
    long countByExample(PerfListExample example);

    int deleteByExample(PerfListExample example);

    int deleteByPrimaryKey(String id);

    int insert(PerfList record);

    int insertSelective(PerfList record);

    List<PerfList> selectByExample(PerfListExample example);

    PerfList selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") PerfList record, @Param("example") PerfListExample example);

    int updateByExample(@Param("record") PerfList record, @Param("example") PerfListExample example);

    int updateByPrimaryKeySelective(PerfList record);

    int updateByPrimaryKey(PerfList record);

    List<PerfList> initList(@Param("perfMonth") String perfMonth);

    List<PerfList> selectFlowPerfUnit(@Param("perfMonth") String perfMonth);

    /**
     *  更新人事名单的人力信息
     * @param perfMonth 绩效月
     * @param ploNum 员工编号
     * @param humFlag 个人人力
     * @param groupFlag 小组人力
     * @param deptFlag 科室人力
     * @return
     */
    int updateLabelHumanFlag(@Param("perfMonth") String perfMonth,@Param("ploNum") String ploNum,@Param("humFlag") Short humFlag,@Param("groupFlag") Short groupFlag,@Param("deptFlag") Short deptFlag);
}