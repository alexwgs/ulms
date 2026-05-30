package com.cmbccd.ulms.edu.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.edu.domain.AreaConfig;
import com.cmbccd.ulms.edu.domain.AreaConfigExample;
import com.cmbccd.ulms.edu.domain.AreaConfigExample.Criteria;
import com.cmbccd.ulms.edu.domain.AreaList;
import com.cmbccd.ulms.edu.domain.AreaListExample;
import com.cmbccd.ulms.edu.service.AreaConfigService;
import com.cmbccd.ulms.edu.service.AreaListService;
import com.cmbccd.ulms.sys.domain.Msg;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("edu/area")
public class ExamAreaController {

	@Resource
	private AreaConfigService areaConfigService;
	
	@Resource
	private AreaListService areaListService;
	
	@GetMapping("/config/list")
	public Msg ListConfigExamArea(@RequestParam Map<String, String> params) {
		String status = params.get("status");
		AreaConfigExample example = new AreaConfigExample();
		Criteria criteria = example.createCriteria();
		if(!Util.isNullorEmpty(status)) {
			criteria.andAreaStatEqualTo(Integer.parseInt(status));
		}
		example.setOrderByClause(" AREA_CODE DESC ");
		List<AreaConfig>list = areaConfigService.listAreaConfig(example);
		return Msg.success(list);
	}
	
	@PostMapping("/config")
	@SaCheckPermission("edu:area:config:add")
	@MyLog(title = "[edu-area]考场管理")
	public Msg CreateNewConfigRecord(@RequestBody AreaConfig record) {
		int count = areaConfigService.create(record);
		if(count < 1) return Msg.error();
		return Msg.success();
	}
	
	@PutMapping("/config")
	@SaCheckPermission("edu:area:config:update")
	@MyLog(title = "[edu-area]考场管理")
	public Msg UpdateConfigRecord(@RequestBody AreaConfig record) {
		int count = areaConfigService.update(record);
		if(count < 1) return Msg.error();
		return Msg.success();
	}
	
	@DeleteMapping("/config/{areaCode}")
	@SaCheckPermission("edu:area:config:delete")
	@MyLog(title = "[edu-area]考场管理")
	public Msg DeleteConfigRecord(@PathVariable("areaCode") String areaCode) {
		int count = areaConfigService.delete(areaCode);
		if(count < 1) return Msg.error();
		return Msg.success();
	}
	
	
	@GetMapping("/detail/list")
	public Msg ListExamAreaDetail(@RequestParam Map<String, String> params) {
		AreaListExample example = new AreaListExample();
		String areaCode = params.get("areaCode");
		com.cmbccd.ulms.edu.domain.AreaListExample.Criteria criteria = example.createCriteria();
		if(!Util.isNullorEmpty(areaCode)) criteria.andAreaCodeEqualTo(areaCode);
		example.setOrderByClause(" AREA_CODE DESC ");
		List<AreaList>list = areaListService.listAreaList(example);
		return Msg.success(list);
	}
	
	@PostMapping("/detail")
	public Msg CreateNewDetailRecord(@RequestBody AreaList record) {
		int count = areaListService.create(record);
		if(count < 1) return Msg.error();
		return Msg.success();
	}
		
	@PutMapping("/detail")
	public Msg UpdateDetailRecord(@RequestBody AreaList record) {
		int count = areaListService.update(record);
		if(count < 1) return Msg.error();
		return Msg.success();
	}
	
	@DeleteMapping("/detail/{areaCode}")
	public Msg DeleteDetailRecord(@PathVariable("areaCode") String areaCode) {
		int count = areaListService.delete(areaCode);;
		if(count < 1) return Msg.error();
		return Msg.success();
	}
}
