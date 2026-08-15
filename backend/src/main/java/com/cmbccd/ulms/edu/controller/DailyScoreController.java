package com.cmbccd.ulms.edu.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.fastjson.JSON;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.ExcelUtils;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.domain.DailyScore;
import com.cmbccd.ulms.edu.domain.QuesBank;
import com.cmbccd.ulms.edu.domain.report.DailyScoreSummary;
import com.cmbccd.ulms.edu.service.DailyScoreService;
import com.cmbccd.ulms.edu.service.QuesBankService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/edu/daily/score")
public class DailyScoreController {

    @Resource
    private DailyScoreService dailyScoreService;
    @Resource
    private QuesBankService quesBankService;

	@PostMapping("/mood/submit")
	public Msg submitMood(@RequestBody DailyScore dailyScore) {
		DailyScore dailyScoreResult = dailyScoreService.submitMood(dailyScore);
		if(Util.isNullorEmpty(dailyScoreResult)) return Msg.error("数据提交失败！");
		return Msg.success(dailyScoreResult);
	}

	@PostMapping("/article/submit")
	public Msg submitArticleStudy(@RequestBody DailyScore dailyScore) {
		DailyScore dailyScoreResult = dailyScoreService.submitArticleStudy(dailyScore);
		if(Util.isNullorEmpty(dailyScoreResult)) return Msg.error("数据提交失败！");
		return Msg.success(dailyScoreResult);
	}

	@PostMapping("/question/submit")
	public Msg submitQuestion(@RequestBody Map<String, String> objects) {
		QuesBank question = JSON.parseObject(objects.get("question"), QuesBank.class);
		DailyScore dailyScore = JSON.parseObject(objects.get("dailyScore"), DailyScore.class);
		// question.getCategory() 代替用户答案
		int flag = quesBankService.checkUserAnswer(question, question.getCategory());
		// 更新答题信息
		DailyScore dailyScoreResult = dailyScoreService.submitQuestion(dailyScore ,flag, question.getCategory());
		if(Util.isNullorEmpty(dailyScoreResult)) return Msg.error("数据提交失败！");
		return Msg.success(dailyScoreResult);
	}

	// 报表
	@GetMapping("/list")
	@MyLog(title = "[edu-daily]每日一学管理", content = "报表下载")
	public Msg listDailyScore(@RequestParam Map<String,String> params) {
		return Msg.success(dailyScoreService.listDailyScoreByQuery(params));
	}
	// 下载报表

	@GetMapping("/report/list")
	@MyLog(title = "[edu-daily]每日一学管理", content = "报表下载")
	public void reportExamScoreHum(HttpServletResponse response, @RequestParam Map<String,String> params) throws IOException {
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");
		List<DailyScoreSummary> list = dailyScoreService.listDailyScoreByQuery(params).getList();
		String fileName = "每日一学统计报表";
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
		HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtils.simpleExcelTemplateStyle();
		// 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
		// 如果这里想使用03 则 传入excelType参数即可
		EasyExcel.write(response.getOutputStream(), DailyScoreSummary.class).registerWriteHandler(horizontalCellStyleStrategy)
				.sheet("每日一学统计报表").doWrite(list);
	}
}