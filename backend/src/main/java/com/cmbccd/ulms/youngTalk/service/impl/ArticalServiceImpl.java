package com.cmbccd.ulms.youngTalk.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cmbccd.ulms.youngTalk.dao.ArticalMapper;
import com.cmbccd.ulms.youngTalk.domain.Artical;
import com.cmbccd.ulms.youngTalk.domain.ArticalExample;
import com.cmbccd.ulms.youngTalk.service.ArticalService;

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
