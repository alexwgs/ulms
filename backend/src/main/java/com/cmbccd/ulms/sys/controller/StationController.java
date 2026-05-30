package com.cmbccd.ulms.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.alibaba.fastjson.JSONObject;
import com.cmbccd.ulms.common.annotation.MyLog;
import com.cmbccd.ulms.common.util.DataPage;
import com.cmbccd.ulms.common.util.Util;
import com.cmbccd.ulms.sys.domain.Msg;
import com.cmbccd.ulms.sys.domain.Station;
import com.cmbccd.ulms.sys.service.StationService;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author WeiGenSheng
 * @Time 2020年4月29日 下午3:33:00
 * @Version 1.0
 * @Description:
 */
@RestController
@CrossOrigin
@RequestMapping("/station")
public class StationController {

	@Resource
	private StationService stationService;

	@Resource
	private HttpServletRequest request;

	@RequestMapping(value = "list", method = RequestMethod.GET)
	@SaCheckPermission("station:list")
	public Msg getStationList(@RequestParam Map<String, String> params) {
		List<Station> stationList = stationService.getStationList(params);
		return Msg.success(new DataPage<Station>(stationList));
	}

	@DeleteMapping(value = "/{pcIp}")
	@SaCheckPermission("station:delete")
	@MyLog(title = "[sys-station]分机管理")
	public Msg deleteStation(@PathVariable(value = "pcIp") String pcIp) {
		if (Util.isNullorEmpty(pcIp)) {
			Msg.error("没有获取到正确的计算机IP地址！");
		}
		int count = stationService.deleteStationByIp(pcIp);
		return count==0?Msg.error("操作失败，没有删除数据！"):Msg.success("成功删除 " + count + " 条记录！");

	}

	@PutMapping(value = "")
	@SaCheckPermission("station:update")
	@MyLog(title = "[sys-station]分机管理")
	public Msg updateStation(@RequestBody Station station) {
		if (Util.isNullorEmpty(station.getPcIp()) || station.getPcIp().split("\\.").length != 4) {
			return Msg.error("没有获取到正确的IP地址！");
		}
		int count = stationService.updateStationByPcIp(station);
		return count==0?Msg.error("操作失败，没有修改数据！"):Msg.success("成功修改 " + count + " 条记录！");
	}

	@PostMapping(value = "")
	@SaCheckPermission("station:add")
	@MyLog(title = "[sys-station]分机管理")
	public Msg addStation(@RequestBody Station station) {
		if (station.getExtnNum() == null || station.getExtnNum().length() != 5) {
			return Msg.error("请正确输入分机号！");
		}
		if (Util.isNullorEmpty(station.getPcIp()) || station.getPcIp().split("\\.").length != 4) {
			return Msg.error("没有获取到正确的IP地址！");
		}
		int count = stationService.insertStation(station);
		return count==0?Msg.error("操作失败，没有新增数据！"):Msg.success("成功新增  " + count + " 条记录！");
	}

	/**
	 * 用户自行更新座位信息
	 * @param station
	 * @return
	 */
	@PostMapping(value = "update")
//	@MyLog(title = "[sys-station]分机管理")
	public Msg updateOrAddStation(@RequestBody Station station) {
		if(Util.isNullorEmpty(station)) return Msg.error("没有获取到表单信息！");
		String ip = Util.getIpAddress(request);
		if (Util.isNullorEmpty(ip)) {
			station.setPcIp("0.0.0.0");
		}
		if (Util.isNullorEmpty(station.getExtnNum()) ) {
			station.setExtnNum("");
		}
		station.setPcIp(ip);
		int count = stationService.insertOrUpdate(station);
		return count==0?Msg.error("数据更新失败！"):Msg.success("信息点信息更新成功！").put("station", station);
	}

	@PostMapping(value = "clientInfo")
//	@MyLog(title = "[sys-station]分机管理")
	public Msg updateClientInfo(@RequestBody JSONObject obj) {
		String memo = obj.toJSONString();
		String userId = Util.userIdByShiro();
		if (Util.isNullorEmpty(userId)) return Msg.error("操作失败！没有获取到你的登录信息！");
		if (memo.length() < 10) return Msg.success("数据未更新！");

		Station record = new Station();
		record.setMemo(memo);
		record.setPcIp(Util.getIpAddress(request));
		record.setPloNum(userId);
		int count = stationService.insertOrUpdate(record);
		return count==0?Msg.error("数据更新失败！"):Msg.success("信息点信息更新成功！");
	}
}
