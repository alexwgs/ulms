package com.cmbccd.ulms.sys.controller;

import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.sys.service.UserFilesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RequestMapping("/userFiles")
@RestController
public class UserFilesController {

	@Resource
	private UserFilesService userFilesService;

	@GetMapping(value = "/list")
	@MyLog(title = "[sys-userfiles]附件管理")
	public Msg getUserFilesList(@RequestParam Map<String, String> params) {
		return Msg.success(userFilesService.getUserFilesListByQuery(params));
	}

	@GetMapping(value = "/suffix")
	public Msg getUserFilesList() {
		List<String> list = userFilesService.getFileSuffix();
		return Msg.success( list);
	}
}
