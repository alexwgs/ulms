package com.cmbccd.ulms.youngTalk.service;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.youngTalk.domain.Article;
import com.cmbccd.ulms.youngTalk.domain.ArticleExample;

import java.util.List;
import java.util.Map;
/**
 * 
* <p>Title: ArticleService.java</p>  
* <p>Description: </p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
public interface ArticleService {
	public List<Article> selectByExampleNoContent(ArticleExample example);

	public DataPage<Article> listCategoryByQuery(Map<String, String> params);
	
	public List<Article> selectByExampleWithContent(ArticleExample example);
	
	public Article getArticleById(int id);
	
	public Article getArticleByIdWithNoContent(int id);
	
	public Integer getNewArticleId ();
	
	public Integer insertArticle(Article record);
	
	public int updateArticle(Article record);
	
	public int updateArticleSelectiveByPrimaryKey(Article record);
	
	//增加浏览量
	public Integer increaseViewNum(Integer id);
	public Integer increaseLikeNum(Integer id);
	public Integer increaseCollectNum(Integer id);
	public Integer decreaseCollectNum(Integer id);
	public Integer increaseReplyNum(Integer id);
	public Integer decreaseReplyNum(Integer id);
	
//	设置文章状态 管理员
	public int updateByPrimaryKeySelective(Article record);
	
    // 获取每周热门数据
    List<Article> selectWeeklyHotList(String dateTime);
}
