package com.cmbccd.ulms.db.controller;

import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.db.domain.Column;
import com.cmbccd.ulms.db.service.ColumnService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/db/column")
public class ColumnController {
	
	@Resource
	private ColumnService columnService;
	/**
	 * 通过表名获取表的字段信息
	 * @param tid 表名可以是Tid或者表名称
	 * @return
	 */
	@GetMapping("/{tid}")
	public Msg getColumnsByTid(@PathVariable("tid") String tid) {
		if(Util.isNullorEmpty(tid)) return Msg.error("没有获取到表参数！");
		if (Util.isInteger(tid)) {
			List<Column> list = columnService.listColumnsByTid(Integer.parseInt(tid));
			return Msg.success(list);
		}
		return Msg.success(columnService.listColumnsByTableName(tid));
	}

	/**
	 * 更新表字段信息
	 * @param list
	 * @return
	 */
	@PostMapping("/update")
	public Msg updateColumnsByTid(@RequestBody List<Column> list) {
		for(Column record: list) {
			columnService.update(record);
		}
		return Msg.success();
	}

}
