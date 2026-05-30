package com.cmbccd.ulms.edu.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.ExcelUtils;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.domain.BrushConfig;
import com.cmbccd.ulms.edu.domain.BrushScore;
import com.cmbccd.ulms.edu.domain.QuesBank;
import com.cmbccd.ulms.edu.service.BrushScoreService;
import com.cmbccd.ulms.edu.service.QuesBankService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
@RestController
@RequestMapping("edu/quesBank")
public class QuesBankController {

	@Resource
	private QuesBankService questionService;
	@Resource
	private BrushScoreService brushScoreService;

	@GetMapping("/list")
	@SaCheckPermission("edu:question:list:self")
	public Msg listQuestionsByLibCode(@RequestParam Map<String, String> params) {
		List<QuesBank> list = questionService.list(params,true);
		return Msg.success(new DataPage<>(list));
	}

	@GetMapping("/sensitive/list")
	@SaCheckPermission("edu:question:list:simple")
	public Msg listQuestionsNoSensitive(@RequestParam Map<String, String> params) {
		List<QuesBank> list = questionService.listNoSensitive(params);
		return Msg.success(new DataPage<>(list));
	}

	@PostMapping("")
	@SaCheckPermission("edu:question:add")
	public Msg insertNewQuestion(@RequestBody QuesBank record) {
		int count = questionService.create(record);
		if (count == 0)
			return Msg.error();
		return Msg.success();
	}

	@PutMapping("")
	@SaCheckPermission("edu:question:update")
	public Msg updateTableTree(@RequestBody QuesBank record) {
		int count = questionService.updateByQuesCodeWithNew(record);
		if (count == 0)
			return Msg.error();
		return Msg.success();
	}

	@DeleteMapping("/{quesCode}")
	@SaCheckPermission("edu:question:delete")
	public Msg deleteTableTree(@PathVariable("quesCode") String quesCode) {
		int count = questionService.deleteByQuesCode(quesCode);
		if (count == 0)  Msg.error();
		return Msg.success();
	}

	@PutMapping("/changeLib")
	public Msg updateQuesLib(@RequestBody QuesBank record) {
		int count = questionService.updateByQuesCodeWithNew(record);
		if (count == 0)
			return Msg.error();
		return Msg.success();
	}

	//	前端获取数据
	@PostMapping("/question")
	public Msg drawQuestionByLibCodes(@RequestBody BrushConfig record) {
		String userId = Util.userIdByShiro();
		if (Util.isNullorEmpty(userId)) {
			return Msg.error("登录信息失败！");
		}
		String[] libCode = record.getLibCode().split(",");
		List<String> libCodes = Arrays.asList(libCode);
		List<String> quesCodes = questionService.getQuestion(libCodes, userId);
		if (quesCodes.isEmpty())
			return Msg.error("没有在题库中找到题目~");
		Random random = new Random();
		int examCodeIndex = random.nextInt(quesCodes.size());
		QuesBank question = questionService.getNoSensitive(quesCodes.get(examCodeIndex));
		int brushCount = brushScoreService.dayBrushCount(Util.getDateToday(),userId);
		if(brushCount >= record.getDayLimit()) {
			return Msg.error("您今日刷题数已满，休息一下吧~明天再来~");
		}
		if (Util.isNullorEmpty(question))
			return Msg.error("休息一下吧~明天再来~");
		// 首先插入答题数据，并将答题
		BrushScore brushScore = new BrushScore();
		brushScore.setQuesCode(question.getQuesCode());
		brushScore.setLibCode(question.getLibCode());
		brushScore = brushScoreService.createBrushScore(brushScore);
		if (Util.isNullorEmpty(brushScore))
			return Msg.error("获取题目失败啦~");
		brushScore.setQuestion(question);
		return Msg.success(brushScore);
	}

	@PutMapping("/transfer/{libCode}")
	@SaCheckPermission("edu:question:transfer")
	public Msg updateTableTree(@PathVariable("libCode") String libCode, @RequestBody String[] questCodes) {
		for (String quesCode : questCodes) {
			QuesBank record = new QuesBank();
			record.setQuesCode(quesCode);
			record.setLibCode(libCode);
			questionService.updateBySelective(record);
		}
		questionService.updateQuesNumber();
		return Msg.success();
	}


	@GetMapping("/{quesCode}")
	public Msg getQuestion(@PathVariable("quesCode") String quesCode) {
		QuesBank quesBank = questionService.get(quesCode);
		return Msg.success(quesBank);
	}

	@GetMapping("/report")
	@SaCheckPermission("edu:question:download")
	@MyLog(title = "[edu-question]题库管理", content = "报表下载")
	public void reportQuestionBank(HttpServletResponse response, @RequestParam Map<String, String> params) throws IOException {
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");
		List<QuesBank> list = questionService.list(params, false);
		String fileName = "题库明细";
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
		HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtils.simpleExcelTemplateStyle();
		EasyExcel.write(response.getOutputStream(), QuesBank.class).registerWriteHandler(horizontalCellStyleStrategy)
				.sheet("题库明细").doWrite(list);
	}

}

