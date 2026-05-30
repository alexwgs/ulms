package com.cmbccd.ulms.edu.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.domain.DisputeQuestion;
import com.cmbccd.ulms.edu.domain.QuesDispute;
import com.cmbccd.ulms.edu.domain.QuesDisputeExample;
import com.cmbccd.ulms.edu.domain.QuesDisputeExample.Criteria;
import com.cmbccd.ulms.edu.service.QuesDisputeService;
import com.cmbccd.ulms.edu.service.QuesScoreService;
import com.cmbccd.ulms.edu.service.QuesTestService;
import com.cmbccd.ulms.sys.domain.Msg;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("edu/ques/dispute")
public class QuesDisputeController {

	@Resource
	private QuesDisputeService quesDisputeService;
	@Resource
	private QuesTestService quesTestService;
	@Resource
	private QuesScoreService examScoreService;
	
	
	@PostMapping("")
	public Msg createDisputeByExam(@RequestBody QuesDispute record) {
		quesDisputeService.create(record);
		return Msg.success();
	}
	
	@PutMapping("")
	@SaCheckPermission("edu:dispute:update")
	@MyLog(title = "[edu-dispute]复议管理")
	public Msg update(@RequestBody QuesDispute record) {
		QuesDisputeExample example = new QuesDisputeExample();
		Criteria criteria = example.createCriteria();
		criteria.andQuesCodeEqualTo(record.getQuesCode());
		String examCode = record.getExamCode();
		String quesCode = record.getQuesCode();
		if(Util.isNullorEmpty(examCode))	criteria.andExamCodeIsNull();
		else criteria.andExamCodeEqualTo(examCode);
		if(!Util.isNullorEmpty(record.getPloNum())) criteria.andPloNumEqualTo(record.getPloNum());
		quesDisputeService.update(record.getDisputeResult(),example);
		// 如复议结果不为维持原判则其他情况均需将考试成绩做更新
		if(!Util.isNullorEmpty(examCode)) {
			// 更新题目状态
			quesTestService.updateQuestionCorrect(examCode, quesCode, (int)record.getDisputeResult());
			examScoreService.updateUserScore(examCode);
		}
		return Msg.success();
	}
	
	@GetMapping("/{examCode}")
	public Msg listUserDispute(@PathVariable ("examCode") String examCode) {
		String userId = Util.userIdByShiro();
		return Msg.success(quesDisputeService.list(examCode, userId));
	}
	
	@GetMapping("")
	@SaCheckPermission("edu:dispute:list")
	@MyLog(title = "[edu-dispute]复议管理")
	public Msg listUserDispute(@RequestParam Map<String, String> params ) {
		QuesDisputeExample example = new QuesDisputeExample();
		String examCode = params.get("examCode");
		String disputeResult = params.get("disputeResult");
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		Criteria criteria = example.createCriteria();
		if (Util.isNullorEmpty(examCode)) criteria.andExamCodeIsNull();
		else criteria.andExamCodeEqualTo(examCode);
		if (!Util.isNullorEmpty(disputeResult))  criteria.andDisputeResultEqualTo(Short.parseShort(disputeResult));
		
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		
		if (!Util.isNullorEmpty(params.get("order"))) {
			example.setOrderByClause(Util.camel4underline(params.get("order")) + " " + params.get("orderType"));
		}
		List<DisputeQuestion> list = quesDisputeService.listDisputeQuestion(example);
		return Msg.success(new DataPage<DisputeQuestion>(list));
	}

	/**
	 * 刷题中的题目问题反馈，不修改得分及题目内容
	 * @param record
	 * @return
	 */
	@PutMapping("/brush")
	public Msg brushDispute(@RequestBody QuesDispute record) {
		QuesDisputeExample example = new QuesDisputeExample();
		Criteria criteria = example.createCriteria();
		criteria.andQuesCodeEqualTo(record.getQuesCode());
		String examCode = record.getExamCode();
		String quesCode = record.getQuesCode();
		if(Util.isNullorEmpty(examCode))	criteria.andExamCodeIsNull();
		else criteria.andExamCodeEqualTo(examCode);
		criteria.andQuesCodeEqualTo(quesCode);
		quesDisputeService.update(record.getDisputeResult(),example);
		return Msg.success();
	}
	
}
