package com.cmbccd.ulms.youngTalk.dao;

import com.cmbccd.ulms.youngTalk.domain.Article;
import com.cmbccd.ulms.youngTalk.domain.ArticleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ArticleMapper {
    long countByExample(ArticleExample example);

    int deleteByExample(ArticleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Article record);

    int insertSelective(Article record);

    List<Article> selectByExampleWithBLOBs(ArticleExample example);

    List<Article> selectByExample(ArticleExample example);

    Article selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Article record, @Param("example") ArticleExample example);

    int updateByExampleWithBLOBs(@Param("record") Article record, @Param("example") ArticleExample example);

    int updateByExample(@Param("record") Article record, @Param("example") ArticleExample example);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);

    //new Id
    int creatNewArticleId();

    //增加查看量
    int viewNumIncrease(Integer id);
    int likeNumIncrease(Integer id);
    int collectNumIncrease(Integer id);
    int collectNumDecrease(Integer id);
    int replyNumIncrease(Integer id);
    int replyNumDecrease(Integer id);

    //使用ID查找文章不返回正文
    Article selectByPrimaryKeyWithNoContent(Integer id);
    // 获取每周热门数据
    List<Article> selectWeeklyHotList(String dateTime);
}