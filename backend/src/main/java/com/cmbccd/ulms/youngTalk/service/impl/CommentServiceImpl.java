package com.cmbccd.ulms.youngTalk.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.youngTalk.dao.CommentMapper;
import com.cmbccd.ulms.youngTalk.domain.Comment;
import com.cmbccd.ulms.youngTalk.domain.CommentExample;
import com.cmbccd.ulms.youngTalk.domain.CommentExample.Criteria;
import com.cmbccd.ulms.youngTalk.domain.Like;
import com.cmbccd.ulms.youngTalk.domain.Reply;
import com.cmbccd.ulms.youngTalk.service.CommentService;
import com.cmbccd.ulms.youngTalk.service.LikeService;
import com.cmbccd.ulms.youngTalk.service.ReplyService;
import com.github.pagehelper.PageHelper;

import jakarta.annotation.Resource;

@Service
public class CommentServiceImpl implements CommentService {

	@Resource
	private CommentMapper commentMapper;

	@Resource
	private ReplyService replyService;

	@Resource
	private LikeService likeService;

	@Override
	public List<Comment> getCommentByArticleId(int articleId) {
		CommentExample example = new CommentExample();
		Criteria criteria = example.createCriteria();
		criteria.andArticleIdEqualTo(articleId);
		criteria.andStatusEqualTo(1);
		example.setOrderByClause(" DATE_TIME DESC");
		return commentMapper.selectByExample(example);
	}

	@Override
	public DataPage<Comment> listCommentByQuery(int articleId, Map<String, String> params, String userId) {
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		List<Comment> comments = getCommentByArticleId(articleId);
		for (Comment comment : comments) {
			if (comment.getAnonFlag() == 1) {
				comment.setUserid("匿名");
			} else {
				comment.setUser(DataCache.getEmployees().get(comment.getUserid()));
			}
			comment.setUser(DataCache.getEmployees().get(comment.getUserid()));
			List<Reply> replys = replyService.getReplyByCommentId(comment.getId());
			// 免登录阅读：未登录用户无"我是否点赞过"标记，跳过点赞查询（userId 为 null 时
			// MyBatis Example 的 andUserIdEqualTo(null) 会抛异常）
			List<Like> likes = null;
			if (!Util.isNullorEmpty(userId)) {
				likes = likeService.getCommentLikeById(comment.getId(), userId);
			}
			if (Util.isNullorEmpty(replys)) {
				continue;
			}
			for (Reply reply : replys) {
				if (reply.getAnonFlag() == 1) {
					reply.setUserid("匿名");
				} else {
					reply.setUser(DataCache.getEmployees().get(reply.getUserid()));
				}
			}
			if (!Util.isNullorEmpty(replys)) {
				comment.setReplys(replys);
			}
			if (!Util.isNullorEmpty(likes)) {
				comment.setLikes(likes);
			}
		}
		return new DataPage<Comment>(comments);
	}

	@Override
	public int insertNewComment(Comment record) {

		return commentMapper.insert(record);
	}

	@Override
	public int newId() {
		return commentMapper.creatNewId();
	}

	@Override
	public int increaseLikeNum(Integer id) {
		return commentMapper.increaseLikeNum(id);
	}

	@Override
	public List<HashMap<String, Object>> getCommentCountRank() {
		return commentMapper.getCommentCountRank();
	}

	@Override
	public int updateSelectiveCommentById(Comment record) {
		return commentMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Comment getCommentById(int id) {
		return commentMapper.selectByPrimaryKey(id);
	}

}
