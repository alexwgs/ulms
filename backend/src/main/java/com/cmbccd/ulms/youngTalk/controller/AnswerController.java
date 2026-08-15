package com.cmbccd.ulms.youngTalk.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.ExcelUtils;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.youngTalk.domain.Answer;
import com.cmbccd.ulms.youngTalk.domain.Artical;
import com.cmbccd.ulms.youngTalk.domain.Question;
import com.cmbccd.ulms.youngTalk.service.AnswerService;
import com.cmbccd.ulms.youngTalk.service.ArticalService;
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
	private ArticalService articalService;

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
		Artical artical = articalService.getArticalById(answers.get(0).getArticalId());
		if (answers.size() > 0) {
			try {
				if (Util.diffDateTime(artical.getCompDate(), currentDateTime) > 0) {
					return Msg.error("调研已经结束！");
				}

			} catch (ParseException e) {
				log.error("调研时间格式转换失败", e);
				return Msg.error("提交失败，调研失效格式转换失败！");
			}
		}
		// 判定当前是否已经完成过调研
		List<Answer> answered = answerService.getAnswerByUserId(artical.getId(), userId);
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

	@GetMapping(value = "/answer/getUserList/{articalId}")
	public Msg getSurveyAnswerUserList(@PathVariable("articalId") int articalId,
			@RequestParam Map<String, String> params) {
		return Msg.success(answerService.listSurveyAnswerUser(articalId, params));
	}

	/**
	 * A6有声调研明细下载
	 * @param articalId
	 * @param response
	 * @throws IOException
	 */
	@GetMapping(value = "download/{articalId}")
	@SaCheckPermission("cyt:artical:survey:report")
	@MyLog(title = "[cyt-answer]调研管理", content = "报表下载")
	public void downloadSurveyReport(@PathVariable("articalId") int articalId, HttpServletResponse response)
			throws IOException {
		List<List<Object>> list = new ArrayList<List<Object>>();

		List<Answer> userIds = answerService.selectSurveyUserIds(articalId);
		for (Answer user : userIds) {
			List<Answer> answers = answerService.getAnswerByUserId(articalId, user.getUserId());
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
				.head(head(articalId)).sheet("数据").doWrite(list);
	}

	private List<List<String>> head(int articalId) {
		List<Question> questions = questionService.selectQuestionByArticalId(articalId);

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
