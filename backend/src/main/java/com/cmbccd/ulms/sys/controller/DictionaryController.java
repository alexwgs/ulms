package com.cmbccd.ulms.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Dictionary;
import com.cmbccd.ulms.sys.domain.DictionaryExample;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.sys.service.DictionaryService;
import com.github.pagehelper.PageHelper;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("dictionary/")
public class DictionaryController {

	@Resource
	private DictionaryService dictionaryService;

	@GetMapping(value = "list")
//	@SaCheckPermission("dictionary:list") 全部人均可以查看
	public Msg getDictionaryList(@RequestParam Map<String, String> params) {

		Map<String, Integer> pageParams = Util.innitTablePages(params);

		DictionaryExample example = new DictionaryExample();
		DictionaryExample.Criteria criteria = example.createCriteria();
		if (!Util.isNullorEmpty(params.get("status"))) {
			criteria.andStatusEqualTo(Short.parseShort(params.get("status")));
		}
		if (!Util.isNullorEmpty(params.get("query"))) {
			example.or().andNameLike('%' + params.get("query") + '%');
			example.or().andCodeLike('%' + params.get("query") + '%');
			example.or().andCodevalLike('%' + params.get("query") + '%');
			example.or().andDescriptionLike('%' + params.get("query") + '%');
		}
		if (!Util.isNullorEmpty(params.get("order"))) {
			example.setOrderByClause(Util.camel4underline(params.get("order")) + " " + params.get("orderType"));
		}
		PageHelper.startPage(pageParams.get("pageNum"), pageParams.get("pageSize"));
		List<Dictionary> dictionaryList = dictionaryService.getDictionaryList(example);

		return Msg.success(new DataPage<Dictionary>(dictionaryList));
	}

	@GetMapping(value = "json")
	public Msg getDictionaryJson() {
		Map<String, List<Dictionary>> dictMap = new HashMap<>();
		List<String> names = dictionaryService.getGroupByName();
		for (String name : names) {
			List<Dictionary> dicts = dictionaryService.getDictionaryByName(name);
			dictMap.put(name, dicts);
		}

		return Msg.success(dictMap);
	}

	/**
	 * 通过名称获取列表
	 * 
	 * @param name
	 * @return
	 */
	@GetMapping(value = "list/{name}")
	public Msg getDictListByName(@PathVariable("name") String name) {
		List<Dictionary> list = dictionaryService.getDictionaryByName(name);
		return Msg.success(list);
	}

	@PutMapping(value = "update")
	@SaCheckPermission("dictionary:update")
	@MyLog(title = "[sys-dict]字典管理")
	public Msg updateDictionary(@RequestBody Dictionary record) {
		int count = dictionaryService.updateDictionary(record);
		if (count == 0) {
			return Msg.error("操作失败，没有更新数据！");
		}
		return Msg.success("成功更新" + count + "条数据！");
	}

	@DeleteMapping(value = "delete/{id}")
	@SaCheckPermission("dictionary:delete")
	@MyLog(title = "[sys-dict]字典管理")
	public Msg deleteDictionary(@PathVariable Integer id) {
		int count = dictionaryService.deleteDictionaryByPrimaryKey(id);
		if (count == 0) {
			return Msg.error("操作失败，没有删除数据！");
		}
		return Msg.success("成功删除" + count + "条数据！");
	}

	@PostMapping(value = "add")
	@SaCheckPermission("dictionary:add")
	@MyLog(title = "[sys-dict]字典管理")
	public Msg addDictionary(@RequestBody Dictionary record) {
		record.setId(dictionaryService.selectNewId());
		int count = dictionaryService.addDictionary(record);
		if (count == 0) {
			return Msg.error("操作失败，没有新增数据！");
		}
		return Msg.success("成功新增" + count + "条数据！");
	}
}
