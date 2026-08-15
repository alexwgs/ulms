package com.cmbccd.ulms.youngTalk.service;

import com.cmbccd.ulms.youngTalk.domain.Like;
import com.cmbccd.ulms.youngTalk.domain.LikeExample;

import java.util.List;

public interface LikeService {
	
    public int isUserLikeByArticleId(int articleId, String userId);
    
    public int countByExample(LikeExample example);
    
	public int newId();
	
	public int insert(Like record);
	
	public List<Like> getCommentLikeById(int commentId,String userId);
}
