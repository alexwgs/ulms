package com.cmbccd.ulms.youngTalk.controller;


import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.youngTalk.domain.Like;
import com.cmbccd.ulms.youngTalk.domain.LikeExample;
import com.cmbccd.ulms.youngTalk.domain.LikeExample.Criteria;
import com.cmbccd.ulms.youngTalk.service.ArticleService;
import com.cmbccd.ulms.youngTalk.service.CommentService;
import com.cmbccd.ulms.youngTalk.service.LikeService;
import com.cmbccd.ulms.youngTalk.service.ReplyService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

@RestController
@RequestMapping("cyt")
public class LikeController {

	@Resource
	private LikeService likeService;
	@Resource
	private ArticleService articleService;
	@Resource
	private CommentService commentService;
	@Resource
	private ReplyService replyService;

	@PostMapping(value = "like/{likeType}/{id}")
	public Msg setLike(@PathVariable("id") int id, @PathVariable("likeType") int likeType) {
		String userId = Util.userIdByShiro();
		if (userId.equals("0")) {
			return Msg.error("您无权做此操作！请通过A6广场操作！");
		}

		LikeExample example = new LikeExample();
		Criteria criteria = example.createCriteria();
		criteria.andArticleIdEqualTo(id);
		criteria.andLikeTypeEqualTo(likeType);
		criteria.andUserIdEqualTo(userId);
		criteria.andStatusEqualTo(1);

		if (likeService.countByExample(example) != 0) {
			return Msg.error("操作失败！已经被点过赞了！");
		}

		Like like = new Like();
		like.setId(likeService.newId());
		like.setLikeType(likeType);
		like.setArticleId(id);
		like.setUserId(userId);
		like.setStatus(1);
		like.setDateTime(Util.currentDateTime());
		int count = likeService.insert(like);
		if (count == 0) {
			return Msg.error("点赞失败！请联系管理员！");
		}

		// 修改文章的点赞数量
		if (likeType == 1) {
			articleService.increaseLikeNum(id);
		} else if (likeType == 2) {
			commentService.increaseLikeNum(id);
		} else if (likeType == 3) {
			replyService.increaseLikeNum(id);
		}
		return Msg.success("点赞成功！");
	}
}
