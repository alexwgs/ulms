package com.cmbccd.ulms.edu.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.domain.BookList;
import com.cmbccd.ulms.edu.domain.BookListExample;
import com.cmbccd.ulms.edu.domain.BookListExample.Criteria;
import com.cmbccd.ulms.edu.service.BookListService;
import com.cmbccd.ulms.sys.domain.Employee;
import com.cmbccd.ulms.sys.domain.Msg;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("edu/book/list")
public class BookListController {
//
//	@Resource
//	private BookListService bookListService;
//
//	@GetMapping("")
//	@SaCheckPermission("edu:book:list:list")
//	public Msg listBookList(@RequestParam Map<String, String> params) {
//		String examCode = params.get("examCode");
//		String queryType = params.get("queryType");
//		String query = params.get("query");
//		Map<String, Integer> pageParams = Util.innitTablePages(params);
//		BookListExample example = new BookListExample();
//		Criteria criteria = example.createCriteria();
//		criteria.andExamCodeEqualTo(examCode);
//		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
//		if(!Util.isNullorEmpty(query)) {
//			if("ploNum".equals(queryType)) {
//				criteria.andPloNumEqualTo(query);
//			}else if("ploName".equals(queryType)) {
//				List<String> ploNums = DataCache.EMPLOYEE.values().stream().filter(e -> e.getPloName().indexOf(query)>-1 ).map(Employee::getPloNum).collect(Collectors.toList());
//				if(ploNums.size()<1) criteria.andPloNumIsNull();
//				else criteria.andPloNumIn(ploNums);
//			}
//		}
//
//		if (!Util.isNullorEmpty(params.get("order"))) {
//			example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
//		}
//		List<BookList> list = bookListService.list(example);
//		return Msg.success( new DataPage<BookList>(list));
//	}
//
//
//	@PutMapping("/delete")
//	@SaCheckPermission("edu:book:list:delete")
//	@MyLog(title = "[edu-book]预约管理")
//	public Msg deleteBookList(@RequestBody BookList record) {
//		int count = bookListService.delete(record);
//		if(count<1) return Msg.error();
//		return Msg.success();
//	}
//
//	@PutMapping("")
//	@MyLog(title = "[edu-book]预约管理")
//	public Msg updateBookList(@RequestBody BookList record) {
//		int count = bookListService.delete(record);
//		if(count<1) return Msg.error();
//		return Msg.success();
//	}
}
