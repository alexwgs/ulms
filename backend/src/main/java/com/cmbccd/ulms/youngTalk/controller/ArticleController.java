package com.cmbccd.ulms.youngTalk.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.youngTalk.domain.*;
import com.cmbccd.ulms.youngTalk.domain.ArticleExample.Criteria;
import com.cmbccd.ulms.youngTalk.service.*;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 
* <p>Title: ArticleController.java</p>  
* <p>Description: </p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
@RestController
@RequestMapping("cyt")
public class ArticleController {

	@Resource
	private ArticleService articleService;
	@Resource
	private LikeService likeService;
	@Resource
	private CollectService collectService;
	@Resource
	private MemberService memberService;
	@Resource
	private ProgressService progressService;
	@Resource
	private QuestionService questionService;
	@Resource
	private OptionService optionService;
	@Resource
	private MessageService messageService;

	@GetMapping(value = "stageList")
	public Msg articleViewOnStage() {
		ArticleExample example = new ArticleExample();
		Criteria criteria = example.createCriteria();
		criteria.andOnStageEqualTo(1);
		criteria.andStatusEqualTo(1);
		example.setOrderByClause(" (VIEW_NUM*0.5+REPLY_NUM*2+COLLECT_NUM+LIKE_NUM) DESC,ELITE_FLAG DESC,ID DESC");
		List<Article> articleList = articleService.selectByExampleNoContent(example);
		return Msg.success(articleList);
	}

	@GetMapping(value = "topList/{type}")
	public Msg articleTopList() {
		ArticleExample example = new ArticleExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(1);
		criteria.andTopFlagEqualTo(1);
		example.setOrderByClause(" (VIEW_NUM*0.5+REPLY_NUM*2+COLLECT_NUM+LIKE_NUM) DESC,ELITE_FLAG DESC,ID DESC");
		List<Article> articleList = articleService.selectByExampleNoContent(example);
		return Msg.success(articleList);
	}

	// 周热榜 通过过滤近一周到评论来确认当前到榜单
	@GetMapping(value = "weekly/rank")
	public Msg getWeeklyHotList() {
		String dateTime = Util.getPastDate(30);
		PageHelper.startPage(0, 15);
		List<Article> articleList = articleService.selectWeeklyHotList(dateTime);
		return Msg.success(articleList);
	}

	/**
	 * 全量数据，不分类别
	 *
	 * @param params
	 * @return
	 */
	@GetMapping(value = "articleList")
	public Msg articleCategoryList(@RequestParam Map<String, String> params) {
		return Msg.success(articleService.listCategoryByQuery(params));
	}

	@GetMapping(value = "articleList/{type}/{category}")
	public Msg articleCategoryList(@PathVariable("type") Integer type, @PathVariable("category") Integer category,
			@RequestParam Map<String, String> params) {
		ArticleExample example = new ArticleExample();
		Criteria criteria = example.createCriteria();
//		criteria.andStatusEqualTo(1);
		criteria.andArticleTypeEqualTo(type);

		if (category != -1) {
			criteria.andCategoryEqualTo(category);
		}

		String sortType = params.get("sortType");
		if ("time".equals(sortType)) {
			example.setOrderByClause(" TOP_FLAG DESC,ELITE_FLAG DESC,ID DESC");
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
		if (!Util.isNullorEmpty(params.get("compType"))) {
			criteria.andCompTypeEqualTo(Integer.parseInt(params.get("compType")));
		}
		if (!Util.isNullorEmpty(params.get("compType"))) {
			criteria.andCompTypeEqualTo(Integer.parseInt(params.get("compType")));
		}
		if (!Util.isNullorEmpty(params.get("compType"))) {
			criteria.andCompTypeEqualTo(Integer.parseInt(params.get("compType")));
		}
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));

		List<Article> articleList = articleService.selectByExampleNoContent(example);
		for (Article article : articleList) {
			if(article.getAnonFlag() == 1) {
				article.setPubUser("匿名");
			} else {
				article.setUser(DataCache.getEmployees().get(article.getPubUser()));
			}
		}
		return Msg.success(new DataPage<Article>(articleList));
	}

	@GetMapping(value = "articleList/{type}")
	public Msg articleList(@PathVariable("type") Integer type) {
		ArticleExample example = new ArticleExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo(1);
		criteria.andArticleTypeEqualTo(type);
		example.setOrderByClause(" (VIEW_NUM*0.5+REPLY_NUM*2+COLLECT_NUM+LIKE_NUM) DESC,ELITE_FLAG DESC,ID DESC");
		List<Article> articleList = articleService.selectByExampleNoContent(example);
		return Msg.success(articleList);
	}

	/**
	 * 通过ID获取文章详情
	 * 
	 * @param id
	 * @return：Article
	 */
	@GetMapping(value = "article/{id}")
	public Msg articleById(@PathVariable("id") Integer id) {
		String userId = Util.userIdByShiro();

		Article article = articleService.getArticleById(id);
		if (Util.isNullorEmpty(article)) {
			return Msg.error("没有获取到文章信息！请正确使用A6有声！");
		}

		if (article.getStatus() != 1 && !article.getPubUser().equals(userId)) {
			return Msg.error("没有获取到文章信息！您无权查看该帖子！");
		}
			// 是否点赞
			if (!Util.isNullorEmpty(userId)) {
				article.setIsLike(likeService.isUserLikeByArticleId(id, userId));
				// 是否收藏
				article.setIsCollect(collectService.isUserCollectByArticleId(id, userId));
			}
		if(article.getAnonFlag() == 1) {
			article.setPubUser("匿名");
		} else {
			article.setUser(DataCache.getEmployees().get(article.getPubUser()));
		}
		// 写入文字发布人对象
		articleService.increaseViewNum(id);
		return Msg.success(article);
	}

	/**
	 * 管理员通过ID获取文章详情
	 * 
	 * @param id
	 * @return：Article
	 */
	@SaCheckPermission("cyt:artical:view")
	@GetMapping(value = "article/manage/{id}")
	@MyLog(title = "[cyt-article]文章管理")
	public Msg adminArticleById(@PathVariable("id") Integer id) {
		String userId = Util.userIdByShiro();
		if (userId.equals("0")) {
			return Msg.error("您无权做此操作！请通过A6广场操作！");
		}
		Article article = articleService.getArticleById(id);
		if (Util.isNullorEmpty(article)) {
			return Msg.error("没有获取到文章信息！请正确使用A6有声！");
		}

		// 是否点赞
		article.setIsLike(likeService.isUserLikeByArticleId(id, userId));
		// 是否收藏
		article.setIsCollect(collectService.isUserCollectByArticleId(id, userId));
		// 写入文字发布人对象
		if(article.getAnonFlag() == 1) {
			article.setPubUser("匿名");
		} else {
			article.setUser(DataCache.getEmployees().get(article.getPubUser()));
		}
		articleService.increaseViewNum(id);
		return Msg.success(article);
	}

	/**
	 * 文章发布（非项目）
	 */
	@PostMapping(value = "article")
	public Msg insertNewArticle(@RequestBody Article article) {
		String userId = Util.userIdByShiro();
		if (userId.equals("0")) {
			return Msg.error("您无权做此操作！请通过A6广场操作！");
		}
		int id = articleService.getNewArticleId();
		article.setId(id);
//		article.setArticleType(2);
		article.setPubUser(userId);
		article.setPubDate(Util.currentDateTime());
		article.setTopFlag(0);
		article.setEliteFlag(0);
		article.setOnStage(0);
		article.setViewNum(0);
		article.setCollectNum(0);
		article.setReplyNum(0);
		article.setLikeNum(0);
		int count = articleService.insertArticle(article);
		if (count == 0) {
			return Msg.error("发帖失败！");
		}
		return Msg.success(id);
	}

	@PostMapping(value = "survey")
	public Msg insertNewSurvey(@RequestBody JSONObject params) {
		String userId = Util.userIdByShiro();
		if (userId.equals("0")) {
			return Msg.error("您无权做此操作！请通过A6广场操作！");
		}

		List<Object> questions = params.getJSONArray("questions");

		Article article = new Article();
		int id = articleService.getNewArticleId();
		article.setContent(params.getString("content"));
		article.setTitle(params.getString("title"));
		article.setCategory(params.getInteger("category"));
		article.setStatus(1);
		article.setId(id);
		// 3为调研
		article.setAnonFlag(Util.isNullorEmpty(params.getString("anonFlag"))?0:Integer.parseInt(params.getString("anonFlag")));
		article.setArticleType(3); 
		article.setPubUser(userId);
		article.setCompDate(params.getString("compDate"));
		article.setPubDate(Util.currentDateTime());
		article.setTopFlag(0);
		article.setEliteFlag(0);
		article.setOnStage(0);
		article.setViewNum(0);
		article.setCollectNum(0);
		article.setReplyNum(0);
		article.setLikeNum(0);

		int count = articleService.insertArticle(article);
		if (count == 0) {
			return Msg.error("调研发布失败！");
		}

		for (Object questionStr : questions) {
			JSONObject questionObj = JSON.parseObject(JSON.toJSONString(questionStr));

			Question quest = new Question();
			Integer questionId = questionService.getNewId();
			quest.setId(questionId);
			quest.setContent(questionObj.getString("content"));
			quest.setArticleId(id);
			quest.setQuestionType(questionObj.getString("questionType"));
			quest.setSort(questionObj.getInteger("sort"));

			count = questionService.insertNewQuestion(quest);
			if (count == 0) {
				return Msg.error("调研问题新增失败！");
			}

			int index = 1;
			List<Object> options = questionObj.getJSONArray("options");
			for (Object optionStr : options) {
				String optionObj = optionStr.toString();

				Option option = new Option();
				option.setId(optionService.getNewId());
				option.setArticleId(id);
				option.setContent(optionObj);
				option.setQuestionId(questionId);
				option.setSort(index);
				count = optionService.insertNewOption(option);
				if (count == 0) {
					return Msg.error("调研选项新增失败！");
				}
				index++;
			}
		}
		return Msg.success("调研发布成功！");
	}

	/**
	 * 我发布的帖子或项目
	 */
	@GetMapping(value = "myArticle")
	public Msg myArticle(@RequestParam Map<String, String> params) {
		String userId = Util.userIdByShiro();
		if (userId.equals("0")) {
			return Msg.error("您无权做此操作！请通过A6广场操作！");
		}
		ArticleExample example = new ArticleExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusNotEqualTo(0);
		criteria.andPubUserEqualTo(userId);

		if (!Util.isNullorEmpty(params.get("dateYear"))) {
			criteria.andPubDateBetween(params.get("dateYear") + "-01-01 00:00:00",
					params.get("dateYear") + "-12-31 23:59:59");
		}
		if (!Util.isNullorEmpty(params.get("status"))) {
			criteria.andStatusEqualTo(Integer.parseInt(params.get("status")));
		}
		example.setOrderByClause(" ID DESC");

		Map<String, Integer> pageParams = Util.innitTablePages(params);
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));

		List<Article> articleList = articleService.selectByExampleNoContent(example);
		for (Article article : articleList) {
			if(article.getAnonFlag() == 1) {
				article.setPubUser("匿名");
			} else {
				article.setUser(DataCache.getEmployees().get(article.getPubUser()));
			}
		}
		return Msg.success( new DataPage<Article>(articleList));
	}

	/**
	 * 我发认领及参与的项目
	 */
	@GetMapping(value = "myItem")
	public Msg myItem(@RequestParam Map<String, String> params) {
		String userId = Util.userIdByShiro();
		if (userId.equals("0")) {
			return Msg.error("您无权做此操作！请通过A6广场操作！");
		}
		List<Integer> ids = memberService.getArticleIdByUserId(userId);
		ArticleExample example = new ArticleExample();
		Criteria criteria = example.createCriteria();
		if (ids != null && ids.size() != 0) {
			criteria.andIdIn(ids);
		} else {
			criteria.andIdEqualTo(-1);
		}
		criteria.andArticleTypeEqualTo(1);
		// 不给前端显示已被管理员删除到项目
		criteria.andStatusNotEqualTo(0); 
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		example.setOrderByClause(" ID DESC ");
		List<Article> articleList = articleService.selectByExampleNoContent(example);
		return Msg.success(new DataPage<Article>(articleList));
	}

	/**
	 * 更新项目BY Id
	 */
	@PutMapping(value = "article")
	public Msg updateArticle(@RequestBody Article record) {
		String userId = Util.userIdByShiro();
		if (userId.equals("0")) {
			return Msg.error("您无权做此操作！请通过A6广场操作！");
		}
		if (Util.isNullorEmpty(record.getId())) {
			return Msg.error("没有获取到文章信息！请正确使用A6有声！");
		}
		if (!record.getPubUser().equals(userId)) {
			return Msg.error("您无权操作此文章！");
		}
		record.setPubDate(Util.currentDateTime());

		int count = articleService.updateArticle(record);
		if (count == 0) {
			return Msg.error("操作成功！");
		}
		return Msg.success("操作成功！");
	}

	/**
	 * 项目认领BY Id(旧版本，若是认领则为PM，其他人不可认领)
	 */
	@PutMapping(value="article/take/{id}")
	public Msg takeItem(@PathVariable ("id") int id) {
		String userId = Util.userIdByShiro();
		if (userId.equals("0")) {
			return Msg.error("您无权做此操作！请通过A6广场操作！");
		}
		Article article = new Article();
		article.setId(id);
		article.setCompType(5);
		int count = articleService.updateArticleSelectiveByPrimaryKey(article);
		if(count == 0)  return Msg.error("认领项目失败！");
		Member member = new Member();
		member.setId(memberService.newId());
		member.setArticleId(id);
		member.setUserId(userId);
		member.setRole(1);
		member.setScoreRate(0);
		member.setStatus(1);
		count = memberService.insertRecord(member);
		if(count == 0)  return Msg.error("认领项目失败！");
		//更新一条进度
		Progress progress = new Progress();
		progress.setArticleId(id);
		progress.setId(progressService.newId());
		progress.setContent("领取项目");
		progress.setDateTime(Util.currentDateTime());
		progress.setUserId(userId);
		progress.setRate(0);
		progress.setType(5);
		progress.setStatus(1);
		count = progressService.insertNewProgress(progress);
		if(count == 0)  return Msg.error("认领项目失败！");
		return Msg.success("项目认领成功！请前往【项目管理】配置项目成员！");
	}
	/**
	 * 项目认领BY Id(可多人认领版本)

	@RequestMapping(value = "article/take/{id}", method = RequestMethod.PUT)
	public Msg takeItem(@PathVariable("id") int id) {
		String userId = Util.userIdByShiro();
		if (Util.isNullorEmpty(userId)) {
			return Msg.error("没有获取到用户信息！请正确使用A6有声！");
		}
		int count;

		MemberExample example = new MemberExample();
		com.cmbccd.ulms.youngTalk.domain.MemberExample.Criteria criteria = example.createCriteria();
		criteria.andArticleIdEqualTo(id);
		criteria.andUserIdEqualTo(userId);
		criteria.andStatusEqualTo(1);
		List<Member> list = memberService.listMember(example);
		if (list.size() > 0) {
			return Msg.error("您已经完成了认领哦！");
		}
		Member member = new Member();
		member.setId(memberService.newId());
		member.setArticleId(id);
		member.setUserId(userId);
		member.setRole(2);
		member.setScoreRate(0);
		member.setStatus(1);
		count = memberService.insertRecord(member);
		if (count == 0) {
			return Msg.error("认领项目失败！");
		}
		return Msg.success("项目认领成功！请前往【项目管理】配置项目成员！");
	}
	 */
	/**
	 * 后台帖子及项目管理（置顶、精华、上广场）操作
	 * 
	 * @param type
	 * @param id
	 * @param status
	 * @return
	 */
	@PutMapping(value = "article/on/{type}/{id}/{status}")
	@SaCheckPermission("cyt:artical:set")
	@MyLog(title = "[cyt-article]文章管理")
	public Msg setTop(@PathVariable("type") String type, @PathVariable("id") int id,
			@PathVariable("status") int status) {
		String userId = Util.userIdByShiro();
		if (userId.equals("0")) {
			return Msg.error("您无权做此操作！请通过A6广场操作！");
		}

		if (Util.isNullorEmpty(type) || Util.isNullorEmpty(id) || Util.isNullorEmpty(status)) {
			return Msg.error("没有获取到操作信息！请正确使用A6有声！");
		}
		Article record = new Article();
		record.setId(id);
		if ("top".equals(type)) {
			record.setTopFlag(status);
		} else if ("elite".equals(type)) {
			record.setEliteFlag(status);
		} else if ("stage".equals(type)) {
			record.setOnStage(status);
		} else {
			return Msg.error("没有获取到操作信息！请正确使用A6有声！");
		}
		int count = articleService.updateArticleSelectiveByPrimaryKey(record);
		if (count == 0) {
			Msg.error("操作失败！");
		}
		return Msg.success();
	}

	/**
	 * 结案管理
	 */
	@PutMapping(value = "article/on/comp")
	@SaCheckPermission("cyt:artical:complete")
	@MyLog(title = "[cyt-article]文章管理")
	public Msg setItemComplete(@RequestBody Article record) {
		String userId = Util.userIdByShiro();
		if (userId.equals("0")) {
			return Msg.error("您无权做此操作！请通过A6广场操作！");
		}

		if (Util.isNullorEmpty(record.getId())) {
			return Msg.error("没有获取到ID！请正确使用A6有声！");
		}
		int count = articleService.updateArticleSelectiveByPrimaryKey(record);
		if (count == 0) {
			Msg.error("操作失败！");
		}
		// 下面需要补充积分记录
		return Msg.success();
	}

	/**
	 * 案件的审核管理
	 * 
	 * @param record
	 * @return
	 */
	@PutMapping(value = "article/on/examine")
	@SaCheckPermission("cyt:artical:examine")
	@MyLog(title = "[cyt-article]文章管理")
	public Msg setItemExamine(@RequestBody Article record) {
		String userId = Util.userIdByShiro();
		if (userId.equals("0")) {
			return Msg.error("您无权做此操作！请通过A6广场操作！");
		}

		if (Util.isNullorEmpty(record.getId())) {
			return Msg.error("没有获取到ID！请正确使用A6有声！");
		}
		int count = articleService.updateArticleSelectiveByPrimaryKey(record);
		if (count == 0) {
			Msg.error("操作失败！");
		}

		Article article = articleService.getArticleByIdWithNoContent(record.getId());

		// 发送审核情况推送
		if (!userId.equals(article.getPubUser())) {
			Message message = new Message();
			message.setDateTime(Util.currentDateTime());
			message.setFromUser("system");
			message.setMessageId(record.getStatus());
			message.setToUser(article.getPubUser());
			message.setId(messageService.newId());
			message.setArticleId(record.getId());
			// 消息类型 1-帖子 2-评论 3-进度 4-帖子状态变更消息 ...
			message.setMessageType(4);
			message.setIsRead(0);
			messageService.insertMessage(message);
		}
		return Msg.success();
	}

	/**
	 * 移动文章到类别
	 * 
	 * @param record
	 * @return
	 */
	@PutMapping(value = "article/category")
	@SaCheckPermission("cyt:artical:category:update")
	@MyLog(title = "[cyt-article]文章管理")
	public Msg updateArticleCategory(@RequestBody Article record) {
		String userId = Util.userIdByShiro();
		if (userId.equals("0")) {
			return Msg.error("您无权做此操作！请通过A6广场操作！");
		}

		if (Util.isNullorEmpty(record.getId())) {
			return Msg.error("没有获取到ID！请正确使用A6有声！");
		}
		int count = articleService.updateArticleSelectiveByPrimaryKey(record);
		if (count == 0) {
			Msg.error("操作失败！");
		}
		return Msg.success();
	}

	/**
	 * 文章的删除或恢复
	 * 
	 * @param record
	 * @return
	 */
	@PutMapping(value = "article/delete")
	@MyLog(title = "[cyt-article]文章管理")
	public Msg articleDeleteOrRecover(@RequestBody Article record) {
		String userId = Util.userIdByShiro();
		if (userId.equals("0")) {
			return Msg.error("您无权做此操作！请通过A6广场操作！");
		}

		if (Util.isNullorEmpty(record.getId())) {
			return Msg.error("没有获取到ID！请正确使用A6有声！");
		}
		int count = articleService.updateArticleSelectiveByPrimaryKey(record);
		if (count == 0) {
			Msg.error("操作失败！");
		}
		return Msg.success();
	}
}
