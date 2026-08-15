package com.cmbccd.ulms.helper.dao;

import com.cmbccd.ulms.helper.domain.HelperArticle;
import com.cmbccd.ulms.helper.domain.HelperArticleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HelperArticleMapper {
    long countByExample(HelperArticleExample example);

    int deleteByExample(HelperArticleExample example);

    int deleteByPrimaryKey(Object journo);

    int insert(HelperArticle record);

    int insertSelective(HelperArticle record);

    List<HelperArticle> selectByExampleWithBLOBs(HelperArticleExample example);

    List<HelperArticle> selectByExample(HelperArticleExample example);

    HelperArticle selectByPrimaryKey(String journo);

    int updateByExampleSelective(@Param("record") HelperArticle record, @Param("example") HelperArticleExample example);

    int updateByExampleWithBLOBs(@Param("record") HelperArticle record, @Param("example") HelperArticleExample example);

    int updateByExample(@Param("record") HelperArticle record, @Param("example") HelperArticleExample example);

    int updateByPrimaryKeySelective(HelperArticle record);

    int updateByPrimaryKeyWithBLOBs(HelperArticle record);

    int updateByPrimaryKey(HelperArticle record);

    int updateArticleReadCount(@Param("journo") String journo);
}