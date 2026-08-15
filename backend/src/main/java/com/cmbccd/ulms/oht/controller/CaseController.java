/**
 *
 * <p>Title: CaseController.java</p>
 * <p>Description: 案件管理</p>
 * @author WeiGenSheng
 * @date 2020年12月21日
 * @version 1.0
 */
/**
 * 求助系统案件管理控制器
 * 提供案件的查询、统计和报表导出等功能
 */
package com.cmbccd.ulms.oht.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.fastjson.JSONObject;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.config.UlmsConfig;
import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.domain.ChartData;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.ExcelUtils;
import com.cmbccd.ulms.common.util.FileUtils;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.oht.domain.*;
import com.cmbccd.ulms.oht.domain.CaseExample.Criteria;
import com.cmbccd.ulms.oht.service.CaseService;
import com.cmbccd.ulms.sys.domain.Msg;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
@RestController
@RequestMapping("oht/case")
public class CaseController {
//	private static final Logger logger = LoggerFactory.getLogger(CaseController.class);


	@Resource
	private CaseService caseService;
	
	@Resource
	private UlmsConfig ulmsConfig;
//	@Resource
//	private CaseTaskService caseTaskService;	

	/**
	 * 获取案件列表
	 * @param params 查询参数，包括日期范围、查询类型和关键词等
	 * @return 包含案件列表的分页数据
	 */
	@GetMapping(value = "list")
	@SaCheckPermission("oht:case:list")
	@MyLog(title = "[oht-case]案件管理")
	public Msg getCaseList(@RequestParam Map<String, String> params) {
		String dates = params.get("dates");
		String begDate, endDate;
		if (Util.isNullorEmpty(dates)) {
			begDate = Util.getDateToday();
			endDate = Util.getDateToday();
		} else {
			String[] dateAry = dates.split(",");
			begDate = dateAry[0];
			endDate = dateAry[1];
		}
		String querytype = params.get("queryType");
		String query = params.get("query");
		
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		CaseExample example = new CaseExample();
		if (!Util.isNullorEmpty(params.get("order"))) {
			example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
		}
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));

		Criteria criteria = example.createCriteria();
		criteria.andDataTimeBetween(begDate + " 00:00:00", endDate + " 23:59:59");

		if(!Util.isNullorEmpty(query)) {
			if("caseId".equals(querytype)) {
				criteria.andCaseIdEqualTo(query);
			}else if("buildId".equals(querytype)) {
				criteria.andBuildIdEqualTo(query);
			}else if("pickId".equals(querytype)) {
				criteria.andPickIdEqualTo(query);
			}else if("user".equals(querytype)) {
				// 前端查看，仅可查看与自己有关的数据
				Criteria criteria2 = example.createCriteria();
				criteria.andBuildIdEqualTo(Util.userIdByShiro());
				example.or(criteria2.andDataTimeBetween(begDate + " 00:00:00", endDate + " 23:59:59")
						.andPickIdEqualTo(Util.userIdByShiro()));
			}
		}
		
		List<Case> cases = caseService.getCaseList(example);
		for (Case item : cases) {
			item.setBuildUser(DataCache.EMPLOYEE.get(item.getBuildId()));
			if (Util.isNullorEmpty(item.getPickId())) {
				continue;
			}
			item.setPickUser(DataCache.EMPLOYEE.get(item.getPickId()));
		}

		return Msg.success(new DataPage<Case>(cases));
	}

	/**
	 * 按业务主任维度统计任务情况
	 * @param params 查询参数，包括日期范围
	 * @return 业务主任接单统计数据
	 */
	@SaCheckPermission("oht:case:dir:list")
	@GetMapping(value = "/dir/list")
	public Msg getTaskByDir(@RequestParam Map<String, String> params) {
		String dates = params.get("dates");
		String begTime, endTime;
		if (Util.isNullorEmpty(dates)) {
			begTime = Util.getDateToday();
			endTime = Util.getDateToday();
		} else {
			String[] dateAry = dates.split(",");
			begTime = dateAry[0];
			endTime = dateAry[1];
		}
		// 现获取时间范围内的接单人ID
		List<DirTakeCaseSumarry> list = caseService.getCaseCompleteCountByDir(begTime, endTime);
		return Msg.success( list);
	}
	
	/**
	 * 获取求助聊天记录
	 * @param params 参数，包括记录ID和月份
	 * @return 聊天记录列表
	 */
	@SaCheckPermission("oht:case:chat:record")
	@GetMapping(value = "/record")
	@MyLog(title = "[oht-case]案件管理-聊天记录")
	public Msg getOhtChatRecord(@RequestParam Map<String, String> params) {
		String id = params.get("id");
		String month = params.get("month");
		// 路径穿越防护：id/month 仅允许字母数字、下划线、连字符，杜绝 ../ 与路径分隔符
		if (Util.isNullorEmpty(id) || !id.matches("[0-9a-zA-Z_-]+")) {
			return Msg.error("非法的记录ID");
		}
		if (Util.isNullorEmpty(month) || !month.matches("[0-9a-zA-Z_-]+")) {
			return Msg.error("非法的月份参数");
		}
		String fileName = id + ".txt";
		String path = ulmsConfig.getUploadPath() + "//chatRecord//" + month + "//";
		List<JSONObject> list = FileUtils.readerChatRecordFile(path , fileName);
		return Msg.success( list);
	}
	
	/**
	 * 按部门维度统计任务情况
	 * @param params 查询参数，包括日期范围
	 * @return 部门任务统计数据
	 */
	@SaCheckPermission("oht:case:dept:list")
	@GetMapping(value = "/dept/list")
	public Msg getTaskByDept(@RequestParam Map<String, String> params) {
		String dates = params.get("dates");
		String begTime, endTime;
		if (Util.isNullorEmpty(dates)) {
			begTime = Util.getDateToday();
			endTime = Util.getDateToday();
		} else {
			String[] dateAry = dates.split(",");
			begTime = dateAry[0];
			endTime = dateAry[1];
		}
		// 现获取时间范围内的接单人ID
		List<DeptTakeCaseHour> list = caseService.getStartCaseCenterCountByDept(begTime, endTime);

		ChartData chartData = new ChartData();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		chartData.setColumns(Arrays.asList("时段", "放行时长", "放行接起率", "投诉时长", "投诉接起率"));
		for (DeptTakeCaseHour item : list) {
			double rate1 = 0.0, rate2 = 0.0;
			if (item.getType1CaseNum() == 0) {
				rate1 = 1.0;
			} else {
				rate1 = Util
						.formatDouble((double) (item.getType1Level1() + item.getType1Level2() + item.getType1Level3())
								/ (double) item.getType1CaseNum());
			}
			if (item.getType2CaseNum() == 0) {
				rate2 = 1.0;
			} else {
				rate2 = Util
						.formatDouble((double) (item.getType2Level1() + item.getType2Level2() + item.getType2Level3())
								/ (double) item.getType2CaseNum());
			}
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("时段", item.getHour());
			row.put("放行时长", item.getType1WaitTime());
			row.put("投诉时长", item.getType2WaitTime());
			row.put("放行接起率", rate1);
			row.put("投诉接起率", rate2);
			rows.add(row);
		}
		chartData.setRows(rows);
		return Msg.success(list).put("chartData", chartData);
	}

	/**
	 * 案件明细报表下载功能
	 * @param response HTTP响应对象
	 * @param params 查询参数，包括日期范围
	 * @throws IOException 文件下载异常
	 */
	@SaCheckPermission("oht:case:report")
	@GetMapping(value = "/rpt/case")
	@MyLog(title = "[oht-case]案件管理", content = "报表下载")
	public void caseExcelDownload(HttpServletResponse response, @RequestParam Map<String, String> params)
			throws IOException {
		String dates = params.get("dates");
		String begDate, endDate;
		if (Util.isNullorEmpty(dates)) {
			begDate = Util.getDateToday();
			endDate = Util.getDateToday();
		} else {
			String[] dateAry = dates.split(",");
			begDate = dateAry[0];
			endDate = dateAry[1];
		}
		CaseExample example = new CaseExample();
		Criteria criteria = example.createCriteria();
		criteria.andDataTimeBetween(begDate + " 00:00:00", endDate + " 23:59:59");

		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");
		List<CaseReport> cases = caseService.getCaseReport(example);
		String fileName = "举手系统案件明细";
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

		HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtils.simpleExcelTemplateStyle();

		// 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
		// 如果这里想使用03 则 传入excelType参数即可
		EasyExcel.write(response.getOutputStream(), CaseReport.class).registerWriteHandler(horizontalCellStyleStrategy)
				.sheet("求助案件明细").doWrite(cases);
	}

	/**
	 * 业务主任接单报表下载功能
	 * @param response HTTP响应对象
	 * @param params 查询参数，包括日期范围
	 * @throws IOException 文件下载异常
	 */
	@SaCheckPermission("oht:case:dir:report")
	@RequestMapping(value = "/rpt/dir", method = RequestMethod.GET)
	public void dirExcelDownload(HttpServletResponse response, @RequestParam Map<String, String> params)
			throws IOException {
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");

		String dates = params.get("dates");
		String begTime, endTime;
		if (Util.isNullorEmpty(dates)) {
			begTime = Util.getDateToday();
			endTime = Util.getDateToday();
		} else {
			String[] dateAry = dates.split(",");
			begTime = dateAry[0];
			endTime = dateAry[1];
		}
		// 现获取时间范围内的接单人ID
		List<DirTakeCaseSumarry> list = caseService.getCaseCompleteCountByDir(begTime, endTime);

		String fileName = "业务主任接单报表";
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

		HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtils.simpleExcelTemplateStyle();

		// 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
		// 如果这里想使用03 则 传入excelType参数即可
		EasyExcel.write(response.getOutputStream(), DirTakeCaseSumarry.class)
				.registerWriteHandler(horizontalCellStyleStrategy).sheet("业务主任接单报表").doWrite(list);
	}

	/**
	 * 部门时段接单报表下载功能
	 * @param response HTTP响应对象
	 * @param params 查询参数，包括日期范围
	 * @throws IOException 文件下载异常
	 */
	@SaCheckPermission("oht:case:dept:report")
	@RequestMapping(value = "/rpt/dept", method = RequestMethod.GET)
	public void deptExcelDownload(HttpServletResponse response, @RequestParam Map<String, String> params)
			throws IOException {
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("utf-8");

		String dates = params.get("dates");
		String begTime, endTime;
		if (Util.isNullorEmpty(dates)) {
			begTime = Util.getDateToday();
			endTime = Util.getDateToday();
		} else {
			String[] dateAry = dates.split(",");
			begTime = dateAry[0];
			endTime = dateAry[1];
		}
		// 现获取时间范围内的接单人ID
		List<DeptTakeCaseHour> list = caseService.getStartCaseCenterCountByDept(begTime, endTime);

		String fileName = "部门时段接单报表";
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

		HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtils.simpleExcelTemplateStyle();

		// 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
		// 如果这里想使用03 则 传入excelType参数即可
		EasyExcel.write(response.getOutputStream(), DeptTakeCaseHour.class)
				.registerWriteHandler(horizontalCellStyleStrategy).sheet("部门时段接单报表").doWrite(list);
	}
}
