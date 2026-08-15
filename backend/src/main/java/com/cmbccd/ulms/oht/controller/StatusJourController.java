package com.cmbccd.ulms.oht.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.domain.ChartData;
import com.cmbccd.ulms.common.util.ExcelUtils;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.oht.domain.StatusJour;
import com.cmbccd.ulms.oht.domain.StatusType;
import com.cmbccd.ulms.oht.service.StatusJourService;
import com.cmbccd.ulms.oht.service.StatusTypeService;
import com.cmbccd.ulms.sys.domain.Employee;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/oht")
public class StatusJourController {

	@Resource
	private StatusJourService statusJourService;

	@Resource
	private StatusTypeService statusTypeService;

	/**
	 * 获取指定日期的接单状态流水列表
	 * @param dataDate 数据日期
	 * @return 接单状态流水列表
	 */
	@RequestMapping(value = "/statusJour/list/{dataDate}", method = RequestMethod.GET)
	public Msg getDayStatusJourList(@PathVariable("dataDate") String dataDate) {
		String userId = Util.userIdByShiro();
		if (Util.isNullorEmpty(userId)) {
			return Msg.error("没有获取账户信息！请重新登录在尝试");
		}
		List<StatusJour> list = statusJourService.selectStatusJourList(userId, dataDate);
		for (StatusJour item : list) {
			Map<String, String> statusType = statusTypeService.getUserStatusAndOhtFlag(item.getStatusId());
			item.setStatusName(
					statusType.get("level1") + "-" + statusType.get("level2") + "-" + statusType.get("level3"));
		}
		return Msg.success(list);
	}

	/**
	 * 获取状态管理图表视图
	 * @param params 查询参数，包括用户列表、开始日期和结束日期
	 * @return 状态管理图表数据
	 */
	@RequestMapping(value = "/statusJour/chart", method = RequestMethod.GET)
	public Msg getStatusManageView(@RequestParam Map<String, String> params) {
		String[] userList = null;
		String begDate = "", endDate = "";
		if (Util.isNullorEmpty(params.get("userList"))) {
			return Msg.error("观察人员名单不可为空！");
		}
		userList = params.get("userList").split(",");
		if (Util.isNullorEmpty(params.get("begDate"))) {
			return Msg.error("开始时间不可为空！");
		}
		begDate = params.get("begDate");
		if (Util.isNullorEmpty(params.get("endDate"))) {
			return Msg.error("结束时间不可为空！");
		}
		endDate = params.get("endDate");
		List<StatusType> typeList = statusTypeService.getAllStatusType();
		List<String> types = new ArrayList<String>();
		Map<Integer, String> typeMap = new HashMap<Integer, String>();
		for (StatusType type : typeList) {
			types.add(type.getStatusName());
			typeMap.put(type.getId(), type.getStatusName());
		}

		JSONArray chartSettings = new JSONArray();
		JSONObject stack = new JSONObject();

		stack.put("xxx", types);
		chartSettings.add(stack);

		ChartData chartData = new ChartData();
		types.add(0, "业务主任");
		chartData.setColumns(types);
		DecimalFormat df = new java.text.DecimalFormat("0.00");
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		for (String userId : userList) {
			Map<String, Object> row = new HashMap<String, Object>();
			List<StatusJour> jourList = statusJourService.getUserStatusTotal(userId, begDate, endDate);
			Employee user = DataCache.getEmployees().get(userId);

			if (Util.isNullorEmpty(user)) {
				continue;
			}

			row.put("业务主任", user.getPloName() + "[" + user.getGroupName() + "]");
			for (StatusJour jour : jourList) {
				Double hours = jour.getDuration() / 3600.0;
				row.put(typeMap.get(jour.getStatusId()), df.format(hours));
			}
			rows.add(row);
		}
		chartData.setRows(rows);
		return Msg.success().put("chartData", chartData).put("chartSettings", chartSettings);
	}

	/**
	 * 获取接单状态流水详情
	 * @param params 查询参数，包括用户列表和日期
	 * @return 接单状态详情数据
	 */
	@RequestMapping(value = "/statusJour/detail", method = RequestMethod.GET)
	public Msg getStatusJourDetail(@RequestParam Map<String, String> params) {
		if (Util.isNullorEmpty(params.get("userList"))) {
			return Msg.error("观察人员名单不可为空！");
		}
		String[] userIds = params.get("userList").split(",");
		if (Util.isNullorEmpty(params.get("dataDate"))) {
			return Msg.error("查询日期不可为空！");
		}
		String dataDate = params.get("dataDate");
		List<Map<String, String>> data = statusJourService.getStatusDetailByUserIds(userIds, dataDate);
		return Msg.success(data);
	}

	/**
	 * 下载接单状态流水汇总报表
	 * @param begDate 开始日期
	 * @param endDate 结束日期
	 * @param response HTTP响应对象
	 * @throws IOException 文件下载异常
	 */
	@GetMapping(value = "/statusJour/download/total/{begDate}/{endDate}")
	@MyLog(title = "[oht-status]主任状态管理", content = "报表下载")
	public void downloadStatusJourTotal(@PathVariable("begDate") String begDate,
			@PathVariable("endDate") String endDate, HttpServletResponse response) throws IOException {
		List<Employee> emps = new ArrayList<Employee>(DataCache.getEmployees().values());
		List<Employee> empList = emps.stream()
				.filter(e -> e.getJobLevel().equals("101") && e.getDeptGroup() != null && e.getPloStatus().equals("00"))
				.sorted(Comparator.comparing(Employee::getDeptGroup)).collect(Collectors.toList());

		List<List<Object>> list = new ArrayList<List<Object>>();

		List<StatusType> typeList = statusTypeService.getAllStatusType();
		List<List<String>> header = new ArrayList<List<String>>();
		List<Integer> ids = new ArrayList<Integer>();
		List<String> head1 = new ArrayList<String>();
		List<String> head2 = new ArrayList<String>();
		List<String> head3 = new ArrayList<String>();
		List<String> head4 = new ArrayList<String>();
		List<String> head5 = new ArrayList<String>();
		head1.add("时间");
		head2.add("科室");
		head3.add("工号");
		head4.add("姓名");
		head5.add("可接单时长");
		header.add(head1);
		header.add(head2);
		header.add(head3);
		header.add(head4);
		header.add(head5);
		for (StatusType type : typeList) {
			List<String> head = new ArrayList<String>();
			head.add(type.getStatusName());
			ids.add(type.getId());
			header.add(head);
		}

		for (Employee user : empList) {
			List<Object> row = new ArrayList<Object>();
			row.add(begDate + "~" + endDate);
			row.add(user.getGroupName());
			row.add(user.getPloNum());
			row.add(user.getPloName());
			row.add(Math
					.round(statusJourService.getAvailableStatusTimeByUserId(user.getPloNum(), begDate, endDate) / 36.0)
					/ 100.0);
			for (Integer id : ids) {
				Double value = Math.round(
						statusJourService.getUserStatusIdTotal(user.getPloNum(), begDate, endDate, id) / 36.0) / 100.0;
				row.add(value);
			}
			list.add(row);
		}

		HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtils.simpleExcelTemplateStyle();
		String fileName = "业务主任状态汇总";
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
		response.setCharacterEncoding("UTF-8");
		EasyExcel.write(response.getOutputStream()).registerWriteHandler(horizontalCellStyleStrategy).head(header)
				.sheet("数据").doWrite(list);
	}

	/**
	 * 下载接单状态流水明细报表
	 * @param params 查询参数，包括用户列表和日期
	 * @param response HTTP响应对象
	 * @throws IOException 文件下载异常
	 */
	@RequestMapping(value = "/statusJour/download/detail", method = RequestMethod.GET)
	@MyLog(title = "[oht-status]主任状态管理", content = "报表下载")
	public void downloadStatusJourDetail(@RequestParam Map<String, String> params, HttpServletResponse response)
			throws IOException {
		if (Util.isNullorEmpty(params.get("userList"))) {
			return;
		}
		String[] userIds = params.get("userList").split(",");
		if (Util.isNullorEmpty(params.get("dataDate"))) {
			return;
		}
		String dataDate = params.get("dataDate");

		String fileName = "业务主任状态明细";
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");
		response.setCharacterEncoding("UTF-8");
		ExcelWriter excelWriter = null;
		HorizontalCellStyleStrategy horizontalCellStyleStrategy = ExcelUtils.simpleExcelTemplateStyle();
		try {
			excelWriter = EasyExcel.write(response.getOutputStream()).build();
			for (int i = 0; i < userIds.length; i++) {
				Employee user = DataCache.getEmployees().get(userIds[i]);

				List<Map<String, String>> data = statusJourService.getStatusDetailByUserId(userIds[i], dataDate);
				List<List<String>> dataList = new ArrayList<List<String>>();
				for (Map<String, String> item : data) {
					List<String> list = new ArrayList<String>();
					list.add(item.get("begTime"));
					list.add(item.get("endTime"));
					list.add(item.get("userId"));
					list.add(item.get("level1"));
					list.add(item.get("level2"));
					list.add(item.get("level3"));
					list.add(item.get("memo"));
					list.add(item.get("identity"));
					dataList.add(list);
				}

				WriteSheet mainSheet = EasyExcel.writerSheet(i, user.getPloName())
						.registerWriteHandler(horizontalCellStyleStrategy).head(statusJourDetailHeader()).build();

				excelWriter.write(dataList, mainSheet);
			}
		} finally {
			if (excelWriter != null) {
				excelWriter.finish();
			}
		}
	}

	/**
	 * 业务主任明细报表头
	 * 
	 * @param
	 * @return
	 */
	private List<List<String>> statusJourDetailHeader() {
		List<List<String>> head = new ArrayList<List<String>>();
		List<String> head0 = new ArrayList<>();
		head0.add("开始时间");
		List<String> head1 = new ArrayList<>();
		head1.add("结束时间");
		List<String> head2 = new ArrayList<>();
		head2.add("工号");
		List<String> head3 = new ArrayList<>();
		head3.add("一级状态");
		List<String> head4 = new ArrayList<>();
		head4.add("二级状态");
		List<String> head5 = new ArrayList<>();
		head5.add("三级状态");
		List<String> head6 = new ArrayList<>();
		head6.add("备注");
		List<String> head7 = new ArrayList<>();
		head7.add("身份");
		head.add(head0);
		head.add(head1);
		head.add(head2);
		head.add(head3);
		head.add(head4);
		head.add(head5);
		head.add(head6);
		head.add(head7);
		return head;
	}

}
