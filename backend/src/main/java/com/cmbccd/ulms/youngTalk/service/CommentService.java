package com.cmbccd.ulms.youngTalk.service;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.youngTalk.domain.Comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CommentService {
	public List<Comment> getCommentByArticalId(int articalId);

	public DataPage<Comment> listCommentByQuery(int articalId, Map<String, String> params, String userId);
	
	public int insertNewComment (Comment record);
	
	public int newId();
	
	public int increaseLikeNum(Integer id);
	
    //获得评论量排序
    List<HashMap<String , Object>> getCommentCountRank();
    
    public int updateSelectiveCommentById(Comment record);
    
    public Comment getCommentById(int id);
}
