package com.cmbccd.ulms.edu.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.domain.QuesLib;
import com.cmbccd.ulms.edu.service.QuesLibService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("edu/quesLib")
public class QuesLibController {

	@Resource
	private QuesLibService quesLibService;
	
	@GetMapping("/{status}")
	public Msg getQuesLibTree(@PathVariable ("status") Integer status) {
		if(Util.isNullorEmpty(status)) return Msg.error("参数获取失败！");
		List<QuesLib> tree = quesLibService.quesTree(status);
		return Msg.success(tree);
	}
	
	@PutMapping("")
	@SaCheckPermission("edu:queslib:update")
	@MyLog(title = "[edu-lib]题库管理")
	public Msg updateTableTree(@RequestBody QuesLib record) {
		int count = quesLibService.updateByLibCode(record);
		if(count == 0) return Msg.error();
		return Msg.success();
	}
	
	@DeleteMapping("/{quesLib}")
	@SaCheckPermission("edu:queslib:delete")
	@MyLog(title = "[edu-lib]题库管理")
	public Msg deleteTableTree(@PathVariable ("quesLib") String quesLib) {
		int count = quesLibService.deleteByLibCode(quesLib);
		if(count == 0) return Msg.error();
		return Msg.success();
	}
	
	@PostMapping("")
	@SaCheckPermission("edu:queslib:add")
	@MyLog(title = "[edu-lib]题库管理")
	public Msg addTableTree(@RequestBody QuesLib record) {
		int count = quesLibService.create(record);
		if(count == 0) return Msg.error();
		return Msg.success();
	}

}
