package com.cmbccd.ulms.sys.controller;

import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.sys.domain.UserFiles;
import com.cmbccd.ulms.sys.domain.UserFilesExample;
import com.cmbccd.ulms.sys.domain.UserFilesExample.Criteria;
import com.cmbccd.ulms.sys.service.UserFilesService;
import com.github.pagehelper.PageHelper;
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
		UserFilesExample example = new UserFilesExample();
		Criteria criteria = example.createCriteria();
		if (!Util.isNullorEmpty(params.get("fileSuffix"))) {
			criteria.andFileSuffixEqualTo(params.get("fileSuffix"));
		}
		if (!(Util.isNullorEmpty(params.get("begDate")) && Util.isNullorEmpty(params.get("endDate")))) {
			criteria.andDataDateBetween(params.get("begDate") + " 00:00:00", params.get("endDate") + " 23:59:59");
		}
		if (!Util.isNullorEmpty(params.get("fileName"))) {
			criteria.andFileNameLike("%" + params.get("fileName") + "%");
		}

		if (!Util.isNullorEmpty(params.get("order"))) {
			example.setOrderByClause(Util.camel4underline(params.get("order")) + " " + params.get("orderType"));
		}
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));

		List<UserFiles> list = userFilesService.getUserFilesList(example);

		return Msg.success(new DataPage<UserFiles>(list));
	}

	@GetMapping(value = "/suffix")
	public Msg getUserFilesList() {
		List<String> list = userFilesService.getFileSuffix();
		return Msg.success( list);
	}
}
