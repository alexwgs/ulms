package com.cmbccd.ulms.edu.controller;

import com.alibaba.excel.EasyExcel;
import cn.dev33.satoken.annotation.SaCheckPermission;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.config.UlmsConfig;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.domain.QuesScore;
import com.cmbccd.ulms.edu.excel.BrushConfigListener;
import com.cmbccd.ulms.edu.excel.QuesScoreListener;
import com.cmbccd.ulms.edu.service.BrushConfigService;
import com.cmbccd.ulms.edu.service.DailyGroupService;
import com.cmbccd.ulms.edu.service.QuesScoreService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.io.File;

@RestController
@RequestMapping("edu/excel/in")
public class ExcelImportController {
	@Resource
	private DailyGroupService dailyGroupService;
	@Resource
	private BrushConfigService brushConfigService;
	@Resource
	private QuesScoreService quesScoreService;
	@Resource
	private UlmsConfig ulmsConfig;
	@PostMapping("/daily/group/{fileName}")
	@SaCheckPermission("edu:excel:import")
	@MyLog(title = "[edu-exam]名单管理", content = "导入数据")
	public Msg processDailyGroupExcel(@PathVariable ("fileName") String fileName) {
		// 审计加固：路径穿越防护
		if (fileName.contains("..") || fileName.contains("/") || fileName.contains("\\")) {
			return Msg.error("非法的文件名！");
		}
		String file = ulmsConfig.getUploadPath() + "excel" + File.separator + fileName;
		dailyGroupService.deleteAll();
		BrushConfigListener listener = new BrushConfigListener( dailyGroupService, brushConfigService.list(null));
		EasyExcel.read(file, listener).sheet(0).doReadSync();
		Msg msg = listener.getMessage();
		return msg;
	}
	
	@PostMapping("/ques/score/{fileName}")
	@SaCheckPermission("edu:excel:import")
	@MyLog(title = "[edu-exam]名单管理", content = "导入数据")
	public Msg processQuesScoreExcel(@PathVariable ("fileName") String fileName, @RequestBody QuesScore record) {
		String examCode = record.getExamCode();
		if(Util.isNullorEmpty(examCode)) return Msg.error("关键数据为空！");
		// 审计加固：路径穿越防护
		if (fileName.contains("..") || fileName.contains("/") || fileName.contains("\\")) {
			return Msg.error("非法的文件名！");
		}

		String file = ulmsConfig.getUploadPath() + "excel" + File.separator + fileName;
		// 删除当前考试的所有名单
		quesScoreService.deleteByExamCode(examCode);
		QuesScoreListener listener = new QuesScoreListener( quesScoreService, examCode);
		EasyExcel.read(file, listener).sheet(0).doReadSync();
		Msg msg = listener.getMessage();
		return msg;
	}
}
