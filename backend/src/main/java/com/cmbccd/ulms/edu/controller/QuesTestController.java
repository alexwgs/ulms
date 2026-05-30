package com.cmbccd.ulms.edu.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.ExcelUtils;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.domain.ExamInfo;
import com.cmbccd.ulms.edu.domain.QuesTest;
import com.cmbccd.ulms.edu.domain.report.QuesTestDetail;
import com.cmbccd.ulms.edu.service.QuesTestService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("edu/exam/test")
public class QuesTestController {

	@Resource
	private QuesTestService quesTestService;
	
	@GetMapping("/my/list/{examCode}")
	public Msg getMyTestList(@PathVariable ("examCode") String examCode) {
		String userId = Util.userIdByShiro();
		if (Util.isNullorEmpty(userId)) {
			return Msg.error("没有获取到您的登录信息！请重新登录！");
		}
		List<QuesTest>list = quesTestService.listMyExamTest(examCode, userId);
		return Msg.success(list);
	}
	
	@PostMapping("/init")
	public Msg listMyExamTest(@RequestBody ExamInfo examInfo, HttpServletRequest request) {
		String ip = Util.getIpAddress(request);
		List<QuesTest>list = quesTestService.createMyExamTest(examInfo, ip);
		return Msg.success(list);
	}
	
	/**
	 * wrongBreak 答错继续，0-答错交卷 1-答错继续
	 * 试卷题目答案匹配，但存在一个问题，如试卷设置答错及退出试卷。则需补充答错需判断试卷是否设置答错继续。并返回给前端相关信息。
	 */
	@PostMapping("/submit/{wrongBreak}")
	public Msg submitAnswer(@RequestBody QuesTest record , @PathVariable ("wrongBreak") String wrongBreak) {
		int passFlag = quesTestService.submitAnswer(record);
		if (passFlag == -1) return Msg.error("答案提交失败！如在考试中请截图反馈月考窗口！");
		if("0".equals(wrongBreak) && passFlag == 0) return Msg.success("break");
		return Msg.success();
	}
	
	//	报表模块
	@GetMapping("/report/detail/{examCode}")
	@SaCheckPermission("edu:ques:test:report")
	@MyLog(title = "[edu-score]得分管理", content = "明细报表下载")
	public void reportExamScoreHum(HttpServletResponse response,@PathVariable ("examCode") String examCode) throws IOException {
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");
		List<QuesTestDetail> list = quesTestService.reportQuesTestDetail(examCode);
		String fileName = "试卷明细";
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
		HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtils.simpleExcelTemplateStyle();
		// 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
		// 如果这里想使用03 则 传入excelType参数即可
		EasyExcel.write(response.getOutputStream(), QuesTestDetail.class).registerWriteHandler(horizontalCellStyleStrategy)
				.sheet("试卷明细").doWrite(list);
	}
}
