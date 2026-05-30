package com.cmbccd.ulms.youngTalk.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cmbccd.ulms.youngTalk.domain.Comment;
import com.cmbccd.ulms.youngTalk.domain.CommentExample;

public interface CommentMapper {
    long countByExample(CommentExample example);

    int deleteByExample(CommentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    List<Comment> selectByExample(CommentExample example);

    Comment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByExample(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);
    //新ID
    int creatNewId();
    int increaseLikeNum(int id);
    
    //获得评论量排序
    List<HashMap<String , Object>> getCommentCountRank();
    
}