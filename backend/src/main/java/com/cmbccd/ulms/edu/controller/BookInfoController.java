package com.cmbccd.ulms.edu.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.controller.DataCache;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.domain.*;
import com.cmbccd.ulms.edu.domain.BookInfoExample.Criteria;
import com.cmbccd.ulms.edu.service.BookInfoService;
import com.cmbccd.ulms.edu.service.BookListService;
import com.cmbccd.ulms.edu.service.BookTimeService;
import com.cmbccd.ulms.sys.domain.Employee;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.sys.domain.Todo;
import com.cmbccd.ulms.sys.service.TodoService;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("edu/book")
public class BookInfoController {
	
	@Resource
	private BookInfoService bookInfoService;
	
	@Resource
	private BookListService bookListService;
	
	@Resource
	private BookTimeService bookTimeService;

	@Resource
	private TodoService todoService;

	@PostMapping("/info/list/force/book")
	@SaCheckPermission("edu:book:list:user")
	@MyLog(title = "[edu-book]预约管理", content = "强制预约")
	public Msg forceExamBookList(@RequestBody BookList record) throws NumberFormatException, ParseException {
		bookListService.setInvalid(record);
		int count = bookListService.create(record);
		return count>0?Msg.success():Msg.error();
	}

	@GetMapping("/list")
	@SaCheckPermission("edu:book:list:list")
	public Msg listBookList(@RequestParam Map<String, String> params) {
		String examCode = params.get("examCode");
		String queryType = params.get("queryType");
		String query = params.get("query");
		Map<String, Integer> pageParams = Util.innitTablePages(params);
		BookListExample example = new BookListExample();
		BookListExample.Criteria criteria = example.createCriteria();
		criteria.andExamCodeEqualTo(examCode);
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		if(!Util.isNullorEmpty(query)) {
			if("ploNum".equals(queryType)) {
				criteria.andPloNumEqualTo(query);
			}else if("ploName".equals(queryType)) {
				List<String> ploNums = DataCache.EMPLOYEE.values().stream().filter(e -> e.getPloName().indexOf(query)>-1 ).map(Employee::getPloNum).collect(Collectors.toList());
				if(ploNums.size()<1) criteria.andPloNumIsNull();
				else criteria.andPloNumIn(ploNums);
			}
		}

		if (!Util.isNullorEmpty(params.get("order"))) {
			example.setOrderByClause(Util.buildOrderByClause(params.get("order"), params.get("orderType")));
		}
		List<BookList> list = bookListService.list(example);
		return Msg.success( new DataPage<BookList>(list));
	}


	@PutMapping("/list/delete")
	@SaCheckPermission("edu:book:list:delete")
	@MyLog(title = "[edu-book]预约管理")
	public Msg deleteBookList(@RequestBody BookList record) {
		int count = bookListService.delete(record);
		if(count<1) return Msg.error();
		return Msg.success();
	}

	@PutMapping("/list")
	@MyLog(title = "[edu-book]预约管理")
	public Msg updateBookList(@RequestBody BookList record) {
		int count = bookListService.delete(record);
		if(count<1) return Msg.error();
		return Msg.success();
	}
	@GetMapping("/info/list/{examCode}")
	public Msg getExamBookTimeInfo(@PathVariable ("examCode") String examCode) {
		if(Util.isNullorEmpty(examCode)) return Msg.error("没有获取到考试编号！");
		BookInfoExample example = new BookInfoExample();
		Criteria criteria = example.createCriteria();
		criteria.andExamCodeEqualTo(examCode);
		example.setOrderByClause(" BOOK_DATE ASC");
		List<BookInfo> list = bookInfoService.listBookInfo(example);
		return Msg.success(list);
	}
	
	@PostMapping("/info")
	public Msg addNewExamBookTimeInfo(@RequestBody BookInfo record) {
		int count = bookInfoService.create(record);
		if(count<1) return Msg.error();
		return Msg.success();
	}
	
	@DeleteMapping("/info/{infoCode}")
	public Msg deleteBookInfo(@PathVariable ("infoCode") String infoCode) {
		int count = bookInfoService.delete(infoCode);
		if(count<1) return Msg.error();
		return Msg.success();
	}
	
	@GetMapping("/info/list/detail")
	public Msg getExamBookDetail(@RequestParam Map<String, String> params) {
		String examCode = params.get("examCode");
		String bookCode = params.get("bookCode");
		String infoCode = params.get("infoCode");
		String userId = params.get("userId");
		if (Util.isNullorEmpty(userId)) {
			userId = Util.userIdByShiro();
		}
		if(Util.isNullorEmpty(examCode)||Util.isNullorEmpty(bookCode)||Util.isNullorEmpty(infoCode)) return Msg.error("考试预约的相关数据没有获取到！");

		// 首先获取Booktime的list
		List<BookTime> list = bookTimeService.listBookTime(bookCode, 1);
		for(BookTime item: list) {
			List<Employee> emps = bookListService.listBookedEmployee(examCode, infoCode, item.getJourno(), 1);
			item.setUsers(emps);
		}
		BookList bookList = new BookList();
		bookList.setBookCode(bookCode);
		bookList.setExamCode(examCode);
		bookList.setInfoCode(infoCode);
		bookList.setPloNum(userId);
		// 获取我的预约记录
		List<BookList> myBooked = bookListService.listMyBookRecord(bookList);
		return Msg.success(list).put("myBooked", myBooked);
	}

	@PostMapping("/info/list/detail")
	public Msg createExamBookList(@RequestBody BookList record) throws NumberFormatException, ParseException {
		String userId = Util.userIdByShiro();
		record.setPloNum(userId);
		record.setListStat(null);
		List<BookList> myBooked = bookListService.listMyBookRecord(record);
		if(myBooked.size() > 1) return Msg.error("您已超过预约的最大上限次数");
		else if(myBooked.size() > 0 ) bookListService.setInvalid(record);
		// 判断当前预约时间是否小于当前时间
		BookInfo bookInfo = bookInfoService.get(record.getInfoCode());
		BookTime bookTime = bookTimeService.get(record.getTimeCode());
		String begTime = bookInfo.getBookDate() + ' ' + bookTime.getBegTime();
		if(Util.isNullorEmpty(bookInfo) || Util.isNullorEmpty(bookTime)) return Msg.error("关键参数为空！");
		if(Util.dateTimeToUnixTIme(begTime) < Long.parseLong(Util.getCurrentTimestamp())) return Msg.error("您预约的时间已经过期！");
		int count = bookListService.create(record);

//		新增待办提醒
		Todo todo = new Todo(bookInfo.getBookDate(), userId, "月考提醒", "您预约的考试将在【"+begTime+"】开始，请注意时间，准时参加考试！PS:如出现改约，此条提醒不会被覆盖！","重要,紧急",1,bookTime.getBegTime(),0);
		todoService.insertNewTodo(todo);
		if(count<1) return Msg.error();
		return Msg.success();
	}
}
