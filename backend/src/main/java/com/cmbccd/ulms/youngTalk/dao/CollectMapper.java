package com.cmbccd.ulms.youngTalk.dao;

import com.cmbccd.ulms.youngTalk.domain.Collect;
import com.cmbccd.ulms.youngTalk.domain.CollectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CollectMapper {
    Integer countByExample(CollectExample example);

    int deleteByExample(CollectExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Collect record);

    int insertSelective(Collect record);

    List<Collect> selectByExample(CollectExample example);

    Collect selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Collect record, @Param("example") CollectExample example);

    int updateByExample(@Param("record") Collect record, @Param("example") CollectExample example);

    int updateByPrimaryKeySelective(Collect record);

    int updateByPrimaryKey(Collect record);
    //修改收藏状态 同一文章同一用户仅可收藏一次
    int updateStatusByUserAndArticle(Collect record);
    //新ID
    int creatNewId();
}