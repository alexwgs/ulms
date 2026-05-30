package com.cmbccd.ulms.sys.controller;

import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.ErrorLog;
import com.cmbccd.ulms.sys.domain.ErrorLogExample;
import com.cmbccd.ulms.sys.domain.ErrorLogExample.Criteria;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.sys.service.ErrorLogService;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/errorLog")
public class ErrorLogController {

	@Resource
	private ErrorLogService errorLogService;

	@GetMapping(value = "/list")
	public Msg getErrorLogList(@RequestParam Map<String, String> params) {

		ErrorLogExample example = new ErrorLogExample();
		Criteria criteria = example.createCriteria();

		if (!Util.isNullorEmpty(params.get("priority"))) {
			criteria.andPriorityEqualTo(params.get("priority"));
		}
		if (!Util.isNullorEmpty(params.get("dataType"))) {
			criteria.andDataTypeEqualTo(params.get("dataType"));
		}
		if (!(Util.isNullorEmpty(params.get("begDate")) && Util.isNullorEmpty(params.get("endDate")))) {
			criteria.andDataTimeBetween(params.get("begDate") + " 00:00:00", params.get("endDate") + " 23:59:59");
		}

		if (!Util.isNullorEmpty(params.get("order"))) {
			example.setOrderByClause(Util.camel4underline(params.get("order")) + " " + params.get("orderType"));
		}
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));

		List<ErrorLog> list = errorLogService.getErrorLogListByExample(example);

		return Msg.success(new DataPage<ErrorLog>(list));
	}
}
