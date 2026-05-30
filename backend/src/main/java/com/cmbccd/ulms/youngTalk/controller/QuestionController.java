package com.cmbccd.ulms.youngTalk.controller;

import com.cmbccd.ulms.common.domain.ChartData;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.youngTalk.domain.Option;
import com.cmbccd.ulms.youngTalk.domain.Question;
import com.cmbccd.ulms.youngTalk.service.OptionService;
import com.cmbccd.ulms.youngTalk.service.QuestionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cyt")
public class QuestionController {

	@Resource
	private QuestionService questionService;

	@Resource
	private OptionService optionService;

	@GetMapping(value = "/questions/{articalId}")
	public Msg getQuestionsByUserId(@PathVariable("articalId") int articalId) {
		String userId = Util.userIdByShiro();

		List<Question> questions = questionService.selectUserQuestionByArticalId(articalId, userId);
		for (Question question : questions) {
			List<Option> options = optionService.getOptionsByQuestionId(articalId, question.getId());
			if (!Util.isNullorEmpty(question.getAnswer()) && "checkbox".equals(question.getQuestionType())) {
				question.setCheckboxs(question.getAnswer().split("\\|"));
			}
			question.setOptions(options);
		}
		return Msg.success(questions);
	}

	@GetMapping(value = "/survey/chart/{articalId}")
	public Msg getQuestionByArticalId(@PathVariable("articalId") int articalId) {
		List<Question> list = questionService.selectQuestionByArticalId(articalId);
		List<ChartData> chartData = new ArrayList<ChartData>();
		for (Question question : list) {
			ChartData ctd = new ChartData().question();
			if ("radio".equals(question.getQuestionType())) {
				List<Map<String, Object>> rows = optionService.selectRadioNumGroupByQuestionId(question.getArticalId(),
						question.getId());
				ctd.setRows(rows);

			} else if ("checkbox".equals(question.getQuestionType())) {
				List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
				//先获取OPTIONS
				List<Option> options = optionService.getOptionsByQuestionId(articalId, question.getId());
				for(Option option : options) {
					Map<String, Object> row = new HashMap<String, Object>();
					int count = optionService.selectCheckboxNumGroupByOptionId(articalId, question.getId(), option.getContent());
					row.put("选项", option.getContent());
					row.put("选择人数", count);
					rows.add(row);
				}
				ctd.setRows(rows);
			}
			chartData.add(ctd);
		}

		return Msg.success(list).put("chartData", chartData);
	}
}
