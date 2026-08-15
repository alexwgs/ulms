package com.cmbccd.ulms.youngTalk.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.youngTalk.dao.ArticleMapper;
import com.cmbccd.ulms.youngTalk.domain.Article;
import com.cmbccd.ulms.youngTalk.domain.ArticleExample;
import com.cmbccd.ulms.youngTalk.domain.ArticleExample.Criteria;
import com.cmbccd.ulms.youngTalk.service.ArticleService;
import com.github.pagehelper.PageHelper;

import jakarta.annotation.Resource;

/**
 * 
* <p>Title: ArticleServiceImpl.java</p>  
* <p>Description: </p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
@Service
public class ArticleServiceImpl implements ArticleService {

	@Resource
	private ArticleMapper articleMapper;
	
	@Override
	public List<Article> selectByExampleNoContent(ArticleExample example) {
		return articleMapper.selectByExample(example);
	}

	@Override
	public DataPage<Article> listCategoryByQuery(Map<String, String> params) {
		ArticleExample example = new ArticleExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(1);
		String sortType = params.get("sortType");
		String queryType = params.get("queryType");

		if ("time".equals(sortType)) {
			example.setOrderByClause(" TOP_FLAG DESC,ID DESC");
		} else if ("comment".equals(sortType)) {
			example.setOrderByClause(" TOP_FLAG DESC,ELITE_FLAG DESC, REPLY_NUM DESC, ID DESC");
		} else { // hot
			example.setOrderByClause(
					" TOP_FLAG DESC,ELITE_FLAG DESC,(VIEW_NUM*0.5+REPLY_NUM*2+COLLECT_NUM+LIKE_NUM) DESC");
		}

		if (!Util.isNullorEmpty(params.get("compType"))) {
			criteria.andCompTypeEqualTo(Integer.parseInt(params.get("compType")));
		}
		if (!Util.isNullorEmpty(params.get("eliteFlag"))) {
			criteria.andEliteFlagEqualTo(Integer.parseInt(params.get("eliteFlag")));
		}
		if (!Util.isNullorEmpty(params.get("topFlag"))) {
			criteria.andTopFlagEqualTo(Integer.parseInt(params.get("topFlag")));
		}
		if (!Util.isNullorEmpty(params.get("onStage"))) {
			criteria.andOnStageEqualTo(Integer.parseInt(params.get("onStage")));
		}
		if (!Util.isNullorEmpty(params.get("status"))) {
			criteria.andStatusEqualTo(Integer.parseInt(params.get("status")));
		}
		if (!Util.isNullorEmpty(params.get("dateYear"))) {
			criteria.andPubDateBetween(params.get("dateYear") + "-01-01 00:00:00",
					params.get("dateYear") + "-12-31 23:59:59");
		}
		if (!Util.isNullorEmpty(params.get("category"))) {
			String typeCategory = params.get("category");
			criteria.andArticleTypeEqualTo(Integer.parseInt(typeCategory.substring(0, 1)));
			criteria.andCategoryEqualTo(Integer.parseInt(typeCategory.substring(1)));
		}

		if (!Util.isNullorEmpty(queryType)) {
			if ("title".equals(queryType)) {
				criteria.andTitleLike("%" + params.get("query") + "%");
			}
		}
		// 不包含系统通知类型9
		criteria.andArticleTypeLessThan(8);
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));

		List<Article> articleList = articleMapper.selectByExample(example);
		for (Article article : articleList) {
			if (article.getAnonFlag() == 1) {
				article.setPubUser("匿名");
			} else {
				article.setUser(DataCache.getEmployees().get(article.getPubUser()));
			}
		}
		return new DataPage<Article>(articleList);
	}

	@Override
	public List<Article> selectByExampleWithContent(ArticleExample example) {
		return articleMapper.selectByExampleWithBLOBs(example);
	}

	@Override
	public Integer getNewArticleId() {
		return articleMapper.creatNewArticleId();
	}

	@Override
	public Integer increaseViewNum(Integer id) {
		return articleMapper.viewNumIncrease(id);
	}

	@Override
	public Article getArticleById(int id) {
		return articleMapper.selectByPrimaryKey(id);
	}

	@Override
	public Integer increaseLikeNum(Integer id) {
		return articleMapper.likeNumIncrease(id);
	}

	@Override
	public Integer increaseCollectNum(Integer id) {
		return articleMapper.collectNumIncrease(id);
	}

	@Override
	public Integer decreaseCollectNum(Integer id) {
		return articleMapper.collectNumDecrease(id);
	}

	@Override
	public Integer increaseReplyNum(Integer id) {
		return articleMapper.replyNumIncrease(id);
	}

	@Override
	public Integer decreaseReplyNum(Integer id) {
		return articleMapper.replyNumDecrease(id);
	}

	@Override
	public Integer insertArticle(Article record) {
		return articleMapper.insertSelective(record);
	}

	@Override
	public Article getArticleByIdWithNoContent(int id) {
		return articleMapper.selectByPrimaryKeyWithNoContent(id);
	}

	@Override
	public int updateArticle(Article record) {
		return articleMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public int updateArticleSelectiveByPrimaryKey(Article record) {
		return 	articleMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Article record) {
		return articleMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<Article> selectWeeklyHotList(String dateTime) {
		return articleMapper.selectWeeklyHotList(dateTime);
	}

	
}
