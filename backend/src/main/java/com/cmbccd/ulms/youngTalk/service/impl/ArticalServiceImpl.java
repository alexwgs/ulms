package com.cmbccd.ulms.youngTalk.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.youngTalk.dao.ArticalMapper;
import com.cmbccd.ulms.youngTalk.domain.Artical;
import com.cmbccd.ulms.youngTalk.domain.ArticalExample;
import com.cmbccd.ulms.youngTalk.domain.ArticalExample.Criteria;
import com.cmbccd.ulms.youngTalk.service.ArticalService;
import com.github.pagehelper.PageHelper;

import jakarta.annotation.Resource;

/**
 * 
* <p>Title: ArticalServiceImpl.java</p>  
* <p>Description: </p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
@Service
public class ArticalServiceImpl implements ArticalService {

	@Resource
	private ArticalMapper articalMapper;
	
	@Override
	public List<Artical> selectByExampleNoContent(ArticalExample example) {
		return articalMapper.selectByExample(example);
	}

	@Override
	public DataPage<Artical> listCategoryByQuery(Map<String, String> params) {
		ArticalExample example = new ArticalExample();
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
			criteria.andArticalTypeEqualTo(Integer.parseInt(typeCategory.substring(0, 1)));
			criteria.andCategoryEqualTo(Integer.parseInt(typeCategory.substring(1)));
		}

		if (!Util.isNullorEmpty(queryType)) {
			if ("title".equals(queryType)) {
				criteria.andTitleLike("%" + params.get("query") + "%");
			}
		}
		// 不包含系统通知类型9
		criteria.andArticalTypeLessThan(8);
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));

		List<Artical> articalList = articalMapper.selectByExample(example);
		for (Artical artical : articalList) {
			if (artical.getAnonFlag() == 1) {
				artical.setPubUser("匿名");
			} else {
				artical.setUser(DataCache.getEmployees().get(artical.getPubUser()));
			}
		}
		return new DataPage<Artical>(articalList);
	}

	@Override
	public List<Artical> selectByExampleWithContent(ArticalExample example) {
		return articalMapper.selectByExampleWithBLOBs(example);
	}

	@Override
	public Integer getNewArticalId() {
		return articalMapper.creatNewArticalId();
	}

	@Override
	public Integer increaseViewNum(Integer id) {
		return articalMapper.viewNumIncrease(id);
	}

	@Override
	public Artical getArticalById(int id) {
		return articalMapper.selectByPrimaryKey(id);
	}

	@Override
	public Integer increaseLikeNum(Integer id) {
		return articalMapper.likeNumIncrease(id);
	}

	@Override
	public Integer increaseCollectNum(Integer id) {
		return articalMapper.collectNumIncrease(id);
	}

	@Override
	public Integer decreaseCollectNum(Integer id) {
		return articalMapper.collectNumDecrease(id);
	}

	@Override
	public Integer increaseReplyNum(Integer id) {
		return articalMapper.replyNumIncrease(id);
	}

	@Override
	public Integer decreaseReplyNum(Integer id) {
		return articalMapper.replyNumDecrease(id);
	}

	@Override
	public Integer insertArtical(Artical record) {
		return articalMapper.insertSelective(record);
	}

	@Override
	public Artical getArticalByIdWithNoContent(int id) {
		return articalMapper.selectByPrimaryKeyWithNoContent(id);
	}

	@Override
	public int updateArtical(Artical record) {
		return articalMapper.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public int updateArticalSelectiveByPrimaryKey(Artical record) {
		return 	articalMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Artical record) {
		return articalMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<Artical> selectWeeklyHotList(String dateTime) {
		return articalMapper.selectWeeklyHotList(dateTime);
	}

	
}
