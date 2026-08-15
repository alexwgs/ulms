package com.cmbccd.ulms.db.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.cmbccd.ulms.db.domain.TableTree;
import com.cmbccd.ulms.db.service.TableTreeService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
@RestController
@RequestMapping("/db/tree")
public class TableTreeController {

	@Resource
	private TableTreeService tableTreeService;
	
	/**
	 * 根据状态获取树形结构数据
	 * @param status 状态值，当status=-1时，返回全部列表
	 * @return 包含TableTree列表的成功消息
	 */
	@GetMapping("/{status}")
	@SaCheckPermission("db:tree:list")
	public Msg getTableTree(@PathVariable ("status") short status) {
//		当status=-1时，为全部列表
		List<TableTree> tree = tableTreeService.getTableTree(status);
		return Msg.success(tree);
	}
	
	/**
	 * 更新树形表格数据
	 * @param record 要更新的TableTree对象
	 * @return 操作结果消息
	 */
	@PutMapping("")
	@SaCheckPermission("db:tree:update")
	public Msg updateTableTree(@RequestBody TableTree record) {
		int count = tableTreeService.updateById(record);
		if(count == 0) return Msg.error("操作失败！");
		return Msg.success("操作成功！");
	}
	
	/**
	 * 删除指定ID的树形表格数据
	 * @param id 要删除的记录ID
	 * @return 操作结果消息
	 */
	@DeleteMapping("/{id}")
	@SaCheckPermission("db:tree:delete")
	public Msg deleteTableTree(@PathVariable ("id") Integer id) {
		int count = tableTreeService.deleteById(id);
		if(count == 0) return Msg.error("修改失败！");
		return Msg.success("操作成功！");
	}
	
	/**
	 * 添加新的树形表格数据
	 * @param record 要添加的TableTree对象
	 * @return 操作结果消息
	 */
	@PostMapping("")
	@SaCheckPermission("db:tree:insert")
	public Msg addTableTree(@RequestBody TableTree record) {
		int count = tableTreeService.create(record);
		if(count == 0) return Msg.error("操作失败！");
		return Msg.success("操作成功！");
	}
	
	/**
	 * 批量添加树形表格数据
	 * @param records 要批量添加的TableTree对象列表
	 * @return 成功消息
	 */
	@PostMapping("/batch")
	@SaCheckPermission("db:tree:insert")
	public Msg addTableTreeBatch(@RequestBody List<TableTree> records) {
		tableTreeService.createWithIdByBatch(records);
		return Msg.success();
	}
}
