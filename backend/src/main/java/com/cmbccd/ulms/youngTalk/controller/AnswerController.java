package com.cmbccd.ulms.youngTalk.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.ExcelUtils;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.youngTalk.domain.Answer;
import com.cmbccd.ulms.youngTalk.domain.Article;
import com.cmbccd.ulms.youngTalk.domain.Question;
import com.cmbccd.ulms.youngTalk.service.AnswerService;
import com.cmbccd.ulms.youngTalk.service.ArticleService;
import com.cmbccd.ulms.youngTalk.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * 
* <p>Title: AnswerController.java</p>  
* <p>Description: </p>  
* @author WeiGenSheng
* @date 2020年12月21日  
* @version 1.0
 */
@RestController
@RequestMapping("/cyt")
public class AnswerController {

	private static final Logger log = LoggerFactory.getLogger(AnswerController.class);

	@Resource
	private ArticleService articleService;

	@Resource
	private AnswerService answerService;

	@Resource
	private QuestionService questionService;


	@PostMapping(value = "/answer")
	public Msg submitAnswer(@RequestBody List<Answer> answers) {
		String userId = Util.userIdByShiro();
		if (userId.equals("0")) {
			return Msg.error("您无权做此操作！请通过A6广场操作！");
		}
		// 首先判断当前是否还在可提交状态
		String currentDateTime = Util.currentDateTime();
		Article article = articleService.getArticleById(answers.get(0).getArticleId());
		if (answers.size() > 0) {
			try {
				if (Util.diffDateTime(article.getCompDate(), currentDateTime) > 0) {
					return Msg.error("调研已经结束！");
				}

			} catch (ParseException e) {
				log.error("调研时间格式转换失败", e);
				return Msg.error("提交失败，调研失效格式转换失败！");
			}
		}
		// 判定当前是否已经完成过调研
		List<Answer> answered = answerService.getAnswerByUserId(article.getId(), userId);
		if (answered.size() > 0) {
			return Msg.error("您已经完成了提交，无需再次提交！");
		}

		for (Answer answer : answers) {
			answer.setDataDate(currentDateTime);
			answer.setUserId(userId);
			answerService.submitAnswer(answer);
		}
		return Msg.success("提交成功！");
	}

	@GetMapping(value = "/answer/getUserList/{articleId}")
	public Msg getSurveyAnswerUserList(@PathVariable("articleId") int articleId,
			@RequestParam Map<String, String> params) {
		return Msg.success(answerService.listSurveyAnswerUser(articleId, params));
	}

	/**
	 * A6有声调研明细下载
	 * @param articleId
	 * @param response
	 * @throws IOException
	 */
	@GetMapping(value = "download/{articleId}")
	@SaCheckPermission("cyt:artical:survey:report")
	@MyLog(title = "[cyt-answer]调研管理", content = "报表下载")
	public void downloadSurveyReport(@PathVariable("articleId") int articleId, HttpServletResponse response)
			throws IOException {
		List<List<Object>> list = new ArrayList<List<Object>>();

		List<Answer> userIds = answerService.selectSurveyUserIds(articleId);
		for (Answer user : userIds) {
			List<Answer> answers = answerService.getAnswerByUserId(articleId, user.getUserId());
			List<Object> row = new ArrayList<Object>();
			row.add(user.getUserId());
			for (Answer answer : answers) {
				row.add(answer.getAnswer());
			}
			list.add(row);
		}

		HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtils.simpleExcelTemplateStyle();

		String fileName = "调研明细";
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
		response.setCharacterEncoding("UTF-8");
		EasyExcel.write(response.getOutputStream()).registerWriteHandler(horizontalCellStyleStrategy)
				.head(head(articleId)).sheet("数据").doWrite(list);
	}

	private List<List<String>> head(int articleId) {
		List<Question> questions = questionService.selectQuestionByArticleId(articleId);

		List<List<String>> list = new ArrayList<List<String>>();
		List<String> head1 = new ArrayList<String>();
		head1.add("员工编号");
		list.add(head1);
		for (Question question : questions) {
			List<String> heads = new ArrayList<String>();
			heads.add(question.getContent());
			list.add(heads);
		}
		return list;
	}

}
