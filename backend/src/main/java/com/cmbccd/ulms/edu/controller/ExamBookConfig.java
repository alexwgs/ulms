package com.cmbccd.ulms.edu.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.domain.BookConfig;
import com.cmbccd.ulms.edu.domain.BookConfigExample;
import com.cmbccd.ulms.edu.domain.BookTime;
import com.cmbccd.ulms.edu.domain.BookTimeExample;
import com.cmbccd.ulms.edu.domain.BookTimeExample.Criteria;
import com.cmbccd.ulms.edu.service.BookConfigService;
import com.cmbccd.ulms.edu.service.BookTimeService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("edu/book/config")
public class ExamBookConfig {

	@Resource
	private BookConfigService bookConfigService;
	
	@Resource
	private BookTimeService bookTimeService;
	
	@GetMapping("")
	public Msg listEduBookExamConfig(@RequestParam Map<String, String> params) {
		String status = params.get("status");
		BookConfigExample example = new BookConfigExample();
		com.cmbccd.ulms.edu.domain.BookConfigExample.Criteria criteria = example.createCriteria();
		if(!Util.isNullorEmpty(status)) {
			criteria.andBookStatEqualTo(Integer.parseInt(status));
		}
		example.setOrderByClause(" HANDLE_DATE desc");
		List<BookConfig> list = bookConfigService.listBookConfig(example);
		return Msg.success(list);
	}
	
	@PutMapping("")
	@SaCheckPermission("edu:book:config:update")
	@MyLog(title = "[edu-book]预约管理")
	public Msg editEduBookExamConfig(@RequestBody BookConfig record) {
		int count = bookConfigService.update(record);
		if(count<1) return Msg.error();
		return Msg.success();
	}
	
	@PostMapping("")
	@SaCheckPermission("edu:book:config:add")
	@MyLog(title = "[edu-book]预约管理")
	public Msg createEduBookExamConfig(@RequestBody BookConfig record) {
		int count = bookConfigService.create(record);
		if(count<1) return Msg.error();
		return Msg.success();
	}
	
	@DeleteMapping("/{bookCode}")
	@SaCheckPermission("edu:book:config:delete")
	@MyLog(title = "[edu-book]预约管理")
	public Msg deleteEduBookExamConfig(@PathVariable("bookCode") String bookCode) {
		int count = bookConfigService.delete(bookCode);
		if(count<1) return Msg.error();
		return Msg.success();
	}
	
	@GetMapping("/bookTime")
	public Msg listEduBookExamTime(@RequestParam Map<String, String> params) {
		String bookCode = params.get("bookCode");
		BookTimeExample example = new BookTimeExample();
		Criteria criteria = example.createCriteria();
		criteria.andBookCodeEqualTo(bookCode);
		example.setOrderByClause(" BEG_TIME desc");
		List<BookTime> list = bookTimeService.listBookTime(example);
		return Msg.success(list);
	}
	
	@PutMapping("/bookTime")
	public Msg editEduBookExamTime(@RequestBody BookTime record) {
		int count = bookTimeService.update(record);
		if(count<1) return Msg.error();
		return Msg.success();
	}
	
	@DeleteMapping("/bookTime/{journo}")
	public Msg deleteEduBookExamTime(@PathVariable("journo") String journo) {
		int count = bookTimeService.delete(journo);
		if(count<1) return Msg.error();
		return Msg.success();
	}
	
	@PostMapping("/bookTime")
	public Msg addEduBookExamTime(@RequestBody BookTime record) {
		int count = bookTimeService.create(record);
		if(count<1) return Msg.error();
		return Msg.success();
	}
}
