package com.cmbccd.ulms.oht.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.ExcelUtils;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.oht.domain.CaseTask;
import com.cmbccd.ulms.oht.domain.CaseTaskReport;
import com.cmbccd.ulms.oht.service.CaseTaskService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/oht")
public class CaseTaskController {

	@Resource
	private CaseTaskService caseTaskService;
	
	/**
	 * 根据案件ID获取任务信息
	 * @param caseId 案件ID
	 * @return 案件任务列表
	 */
	@SaCheckPermission("oht:case:task:push")
	@GetMapping( value="/task/{caseId}")
	public Msg getCaseTaskByCaseId(@PathVariable("caseId") String caseId) {
		List<CaseTask> list = caseTaskService.selectTaskByCaseId(caseId);
		return Msg.success(list);
	}
	
	/**
	 * 根据日期范围获取任务汇总信息
	 * @param params 查询参数，包括日期范围
	 * @return 案件任务汇总列表
	 */
	@GetMapping( value="/task/summary")
	@SaCheckPermission("oht:case:task:list")
	public Msg getCaseTaskByDate(@RequestParam Map<String, String> params ) {
		String dates = params.get("dates");
		String begDate,endDate;
		if (Util.isNullorEmpty(dates)) {
			begDate = Util.getDateToday();
			endDate = Util.getDateToday();
		}else {
			String [] dateAry = dates.split(",");
			begDate = dateAry[0];
			endDate = dateAry[1];
		}
		List<CaseTaskReport> list = caseTaskService.selectTsaksByDate(begDate,endDate);
		return Msg.success(list);
	}
	
	/**
	 * 案件推送操作总览报表下载
	 * @param response HTTP响应对象
	 * @param params 查询参数，包括日期范围
	 * @throws IOException 文件下载异常
	 */
	@SaCheckPermission("oht:case:task:report")
	@GetMapping(value = "/task/summary/report")
	@MyLog(title = "[oht-case]推送管理", content = "报表下载")
	public void caseExcelDownload(HttpServletResponse response, @RequestParam Map<String, String> params) throws IOException {
		String dates = params.get("dates");
		String begDate,endDate;
		if (Util.isNullorEmpty(dates)) {
			begDate=Util.getDateToday();
			endDate=Util.getDateToday();
		}else {
			String [] dateAry = dates.split(",");
			begDate=dateAry[0];
			endDate=dateAry[1];
		}		
		response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        List<CaseTaskReport> list = caseTaskService.selectTsaksByDate(begDate,endDate);
		String fileName = "案件推送操作总览";
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
		
	    HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtils.simpleExcelTemplateStyle();

        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(response.getOutputStream(), CaseTaskReport.class).registerWriteHandler(horizontalCellStyleStrategy).sheet("求助案件明细").doWrite(list);
	}
	
}
