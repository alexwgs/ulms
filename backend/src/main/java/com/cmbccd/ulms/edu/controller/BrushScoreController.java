package com.cmbccd.ulms.edu.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.ExcelUtils;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.domain.BrushScore;
import com.cmbccd.ulms.edu.domain.report.BrushScoreDept;
import com.cmbccd.ulms.edu.domain.report.BrushScoreGroup;
import com.cmbccd.ulms.edu.domain.report.BrushScoreHum;
import com.cmbccd.ulms.edu.domain.report.CategoryInfo;
import com.cmbccd.ulms.edu.service.BrushScoreService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("edu/brush/score")
public class BrushScoreController {

	@Resource
	private BrushScoreService brushScoreService;

	@PostMapping("/answer")
	public Msg answerBrushScore(@RequestBody BrushScore record) {

		BrushScore brushScoore = brushScoreService.submitAnswer(record);
		if (Util.isNullorEmpty(brushScoore))
			return Msg.error("提交答案失败！");
		return Msg.success(brushScoore);
	}

	// 错题集
	@GetMapping("/wrong")
	public Msg listWrongQuestion() {
		String userId = Util.userIdByShiro();
		List<BrushScore> list = brushScoreService.listWrongQuestion(userId);
		return Msg.success(list);
	}

	// 报表前端查看模块- 个人
	@GetMapping("/report/board/hum")
	public Msg reportExamScoreHumBoard(@RequestParam Map<String, String> params) {
		List<BrushScoreHum> list = brushScoreService.reportBrushScoreHum(params);
		return Msg.success(list);
	}

	// 报表前端查看模块 - 小组
	@GetMapping("/report/board/group")
	public Msg reportExamScoreGroupBoard(@RequestParam Map<String, String> params) {
		List<BrushScoreGroup> list = brushScoreService.reportBrushScoreGroup(params);
		return Msg.success(list);
	}

	// 报表前端查看模块 - 科室
	@GetMapping("/report/board/dept")
	public Msg reportExamScoreDeptBoard(@RequestParam Map<String, String> params) {
		List<BrushScoreDept> list = brushScoreService.reportBrushScoreDept(params);
		return Msg.success(list);
	}

	/**
	 * 学霸榜 - 部门错题榜单
	 * @return
	 */
	@GetMapping("/report/board/center")
	public Msg reportCenterWrongMostQuestion() {
		List<BrushScore> list = brushScoreService.centerWrongMostQuestion();
		return Msg.success(list);
	}

	@GetMapping("/report/board/category")
	public Msg reportCategoryInfo(@RequestParam Map<String, String> params) {
		List<CategoryInfo> list = brushScoreService.reportCategoryInfo(params);
		return Msg.success(list);
	}

	// 报表模块（审计：按角色配置权限码 edu:brush:report 控制下载）
	@GetMapping("/report/hum")
	@SaCheckPermission("edu:brush:report")
	@MyLog(title = "[edu-brush]刷题管理", content = "报表下载")
	public void reportExamScoreHum(HttpServletResponse response, @RequestParam Map<String, String> params)
			throws IOException {
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");
		List<BrushScoreHum> list = brushScoreService.reportBrushScoreHum(params);
		String fileName = "人员刷题情况";
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
		HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtils.simpleExcelTemplateStyle();
		// 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
		// 如果这里想使用03 则 传入excelType参数即可
		EasyExcel.write(response.getOutputStream(), BrushScoreHum.class)
				.registerWriteHandler(horizontalCellStyleStrategy).sheet("人员刷题情况").doWrite(list);
	}

	@GetMapping("/report/group")
	@SaCheckPermission("edu:brush:report")
	@MyLog(title = "[edu-brush]刷题管理", content = "报表下载")
	public void reportExamScoreGroup(HttpServletResponse response, @RequestParam Map<String, String> params)
			throws IOException {
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");
		List<BrushScoreGroup> list = brushScoreService.reportBrushScoreGroup(params);
		String fileName = "小组刷题情况";
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
		HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtils.simpleExcelTemplateStyle();
		EasyExcel.write(response.getOutputStream(), BrushScoreGroup.class)
				.registerWriteHandler(horizontalCellStyleStrategy).sheet("小组刷题情况").doWrite(list);
	}

	@GetMapping("/report/dept")
	@SaCheckPermission("edu:brush:report")
	@MyLog(title = "[edu-brush]刷题管理", content = "报表下载")
	public void reportExamScoreDept(HttpServletResponse response, @RequestParam Map<String, String> params)
			throws IOException {
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");
		List<BrushScoreDept> list = brushScoreService.reportBrushScoreDept(params);
		String fileName = "科室刷题情况";
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
		HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtils.simpleExcelTemplateStyle();
		EasyExcel.write(response.getOutputStream(), BrushScoreDept.class)
				.registerWriteHandler(horizontalCellStyleStrategy).sheet("科室刷题情况").doWrite(list);
	}

	@GetMapping("/report/category")
	@SaCheckPermission("edu:brush:report")
	@MyLog(title = "[edu-brush]刷题管理", content = "报表下载")
	public void reportCategoryInfo(HttpServletResponse response, @RequestParam Map<String, String> params)
			throws IOException {
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");
		List<CategoryInfo> list = brushScoreService.reportCategoryInfo(params);
		String fileName = "各业务类型答题情况";
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
		HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtils.simpleExcelTemplateStyle();
		EasyExcel.write(response.getOutputStream(), CategoryInfo.class)
				.registerWriteHandler(horizontalCellStyleStrategy).sheet("各业务类型答题情况").doWrite(list);
	}
}
