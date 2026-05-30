/**
 * 数据库表信息管理控制器
 * 提供数据库表信息的查询、同步、更新等操作接口
 */
package com.cmbccd.ulms.db.controller;

import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.db.domain.Table;
import com.cmbccd.ulms.db.service.TableService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/db/table")
public class TableController {
	
	@Resource
	private TableService tableService;
	
	/**
	 * 根据ID获取表信息
	 * @param id 表记录的唯一标识符
	 * @return 包含Table对象的成功消息
	 */
	@GetMapping("/{id}")
	public Msg getTableInfoById(@PathVariable("id") int id) {
		Table table = tableService.getTableInfoById(id);
		return Msg.success(table);
	}

	/**
	 * 按分类获取表列表
	 * @param params 请求参数，包含flag（1-已分类，0-未分类）和keyWord（关键词）
	 * @return 包含Table列表的成功消息或错误消息
	 */
	@GetMapping("/sort")
	public Msg listTables(@RequestParam Map<String, String> params) {
		String flag = params.get("flag");
		String keyWord = params.get("keyWord");
		if(Util.isNullorEmpty(flag)) {
			return Msg.error("获取数据失败！");
		}else {
			// 1-已分类 0-未分类
			List<Table> list = tableService.selectTablesSorted(flag.equals("1"),keyWord);
			return Msg.success(list);
		}
	}
	
	// 一键同步数据库中所有表及字段信息
	@GetMapping("sync")
	@MyLog(title = "[db-table]表管理")
	public Msg listTables() {
		String msg = tableService.syncTableInfo();
		return Msg.success(msg);
	}
	
	/**
	 * 更新表信息
	 * @param record 要更新的Table对象
	 * @return 成功消息
	 */
	@PostMapping("/update")
	public Msg updateTable(@RequestBody Table record) {
		tableService.updateSelective(record);
		return Msg.success();
	}
}
