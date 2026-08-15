package com.cmbccd.ulms.edu.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.domain.ExamInfo;
import com.cmbccd.ulms.edu.domain.QuesTemp;
import com.cmbccd.ulms.edu.service.BookListService;
import com.cmbccd.ulms.edu.service.ExamInfoService;
import com.cmbccd.ulms.edu.service.QuesTempService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("edu/exam")
public class ExamInfoController {

	@Resource
	private ExamInfoService examInfoService;
	
	@Resource
	private BookListService bookListService;
	
	@Resource
	private QuesTempService quesTempService;

	@GetMapping("/config/list")
	@SaCheckPermission(value = {"edu:exam:list:self", "edu:exam:list"}, mode = SaMode.OR)
	public Msg listExamInfoQuery(@RequestParam Map<String, String> params) {
		boolean selfOnly = !StpUtil.hasPermission("edu:exam:list");
		return Msg.success(examInfoService.listExamInfoQuery(params, selfOnly));
	}
	
	@PutMapping("/config/update")
	@SaCheckPermission("edu:exam:update")
	@MyLog(title = "[edu-exam]考试管理")
	public Msg updateExamInfo(@RequestBody ExamInfo record) {
		int count = examInfoService.update(record);
		if(count<1) return Msg.error();
		return Msg.success();
	}
	
	@PostMapping("/config")
	@SaCheckPermission("edu:exam:add")
	@MyLog(title = "[edu-exam]考试管理")
	public Msg createExamInfo(@RequestBody ExamInfo record) {
		int count = examInfoService.create(record);
		if(count<1) return Msg.error();
		return Msg.success();
	}
	
	@DeleteMapping("/config/{examCode}")
	@SaCheckPermission("edu:exam:delete")
	@MyLog(title = "[edu-exam]考试管理")
	public Msg createExamInfo(@PathVariable("examCode") String examCode) {
		int count = examInfoService.delete(examCode);
		if(count<1) return Msg.error();
		return Msg.success();
	}
	
	/********关于抽题部分*****************/
	@GetMapping("/config/ques/temp/{examCode}")
	public Msg createExamTemp(@PathVariable("examCode") String examCode) {
		List<QuesTemp> list = quesTempService.list(examCode);
		return Msg.success(list);
	}
	
	@PostMapping("/config/ques/temp/{examCode}")
	@Transactional(rollbackFor = Exception.class)
	public Msg createExamTemp(@PathVariable("examCode") String examCode,@RequestBody List<QuesTemp> list) {
		quesTempService.delete(examCode);
		int quesNum = quesTempService.create(list);
		// 更新试题数量
		ExamInfo examInfo = new ExamInfo();
		examInfo.setExamCode(examCode);
		examInfo.setQuesNum(quesNum);
		examInfoService.update(examInfo);
		if(quesNum < 1 ) return Msg.success("所有题目配置已删除！");
		return Msg.success();
	}
	/*
	 * 前端使用API
	 */
	@GetMapping("/current/exam/list")
	public Msg listCurrentExam() {
		List<ExamInfo> list = examInfoService.listCurrentExam();
		// 判断当前考试列表中是否存在需要预约时间的，如存在则需获取当前是否已完成预约
		for(ExamInfo item : list) {
			item.setBookTime(bookListService.getBookTime(item.getExamCode()));
		}
		return Msg.success(list).put("userId",Util.userIdByShiro());
	}
	
}
