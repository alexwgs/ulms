package com.cmbccd.ulms.youngTalk.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.websocket.domain.MsgTemplate;
import com.cmbccd.ulms.websocket.service.WebSocketServer;
import com.cmbccd.ulms.youngTalk.domain.*;
import com.cmbccd.ulms.youngTalk.service.*;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
* <p>Title: CommentController.java</p>  
* <p>Description: </p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
@RestController
@RequestMapping("cyt/")
public class CommentController {
	private static final Logger LOG = LoggerFactory.getLogger(CommentController.class);

	@Resource
	private CommentService commentService;
	@Resource
	private ReplyService replyService;
	@Resource
	private ArticalService articalService;
	@Resource
	private LikeService likeService;
	@Resource
	private MessageService messageService;

	/**
	 * 获取评论列表
	 * @param articalId
	 * @param params
	 * @return
	 */
	@GetMapping(value = "comment/list/{articalId}")
	public Msg getCommentList(@PathVariable ("articalId") int articalId ,@RequestParam Map<String, String> params) {
		String userId = Util.userIdByShiro();
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		List<Comment> comments = commentService.getCommentByArticalId(articalId);
		//设置是否存在评论到回复并写入评论
		for (Comment comment: comments) {
			comment.getArticalId();
			if (comment.getAnonFlag() == 1) {
				comment.setUserid("匿名");
			}else{
				comment.setUser(DataCache.getEmployees().get(comment.getUserid()));
			}
			comment.setUser(DataCache.getEmployees().get(comment.getUserid()));
			List<Reply> replys = replyService.getReplyByCommentId(comment.getId());
			List<Like> likes = likeService.getCommentLikeById(comment.getId(),userId);
			if(Util.isNullorEmpty(replys)) { continue;}
			for (Reply reply :replys) {
				if (reply.getAnonFlag() == 1) {
					reply.setUserid("匿名");
				}else{
					reply.setUser(DataCache.getEmployees().get(reply.getUserid()));
				}
			}
			
			if(!Util.isNullorEmpty(replys)) { comment.setReplys(replys);}
			if(!Util.isNullorEmpty(likes)) {comment.setLikes(likes);}

		}return Msg.success(new DataPage<Comment>(comments));
	}

	/**
	 * 发表评论
	 * 
	 * @param comment
	 * @return
	 */
	@PostMapping(value = "comment")
	public Msg insertNewComment(@RequestBody Comment comment) {
		String userId = Util.userIdByShiro();
		if (userId.equals("0")) {
			return Msg.error("您无权做此操作！请通过A6广场操作！");
		}
		int commentId = commentService.newId();
		comment.setId(commentId);
		comment.setDateTime(Util.currentDateTime());
		comment.setUserid(userId);
		comment.setLikeNum(0);
		comment.setStatus(1);
		LOG.info(comment.toString());

		// 获取当前文章发帖人
		Artical artical = articalService.getArticalByIdWithNoContent(comment.getArticalId());

		if (Util.isNullorEmpty(comment.getArticalId()) || Util.isNullorEmpty(comment.getContent())) {
			return Msg.error("请正确使用A6有声！");
		}
		int count = commentService.insertNewComment(comment);
		if (count == 0) {
			return Msg.error("评论回复失败！");
		}
		articalService.increaseReplyNum(comment.getArticalId());
		// 回复成功则将计入消息
		if (!comment.getToUser().equals(userId) && !artical.getPubUser().equals(userId)) {
			Message message = new Message();
			message.setDateTime(Util.currentDateTime());
			if (comment.getAnonFlag() == 1) {
				message.setFromUser("0" + userId);
			} else {
				message.setFromUser(userId);
			}
			message.setMessageId(commentId);
			message.setToUser(comment.getToUser());
			message.setId(messageService.newId());
			message.setArticalId(comment.getArticalId());
			// 消息类型 1-帖子 2-评论 3-进度 4-帖子状态变更消息 ...
			message.setMessageType(1);
			message.setIsRead(0);
			messageService.insertMessage(message);
		}

		WebSocketServer.sendMessage(MsgTemplate.success("cyt", "notice", "您发布的帖子有了新的回复！请在A6有声-我的消息中查看"),
				artical.getPubUser());
		return Msg.success("回复成功！");
	}

	/**
	 * 通过主键获取评论内容
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "comment/{id}")
	public Msg getCommentById(@PathVariable("id") Integer id) {
		if (Util.isNullorEmpty(id)) {
			return Msg.error("没有获取到评论ID！");
		}
		Comment comment = commentService.getCommentById(id);
		return Msg.success(comment);
	}

	@GetMapping(value = "comment/rank")
	public Msg getCommentCountRank() {
		PageHelper.startPage(1, 12);
		List<HashMap<String, Object>> rank = commentService.getCommentCountRank();
		for (HashMap<String, Object> item : rank) {
			item.put("user", DataCache.getEmployees().get(item.get("userid")));
		}
		return Msg.success(rank);
	}

	@PutMapping (value = "comment/delete/{id}")
	@SaCheckPermission("cyt:comment:delete")
	public Msg setCommentDisable(@PathVariable("id") int id) {
		if (Util.isNullorEmpty(id)) {
			return Msg.error("没有获取到评论ID！");
		}
		Comment record = new Comment();
		record.setId(id);
		record.setStatus(0);
		int count = commentService.updateSelectiveCommentById(record);
		if (count == 0) {
			return Msg.error("评论删除失败！");
		}
		return Msg.success();
	}

}
