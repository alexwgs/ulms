package com.cmbccd.ulms.sys.controller;

import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.sys.service.ErrorLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/errorLog")
public class ErrorLogController {

	@Resource
	private ErrorLogService errorLogService;

	@GetMapping(value = "/list")
	public Msg getErrorLogList(@RequestParam Map<String, String> params) {
		return Msg.success(errorLogService.getErrorLogListByQuery(params));
	}
}
