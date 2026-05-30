package com.cmbccd.ulms.youngTalk.service;

import com.cmbccd.ulms.youngTalk.domain.Artical;
import com.cmbccd.ulms.youngTalk.domain.ArticalExample;

import java.util.List;
/**
 * 
* <p>Title: ArticalService.java</p>  
* <p>Description: </p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
public interface ArticalService {
	public List<Artical> selectByExampleNoContent(ArticalExample example);
	
	public List<Artical> selectByExampleWithContent(ArticalExample example);
	
	public Artical getArticalById(int id);
	
	public Artical getArticalByIdWithNoContent(int id);
	
	public Integer getNewArticalId ();
	
	public Integer insertArtical(Artical record);
	
	public int updateArtical(Artical record);
	
	public int updateArticalSelectiveByPrimaryKey(Artical record);
	
	//增加浏览量
	public Integer increaseViewNum(Integer id);
	public Integer increaseLikeNum(Integer id);
	public Integer increaseCollectNum(Integer id);
	public Integer decreaseCollectNum(Integer id);
	public Integer increaseReplyNum(Integer id);
	public Integer decreaseReplyNum(Integer id);
	
//	设置文章状态 管理员
	public int updateByPrimaryKeySelective(Artical record);
	
    // 获取每周热门数据
    List<Artical> selectWeeklyHotList(String dateTime);
}
