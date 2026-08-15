package com.cmbccd.ulms.edu.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.ExcelUtils;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.domain.ExamInfo;
import com.cmbccd.ulms.edu.domain.QuesScore;
import com.cmbccd.ulms.edu.domain.QuesScoreExample;
import com.cmbccd.ulms.edu.domain.QuesScoreExample.Criteria;
import com.cmbccd.ulms.edu.domain.report.QuesScoreHum;
import com.cmbccd.ulms.edu.service.AreaListService;
import com.cmbccd.ulms.edu.service.ExamInfoService;
import com.cmbccd.ulms.edu.service.QuesScoreService;
import com.cmbccd.ulms.edu.service.QuesTestService;
import com.cmbccd.ulms.sys.domain.Department;
import com.cmbccd.ulms.sys.domain.Employee;
import com.cmbccd.ulms.sys.domain.Msg;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("edu/exam/score")
public class QuesScoreController {
	
	@Resource
	private QuesScoreService quesScoreService;
	@Resource
	private ExamInfoService examInfoService;	
	@Resource
	private QuesTestService quesTestService;	
	@Resource
	private AreaListService areaListService;
	
	@GetMapping("/user/list")
	public Msg listExamUserList(@RequestParam Map<String, String> params) {
		String examCode = params.get("examCode");
		String query = params.get("query");
		String queryType = params.get("queryType");
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		QuesScoreExample example = new QuesScoreExample();
		Criteria criteria = example.createCriteria();
		if(!Util.isNullorEmpty(query)) {
			if("ploNum".equals(queryType)) {
				criteria.andPloNumEqualTo(query);
			}else if("ploName".equals(queryType)) {
				List<String> ploNums = DataCache.getEmployees().values().stream().filter(e -> e.getPloName().contains(query)).map(Employee::getPloNum).collect(Collectors.toList());
				if(ploNums.isEmpty()) criteria.andPloNumIsNull();
				else criteria.andPloNumIn(ploNums);
			}else if("deptNum".equals(queryType)) {
				List<String> deptNums = DataCache.getDepartments().values().stream().filter(e -> e.getDeptName().contains(query) && Util.isNullorEmpty(e.getUpDept())).map(Department::getDeptNum).collect(Collectors.toList());
				if(deptNums.isEmpty()) criteria.andPloNumIsNull();
				else criteria.andDeptNumIn(deptNums);
			}else if("deptGroup".equals(queryType)) {
				List<String> deptNums = DataCache.getDepartments().values().stream().filter(e -> e.getDeptName().contains(query) && !Util.isNullorEmpty(e.getUpDept()) ).map(Department::getDeptNum).collect(Collectors.toList());
				if(deptNums.isEmpty()) criteria.andPloNumIsNull();
				else criteria.andDeptGroupIn(deptNums);
			}
		}
		
		if(!Util.isNullorEmpty(examCode)) criteria.andExamCodeEqualTo(examCode);
		
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		
		if (!Util.isNullorEmpty(params.get("order"))) {
			example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
		}
		List<QuesScore> list = quesScoreService.list(example);
		return Msg.success(new DataPage<QuesScore>(list));
	}
	
	@PostMapping("/add")
	@MyLog(title = "[edu-score]得分管理", content = "添加考试用户")
	@SaCheckPermission("edu:score:user:add")
	public Msg addNewQuesScore(@RequestBody QuesScore record) {
		if(Util.isNullorEmpty(record.getPloNum()))	return Msg.error("获取用户工号失败！");
		QuesScore quesScore = quesScoreService.getUserQuesScore(record.getExamCode(), record.getPloNum());
		if(!Util.isNullorEmpty(quesScore)) return Msg.error("该记录已经存在！");	
		int count =quesScoreService.create(record);
		if(count < 1) return Msg.error();
		return Msg.success();
	}
	
	@DeleteMapping("/user/{journo}")
	@SaCheckPermission("edu:score:user:delete")
	@MyLog(title = "[edu-score]得分管理", content = "删除考试用户")
	public Msg deleteQuesScoreUser(@PathVariable ("journo") String journo) {
		int count = quesScoreService.delete(journo);
		if(count < 1) return Msg.error();
		return Msg.success();
	}

	/**
	 * 重置用户的考试状态
	 * @param record record
	 * @return Msg
	 */
	@PostMapping("/reset")
	@MyLog(title = "[edu-score]得分管理", content = "重置用户得分")
	@SaCheckPermission("edu:score:user:reset")
	public Msg resetUserExam(@RequestBody QuesScore record) {
		// 重置考试状态
		int count = quesScoreService.resetExam(record);
		// 删除试卷
		quesTestService.deleteUserTest(record.getExamCode(), record.getPloNum());
		return count<1 ? Msg.error("操作失败！"):Msg.success("操作成功！");
	}
	
	@GetMapping("/my/list")
	public Msg listMyExam(@RequestParam Map<String, String> params) {
		String userId = Util.userIdByShiro();
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		QuesScoreExample example = new QuesScoreExample();
		Criteria criteria = example.createCriteria();
		criteria.andCompStatGreaterThan(0);
		criteria.andPloNumEqualTo(userId);
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		example.setOrderByClause(" journo desc");
		List<QuesScore> list = quesScoreService.list(example);
		DataPage<QuesScore> plist = new DataPage<QuesScore>(list);
		List<QuesScore> quesScoreList =  plist.getList();
		for(QuesScore item: quesScoreList) {
			item.setExamInfo(examInfoService.getRecord(item.getExamCode()));
		}
		return Msg.success(new DataPage<QuesScore>(list));
	}
	
	@PostMapping("/pre/check")
	public Msg listMyExamTest(@RequestBody ExamInfo examInfo, HttpServletRequest request) throws ParseException {
		String userId = Util.userIdByShiro();
		Employee user = DataCache.getEmployees().get(userId);
		String ip = Util.getIpAddress(request);
		QuesScore quesScore = quesScoreService.getUserQuesScore(examInfo.getExamCode(), userId);
		if(quesScore.getCompStat() != 0) return Msg.error("您的考试已完成，或您在免考名单中！");
		// 是否可中断
		if (!Util.isNullorEmpty(quesScore.getBegDate())) {
			if(examInfo.getIfBreak() == 0 ) {
				// 如果考试已经开始则无法再继续答题
				Double score = quesTestService.getUserScore(examInfo.getExamCode());
				if(Util.isNullorEmpty(quesScore.getHandleIp())) quesScore.setHandleIp(ip);
				quesScore.setUserScore(score);
				quesScore.setCompStat(2);
				quesScoreService.update(quesScore);
				return Msg.error("本次考试不可中途退出，您已被提前交卷！");
			}else {
				if (examInfo.getExamTime() !=0) {
					// 判断预约考试时间是否超时，若超时则直接交卷
					Long begTimeUnix = Util.dateTimeToUnixTIme(quesScore.getBegDate());
					Long curTimeUnix = Long.parseLong(Util.getCurrentTimestamp());
					if( (curTimeUnix - begTimeUnix) > examInfo.getExamTime()*60) {
//						LOG.info("进入超时判定");
						Double score = quesTestService.getUserScore(examInfo.getExamCode());
//						LOG.info("获取结算得分{}！", score);
						if(Util.isNullorEmpty(quesScore.getHandleIp())) quesScore.setHandleIp(ip);
						quesScore.setUserScore(score);
						quesScore.setCompStat(2);
//						LOG.info("更新得分对象{}！", quesScore);
						quesScoreService.update(quesScore);
						return Msg.error("您的考试时间已到，您已被提前交卷！");
					}
				}
			}
		}
		// 是否在指定位置
		boolean areaFlag = true;
		if(!Util.isNullorEmpty(examInfo.getAreaCode())) {
			areaFlag = areaListService.checkArea(examInfo.getAreaCode(), ip);
			if(!areaFlag) return Msg.error("本次考试需在指定位置，请前往指定位置考试！");
		}
		return Msg.success().put("areaFlag", areaFlag).put("examScore", quesScore).put("user",user);
	}
	
	@PutMapping("/submit")
	public Msg submitExamTest(@RequestBody QuesScore record, HttpServletRequest request) {
		Double score = quesTestService.getUserScore(record.getExamCode());
		record.setUserScore(score);
		record.setCompStat(1);
		record.setEndDate(Util.currentDateTime());
		record.setHandleIp(Util.getIpAddress(request));
		int count = quesScoreService.update(record);
		if(count < 1) return Msg.error();
		return Msg.success(record);
	}
	
	// 试卷阅卷
	@GetMapping("/read/{examCode}")
	@MyLog(title = "[edu-score]得分管理", content = "考试阅卷")
	@SaCheckPermission("edu:score:read")
	public Msg readExam(@PathVariable ("examCode") String examCode) {
		quesScoreService.updateUserScore(examCode);
		quesScoreService.updateCompleteStatus(examCode);
		ExamInfo examInfo = new ExamInfo();
		examInfo.setExamCode(examCode);
		examInfo.setIfRead(1);
		examInfoService.update(examInfo);
		return Msg.success();
	}

		//		报表模块
	@GetMapping("/report/hum/{examCode}")
	@MyLog(title = "[edu-score]得分管理", content = "报表下载")
	@SaCheckPermission("edu:score:report:download")
	public void reportExamScoreHum(HttpServletResponse response,@PathVariable ("examCode") String examCode) throws IOException {
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");
		List<QuesScoreHum> list = quesScoreService.reportExamScoreHum(examCode);
		String fileName = "个人考试得分明细";
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
		HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtils.simpleExcelTemplateStyle();
		// 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
		// 如果这里想使用03 则 传入excelType参数即可
		EasyExcel.write(response.getOutputStream(), QuesScoreHum.class).registerWriteHandler(horizontalCellStyleStrategy)
				.sheet("个人考试得分明细").doWrite(list);
	}
}
